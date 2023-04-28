package com.rds.stm.swiss;

import java.io.Serializable;

public enum MatchResult implements Serializable {
	WIN(1),
	DRAW(0.5f),
	LOSS(0);

	private final float pts;
	MatchResult(float pts) {
		this.pts = pts;
	}

	public float getPts() {
		return pts;
	}
}
