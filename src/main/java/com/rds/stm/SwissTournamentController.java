package com.rds.stm;

import com.rds.stm.swiss.Player;
import com.rds.stm.swiss.Round;
import com.rds.stm.swiss.Tournament;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

public class SwissTournamentController {

	private Tournament tournament = new Tournament();

	@FXML
	private TextField playerName;

	@FXML
	private TextField playerRating;

	@FXML
	private ListView<Player> playerList;

	@FXML
	private ChoiceBox<Round> roundSelector;

	/**
	 * Accessed from "Add player" button.
	 */
	public void addPlayer() {
		try {
			Player player = new Player(playerName.getText(), Integer.parseInt(playerRating.getText()));
			tournament.addPlayer(player);
			playerList.getItems().add(player);
		}
		catch(InputMismatchException | NumberFormatException e) {
			error(e.getMessage()).show();
		}
	}

	/**
	 * Accessed from keyboard press.
	 */
	public void removePlayer(KeyEvent e) {
		if(e.getCode() == KeyCode.DELETE) {
			Player selected = playerList.getSelectionModel().getSelectedItem();
			tournament.removePlayer(selected);
			playerList.getItems().remove(selected);
		}
	}

	/**
	 * Accessed from the menu bar.
	 */
	public void createNewTournament() {
		tournament = new Tournament();
		loadTournament(tournament);
	}

	/**
	 * Accessed from the menu bar.
	 */
	public void saveTournament() {
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("Choose location");
			FileOutputStream location = new FileOutputStream(fc.showOpenDialog(playerName.getScene().getWindow()));
			ObjectOutputStream oos = new ObjectOutputStream(location);
			oos.writeObject(tournament);
			oos.close();
		}
		catch(IOException e) {
			error(e.getMessage()).show();
		}
	}

	/**
	 * Accessed from the menu bar.
	 */
	public void loadTournament() {
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("Choose file");
			FileInputStream location = new FileInputStream(fc.showOpenDialog(playerName.getScene().getWindow()));
			ObjectInputStream ois = new ObjectInputStream(location);
			tournament = (Tournament) ois.readObject();
		}
		catch(IOException | ClassNotFoundException e) {
			error(e.getMessage()).show();
		}
		loadTournament(tournament);
	}

	/**
	 * Accessed from button.
	 */
	public void startNewRound() {
		Round round;
		try {
			round = tournament.startNewRound();
		}
		catch(RuntimeException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.show();
			return;
		}
		roundSelector.getItems().add(round);
		showRoundMatches(round);
	}

	/**
	 * Sets all the frontend based on the tournament that's passed.
	 *
	 * @param tournament What values to insert in the tournament
	 */
	private void loadTournament(Tournament tournament) {

		// List the players
		playerList.getItems().clear();
		for(Player player : tournament.getPlayers()) {
			playerList.getItems().add(player);
		}

		// Add all rounds to the dropdown
		roundSelector.getItems().clear();
		roundSelector.getItems().addAll(tournament.getRounds());

		// Show the matches of the last round
		showRoundMatches(tournament.getRounds().getLast());

	}

	/**
	 * Show the matches of the given round in the table. The user should be able to click
	 * a dropdown to select whether the match was won, drawn or lost.
	 *
	 * @param round The round to load
	 */
	private void showRoundMatches(Round round) {
		// TODO
	}



	private Alert error(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setContentText(message);
		return alert;
	}

}