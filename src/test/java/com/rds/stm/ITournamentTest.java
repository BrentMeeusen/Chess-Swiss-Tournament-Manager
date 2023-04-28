package com.rds.stm;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.stm.swiss.Match;
import com.rds.stm.swiss.MatchResult;
import com.rds.stm.swiss.Player;
import com.rds.stm.swiss.Round;
import com.rds.stm.swiss.Tournament;
import java.util.ArrayList;
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

		// Start round 1
		Round round1 = tournament.startNewRound();
		assertEquals(0, p1.getSkipped());
		assertEquals(0, p2.getSkipped());
		assertEquals(1, p1.getMatches().size());
		assertEquals(1, p2.getMatches().size());
		assertEquals(1, p1.getMatchesAgainst(p2).size());
		assertEquals(1, p2.getMatchesAgainst(p1).size());

		// 1 match should have been played this round, p1 vs p2
		ArrayList<Match> firstMatches = round1.getMatches();
		assertEquals(1, firstMatches.size());
		Match match = firstMatches.get(0);

		// Check if the matches p1 and p2 played is equal to this match, inversed or not
		assertEquals(p1.getMatches().get(0), match);
		assertEquals(p2.getMatches().get(0), match);
		assertEquals(new Match(p1, p2, 1), match);
		assertEquals(new Match(p2, p1, 1), match);

		// Suppose player 1 wins the match, player 1 should have 1pt and player 2 should have 0pt
		match.setResult(MatchResult.WIN);
		assertEquals(1f, p1.getResults().getScore());
		assertEquals(0f, p2.getResults().getScore());

		// Suppose the players draw, both players should have 0.5pt
		match.setResult(MatchResult.DRAW);
		assertEquals(0.5f, p1.getResults().getScore());
		assertEquals(0.5f, p2.getResults().getScore());

		// Suppose player 2 wins the match, player 1 should have 0pt and player 2 should have 1pt
		match.setResult(MatchResult.LOSS);
		assertEquals(0f, p1.getResults().getScore());
		assertEquals(1f, p2.getResults().getScore());

		// Start round 2, both players should have 2 matches played
		Round round2 = tournament.startNewRound();
		assertEquals(0, p1.getSkipped());
		assertEquals(0, p2.getSkipped());
		assertEquals(2, p1.getMatches().size());
		assertEquals(2, p2.getMatches().size());
		assertEquals(2, p1.getMatchesAgainst(p2).size());
		assertEquals(2, p2.getMatchesAgainst(p1).size());

		// One match was played this round too
		assertEquals(1, round2.getMatches().size());
		match = round2.getMatches().get(0);

		// Suppose player 1 wins the match, player 1 should have 1pt and player 2 should have 1pt
		match.setResult(MatchResult.WIN);
		assertEquals(1f, p1.getResults().getScore());
		assertEquals(1f, p2.getResults().getScore());

		// Suppose the players draw, player 1 should have 0.5pt and player 2 should have 1.5pt
		match.setResult(MatchResult.DRAW);
		assertEquals(0.5f, p1.getResults().getScore());
		assertEquals(1.5f, p2.getResults().getScore());

		// Suppose player 2 wins the match, player 1 should have 0pt and player 2 should have 2pt
		match.setResult(MatchResult.LOSS);
		assertEquals(0f, p1.getResults().getScore());
		assertEquals(2f, p2.getResults().getScore());

	}

	@Test
	public void simpleTournament3Players() {

		// Create tournament with 2 players
		Tournament tournament = createTournament(3);

		// Expect 3 players
		ArrayList<Player> players = tournament.getPlayers();
		assertEquals(3, players.size());

		// Play 3 rounds, set each match to a win
		for(int i = 0; i < 3; i++) {
			Round round = tournament.startNewRound();
			round.getMatches().get(0).setResult(MatchResult.WIN);
		}

		// Each player should have 1 match skipped, 2 matches played
		for(Player p : players) {
			assertEquals(1, p.getSkipped());
			assertEquals(2, p.getMatches().size());
		}

		// Each player should...
		int totalScore = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(i == j) continue;
				Match m = new Match(players.get(i), players.get(j), 1);
				m.setResult(MatchResult.WIN);

				// ...have played each other player once
				assertEquals(1, players.get(i).getMatchesAgainst(players.get(j)).size());
				assertEquals(m, players.get(i).getMatchesAgainst(players.get(j)).get(0));
			}

			// ...have a score between 0 and 2
			totalScore += players.get(i).getResults().getScore();
			assertTrue(players.get(i).getResults().getScore() >= 0);
			assertTrue(players.get(i).getResults().getScore() <= 2);

		}

		// After 3 matches, the players should have a combined total of 3
		assertEquals(3, totalScore);

	}

}
