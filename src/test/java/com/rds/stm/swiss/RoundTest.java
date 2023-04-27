package com.rds.stm.swiss;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RoundTest {

	public static Player[] getEvenPlayers() {
		return new Player[] {
			new Player("Alice", 500),
			new Player("Bob", 800),
			new Player("Chris", 600),
			new Player("Delilah", 900)
		};
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource
	void testFirstRoundMatchmaking(String name, Player[] players, Match[] expected) {
		Round round = new Round(1, new ArrayList<>(List.of(players)));
		ArrayList<Match> matches = round.getMatches();

		assertEquals(players.length / 2, matches.size());
		assertEquals(matches.size(), expected.length);
		for(int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], matches.get(i));
		}
	}

	static Stream<Arguments> testFirstRoundMatchmaking() {
		Player[] evenPlayers = getEvenPlayers();

		return Stream.of(

			// Even number of players
			Arguments.of("Even", evenPlayers, new Match[] {
				new Match(evenPlayers[0], evenPlayers[2]),
				new Match(evenPlayers[1], evenPlayers[3])
			}),

			// Even number of players (inverse players in match)
			Arguments.of("Even inversed", evenPlayers, new Match[] {
				new Match(evenPlayers[2], evenPlayers[0]),
				new Match(evenPlayers[3], evenPlayers[1])
			})

			// Odd number of players
//			Arguments.of("Odd", evenPlayers, new Match[] {
//				new Match(evenPlayers[0], evenPlayers[2]),
//				new Match(evenPlayers[1], evenPlayers[3])
//			})

		);
	}

}
