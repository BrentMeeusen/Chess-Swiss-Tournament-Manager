package com.rds.stm.swiss;

public class Player implements Comparable<Player> {

	private static int ID = 1;

	final private int id;
	private String name;

	private final int rating;
	private float score;

	public Player(String name, int rating) {
		this.id = Player.ID++;
		this.name = name;
		this.rating = rating;
		this.score = 0;
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

	/**
	 * Order on score, on rating if equal score.
	 */
	@Override
	public int compareTo(Player o) {
		int score = Float.compare(this.score, o.score);
		return score == 0 ? Integer.compare(this.rating, o.rating) : score;
	}

	@Override
	public String toString() {
		return name + " (" + rating + ")";
	}

}
