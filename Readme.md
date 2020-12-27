# Kafka Practice

## Component
* Zookeeper (for Kafka)
* Kafka Producer (Kafka Image)
* Kafka Consumer

## 테스트
### 카프카 브로커 실행 
브로커는 1개 설정으로 되어 있음.(docker-compose.yml)
```
docker-compose up -d
```

### 토픽 생성 테스트 
* SimpleProducer 실행
* (kafka container) 토픽 컨슈머 실행
    ```
    kafka-console-consumer.sh --bootstrap-server {IP:Port} --topic test
    ```

### 토픽 소비 테스트
* SimpleConsumer 실행
* (kafka container) 토픽 레코드 프로듀서 실행
    ```
    kafka-console-producer.sh --bootstrap-server {IP:Port} --topic test
    ```

## Dependencies
* [wurstmeister/kafka](https://hub.docker.com/r/wurstmeister/kafka)
* [wurstmeister/zookeeper](https://hub.docker.com/r/wurstmeister/zookeeper)

