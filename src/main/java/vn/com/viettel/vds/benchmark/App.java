package vn.com.viettel.vds.benchmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import vn.com.viettel.vds.arch.annotation.EnableArchetype;

@SpringBootApplication(
        exclude = KafkaAutoConfiguration.class,
        scanBasePackages = {"vn.com.viettel.vds.benchmark"}
)
@EnableArchetype // Require
@EnableScheduling
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}

