/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

/**
 *
 * @author eliezer
 */

public class SelectFicha extends Thread{
    String noPlayer;
    Socket socket;
    Alert alertV = new Alert(Alert.AlertType.ERROR);
    String fichaSelect;

    public SelectFicha(String noPlayer, Socket socket, String fichaSelect) {
        this.noPlayer = noPlayer;
        this.socket = socket;
        this.fichaSelect = fichaSelect;
    }
    @Override
    public void run() {
        socket.on("errorficha", new Emitter.Listener() {
            public void call(Object... args) {
                
               Platform.runLater(new Runnable() {
                    @Override public void run() {
                        if(args[0].equals(noPlayer)){
                            showAlert();
                        }
                    }
                });
                
            }
        });
        socket.emit(noPlayer+"ficha", fichaSelect);
        
    }
    public void showAlert(){
        alertV.setTitle("Error al elegir ficha");
        alertV.setHeaderText(null);
        alertV.setContentText("Ese color de ficha ya fue seleccionado por otro jugador, elige otro");
        alertV.show();
    }
}
