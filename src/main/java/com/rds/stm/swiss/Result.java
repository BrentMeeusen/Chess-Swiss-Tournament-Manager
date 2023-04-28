package com.rds.stm.swiss;

import java.util.LinkedList;

public class Result {

	private int wins, draws, losses;
	private float score;

	public Result(int wins, int draws, int losses) {
		this.wins = wins;
		this.draws = draws;
		this.losses = losses;
		this.score = wins + 0.5f * draws;
	}

	public int getWins() {
		return wins;
	}

	public int getDraws() {
		return draws;
	}

	public int getLosses() {
		return losses;
	}

	public float getScore() {
		return score;
	}

}
