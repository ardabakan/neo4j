package com.imdb;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import com.imdb.events.EventPublisher;
import com.imdb.events.PostSaveEvent;
import com.imdb.events.PreDeleteEvent;
import com.imdb.events.PreSaveEvent;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EntityScan("com.imdb.domain")
@EnableNeo4jRepositories("com.imdb.repository")
public class Application {

	@Value("${imdb.folder.directory}")
	public static String IMDB_FILES_PATH;

	private final static Logger logger = LoggerFactory
			.getLogger(Application.class);

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public EventPublisher eventPublisher() {
		return new EventPublisher();
	}

	@EventListener
	public void onPreSaveEvent(PreSaveEvent event) {
		Object entity = event.getSource();
		logger.debug("Before save of: " + entity);
	}

	@EventListener
	public void onPostSaveEvent(PostSaveEvent event) {
		Object entity = event.getSource();
		logger.debug("After save of: " + entity);
	}

	@EventListener
	public void onPreDeleteEvent(PreDeleteEvent event) {
		Object entity = event.getSource();
		logger.debug("Before delete of: " + entity);
	}
}
