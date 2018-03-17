import kafka.serializer.{DefaultDecoder, StringDecoder}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WordCount {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(20))

    val kafkaConf = Map(
      "zookeeper.connect" -> "localhost:2181",
      "group.id" -> "test-consumer-group",
      "zookeeper.connection.timeout.ms" -> "5000"
    )

    val topics = Array("test")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaConf)
    )

    stream.map(record => (record.key, record.value)).print()
    ssc.start()
  }
}
