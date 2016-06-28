import sbt._
import Keys._
import sbtrelease.ReleasePlugin.autoImport._
import ReleaseTransformations._

// scalastyle:off public.methods.have.type


object BuildSettings {

  val buildOrganization = "me.sgrouples"
  val buildScalaVersion = "2.10.5"
  val buildName = "solr-plugin"

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := buildOrganization,
    scalaVersion := buildScalaVersion,
    sbtPlugin    := true,
    name := buildName,
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:postfixOps", "-language:implicitConversions") //, "-Ywarn-unused-import")
  ) ++ Formatting.formatSettings


  val coreSettings = Seq(
    unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(Seq(_))
  )

  val publishSettings = Seq(
    credentials += Credentials(Path.userHome / ".ivy2" / ".meweCredentials"),
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := Function.const(false),
    publishTo <<= version { v =>
      val nexus = "https://nexus.groupl.es/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("Snapshots" at nexus + "repository/maven-snapshots/")
      else
        Some("Releases" at nexus + "repository/maven-releases/")
    },
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      //ReleaseStep(action = Command.process("package", _)),
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      publishArtifacts,
      setNextVersion,
      commitNextVersion,
      pushChanges)
  )
}

