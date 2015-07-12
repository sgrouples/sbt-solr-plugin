package me.sgrouples
import java.util.concurrent.atomic.AtomicReference

import sbt._
import Keys._
object SolrPlugin extends AutoPlugin {

  private lazy val jettySolrKey = settingKey[AtomicReference[Option[Process]]]("solr process")

  object autoImport {
    lazy val solrStart = taskKey[Process]("start Solr")

    lazy val solrStop = taskKey[Unit]("stop Solr")

    lazy val solrCollectJars = taskKey[Seq[(File, String)]]("prepare test solr installation")

    lazy val solrCopyConfig = taskKey[Unit]("Copy solr config files to run location")

    lazy val solrPort = settingKey[Int]("http solr port")

    lazy val solrRunFolder = settingKey[File]("solr run ")

    lazy val solrContext = settingKey[String]("http context, default solr")

    //todo - make settings
    lazy val solrConfigHome = settingKey[File]("folder with solr configs to use")
  }

  import autoImport._

  override def trigger = allRequirements

  val Solr = config("solr").hide

  override val projectConfigurations = Seq(Solr)

  override lazy val projectSettings: scala.Seq[sbt.Def.Setting[_]] =
    solrSettings(Solr)

  def solrSettings(conf: Configuration) = {
    Seq(libraryDependencies ++= Seq(
      "org.apache.solr" % "solr-solrj" % "5.2.1" % "solr",
      "org.apache.solr" % "solr-core" % "5.2.1" % "solr",
      "org.slf4j" % "jcl-over-slf4j" % "1.7.12" % "solr",
      "org.slf4j" % "slf4j-simple" % "1.7.12" % "solr",
      "me.sgrouples" % "solr-starter" % "1.0.0" % "solr"
    ),
      jettySolrKey  := new AtomicReference(Option.empty[Process]),
      solrPort := 8983,
      solrRunFolder := target.value / "solr",
      solrContext := "/solr",
      solrConfigHome := (resourceDirectory in Compile).value / "solr"
    ) ++
    inConfig(conf)(Seq(
      solrStart := (startTask dependsOn (solrCollectJars, solrCopyConfig)).value,
      solrStop := stopTask.value,
      solrCollectJars := collectJars.value,
      solrCopyConfig := copyConfig.value
    ))
  }

  val forkOptions = new ForkOptions

  private def collectJars = Def.task {
    val conf = configuration.value
    val cpLibs = Classpaths.managedJars(conf, classpathTypes.value, update.value)
    val solrLibFolder = solrRunFolder.value / "lib"
    for {
      cpItem <- cpLibs
      file    = cpItem.data
      if !file.isDirectory
      name    = file.getName
      if name.endsWith(".jar")
    } yield IO.copyFile(file, solrLibFolder / name)
    (solrLibFolder ** "*") pair (relativeTo(solrLibFolder) | flat)
  }

  private def copyConfig = Def.task {
    val solrConfig = IO.copyDirectory( solrConfigHome.value , solrRunFolder.value, true )
  }

  private def shutdownSolr(p: Option[Process]) = p.map(_.destroy())

  private def startTask = Def.task {
    val processStore = jettySolrKey.value
    shutdownSolr(processStore.get())
    processStore.set(None)
    val conf = configuration.value
    val libs = collectJars.value.map(_._1)
    val cp = Path.makeString(libs)
    val args = Seq("me.sgrouples.SolrStarter", solrRunFolder.value.toString, solrPort.value.toString, solrContext.value)
    val p = new Fork("java", None).fork(forkOptions, Seq("-cp", cp) ++ args)
    processStore.set(Some(p))
    p
  }

  private def stopTask: Def.Initialize[Task[Unit]]  = Def.task {
    val processStore = jettySolrKey.value
    shutdownSolr(processStore.get())
    processStore.set(None)
  }

}