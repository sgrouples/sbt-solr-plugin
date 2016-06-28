resolvers += Classpaths.typesafeReleases

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0" excludeAll(ExclusionRule(organization = "com.danieltrinh")))

addSbtPlugin("com.github.gseitz" %% "sbt-release" % "1.0.3")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")
