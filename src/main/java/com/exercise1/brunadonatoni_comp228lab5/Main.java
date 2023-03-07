package com.exercise1.brunadonatoni_comp228lab5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.StyleableObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main extends javafx.application.Application {

    private TextArea tArea;
    private ListView<String> lv;
    private ObservableList data = FXCollections.observableArrayList();

    Connection conn;

    @Override
    public void start(Stage primaryStage) throws IOException {

        //SQL Connection
        try {
            System.out.println("> Start Program ...");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("> Driver Loaded successfully.");
            //use when in person in Centennial
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@ 199.212.26.208:1521:SQLD", "COMP228_M22_sy_59", "password");
            //use when not in centennial
            conn =DriverManager.getConnection("jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD","COMP228_M22_sy_59","password");
            System.out.println("Database connected successfully.");
        } catch (Exception e) {
            System.out.printf("Error connecting to DB: %s%n", e);

        }


        //Interface
        BorderPane borderPane = new BorderPane();

        //create the grid pane for entries
        GridPane playerInfoPanel = new GridPane();

        //personal information style
        playerInfoPanel.setHgap(5);
        playerInfoPanel.setVgap(5);
        playerInfoPanel.setStyle(
                "-fx-padding: 10;");

        //create labels
        Label lblTittle = new Label("Player Information");
        Label lblFirstName = new Label("First Name:");
        Label lblLastName = new Label("Last Name:");
        Label lblAddress = new Label("Address:");
        Label lblProvince = new Label("Province:");
        Label lblPostalCode = new Label("Postal Code:");
        Label lblPhoneNumber = new Label("Phone Number:");

        //create text fields
        TextField txtFirstName = new TextField();
        TextField txtLastName = new TextField();
        TextField txtAddress = new TextField();
        TextField txtProvince = new TextField();
        TextField txtPostalCode = new TextField();
        TextField txtPhoneNumber = new TextField();


        playerInfoPanel.add(lblTittle, 0, 0);
        //add controls to grid pane
        playerInfoPanel.add(lblFirstName, 0, 1);
        playerInfoPanel.add(txtFirstName, 1, 1);

        playerInfoPanel.add(lblLastName, 0, 2);
        playerInfoPanel.add(txtLastName, 1, 2);

        playerInfoPanel.add(lblAddress, 0, 3);
        playerInfoPanel.add(txtAddress, 1, 3);

        playerInfoPanel.add(lblProvince, 0, 4);
        playerInfoPanel.add(txtProvince, 1, 4);

        playerInfoPanel.add(lblPostalCode, 0, 5);
        playerInfoPanel.add(txtPostalCode, 1, 5);

        playerInfoPanel.add(lblPhoneNumber, 0, 6);
        playerInfoPanel.add(txtPhoneNumber, 1, 6);


        //align buttons in grid pane
//        GridPane.setHalignment(btnDisplay, HPos.RIGHT);

        //place grid pane in the center of border pane
        borderPane.setCenter(playerInfoPanel);


        //create the toggle group to group Student major box
        GridPane gameGroup = new GridPane();

        //personal information style
        gameGroup.setHgap(5);
        gameGroup.setVgap(5);
        gameGroup.setStyle(
                "-fx-padding: 10;");

        //create labels
        Label lblUpdatePlayer = new Label("Update Player by ID:");
        Label lblGameInfo = new Label("Game Information:");
        Label lblGameTitle = new Label("Game Title:");
        Label lblGameScore = new Label("Game Score:");
        Label lblDatePlayed = new Label("Date Played:");

        //create text fields
        TextField txtUpdatePlayer = new TextField();
        TextField txtGameTitle = new TextField();
        TextField txtGameScore = new TextField();
        TextField txtDatePlayed = new TextField();

        //add controls to grid pane
        gameGroup.add(lblUpdatePlayer, 0, 1);
        gameGroup.add(txtUpdatePlayer, 1, 1);

        gameGroup.add(new Label(), 0, 2);
        gameGroup.add(new Label(), 0, 3);
        gameGroup.add(new Label(), 0, 4);
        gameGroup.add(new Label(), 0, 5);

        gameGroup.add(lblGameInfo, 0, 6);

        gameGroup.add(lblGameTitle, 0, 7);
        gameGroup.add(txtGameTitle, 1, 7);

        gameGroup.add(lblGameScore, 0, 8);
        gameGroup.add(txtGameScore, 1, 8);

        gameGroup.add(lblDatePlayed, 0, 9);
        gameGroup.add(txtDatePlayed, 1, 9);

        //create buttons
        Button btnUpdate = new Button("Update");
        gameGroup.add(btnUpdate, 2, 1);


        //create grid for buttons
        GridPane btnGrid = new GridPane();
        btnGrid.setHgap(5);
        btnGrid.setVgap(5);
        btnGrid.setStyle(
                "-fx-padding: 10;");


        Button btnCreate = new Button("Create Player");
        btnGrid.add(btnCreate, 0, 0);
        Button btnDisplay = new Button("Display All Players");
        btnGrid.add(btnDisplay, 1, 0);


        HBox hCenterBox = new HBox();
        hCenterBox.getChildren().addAll(playerInfoPanel, gameGroup);
        VBox vCenterBox = new VBox();
        vCenterBox.getChildren().addAll(hCenterBox, btnGrid);
        borderPane.setCenter(vCenterBox);


        //create the text area
        tArea = new TextArea();

        // Create a scroll pane to hold the text area
        ScrollPane scrollPane = new ScrollPane(tArea);


        //handle click events
        btnDisplay.setOnAction(e -> {
            try {
                DisplayTable tc = new DisplayTable();
                displayAllPlayers();
                tc.displayPlayers(conn);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnCreate.setOnAction(e -> {
            try {
                createPlayers(
                        txtFirstName,
                        txtLastName,
                        txtAddress,
                        txtProvince,
                        txtPostalCode,
                        txtPhoneNumber,
                        txtGameTitle,
                        txtGameScore,
                        txtDatePlayed);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        btnUpdate.setOnAction(e -> {
            try {
                updatePlayer(
                        txtUpdatePlayer,
                        txtFirstName,
                        txtLastName,
                        txtAddress,
                        txtProvince,
                        txtPostalCode,
                        txtPhoneNumber,
                        txtGameTitle,
                        txtGameScore,
                        txtDatePlayed
                );
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });



        //set the scroll pane to the bottom of border pane
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        borderPane.setBottom(scrollPane);


        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 650, 500);

        primaryStage.setTitle("Student Info"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage


    }


    public void createPlayers(
            TextField txtFirstName,
            TextField txtLastName,
            TextField txtAddress,
            TextField txtProvince,
            TextField txtPostalCode,
            TextField txtPhoneNumber,
            TextField txtGameTitle,
            TextField txtGameScore,
            TextField txtDatePlayed) throws SQLException, ParseException {

        //create player
        Player player = new Player();
        player.setId(getNextId("PLAYER"));
        player.setfName(txtFirstName.getText());
        player.setlName(txtLastName.getText());
        player.setAddress(txtAddress.getText());
        player.setProvince(txtProvince.getText());
        player.setPostalCode(txtPostalCode.getText());
        player.setPhoneNumber(txtPhoneNumber.getText());

        //create Game
        Game game = new Game();
        game.setId(getNextId("GAME"));
        game.setTitle(txtGameTitle.getText());

        //create Player and Game relationship
        PlayerAndGame playerAndGame = new PlayerAndGame();
        playerAndGame.setId(getNextId("PLAYERANDGAME"));
        playerAndGame.setPlayer(player);
        playerAndGame.setGame(game);
        playerAndGame.setScore(txtGameScore.getText());
        playerAndGame.setPlayingDate(txtDatePlayed.getText());

        //insert player
        PreparedStatement stmtPlayer = conn.prepareStatement("insert into Player values(?,?,?,?,?,?,?)");
        stmtPlayer.setInt(1, player.getId());
        stmtPlayer.setString(2, player.getfName().substring(0, Math.min(player.getfName().length(), 50)));
        stmtPlayer.setString(3, player.getlName().substring(0, Math.min(player.getlName().length(), 50)));
        stmtPlayer.setString(4, player.getAddress().substring(0, Math.min(player.getAddress().length(), 255)));
        stmtPlayer.setString(5, player.getPostalCode().substring(0, Math.min(player.getPostalCode().length(), 10)));
        stmtPlayer.setString(6, player.getProvince().substring(0, Math.min(player.getProvince().length(), 50)));
        stmtPlayer.setString(7, player.getPhoneNumber().substring(0, Math.min(player.getPhoneNumber().length(), 50)));
        int rsPlayer = stmtPlayer.executeUpdate();

        //insert game
        PreparedStatement stmtGame = conn.prepareStatement("insert into Game values(?,?)");
        stmtGame.setInt(1, game.getId());
        stmtGame.setString(2, game.getTitle().substring(0, Math.min(game.getTitle().length(), 255)));
        int rsGame = stmtGame.executeUpdate();

        PreparedStatement stmtPlayerGame = conn.prepareStatement("insert into playerAndGame values(?,?,?,?,?)");
        stmtPlayerGame.setInt(1, playerAndGame.getId());
        stmtPlayerGame.setInt(2, playerAndGame.getGame().getId());
        stmtPlayerGame.setInt(3, playerAndGame.getPlayer().getId());
        stmtPlayerGame.setDate(4,  java.sql.Date.valueOf(playerAndGame.getPlayingDate() ));
        stmtPlayerGame.setString(5, playerAndGame.getScore().substring(0, Math.min(playerAndGame.getScore().length(), 50)));
        int rsPG = stmtPlayerGame.executeUpdate();

        //Clear fields
        clearFields(
                txtFirstName,
                txtLastName,
                txtAddress,
                txtProvince,
                txtPostalCode,
                txtPhoneNumber,
                txtGameTitle,
                txtGameScore,
                txtDatePlayed

        );

        displayAllPlayers();

    }
    //Select next Id in the table
    public int getNextId(String tableName) throws SQLException {

        if ("PLAYER".equals(tableName)) {
            PreparedStatement stmt = conn.prepareStatement("select max(PLAYER_ID) from Player");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) + 1;
        } else if ("GAME".equals(tableName)) {
            PreparedStatement stmt = conn.prepareStatement("select max(GAME_ID) from Game");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) + 1;
        } else if ("PLAYERANDGAME".equals(tableName)) {
            PreparedStatement stmt = conn.prepareStatement("select max(PLAYER_GAME_ID) from PlayerAndGame");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) + 1;
        }
        return 0;
    }

    // display entries in text area
    public void displayAllPlayers() throws SQLException {

        tArea.clear();

        PreparedStatement stmt = conn.prepareStatement("select * from PlayerAndGame order by PLAYER_GAME_ID ASC ");
        ResultSet rs = stmt.executeQuery();
        List<PlayerAndGame> playerList = new ArrayList<>();

        while (rs.next()) {
            PlayerAndGame playerAndGame = new PlayerAndGame();
            playerAndGame.setId(rs.getInt(1));
            playerAndGame.setGame(getGame(rs.getInt(2)));
            playerAndGame.setPlayer(getPlayer(rs.getInt(3)));
            playerAndGame.setPlayingDate(rs.getDate(4).toString());
            playerAndGame.setScore(rs.getString(5));

            playerList.add(playerAndGame);
        }

        tArea.appendText("\n");
        playerList.forEach(playerAndGame -> {
            Player player = playerAndGame.getPlayer();
            Game game= playerAndGame.getGame();
            String txt = "ID: " + player.getId() + " | "
                    + "Name: " + player.getfName() +" "+ player.getlName() + " | "
                    + "ADDRESS: " + player.getAddress() + " | "
                    + "POSTAL CODE: " + player.getPostalCode() + " | "
                    + "PROVINCE: " + player.getProvince() + " | "
                    + "PHONE NUMBER: " + player.getPhoneNumber() + " | "
                    + "GAME TITLE: " + game.getTitle() + " | "
                    + "SCORE: " + playerAndGame.getScore() + " | "
                    + "DATE PLAYED: " + playerAndGame.getPlayingDate();
            tArea.appendText(txt + "\n");
        });

    }

    public void updatePlayer(
            TextField txtUpdatePlayer,
            TextField txtFirstName,
            TextField txtLastName,
            TextField txtAddress,
            TextField txtProvince,
            TextField txtPostalCode,
            TextField txtPhoneNumber,
            TextField txtGameTitle,
            TextField txtGameScore,
            TextField txtDatePlayed) throws SQLException, ParseException {

        //validation
        if(txtUpdatePlayer == null || txtUpdatePlayer.getText().isEmpty()) {
            tArea.clear();
            tArea.appendText("Id invalid");
            return;
        }



        //find player
        PlayerAndGame playerAndGame = null;
        try {
            playerAndGame = getPlayerAndGame(Integer.parseInt(txtUpdatePlayer.getText()));
        } catch (Exception e){
            tArea.clear();
            tArea.appendText("Player not Found");
        }
        if (playerAndGame == null){
            return;
        }

        //create player
        if (txtFirstName != null && !txtFirstName.getText().isEmpty() )
            playerAndGame.getPlayer().setfName(txtFirstName.getText());
        if (txtLastName != null && !txtLastName.getText().isEmpty() )
                playerAndGame.getPlayer().setlName(txtLastName.getText());
        if (txtAddress != null && !txtAddress.getText().isEmpty() )
            playerAndGame.getPlayer().setAddress(txtAddress.getText());
        if (txtProvince != null && !txtProvince.getText().isEmpty() )
            playerAndGame.getPlayer().setProvince(txtProvince.getText());
        if (txtPostalCode != null && !txtPostalCode.getText().isEmpty() )
            playerAndGame.getPlayer().setPostalCode(txtPostalCode.getText());
        if (txtPhoneNumber != null && !txtPhoneNumber.getText().isEmpty() )
            playerAndGame.getPlayer().setPhoneNumber(txtPhoneNumber.getText());

        //create Game
        if (txtGameTitle != null && !txtGameTitle.getText().isEmpty() )
            playerAndGame.getGame().setTitle(txtGameTitle.getText());

        //create Player and Game relationship
        if (txtGameScore != null && !txtGameScore.getText().isEmpty() )
            playerAndGame.setScore(txtGameScore.getText());
        if (txtDatePlayed != null && !txtDatePlayed.getText().isEmpty() )
        playerAndGame.setPlayingDate(txtDatePlayed.getText());

        tArea.clear();

        //update player
        PreparedStatement stmtPlayer = conn.prepareStatement(
                "update Player set FIRST_NAME = ?, LAST_NAME = ?, ADDRESS = ?, POSTAL_CODE = ?, PROVINCE = ?, PHONE_NUMBER = ? WHERE PLAYER_ID = ?");
        stmtPlayer.setInt(7, playerAndGame.getPlayer().getId());
        stmtPlayer.setString(1, playerAndGame.getPlayer().getfName().substring(0, Math.min(playerAndGame.getPlayer().getfName().length(), 50)));
        stmtPlayer.setString(2, playerAndGame.getPlayer().getlName().substring(0, Math.min(playerAndGame.getPlayer().getlName().length(), 50)));
        stmtPlayer.setString(3, playerAndGame.getPlayer().getAddress().substring(0, Math.min(playerAndGame.getPlayer().getAddress().length(), 255)));
        stmtPlayer.setString(4, playerAndGame.getPlayer().getPostalCode().substring(0, Math.min(playerAndGame.getPlayer().getPostalCode().length(), 10)));
        stmtPlayer.setString(5, playerAndGame.getPlayer().getProvince().substring(0, Math.min(playerAndGame.getPlayer().getProvince().length(), 50)));
        stmtPlayer.setString(6, playerAndGame.getPlayer().getPhoneNumber().substring(0, Math.min(playerAndGame.getPlayer().getPhoneNumber().length(), 50)));
        int rsPlayer = stmtPlayer.executeUpdate();

        //update game
        PreparedStatement stmtGame = conn.prepareStatement("update Game set GAME_TITLE= ? WHERE GAME_ID = ?");
        stmtGame.setInt(2, playerAndGame.getGame().getId());
        stmtGame.setString(1, playerAndGame.getGame().getTitle().substring(0, Math.min(playerAndGame.getGame().getTitle().length(), 255)));
        int rsGame = stmtGame.executeUpdate();

        PreparedStatement stmtPlayerGame = conn.prepareStatement("update playerAndGame set PLAYING_DATE = ?, SCORE = ? WHERE PLAYER_GAME_ID = ?");
        stmtPlayerGame.setInt(3, playerAndGame.getId());
        stmtPlayerGame.setDate(1, Date.valueOf(playerAndGame.getPlayingDate()));
        stmtPlayerGame.setString(2, playerAndGame.getScore().substring(0, Math.min(playerAndGame.getScore().length(), 50)));
        int rsPG = stmtPlayerGame.executeUpdate();

        //Clear fields
        clearFields(txtFirstName,
                txtLastName,
                txtAddress,
                txtProvince,
                txtPostalCode,
                txtPhoneNumber,
                txtGameTitle,
                txtGameScore,
                txtDatePlayed
        );

        displayAllPlayers();

    }

    public PlayerAndGame getPlayerAndGame(int id) throws SQLException {
        PlayerAndGame playerAndGame = new PlayerAndGame();

        PreparedStatement stmt = conn.prepareStatement("select * from PlayerAndGame WHERE PLAYER_ID = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        rs.next();
        playerAndGame.setId(rs.getInt(1));
        playerAndGame.setGame(getGame(rs.getInt(2)));
        playerAndGame.setPlayer(getPlayer(rs.getInt(3)));
        playerAndGame.setPlayingDate(rs.getDate(4).toString());
        playerAndGame.setScore(rs.getString(5));

        return playerAndGame;
    }

    public Game getGame(int id) throws SQLException {
        Game game = new Game();

        PreparedStatement stmt = conn.prepareStatement("select * from Game WHERE GAME_ID = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        rs.next();
        game.setId(rs.getInt(1));
        game.setTitle(rs.getString(2));

        return game;
    }

    public Player getPlayer(int id) throws SQLException {
        Player player = new Player();

        PreparedStatement stmt = conn.prepareStatement("select * from Player WHERE PLAYER_ID = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        rs.next();
        player.setId(rs.getInt(1));
        player.setfName(rs.getString(2));
        player.setlName(rs.getString(3));
        player.setAddress(rs.getString(4));
        player.setPostalCode(rs.getString(5));
        player.setProvince(rs.getString(6));
        player.setPhoneNumber(rs.getString(7));

        return player;
    }

    public static void main(String[] args) {
        launch();
    }

    //Clen fields
    void clearFields
            (
                    TextField txtFirstName,
                    TextField txtLastName,
                    TextField txtAddress,
                    TextField txtProvince,
                    TextField txtPostalCode,
                    TextField txtPhoneNumber,
                    TextField txtGameTitle,
                    TextField txtGameScore,
                    TextField txtDatePlayed
            )
    {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAddress.setText("");
        txtProvince.setText("");
        txtPostalCode.setText("");
        txtPhoneNumber.setText("");
        txtGameTitle.setText("");
        txtGameScore.setText("");
        txtDatePlayed.setText("");
    }


}
