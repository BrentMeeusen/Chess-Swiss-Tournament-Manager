package com.rds.stm.swiss;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PlayerTest {

	public Player[] getPlayers() {
		return new Player[] {
			new Player("Alice", 500),
			new Player("Bob", 800),
			new Player("Chris", 600),
			new Player("Delilah", 900),
			new Player("Eric", 500)
		};
	}

	public List<Player> sortPlayers(Player[] players) {
		return new ArrayList<>(List.of(players)).stream().sorted().toList();
	}

	@Test
	public void testPlayerIds() {
		Player[] players = getPlayers();
		assertEquals(players[0].getId(), players[1].getId() - 1);
	}

	@Test
	public void testSortedOrderWithoutScore() {
		Player[] players = getPlayers();
		List<Player> sorted = sortPlayers(players);
		assertEquals(players.length, sorted.size());

		for(int i = 0; i < sorted.size() - 1; i++) {
			assertTrue(sorted.get(i).getRating() <= sorted.get(i + 1).getRating());
		}
	}

	@Test
	public void testSortedOrderWithScores() {
		Player[] players = getPlayers();
		players[0].addResult(MatchResult.WIN);
		players[1].addResult(MatchResult.DRAW);
		List<Player> sorted = sortPlayers(players);

		int[] expected = { 4, 2, 3, 1, 0 };
		assertEquals(expected.length, sorted.size());

		for(int i = 0; i < expected.length; i++) {
			assertEquals(players[expected[i]], sorted.get(i));
		}

		players[3].addResult(MatchResult.WIN);
		sorted = sortPlayers(players);
		expected = new int[] {4, 2, 1, 0, 3};

		for(int i = 0; i < expected.length; i++) {
			assertEquals(players[expected[i]], sorted.get(i));
		}

	}

}
