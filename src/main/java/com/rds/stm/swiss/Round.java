package com.rds.stm.swiss;

import java.util.ArrayList;

public class Round {

	ArrayList<Player> players;

	public Round(ArrayList<Player> players) {
		this.players = (ArrayList<Player>) players.stream().sorted().toList();
	}

}
