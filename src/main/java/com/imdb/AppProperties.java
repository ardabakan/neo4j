package com.imdb;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "imdb")
public class AppProperties {

	private String folderDirectory;
	private long maxLines;

}
