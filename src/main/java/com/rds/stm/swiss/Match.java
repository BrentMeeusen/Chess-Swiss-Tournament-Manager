package com.rds.stm.swiss;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Match implements Serializable {

	private int board;
	private Player p1, p2;
	private MatchResult result;

	public Match(Player p1, Player p2, int board) {
		this.board = board;
		this.p1 = p1;
		this.p2 = p2;
		this.result = null;
	}

	public int getBoard() {
		return board;
	}

	public Player getP1() {
		return p1;
	}

	public Player getP2() {
		return p2;
	}

	public MatchResult getResult() {
		return result;
	}

	public void setResult(MatchResult result) {
		this.result = result;
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

	@Override
	public String toString() {
		return "Match{" +
			"p1=" + p1 +
			", p2=" + p2 +
			", result=" + result +
			'}';
	}

}
