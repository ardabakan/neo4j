package com.imdb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImdbException extends Exception {

	public String code;
	public String explanation;
	
}
