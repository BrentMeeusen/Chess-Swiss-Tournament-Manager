package com.rds.stm.swiss;

import static org.junit.jupiter.api.Assertions.*;

import java.util.InputMismatchException;
import org.junit.jupiter.api.Test;

class TournamentTest {

	private Player[] getPlayers() {
		return new Player[] {
			new Player("Alice", 500),
			new Player("Bob", 600),
			new Player("Chris", 550),
			new Player("Delilah", 580)
		};
	}

	@Test
	void testAddingPlayers() {
		Player[] players = getPlayers();

		Tournament t = new Tournament();

		t.addPlayer(players[0]);
		assertEquals(1, t.getPlayers().size());

		t.addPlayer(players[1]);
		assertEquals(2, t.getPlayers().size());

		assertThrows(InputMismatchException.class, () -> {
			t.addPlayer(players[1]);
		});
	}

}