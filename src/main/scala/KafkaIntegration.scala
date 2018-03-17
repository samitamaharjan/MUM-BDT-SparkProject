import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.commons
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

object KafkaIntegration {

  def main(args : Array[String]): Unit = {
//    val conf = new SparkConf().setAppName("myapp").setMaster("local")
//    val sc = new SparkContext(conf)
//    val x = sc.parallelize(1 to 100)
//    x.collect().foreach(x => println(x))

      /*val sc = new SparkContext(conf)
      val input = sc.textFile("input")
      val count = input.flatMap(line ⇒ line.split(" ")).
        map(word ⇒ (word, 1)).
        reduceByKey(_+_)

      count.saveAsTextFile("output")
      System.out.println("OK");*/

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "group1",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val conf = new SparkConf().setMaster("local").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(1))
    val topics = Array("test")
    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    stream.map(record => (record.key, record.value))
  }
}
