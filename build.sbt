import BuildSettings._

lazy val settings = BuildSettings.buildSettings ++
  coreSettings ++
  publishSettings ++
  Seq(fork := true)

lazy val sorlPlugin = (project in file(".")).settings(settings)
