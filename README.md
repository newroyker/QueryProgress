# QueryProgress
An example of how one can create a [StreamingQueryListener](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.sql.streaming.StreamingQueryListener) that will forward all query progress information to Kafka.

# Approach:
* Add listener in Spark app: `spark.streams.addListener(new KafkaMetrics("localhost:6001"))`
* Consume metrics from Kafka afterwards: `consumeNumberStringMessagesFrom("streaming-metrics", 4)`

# Query progress information:
Output of `sbt clean test` (consumed back from Kafka):
```text
{"id":"51c46891-87e2-4db7-8a6f-3e8bc6140868","runId":"bfbe4496-d071-4de9-968a-9e3ce740fc2b","name":null,"timestamp":"2019-04-08T19:17:16.966Z","batchId":0,"numInputRows":1,"processedRowsPerSecond":0.05867511588335387,"durationMs":{"addBatch":16351,"getBatch":85,"getOffset":122,"queryPlanning":292,"triggerExecution":17043,"walCommit":103},"stateOperators":[{"numRowsTotal":1,"numRowsUpdated":1,"memoryUsedBytes":41663,"customMetrics":{"loadedMapCacheHitCount":0,"loadedMapCacheMissCount":0,"stateOnCurrentVersionSizeBytes":12863}}],"sources":[{"description":"FileStreamSource[file:/Users/debajyoti.roy/Dev/QueryProgress/src/main/resources/input]","startOffset":null,"endOffset":{"logOffset":0},"numInputRows":1,"processedRowsPerSecond":0.05867511588335387}],"sink":{"description":"org.apache.spark.sql.execution.streaming.ConsoleSinkProvider@37ffcbfb"}}
{"id":"51c46891-87e2-4db7-8a6f-3e8bc6140868","runId":"bfbe4496-d071-4de9-968a-9e3ce740fc2b","name":null,"timestamp":"2019-04-08T19:17:34.035Z","batchId":1,"numInputRows":1,"inputRowsPerSecond":0.05858574023082782,"processedRowsPerSecond":0.08598452278589853,"durationMs":{"addBatch":11268,"getBatch":60,"getOffset":121,"queryPlanning":20,"triggerExecution":11630,"walCommit":72},"stateOperators":[{"numRowsTotal":1,"numRowsUpdated":1,"memoryUsedBytes":75367,"customMetrics":{"loadedMapCacheHitCount":400,"loadedMapCacheMissCount":0,"stateOnCurrentVersionSizeBytes":17583}}],"sources":[{"description":"FileStreamSource[file:/Users/debajyoti.roy/Dev/QueryProgress/src/main/resources/input]","startOffset":{"logOffset":0},"endOffset":{"logOffset":1},"numInputRows":1,"inputRowsPerSecond":0.05858574023082782,"processedRowsPerSecond":0.08598452278589853}],"sink":{"description":"org.apache.spark.sql.execution.streaming.ConsoleSinkProvider@37ffcbfb"}}
{"id":"51c46891-87e2-4db7-8a6f-3e8bc6140868","runId":"bfbe4496-d071-4de9-968a-9e3ce740fc2b","name":null,"timestamp":"2019-04-08T19:17:45.666Z","batchId":2,"numInputRows":1,"inputRowsPerSecond":0.08597713008339782,"processedRowsPerSecond":0.10184336490477644,"durationMs":{"addBatch":9489,"getBatch":44,"getOffset":99,"queryPlanning":17,"triggerExecution":9819,"walCommit":64},"stateOperators":[{"numRowsTotal":2,"numRowsUpdated":1,"memoryUsedBytes":80199,"customMetrics":{"loadedMapCacheHitCount":800,"loadedMapCacheMissCount":0,"stateOnCurrentVersionSizeBytes":17767}}],"sources":[{"description":"FileStreamSource[file:/Users/debajyoti.roy/Dev/QueryProgress/src/main/resources/input]","startOffset":{"logOffset":1},"endOffset":{"logOffset":2},"numInputRows":1,"inputRowsPerSecond":0.08597713008339782,"processedRowsPerSecond":0.10184336490477644}],"sink":{"description":"org.apache.spark.sql.execution.streaming.ConsoleSinkProvider@37ffcbfb"}}
{"id":"51c46891-87e2-4db7-8a6f-3e8bc6140868","runId":"bfbe4496-d071-4de9-968a-9e3ce740fc2b","name":null,"timestamp":"2019-04-08T19:17:55.486Z","batchId":3,"numInputRows":1,"inputRowsPerSecond":0.10183299389002036,"processedRowsPerSecond":0.14885382554331647,"durationMs":{"addBatch":6376,"getBatch":40,"getOffset":109,"queryPlanning":15,"triggerExecution":6718,"walCommit":75},"stateOperators":[{"numRowsTotal":3,"numRowsUpdated":1,"memoryUsedBytes":80415,"customMetrics":{"loadedMapCacheHitCount":1200,"loadedMapCacheMissCount":0,"stateOnCurrentVersionSizeBytes":17951}}],"sources":[{"description":"FileStreamSource[file:/Users/debajyoti.roy/Dev/QueryProgress/src/main/resources/input]","startOffset":{"logOffset":2},"endOffset":{"logOffset":3},"numInputRows":1,"inputRowsPerSecond":0.10183299389002036,"processedRowsPerSecond":0.14885382554331647}],"sink":{"description":"org.apache.spark.sql.execution.streaming.ConsoleSinkProvider@37ffcbfb"}}
```

Formatted view of 1 message:
```json
{  
   "id":"51c46891-87e2-4db7-8a6f-3e8bc6140868",
   "runId":"bfbe4496-d071-4de9-968a-9e3ce740fc2b",
   "name":null,
   "timestamp":"2019-04-08T19:17:55.486Z",
   "batchId":3,
   "numInputRows":1,
   "inputRowsPerSecond":0.10183299389002036,
   "processedRowsPerSecond":0.14885382554331647,
   "durationMs":{  
      "addBatch":6376,
      "getBatch":40,
      "getOffset":109,
      "queryPlanning":15,
      "triggerExecution":6718,
      "walCommit":75
   },
   "stateOperators":[  
      {  
         "numRowsTotal":3,
         "numRowsUpdated":1,
         "memoryUsedBytes":80415,
         "customMetrics":{  
            "loadedMapCacheHitCount":1200,
            "loadedMapCacheMissCount":0,
            "stateOnCurrentVersionSizeBytes":17951
         }
      }
   ],
   "sources":[  
      {  
         "description":"FileStreamSource[file:/Users/debajyoti.roy/Dev/QueryProgress/src/main/resources/input]",
         "startOffset":{  
            "logOffset":2
         },
         "endOffset":{  
            "logOffset":3
         },
         "numInputRows":1,
         "inputRowsPerSecond":0.10183299389002036,
         "processedRowsPerSecond":0.14885382554331647
      }
   ],
   "sink":{  
      "description":"org.apache.spark.sql.execution.streaming.ConsoleSinkProvider@37ffcbfb"
   }
}
```
