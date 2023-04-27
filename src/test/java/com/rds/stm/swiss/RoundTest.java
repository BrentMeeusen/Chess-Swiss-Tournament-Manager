package com.rds.stm.swiss;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class RoundTest {

	public Player[] getPlayers() {
		return new Player[] {
			new Player("Alice", 500),
			new Player("Bob", 800),
			new Player("Chris", 600),
			new Player("Delilah", 900)
//			new Player("Eric", 500)
		};
	}

	@Test
	public void testFirstRoundMatchmaking() {
		Player[] players = getPlayers();
		ArrayList<Player> playerArrayList = new ArrayList<>(List.of(players));
		Round round = new Round(1, playerArrayList);
		ArrayList<Match> matches = round.getMatches();

		assertEquals(2, matches.size());
		assertEquals(new Match(players[0], players[2]), matches.get(0));
		assertEquals(new Match(players[1], players[3]), matches.get(1));
	}

}