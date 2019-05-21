package com.imdb.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person extends ImdbItem {

	public static final String TSV_PATH = "\\name.basics.tsv\\data.tsv";

	private Long id;

	private String originalId;

	private String name;

	@Override
	public void readFromLine(String line) {

		String[] values = lineToValues(line, "\t");

		setOriginalId(values[0]);
		setName(values[1]);

	}

	@Relationship(type = "ACTED_IN")
	private List<Movie> movies = new ArrayList<>();

	public Person(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (originalId == null) {
			if (other.originalId != null)
				return false;
		} else if (!originalId.equals(other.originalId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((originalId == null) ? 0 : originalId.hashCode());
		return result;
	}

}