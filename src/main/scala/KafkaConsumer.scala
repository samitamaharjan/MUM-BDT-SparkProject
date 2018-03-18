import java.util.UUID

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.network.server.MessageHandler
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._

object KafkaConsumer {

  def main(args : Array[String]): Unit = {
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "1",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val conf = new SparkConf().setMaster("local[1]").setAppName("KafkaConsumer")
    val ssc = new StreamingContext(conf, Seconds(15))
    val topics = Array("test") // can listen to multiple topics
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    val messageHandler = new MessageHandler()
    stream.map(r => (r.key, r.value)).foreachRDD{(rdd: RDD[(String, String)]) =>
     /* val data = rdd.map{_._2}.collect()
      data.foreach(x => {
        val msg = new Message(x)
        messageHandler.handleMessage(msg)
      })*/
      if (rdd.count() > 0) {
        val id = UUID.randomUUID().toString;
        rdd.saveAsTextFile("hdfs://quickstart.cloudera:8020/user/cloudera/SparkProject/" + id)
      }
    }
    ssc.start()
    ssc.awaitTermination()
  }
}
