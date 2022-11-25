
package application.controller;

import application.model.Player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    @FXML private Label errorText;
    @FXML private TextArea userField;

    static Path path = Paths.get("src/main/resources/application/files/players.txt");
    static ArrayList <Player> players = new ArrayList<>();
    static ArrayList <String> names = new ArrayList<>();
    static ArrayList <Integer> wins = new ArrayList<>();
    static ArrayList <Integer> losts = new ArrayList<>();
    static String name;
    static boolean readDone = false;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        if (!readDone) {
            read_All(path);
        }
        errorText.setText("");
        name = userField.getText();
        boolean exisit = false;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equalsIgnoreCase(name)){
                i = players.size();
                exisit = true;
            }
        }
        if (!exisit){
            players.add(new Player(name, 0, 0, 0));
        }

        for (int i = 0; i < players.size(); i++) {

        }


        System.out.println(name);
        if(name.equals("")){
            errorText.setText("El nombre no puede estar vacio");
        }else{
            {
                ChangeScene("game.fxml");
            }
        }
    }

    public void ChangeScene(String sceneName) throws IOException {
        try {
            Stage stage = (Stage) errorText.getScene().getWindow();
            Parent root = FXMLLoader.load(LoginController.class.getResource(sceneName));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void write(Path path){
        try{
            BufferedWriter bufferWriter= Files.newBufferedWriter(path,
                    java.nio.charset.StandardCharsets.UTF_8, java.nio.file.StandardOpenOption.CREATE);
            for (int i=0;i<players.size();i++){
                bufferWriter.write((i+1)+";"+players.get(i).getName()+";"+players.get(i).getPlayedGames()+";"+players.get(i).getWonGames()+";"+players.get(i).getLostGames());
                bufferWriter.newLine();
            }
            bufferWriter.flush();
            bufferWriter.close();
        } catch (IOException ex) {
            System.err.println("I/O Error: "+ex);
        }
    }

    static void writeany(Path path){
        try{
            BufferedWriter bufferWriter= Files.newBufferedWriter(path,
                    java.nio.charset.StandardCharsets.UTF_8, java.nio.file.StandardOpenOption.CREATE);
            bufferWriter.write("");
            bufferWriter.flush();
            bufferWriter.close();
        } catch (IOException ex) {
            System.err.println("I/O Error: "+ex);
        }
    }

    static void read_All(Path path){
        try {
            List<String> lines = Files.readAllLines(path, java.nio.charset.StandardCharsets.UTF_8);
            for (String linea : lines){
                String linea_info [] = linea.split(";");
                players.add(new Player(linea_info[1], Integer.parseInt(linea_info[2]), Integer.parseInt(linea_info[3]), Integer.parseInt(linea_info[4])));
            }
        } catch (IOException ex) {
            System.err.println("I/O Error: "+ex);
        }
    }

}
