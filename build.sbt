name := "SparkProject"

version := "0.1"

scalaVersion := "2.11.11"

val sparkVersion = "2.2.0"

/*resolvers ++= Seq(
  "Maven Central Repository" at "http://central.maven.org/maven2/",
  "Spring Libs Repository" at "http://repo.spring.io/libs-milestone/",
  "Conjars Repository" at "http://conjars.org/repo/"
)*/

libraryDependencies ++= Seq (
  "org.apache.spark" % "spark-core_2.11" % sparkVersion,
  "org.apache.spark" % "spark-streaming_2.11" % sparkVersion,
  "org.apache.spark" % "spark-sql_2.11" % sparkVersion,
  "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % sparkVersion,
  "org.apache.spark" % "spark-hive_2.11" % sparkVersion,
  "org.plotly-scala" % "plotly-core_2.11" % "0.3.3"
)

// http://central.maven.org/maven2/org/apache/spark/spark-core_2.11/2.2.0/spark-core_2.11-2.2.0.pom
