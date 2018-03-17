name := "SparkProject"

version := "0.1"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq (
  "org.apache.spark" % "spark-core_2.11" % "2.2.0",
  "org.apache.spark" % "spark-sql_2.11" % "2.2.0",
  "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.2.0"
)

//resolvers ++= Seq(
// "Maven Central" at "http://central.maven.org"
//)

// http://central.maven.org/maven2/org/apache/spark/spark-core_2.11/2.2.0/spark-core_2.11-2.2.0.pom