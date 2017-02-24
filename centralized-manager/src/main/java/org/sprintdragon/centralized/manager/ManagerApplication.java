package org.sprintdragon.centralized.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by wangdi on 16-11-30.
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@ImportResource({"classpath:/META-INF.spring/spring-config.xml"})
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}