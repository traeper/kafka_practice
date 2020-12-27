package kafkaPractice.multiThread;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author traeper
 */
public class MultipleThreadConsumer {
    private static String TOPIC_NAME = "test3";
    private static String GROUP_ID = "testgroup";
    private static String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
    private static int CONSUMER_COUNT = 3; // 파티션도 3개 이상이어야 한다.
    private static List<ConsumerWorker> workers = new ArrayList<>();

    public static void main(String[] args) {
        // Graceful Shutdown(SIGTERM으로 재현)
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            ConsumerWorker worker = new ConsumerWorker(prop, TOPIC_NAME, i);
            workers.add(worker);
            executorService.execute(worker);
        }
    }

    static class ShutdownThread extends Thread {
        @Override
        public void run() {
            // Graceful Shutdown
            workers.forEach(ConsumerWorker::shutdown);
            System.out.println("Bye");
        }
    }

}