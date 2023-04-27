package com.rds.stm.swiss;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PlayerTest {

	@Test
	public void testPlayerIds() {
		Player p1 = new Player("Alice");
		Player p2 = new Player("Bob");

		assertEquals(1, p1.getId());
		assertEquals(2, p2.getId());
	}

}
