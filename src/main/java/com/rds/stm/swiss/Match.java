package com.rds.stm.swiss;

import java.util.Objects;

public class Match {

	private Player p1, p2;
	private MatchResult result;

	public Match(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.result = null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Match match = (Match) o;
		return ( (Objects.equals(p1, match.p1) && Objects.equals(p2, match.p2)) ||
				 (Objects.equals(p1, match.p2) && Objects.equals(p2, match.p1)) ) &&
			result == match.result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(p1, p2, result);
	}

}
