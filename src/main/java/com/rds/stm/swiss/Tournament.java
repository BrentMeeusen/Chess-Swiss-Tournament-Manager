package com.rds.stm.swiss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;

public class Tournament implements Serializable {

	private ArrayList<Player> players;
	private LinkedList<Round> rounds;

	public Tournament() {
		players = new ArrayList<>();
		rounds = new LinkedList<>();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Round startNewRound() {

		// Start a new round with the players
		Round round = new Round(rounds.size() + 1, players);

		// If uneven number of players, pick random substitute player (that's not been picked yet)
		// Match to the closest player they haven't met yet

		// Add round to list and return it
		rounds.addLast(round);
		return round;

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
