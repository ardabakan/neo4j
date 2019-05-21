package com.imdb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating extends ImdbItem {

	public static final String TSV_PATH = "\\title.ratings.tsv\\data.tsv";

	private Long id;

	private String movieId;

	private Float rating;

	@Override
	public void readFromLine(String line) {

		String[] values = lineToValues(line, "\t");

		setMovieId(values[0]);
		setRating(Float.parseFloat(values[1]));

	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", movieId=" + movieId + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rating other = (Rating) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (movieId == null) {
			if (other.movieId != null)
				return false;
		} else if (!movieId.equals(other.movieId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((movieId == null) ? 0 : movieId.hashCode());
		return result;
	}
}