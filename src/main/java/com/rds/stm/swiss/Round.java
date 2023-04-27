package com.rds.stm.swiss;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Round {

	private final int round;
	private ArrayList<Player> players;
	private ArrayList<Match> matches;

	public Round(int round, ArrayList<Player> players) {
		this.round = round;
		this.players = (ArrayList<Player>) players.stream().sorted().collect(Collectors.toList());
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
		ArrayList<Player> matchedPlayers = new ArrayList<>();

		for(int i = 0; i < players.size(); i++) {
			Player p1 = players.get(i);
			if(matchedPlayers.contains(p1)) continue;

			for(int j = i + 1; j < players.size(); j++) {
				Player p2 = players.get(j);
				if(matchedPlayers.contains(p2)) continue;
				Match m = new Match(p1, p2);

				// If they haven't played against each other in this set of rounds yet, schedule the match
				if(p1.getMatchesAgainst(p2).size() == ((round - 1) / (players.size() - 1)) &&
						!matches.contains(m)) {
					matches.add(m);
					matchedPlayers.add(p1);
					matchedPlayers.add(p2);
					break;
				}
			}
		}
		return matches;
	}

}
