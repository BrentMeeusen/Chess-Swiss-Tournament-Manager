package com.rds.stm.swiss;

public class Player {

	private static int ID = 1;

	final private int id;
	private final int rating;
	private String name;

	public Player(String name, int rating) {
		this.id = Player.ID++;
		this.name = name;
		this.rating = rating;
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

	@Override
	public String toString() {
		return name + " (" + rating + ")";
	}

}
