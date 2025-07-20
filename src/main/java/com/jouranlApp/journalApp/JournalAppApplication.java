package com.jouranlApp.journalApp;

import ch.qos.logback.core.Context;
import io.micrometer.observation.Observation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
//@EnableTransactionManagement
public class JournalAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(JournalAppApplication.class, args);
    }

    @Bean
     public RestTemplate restTemplate(){
        return  new  RestTemplate();
     }
}








//@Bean
//	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//		return new JpaTransactionManager(emf);
//	}