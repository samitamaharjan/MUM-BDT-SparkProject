1. Set up the environment in cloudera hadoop (CDH)
# Requirements to set up the environment:
cloudera quickstart vmware 5.12.0
scala 2.11
spark 2.2.0
jdk 1.8
kakfa 0.10

#Install scala 2.11 and sbt: https://www.youtube.com/watch?v=SFJsuo2XISs
#Upgrade jdk1.8: https://github.com/samitamaharjan/MUM-BDT-SparkProject/blob/master/InstallJdk8inCDH.pdf
#Upgrade spark 2.2.0: https://www.youtube.com/watch?v=NVr5Dc-b4C4
  # copy spark-env.sh from previous spark to new spark2.2.0 folder in order to run with master "yarn"
#Install kafka 0.10: 
  https://www.youtube.com/watch?v=Fg8cTsEk7Gc&t=107s, 
  https://kafka.apache.org/quickstart, 
  https://kafka.apache.org/downloads

2. Spark Streaming using Kafka
# Run the producer.scala file first then run the consumer.scala file. Check if streaming data is published and subscribed by Kafka in terminal 
# Execute all the commands from the directory where kafka is located: mine is /opt/kafka_2.11-1.0.1
# run the zookeeper server
bin/zookeeper-server-start.sh config/zookeeper.properties

# create topic in kafka in shell
bin/kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

# create kafka-producer 
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

# create kafka-consumer in another terminal
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test

3.SparkSQL and Hive 
The files will be stored in Hive Table in snappy compression, parquet file format. I have created some Hive table to visualize the data in Tableau using SparkSQL and Impala. Since Hive executes MapReduce jobs for most of the queries in Hive and making operation slow, I used Impala instead. I can access all tables created in Impala in Hive. That's why I created some queries and stored them to new Hive tables to visualize those analysis in Tableau. 

I have written some SparkSQL integrated with Hive in visualisation.scala. In addition, I wrote some queries in Impala to create Hive tables to visualise in tableau.

4. Tableau for visualisation
I have installed tableau in windows10. I connected tableau with cloudera hadoop using HiveServer2 and cloudera's IP address. This links all the hive tables to the tableau where can I join the tables, and play statistics analysis and plot the charts. If data changes in hive table due to streaming data into the hive table, the charts in tableau also changes. But, we need to have a lot of patience to open the file and create the charts. However, the online tableau with data in cloud is much faster than desktop tableau with cloudera hadoop.













# find hdfs url:
cloudera@quickstart samita]$ hdfs getconf -confKey fs.defaultFS
hdfs://quickstart.cloudera:8020
