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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
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
	private Button startNewRound;

	@FXML
	private ChoiceBox<Round> roundSelector;

	/**
	 * Accessed from "Add player" button.
	 */
	public void addPlayer() {
		try {
			Player player = new Player(playerName.getText(), Integer.parseInt(playerRating.getText()));
			tournament.addPlayer(player);
		}
		catch(InputMismatchException | NumberFormatException e) {
			error(e.getMessage()).show();
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
		showPlayers();
		addRoundsToDropdown(tournament.getRounds());
		showRoundMatches(tournament.getRounds().getLast());
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
		showPlayers();
		addRoundToDropdown(round);
		showRoundMatches(round);
	}

	private void showPlayers() {
		playerList.getItems().clear();
		for(Player player : tournament.getPlayers()) {
			playerList.getItems().add(player);
		}
	}

	private void addRoundToDropdown(Round round) {
		roundSelector.getItems().add(round);
	}

	private void addRoundsToDropdown(List<Round> rounds) {
		roundSelector.getItems().addAll(rounds);
	}

	private void showRoundMatches(Round round) {
		// TODO
	}



	private Alert error(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setContentText(message);
		return alert;
	}

}