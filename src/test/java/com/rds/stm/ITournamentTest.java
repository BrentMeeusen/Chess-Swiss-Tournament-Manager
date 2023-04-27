package com.rds.stm;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.stm.swiss.Match;
import com.rds.stm.swiss.Player;
import com.rds.stm.swiss.Round;
import com.rds.stm.swiss.Tournament;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

// I for Integration
public class ITournamentTest {

	public static Player[] getPlayers() {
		return new Player[] {
			new Player("Alice", 500),
			new Player("Bob", 800),
			new Player("Chris", 600),
			new Player("Delilah", 900),
			new Player("Eric", 1000),
			new Player("Fred", 1000)
		};
	}

	public static Tournament createTournament(int number) {
		Player[] players = getPlayers();
		Tournament tournament = new Tournament();

		int i = 0;
		while(i < number && i < players.length) tournament.addPlayer(players[i++]);
		return tournament;
	}

	@Test
	public void simpleTournament2Players() {

		// Create tournament with 2 players
		Tournament tournament = createTournament(2);

		// Expect 2 players
		ArrayList<Player> players = tournament.getPlayers();
		assertEquals(2, players.size());
		Player p1 = players.get(0);
		Player p2 = players.get(1);

		// Expect 0 skipped, 1 played total, 1 played against the 1 opponent
		Round first = tournament.startNewRound();
		assertEquals(0, p1.getSkipped());
		assertEquals(0, p2.getSkipped());
		assertEquals(1, p1.getMatches().size());
		assertEquals(1, p2.getMatches().size());
		assertEquals(1, p1.getMatchesAgainst(p2).size());
		assertEquals(1, p2.getMatchesAgainst(p1).size());

		// 1 match should have been played this round, p1 vs p2
		ArrayList<Match> firstMatches = first.getMatches();
		assertEquals(1, firstMatches.size());
		Match match = firstMatches.get(0);

		// Check if the matches p1 and p2 played is equal to this match, inversed or not
		assertEquals(p1.getMatches().get(0), match);
		assertEquals(p2.getMatches().get(0), match);
		assertEquals(new Match(p1, p2), match);
		assertEquals(new Match(p2, p1), match);

	}

}
