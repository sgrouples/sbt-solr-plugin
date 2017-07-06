import BuildSettings._

lazy val settings = BuildSettings.buildSettings ++
  coreSettings ++
  publishSettings ++
  Seq(fork := true)

ScriptedPlugin.scriptedSettings

scriptedBufferLog := false

scriptedLaunchOpts  += { "-Dplugin.version=" + version.value }

lazy val sorlPlugin = (project in file(".")).settings(settings)

