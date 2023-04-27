package com.rds.stm.swiss;

import java.io.Serializable;
import java.util.ArrayList;

public class Tournament implements Serializable {

	private ArrayList<Player> players;

	public Tournament() {
		players = new ArrayList<>();
	}

}
