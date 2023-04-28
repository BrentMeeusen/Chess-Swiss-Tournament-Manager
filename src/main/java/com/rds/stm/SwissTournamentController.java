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
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

public class SwissTournamentController implements Initializable {

	private Tournament tournament = new Tournament();

	@FXML private TextField playerName;
	@FXML private TextField playerRating;
	@FXML private ListView<Player> playerList;

	@FXML private ChoiceBox<Round> roundSelector;

	@FXML private TableView<Match> activeRoundTable;
	@FXML private TableColumn<Match, Integer> boardNumberColumn;
	@FXML private TableColumn<Match, Player> p1Column;
	@FXML private TableColumn<Match, Player> p2Column;
	@FXML private TableColumn<Match, ChoiceBox<MatchResult>> resultColumn;
	private final ChoiceBox<MatchResult> resultChoice = new ChoiceBox<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Create match result ChoiceBox
		resultChoice.getItems().add(MatchResult.WIN);
		resultChoice.getItems().add(MatchResult.DRAW);
		resultChoice.getItems().add(MatchResult.LOSS);
		SimpleObjectProperty<ChoiceBox<MatchResult>> rcb = new SimpleObjectProperty<>(resultChoice);
		resultChoice.setOnAction(System.out::println);

		// Setup match table
		boardNumberColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBoard()));
		p1Column.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getP1()));
		p2Column.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getP2()));
		resultColumn.setCellValueFactory(data -> rcb);

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
		if(tournament.getRounds().size() > 0) showRoundMatches(tournament.getRounds().getLast());

	}

	/**
	 * Show the matches of the given round in the table. The user should be able to click
	 * a dropdown to select whether the match was won, drawn or lost.
	 *
	 * @param round The round to load
	 */
	private void showRoundMatches(Round round) {
		activeRoundTable.getItems().addAll(round.getMatches());
	}



	private Alert error(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setContentText(message);
		return alert;
	}

}