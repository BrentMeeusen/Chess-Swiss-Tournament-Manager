package com.rds.stm.swiss;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Player implements Comparable<Player>, Serializable {

	private static int ID = 1;

	final private int id;
	private String name;

	private final int rating;
	private int skipped = 0;
	private LinkedList<Match> matches;

	public Player(String name, int rating) {
		this.id = Player.ID++;
		this.name = name;
		this.rating = rating;
		this.matches = new LinkedList<>();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
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

//	public float getScore() {
//		float score = 0;
//		for(Match m : matches) {
//			if(m.getP1().equals(this)) score += m.getResult().getPts();
//			if(m.getP2().equals(this)) score += (1 - m.getResult().getPts());
//		}
//		return score;
//	}

	public Result getResults() {
		int wins = 0, draws = 0;
		for(Match match : matches) {
			if(match.getResult() == MatchResult.DRAW) draws++;
			if((match.getResult() == MatchResult.WIN && match.getP1().equals(this)) ||
				match.getResult() == MatchResult.LOSS && match.getP2().equals(this)) wins++;
		}
		return new Result(wins, draws, matches.size() - wins - draws);
	}

	/**
	 * Order on score, on rating if equal score.
	 */
	@Override
	public int compareTo(Player o) {
		int score = Float.compare(this.getResults().getScore(), o.getResults().getScore());
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
			Float.compare(player.getResults().getScore(), getResults().getScore()) == 0 && name.equals(player.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, rating, getResults().getScore());
	}

	@Override
	public String toString() {
		return name + " (" + rating + ")";
	}

}
