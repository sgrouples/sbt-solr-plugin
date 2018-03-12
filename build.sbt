import BuildSettings._


lazy val settings = BuildSettings.buildSettings ++
  Seq(
  scalaVersion := "2.12.4",
  sbtVersion in Global := "0.13.16",
  crossSbtVersions := Vector("0.13.16", "1.0.0"),
  scalaCompilerBridgeSource := {
    val sv = appConfiguration.value.provider.id.version
    ("org.scala-sbt" % "compiler-interface" % sv % "component").sources
  }
  ) ++
  coreSettings ++
  publishSettings ++
  Seq(fork := true)

ScriptedPlugin.scriptedSettings

scriptedBufferLog := false

scriptedLaunchOpts  += { "-Dplugin.version=" + version.value }

lazy val sorlPlugin = (project in file(".")).settings(settings)

