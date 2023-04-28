package com.rds.stm;

import com.rds.stm.swiss.Match;
import com.rds.stm.swiss.MatchResult;
import com.rds.stm.swiss.Player;
import com.rds.stm.swiss.Round;
import com.rds.stm.swiss.Tournament;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

public class SwissTournamentController implements Initializable {

	private Tournament tournament = new Tournament();

	@FXML private TextField playerName;
	@FXML private TextField playerRating;
	@FXML private ListView<Player> playerList;

	@FXML private ChoiceBox<Round> roundDropdown;

	@FXML private TableView<Match> matchesTable;
	@FXML private TableColumn<Match, Integer> boardNumberColumn;
	@FXML private TableColumn<Match, Player> p1Column;
	@FXML private TableColumn<Match, Player> p2Column;
	@FXML private TableColumn<Match, ChoiceBox<MatchResult>> resultColumn;

	/**
	 * Creates a new ChoiceBox with the MatchResult options to select what the result of the match was.
	 *
	 * @param match The corresponding match
	 * @return The ChoiceBox
	 */
	private ChoiceBox<MatchResult> getResultChoice(Match match) {

		// Create the result box
		ChoiceBox<MatchResult> resultChoice = new ChoiceBox<>();
		resultChoice.getItems().add(MatchResult.WIN);
		resultChoice.getItems().add(MatchResult.DRAW);
		resultChoice.getItems().add(MatchResult.LOSS);

		// Select result if the match already has a result
		if(match.getResult() != null) resultChoice.getSelectionModel().select(match.getResult());

		// Set the match result to the selected result when clicked
		resultChoice.setOnAction(v -> match.setResult(resultChoice.getSelectionModel().getSelectedItem()));

		// Return the object
		return resultChoice;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// When round is selected
		roundDropdown.setOnAction(event -> {
			Round r = roundDropdown.getSelectionModel().getSelectedItem();
			if(r == null) matchesTable.getItems().clear();          // If no round is selected, show no matches
			else matchesTable.getItems().setAll(r.getMatches());    // Else, show matches of the round
		});

		// Setup match table
		boardNumberColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBoard()));
		p1Column.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getP1()));
		p2Column.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getP2()));
		resultColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(getResultChoice(data.getValue())));

	}

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
			FileOutputStream location = new FileOutputStream(fc.showSaveDialog(playerName.getScene().getWindow()));
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

		// Clear dropdown
		roundDropdown.getItems().clear();

		// Add all rounds and select the last played round if any were played
		if(tournament.getRounds().size() > 0) {
			roundDropdown.getItems().setAll(tournament.getRounds());
			roundDropdown.getSelectionModel().select(tournament.getRounds().getLast());
		}

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
		roundDropdown.getItems().add(round);
		roundDropdown.getSelectionModel().select(round);
	}



	private Alert error(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setContentText(message);
		return alert;
	}

}