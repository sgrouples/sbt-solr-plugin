resolvers += Classpaths.typesafeReleases

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")

addSbtPlugin("com.github.gseitz" %% "sbt-release" % "1.0.6")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value