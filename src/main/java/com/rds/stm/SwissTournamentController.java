package com.rds.stm;

import com.rds.stm.swiss.Player;
import com.rds.stm.swiss.Tournament;
import java.text.ParseException;
import java.util.InputMismatchException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SwissTournamentController {

	private Tournament tournament = new Tournament();

	@FXML
	private TextField playerName;

	@FXML
	private TextField playerRating;

	@FXML
	private ListView<Player> playerList;

	/**
	 * Accessed from "Add player" button.
	 */
	public void addPlayer() {
		try {
			Player player = new Player(playerName.getText(), Integer.parseInt(playerRating.getText()));
			tournament.addPlayer(player);
		}
		catch(InputMismatchException | NumberFormatException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.show();
			return;
		}
		showPlayers();
	}

	/**
	 * Accessed from keyboard press.
	 */
	public void removePlayer(KeyEvent e) {
		System.out.println("Pressed " + e.getCode());
		if(e.getCode() == KeyCode.DELETE) {
			Player selected = playerList.getSelectionModel().getSelectedItem();
			tournament.removePlayer(selected);
			showPlayers();
		}
	}

	/**
	 * Accessed from the menu bar.
	 */
	public void createNewTournament() {
		tournament = new Tournament();
		showPlayers();
	}

	private void showPlayers() {
		playerList.getItems().clear();
		for(Player player : tournament.getPlayers()) {
			playerList.getItems().add(player);
		}
	}

}