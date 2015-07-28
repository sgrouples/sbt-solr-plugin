# Solr plugin for sbt

Allows to start solr from sbt in a separate jvm

## Usage:
1. Add solr to *project/plugins.sbt*:

```scala
addSbtPlugin("me.sgrouples" % "solr-plugin" % "0.4")
```

also add repository to *project/plugins.sbt*:
```
resolvers += "sgrouples custom builds" at "https://raw.github.com/sgrouples/mvn-repo/master/"
```


2. Enable pluing for your project

*build.sbt*:

```scala
enablePlugins(SolrPlugin)
```

3. Put configuration in *src/main/resources/solr/*


4. Start solr
```
solrStart
```

## Tasks:
`solrStart` - start solr
`solrStop` - stop solr

## Configuration

`solrPort := 8983` to run on

`solrConfigHome := src/main/resources/solr` folder where solr configuration lives
  
`solrRunFolder := target/solr` working directory where solr will run
 
`solrContext := /solr` http context
 
