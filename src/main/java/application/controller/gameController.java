package application.controller;

import application.model.*;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static application.controller.LoginController.players;

public class gameController {

    @FXML private Text name;
    @FXML private Text PlayGames;
    @FXML private Text WinGames;
    @FXML private Text LostGames;

    @FXML private Text bank;
    @FXML private Text player;
    @FXML private Text result;

    @FXML private Button standbtn;
    @FXML private Button OneCardbtn;
    @FXML private Button TryAgain;
    @FXML private Button ChangePlayer;

    @FXML private ImageView bankImg;
    @FXML private ImageView bankImg1;
    @FXML private ImageView bankImg2;
    @FXML private ImageView bankImg3;
    @FXML private ImageView bankImg4;
    @FXML private ImageView bankImg5;
    @FXML private ImageView bankImg6;
    @FXML private ImageView bankImg7;


    @FXML private ImageView PlayerImg;
    @FXML private ImageView PlayerImg1;
    @FXML private ImageView PlayerImg2;
    @FXML private ImageView PlayerImg3;
    @FXML private ImageView PlayerImg4;
    @FXML private ImageView PlayerImg5;
    @FXML private ImageView PlayerImg6;
    @FXML private ImageView PlayerImg7;

    @FXML private Label errorText_combat;

    @FXML private AnchorPane Bnk_img_container;
    Game game;
    Player playerUser;
    Boolean enterFromOneCard = false;
    boolean continuar = true;

    @FXML
    protected void initialize() {
        TryAgain.setVisible(false);
        ChangePlayer.setVisible(false);
        enterFromOneCard = false;
        start();
    }

    @FXML public void OneMoreCardClick(){
        if (continuar) {
            game.playerTurn();
            bank.setText("Bank: " + game.getBankerHand().getValue());
            player.setText(playerUser.getName() + ": " + game.getPlayerHand().getValue());
            getBankerHandImage();
            getPlayerHandImage();
            if (game.getGameOver()){
                continuar = false;
                enterFromOneCard = true;
                Stand();
            } else {
                getBankerHandImage();
                getPlayerHandImage();
                continuar = true;
            }
        } else {
            Stand();
        }
        gameSummary(game);
        getBankerHandImage();
        getPlayerHandImage();
    }

    @FXML public void Stand(){
        if (enterFromOneCard == true) {
            System.out.println(playerUser.getLostGames());
            TryAgain.setVisible(true);
            ChangePlayer.setVisible(true);
            continuar = false;
            standbtn.setDisable(true);
            OneCardbtn.setDisable(true);
            PlayGames.setText(String.valueOf(playerUser.getPlayedGames()));
            WinGames.setText(String.valueOf(playerUser.getWonGames()));
            LostGames.setText(String.valueOf(playerUser.getLostGames()));
            result.setText(String.valueOf(game.getWinner() + " WINS, " + game.getLoser() + " LOSES"));
            getBankerHandImage();
            getPlayerHandImage();
            gameSummary(game);
            LoginController.writeany(LoginController.path);
            LoginController.write(LoginController.path);
            LoginController.readDone = true;
        } else {
            TryAgain.setVisible(true);
            ChangePlayer.setVisible(true);
            continuar = false;
            game.bankerTurn();
            bank.setText(String.valueOf(game.getBankerHand().getValue()));
            standbtn.setDisable(true);
            OneCardbtn.setDisable(true);
            PlayGames.setText(String.valueOf(playerUser.getPlayedGames()));
            WinGames.setText(String.valueOf(playerUser.getWonGames()));
            LostGames.setText(String.valueOf(playerUser.getLostGames()));
            result.setText(String.valueOf(game.getWinner() + " WINS, " + game.getLoser() + " LOSES"));
            getBankerHandImage();
            getPlayerHandImage();
            gameSummary(game);
            LoginController.writeany(LoginController.path);
            LoginController.write(LoginController.path);
            LoginController.readDone = true;
        }

    }

    @FXML public void TryAgain(){
        standbtn.setDisable(false);
        OneCardbtn.setDisable(false);
        TryAgain.setVisible(false);
        ChangePlayer.setVisible(false);
        continuar = true;
        result.setText("");
        enterFromOneCard = false;
        start();
    }

    @FXML public void ChangeScene() throws IOException {
        System.out.println(playerUser.getPlayedGames());
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(playerUser.getName())){
                players.set(i, playerUser);
            }
        }
        LoginController.writeany(LoginController.path);
        LoginController.write(LoginController.path);
        LoginController.readDone = true;
        String sceneName = "login.fxml";
        try {
            Stage stage = (Stage) errorText_combat.getScene().getWindow();
            Parent root = FXMLLoader.load(gameController.class.getResource(sceneName));
            Scene scene2 = new Scene(root);
            stage.setScene(scene2);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void gameSummary(Game game) {
        displayHand(game.getPlayerName(), game.getPlayerHand());
        displayHand("Banker", game.getBankerHand());
        if(game.getGameOver()){
            System.out.println(String.format("%s wins, %s loses",game.getWinner(), game.getLoser()));
        }
    }

    private void displayHand(String nom, Hand hand) {
        System.out.println(String.format("%s's hand---------------", nom));
        for (Card card : hand.getCards()) {
            System.out.println(card);
        }
        System.out.println("        --------------------");
        System.out.println(String.format("       Total hand value: %.1f",hand.getValue()));
        System.out.println("----------------------------");
    }

    void start() {
        System.out.println("Wellcome to Seven And a Half Game");
        System.out.println(" -You should get as closer to 7.5 without exceed it.");
        System.out.println(" -Banker will try as well.");
        System.out.println(" -Who exceeds 7.5 will lose the game.");
        System.out.println(" -To win you must get closer to 7.5 than the Banker");
        System.out.println("Let's play!!!");
        getPlayerHandImageNull();
        getBankerHandImageNull();
        playerUser = getPlayer();


    }

    private Player getPlayer() {
        String playerName = LoginController.name;
        for (Player playerTest : players) {
            if (playerTest.getName().equals(playerName)) {
                name.setText(playerTest.getName());
                PlayGames.setText(String.valueOf(playerTest.getPlayedGames()));
                WinGames.setText(String.valueOf(playerTest.getWonGames()));
                LostGames.setText(String.valueOf(playerTest.getLostGames()));
                game = new Game(playerTest);
                player.setText(playerTest.getName() + ": " + game.getPlayerHand().getValue());
                bank.setText("Bank: " + game.getBankerHand().getValue());
                getBankerHandImage();
                getPlayerHandImage();
                return playerTest;
            }
        }
        Player newPlayer = new Player(playerName);
        players.add(newPlayer);
        game = new Game(newPlayer);
        return newPlayer;
    }

    private void getPlayerHandImage(){
        Image plrImage;
        for (int i = 0; i < game.getPlayerHand().getCards().size(); i++) {
            System.out.println(game.getPlayerHand().getCards().size());
            plrImage = new Image(new File(String.format("src/main/resources/application/images/%s.png", game.getPlayerHand().getCards().get(i).getCardCode())).toURI().toString());
            switch (i) {
                case 0:
                    PlayerImg.setImage(new Image(new File(String.format("src/main/resources/application/images/%s.png", game.getPlayerHand().getCards().get(0).getCardCode())).toURI().toString()));
                    break;
                case 1:
                    PlayerImg1.setImage(plrImage);
                    break;
                case 2:
                    PlayerImg2.setImage(plrImage);
                    break;
                case 3:
                    PlayerImg3.setImage(plrImage);
                    break;
                case 4:
                    PlayerImg4.setImage(plrImage);
                    break;
                case 5:
                    PlayerImg5.setImage(plrImage);
                    break;
                case 6:
                    PlayerImg6.setImage(plrImage);
                    break;
                case 7:
                    PlayerImg7.setImage(plrImage);
                    break;
                default:
                    PlayerImg.setImage(plrImage);
                    break;
            }
        }
    }

    private void getBankerHandImage(){
        Image Bnkimg = new Image(new File(String.format("src/main/resources/application/images/%s.png", game.getBankerHand().getCards().get(game.getBankerHand().getCards().size()-1).getCardCode())).toURI().toString());
        for (int i = 0; i < game.getBankerHand().getCards().size(); i++) {
            Bnkimg = new Image(new File(String.format("src/main/resources/application/images/%s.png", game.getBankerHand().getCards().get(i).getCardCode())).toURI().toString());
            switch (i) {
                case 0:
                    bankImg.setImage(new Image(new File(String.format("src/main/resources/application/images/%s.png", game.getBankerHand().getCards().get(0).getCardCode())).toURI().toString()));
                    break;
                case 1:
                    bankImg1.setImage(Bnkimg);
                    break;
                case 2:
                    bankImg2.setImage(Bnkimg);
                    break;
                case 3:
                    bankImg3.setImage(Bnkimg);
                    break;
                case 4:
                    bankImg4.setImage(Bnkimg);
                    break;
                case 5:
                    bankImg5.setImage(Bnkimg);
                    break;
                case 6:
                    bankImg6.setImage(Bnkimg);
                    break;
                case 7:
                    bankImg7.setImage(Bnkimg);
                    break;
                default:
                    bankImg.setImage(Bnkimg);
                    break;
            }
        }
    }

    private void getPlayerHandImageNull(){
        Image plrImage;
        for (int i = 0; i < 8; i++) {
            plrImage = new Image(new File(String.format("src/main/resources/application/images/8.png")).toURI().toString());
            switch (i) {
                case 0:
                    PlayerImg.setImage(plrImage);
                    break;
                case 1:
                    PlayerImg1.setImage(plrImage);
                    break;
                case 2:
                    PlayerImg2.setImage(plrImage);
                    break;
                case 3:
                    PlayerImg3.setImage(plrImage);
                    break;
                case 4:
                    PlayerImg4.setImage(plrImage);
                    break;
                case 5:
                    PlayerImg5.setImage(plrImage);
                    break;
                case 6:
                    PlayerImg6.setImage(plrImage);
                    break;
                case 7:
                    PlayerImg7.setImage(plrImage);
                    break;
                default:
                    PlayerImg.setImage(plrImage);
                    break;
            }
        }
    }

    private void getBankerHandImageNull(){
        Image Bnkimg = new Image(new File(String.format("src/main/resources/application/images/8.png")).toURI().toString());
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    bankImg.setImage(Bnkimg);
                    break;
                case 1:
                    bankImg1.setImage(Bnkimg);
                    break;
                case 2:
                    bankImg2.setImage(Bnkimg);
                    break;
                case 3:
                    bankImg3.setImage(Bnkimg);
                    break;
                case 4:
                    bankImg4.setImage(Bnkimg);
                    break;
                case 5:
                    bankImg5.setImage(Bnkimg);
                    break;
                case 6:
                    bankImg6.setImage(Bnkimg);
                    break;
                case 7:
                    bankImg7.setImage(Bnkimg);
                    break;
                default:
                    bankImg.setImage(Bnkimg);
                    break;
            }
        }
    }
}
