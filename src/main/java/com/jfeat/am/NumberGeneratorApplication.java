package com.jfeat.am;

import com.jfeat.am.module.NumberGenerator.services.crud.service.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class NumberGeneratorApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NumberGeneratorApplication.class, args);
		context.registerShutdownHook();
	}

}

