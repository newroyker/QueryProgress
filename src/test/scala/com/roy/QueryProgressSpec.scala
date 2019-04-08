package com.roy

import net.manub.embeddedkafka.EmbeddedKafka
import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.types.StructType
import org.scalatest.{Matchers, WordSpec}

class QueryProgressSpec extends WordSpec with Matchers with SparkSupport with EmbeddedKafka {

  import spark.implicits._

  "runs with embedded kafka on a specific port" should {

    "work" in {

      withRunningKafka {
        spark.streams.addListener(new KafkaMetrics("localhost:6001"))

        val schema: StructType = ScalaReflection.schemaFor[Emp].dataType.asInstanceOf[StructType]

        val query = spark
          .readStream
          .option("maxFilesPerTrigger", 1) // to trickle in the data
          .schema(schema)
          .json("src/main/resources/input")
          .as[Emp]
          .groupBy(col("dept"))
          .count()
          .writeStream
          .outputMode(OutputMode.Complete())
          .format("console")
          .start()

        query.processAllAvailable()

        val messages = consumeNumberStringMessagesFrom("streaming-metrics", 4)
        messages.size shouldBe 4
        messages.foreach(println)
      }
    }
  }

}
