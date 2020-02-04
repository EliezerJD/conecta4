/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import hellofx.TableroController;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author eliezer
 */

public class SelectFicha extends Thread{
    String noPlayer;
    Socket socket;
    Alert alertV = new Alert(Alert.AlertType.ERROR);
    String fichaSelect;
    boolean show = false;
    Color color;
    FXMLLoader Loader;
    boolean error = false;
    Event event;
    Color otherColor;

    public SelectFicha(String noPlayer, Socket socket, String fichaSelect, FXMLLoader Loader, Event event) {
        this.noPlayer = noPlayer;
        this.socket = socket;
        this.fichaSelect = fichaSelect;
        this.Loader = Loader;
        this.event = event;
    }
    @Override
    public void run() {
        socket.on("errorficha", new Emitter.Listener() {
            public void call(Object... args) {
               Platform.runLater(new Runnable() {
                    @Override public void run() {
                        if(args[0].equals(noPlayer)){
                            error = true;
                            if(show==false ){
                                showAlert();
                                
                            }
                            
                        }
                    }
                });
                
            }
        });
        socket.emit(noPlayer+"ficha", fichaSelect);
        try {
            Thread.currentThread().sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(SelectFicha.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(error==false){
            switch(fichaSelect){
                case "Roja":{
                    color= Color.RED;
                    otherColor = Color.YELLOW;
                    break;
                }
                case "Amarilla":{
                    color= Color.YELLOW;
                    otherColor = Color.RED;
                    break;
                }
            }
            
            Platform.runLater(new Runnable() {
                 @Override public void run() {
                    try {
                        Loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(conectToServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    TableroController tc = Loader.getController();
                    tc.setData(color, otherColor, noPlayer, socket);
                    Parent p = Loader.getRoot();
                    Stage primaryStage = primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    primaryStage.setScene(new Scene(p));
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                }
            });
            
        }
        
        
        
    }
    public void showAlert(){
        show = true;
        alertV.setTitle("Error al elegir ficha");
        alertV.setHeaderText(null);
        alertV.setContentText("Ese color de ficha ya fue seleccionado por otro jugador, elige otro");
        alertV.show();
        
    }
}
