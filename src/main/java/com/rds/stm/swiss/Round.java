package com.rds.stm.swiss;

import java.util.ArrayList;

public class Round {

	private final int round;
	private ArrayList<Player> players;
	private ArrayList<Match> matches;

	public Round(int round, ArrayList<Player> players) {
		this.round = round;
		this.players = (ArrayList<Player>) players.stream().sorted().toList();
		this.matches = findMatches();
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
		for(int i = 0; i < players.size(); i++) {
			Player p1 = players.get(i);
			for(int j = i + 1; j < players.size(); j++) {
				Player p2 = players.get(j);
				Match m = new Match(p1, p2);

				// If they haven't played against each other in this set of rounds yet, schedule the match
				if(p1.getMatchesAgainst(p2).size() == ((round - 1) / (players.size() - 1)) &&
						!matches.contains(m)) {
					matches.add(m);
				}
			}
		}
		return matches;
	}

}
