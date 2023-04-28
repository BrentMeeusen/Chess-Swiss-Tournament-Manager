package com.rds.stm.swiss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Round implements Serializable {

	private final int round;
	private final ArrayList<Player> players;
	private final ArrayList<Match> matches;

	public Round(int round, ArrayList<Player> players) {
		this.round = round;
		this.players = (ArrayList<Player>) players.stream().sorted().collect(Collectors.toList());
		this.matches = findMatches();
	}

	public int getRound() {
		return round;
	}

	public ArrayList<Match> getMatches() {
		return matches;
	}

	/**
	 * Finds the matches that will be played this round based on performance and ELO.
	 *
	 * @return A list of matches that will be played this round
	 */
	private ArrayList<Match> findMatches() {
		ArrayList<Match> matches = new ArrayList<>();
		ArrayList<Player> matchedPlayers = new ArrayList<>();

		// Odd number of players? Pick a random player to skip a match that hasn't skipped yet
		int cycle = players.size() % 2 == 1 ? players.size() : players.size() - 1;
		int threshold = (round - 1) / cycle;
		if(players.size() % 2 == 1) {
			List<Player> playerList = players.stream().filter(p -> p.getSkipped() == threshold).toList();
			Player skipped = playerList.get((int) (Math.random() * playerList.size()));
			skipped.skip();
			matchedPlayers.add(skipped);
		}

		for(int i = 0; i < players.size(); i++) {
			Player p1 = players.get(i);
			if(matchedPlayers.contains(p1)) continue;

			for(int j = i + 1; j < players.size(); j++) {
				Player p2 = players.get(j);
				if(matchedPlayers.contains(p2)) continue;
				Match m = new Match(p1, p2, (players.size() / 2) - (matchedPlayers.size() / 2));

				// If they haven't played against each other in this set of rounds yet, schedule the match
				if(p1.getMatchesAgainst(p2).size() == threshold && !matches.contains(m)) {
					matches.add(m);
					p1.addMatch(m);
					p2.addMatch(m);
					matchedPlayers.add(p1);
					matchedPlayers.add(p2);
					break;
				}
			}
		}
		return matches;
	}

	@Override
	public String toString() {
		return "Round " + round;
	}

}
