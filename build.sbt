name := "QueryProgress"

version := "1.0"

scalaVersion := "2.12.8"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0"

libraryDependencies += "org.apache.kafka" %% "kafka" % "2.2.0" exclude("com.fasterxml.jackson.core", "jackson-databind")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

libraryDependencies += "io.github.embeddedkafka" %% "embedded-kafka" % "2.2.0" % "test"
