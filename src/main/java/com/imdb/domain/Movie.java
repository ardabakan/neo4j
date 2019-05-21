package com.imdb.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends ImdbItem {

	public static final String TSV_PATH = "\\title.basics.tsv\\data.tsv";

	private Long id;

	private String originalId;

	private String title;

	private Float rating;

	@JsonIgnoreProperties("movie")
	@Relationship(type = "ACTED_IN", direction = Relationship.INCOMING)
	private List<Role> roles;

	private List<String> genres;

	public Movie(String title) {
		this.title = title;
	}

	public void addRole(Role role) {
		if (this.roles == null) {
			this.roles = new ArrayList<>();
		}
		this.roles.add(role);
	}

	@Override
	public void readFromLine(String line) {

		String[] values = lineToValues(line, "\t");

		setOriginalId(values[0]);
		setTitle(values[2]);

		setGenres(Arrays.asList(lineToValues(values[8], ",")));

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (originalId == null) {
			if (other.originalId != null)
				return false;
		} else if (!originalId.equals(other.originalId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((originalId == null) ? 0 : originalId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

}