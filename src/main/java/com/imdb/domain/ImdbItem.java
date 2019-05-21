package com.imdb.domain;

import java.util.StringTokenizer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ImdbItem {

	public abstract void readFromLine(String line);

	public String[] lineToValues(String line, String delimeter) {

		StringTokenizer st = new StringTokenizer(line, delimeter);

		String[] values = new String[st.countTokens()];

		int i = 0;
		while (st.hasMoreElements()) {
			values[i++] = (String) st.nextElement();
		}

		return values;
	}

}
