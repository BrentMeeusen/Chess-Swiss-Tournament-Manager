package com.rds.stm.swiss;

import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Player implements Comparable<Player> {

	private static int ID = 1;

	final private int id;
	private String name;

	private final int rating;
//	private float score;
	private int skipped = 0;
	private LinkedList<Match> matches;

	public Player(String name, int rating) {
		this.id = Player.ID++;
		this.name = name;
		this.rating = rating;
//		this.score = 0;
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

	public void addMatch(Match m) {
		matches.add(m);
	}

	public LinkedList<Match> getMatchesAgainst(Player p) {
		return matches.stream().filter(m -> m.getP1().equals(p) || m.getP2().equals(p)).collect(
			Collectors.toCollection(LinkedList::new));
	}

	public float getScore() {
		float score = 0;
		for(Match m : matches) {
			if(m.getP1().equals(this)) score += m.getResult().getPts();
			if(m.getP2().equals(this)) score += (1 - m.getResult().getPts());
		}
		return score;
	}

//	public void addResult(MatchResult result) {
//		this.score += result.getPts();
//	}
//
//	public void addReversedResult(MatchResult result) {
//		this.score += (1 - result.getPts());
//	}

	/**
	 * Order on score, on rating if equal score.
	 */
	@Override
	public int compareTo(Player o) {
		int score = Float.compare(this.getScore(), o.getScore());
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
			Float.compare(player.getScore(), getScore()) == 0 && name.equals(player.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, rating, getScore());
	}

	@Override
	public String toString() {
		return name + " (" + rating + ")";
	}

}
