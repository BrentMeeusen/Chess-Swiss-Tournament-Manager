package com.rds.stm.swiss;

public class Player {

	private static int ID = 1;

	final private int id;
	private String name;

	public Player(String name) {
		this.id = Player.ID++;
		this.name = name;
	}

}
