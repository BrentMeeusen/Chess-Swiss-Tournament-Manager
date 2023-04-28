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

	public LinkedList<Round> getRounds() {
		return rounds;
	}

	public Round startNewRound() {

		// Check if all matches in the previous round are finished
		if(rounds.size() != 0 &&
			rounds.getLast().getMatches().stream().filter(m -> m.getResult() == null).toList().size() > 0) {
			throw new RuntimeException("All matches of the current round should be finished before starting a new round.");
		}

		if(players.size() < 2) throw new RuntimeException("You need at least 2 players to start a tournament.");

		// Start round and add it
		Round round = new Round(rounds.size() + 1, players);
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
