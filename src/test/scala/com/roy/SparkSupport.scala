package com.roy

import org.apache.spark.sql.SparkSession

trait SparkSupport {

  implicit val spark: SparkSession = SparkSession
    .builder
    .master("local[*]")
    .appName("QueryProgressApp")
    .config("spark.driver.host", "localhost")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

}
