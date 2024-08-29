```angular2html
spring:
  cloud:
    function:
      definition: kafkaConsumer
    stream:
      kafka:
        bindings:
          producer-out-0:
            producer:
              configuration:
                key:
                  serializer: org.apache.kafka.common.serialization.StringSerializer
          kafkaConsumer-in-0:
            consumer:
              configuration:
                key:
                  deserializer: org.apache.kafka.common.serialization.StringDeserializer
        binder:
          brokers: 127.0.0.1:9092
      bindings:
        producer-out-0:
          destination: test1 # topic
          contentType: application/json
        kafkaConsumer-in-0:
          destination: test1  # topic
```