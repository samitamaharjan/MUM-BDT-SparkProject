object ProducerExample extends App {

  import java.util.Properties

  import org.apache.kafka.clients.producer._

  val  props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")

  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val TOPIC="test"

  val messages = new FileManager().getMessages()

  val it = messages.iterator()

  while(it.hasNext) {
    val msg = it.next()
    val record = new ProducerRecord(TOPIC, "key", msg)
    producer.send(record)
    Thread.sleep(200)
  }
  producer.close()
}
