package com.rds.stm.swiss;

import java.util.LinkedList;
import java.util.Objects;

public class Player implements Comparable<Player> {

	private static int ID = 1;

	final private int id;
	private String name;

	private final int rating;
	private float score;
	private int skipped = 0;
	private LinkedList<Match> matches;

	public Player(String name, int rating) {
		this.id = Player.ID++;
		this.name = name;
		this.rating = rating;
		this.score = 0;
		this.matches = new LinkedList<>();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		return rating;
	}

	public LinkedList<Match> getMatches() {
		return matches;
	}

	public void skip() {
		skipped++;
	}

	public int getSkipped() {
		return skipped;
	}

	public LinkedList<Match> getMatchesAgainst(Player p) {
		// TODO
		return new LinkedList<>();
//		return null;
	}

	public void addResult(MatchResult result) {
		this.score += result.getPts();
	}

	/**
	 * Order on score, on rating if equal score.
	 */
	@Override
	public int compareTo(Player o) {
		int score = Float.compare(this.score, o.score);
		return score == 0 ? Integer.compare(this.rating, o.rating) : score;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Player player = (Player) o;
		return id == player.id && rating == player.rating &&
			Float.compare(player.score, score) == 0 && name.equals(player.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, rating, score);
	}

	@Override
	public String toString() {
		return name + " (" + rating + ")";
	}

}
