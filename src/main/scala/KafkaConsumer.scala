import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._
import org.apache.spark.sql.hive.HiveContext

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
    conf.set("spark.driver.allowMultipleContexts", "true")
    //conf.set("hive.metastore.warehouse.dir", "/user/hive/warehouse")
    conf.set("hive.metastore.uris", "thrift://127.0.0.1:9083")
    val ssc = new StreamingContext(conf, Seconds(15))
    val ss = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()
    val sc = ss.sparkContext

    val topics = Array("test") // can listen to multiple topics
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    //val messageHandler = new MessageHandler()
    val hiveContext = new HiveContext(sc)
    import hiveContext.implicits._

    stream.map(r => (r.key, r.value)).
      foreachRDD{(rdd: RDD[(String, String)]) => {
        if (rdd.count() > 0) {
          val movieDF = rdd. //(null,"123,123456,4,2018-01-02")
            map(_._2). //"123,123456,4,2018-01-02"
            map(x => new Message(x)). // message object
            map(msg => (msg.getMovieId(), msg.getCustomerId(), msg.getRating(), msg.getRatingDate())).
            toDF("movieId", "customerId", "rating", "ratingDate")

          movieDF.write.mode(SaveMode.Append).saveAsTable("movie_rating")
        }
      }}
    ssc.start()
    ssc.awaitTermination()
  }
}
