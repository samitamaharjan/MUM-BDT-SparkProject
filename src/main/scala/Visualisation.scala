import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._

object Visualisation {

  def main(args : Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("Visualisation")
    conf.set("spark.driver.allowMultipleContexts", "true")
    conf.set("hive.metastore.uris", "thrift://127.0.0.1:9083")
    val ss = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()
    val sc = ss.sparkContext

    val hiveContext = new HiveContext(sc)
    import hiveContext.implicits._

    val resultDF = hiveContext.sql("select rating, count(*) total_count from movie_rating group by rating")
    resultDF.write.mode(SaveMode.Overwrite).saveAsTable("rating_summary")

    val tbl1 = hiveContext.sql("select year(cast(ratingdate as timestamp)) as year, count(distinct customerId) users from movie_rating group by 1")
    tbl1.write.mode(SaveMode.Overwrite).saveAsTable("year_users")

  }
}
