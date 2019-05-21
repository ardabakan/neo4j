package com.imdb.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "ACTED_IN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends ImdbItem {

	public static final String TSV_PATH = "\\title.principals.tsv\\data.tsv";

	@Override
	public void readFromLine(String line) {

		String[] values = lineToValues(line, "\t");

		setOriginalMovieId(values[0]);
		setOriginalPersonId(values[2]);
		setCategory(values[3]);

	}

	private Long id;

	private String originalMovieId;
	private String originalPersonId;
	private String category;

	private List<String> roles = new ArrayList<>();

	@StartNode
	private Person person;

	@EndNode
	private Movie movie;

	public Role(Movie movie, Person actor) {
		this.movie = movie;
		this.person = actor;
	}

	public void addRoleName(String name) {
		if (this.roles == null) {
			this.roles = new ArrayList<>();
		}
		this.roles.add(name);
	}

	@Override
	public String toString() {
		return "Role [originalMovieId=" + originalMovieId
				+ ", originalPersonId=" + originalPersonId + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (originalMovieId == null) {
			if (other.originalMovieId != null)
				return false;
		} else if (!originalMovieId.equals(other.originalMovieId))
			return false;
		if (originalPersonId == null) {
			if (other.originalPersonId != null)
				return false;
		} else if (!originalPersonId.equals(other.originalPersonId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((originalMovieId == null) ? 0 : originalMovieId.hashCode());
		result = prime * result + ((originalPersonId == null) ? 0
				: originalPersonId.hashCode());
		return result;
	}
}