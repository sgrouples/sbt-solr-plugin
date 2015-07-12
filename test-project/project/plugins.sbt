lazy val root = Project("plugins", file(".")) dependsOn(SolrPlugin)
 
// depends on the awesomeOS project
lazy val SolrPlugin = file("..").getAbsoluteFile.toURI