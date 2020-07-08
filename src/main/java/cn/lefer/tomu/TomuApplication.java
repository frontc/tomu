package cn.lefer.tomu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TomuApplication {

    public static void main(String[] args) {
        SpringApplication.run(TomuApplication.class, args);
    }

}
