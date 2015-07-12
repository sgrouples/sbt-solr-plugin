organization := "me.sgrouples"

name          := "sorl-plugin-test"

scalaVersion  := "2.10.5"

version := "1.0"

scalacOptions ++= Seq("-feature", "-deprecation")

enablePlugins(SolrPlugin)

resolvers += Resolver.mavenLocal

