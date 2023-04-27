package com.rds.stm.swiss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Tournament implements Serializable {

	private ArrayList<Player> players;

	public Tournament() {
		players = new ArrayList<>();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * Adds a player to the tournament.
	 *
	 * @param player The player to add
	 * @throws InputMismatchException If the player's name is not unique
	 */
	public void addPlayer(Player player) {
		if(players.stream().filter(p -> p.getName().equals(player.getName())).toList().size() == 0) {
			players.add(player);
		}
		else {
			throw new InputMismatchException("Duplicate names cannot exist.");
		}
	}

	/**
	 * Removes the player from the tournament if the player was found.
	 *
	 * @param player The player to remove
	 */
	public void removePlayer(Player player) {
		players.remove(player);
	}

}
