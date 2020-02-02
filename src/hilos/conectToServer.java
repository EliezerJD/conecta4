/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import hellofx.MainController;
import hellofx.selectFichaController;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author eliezer
 */
public class conectToServer extends Thread{
    Socket socket;
    String noPlayer;
    Semaphore semA;
    Semaphore semB;
    Event event;
    ImageView error;
    Button btnAtras;
    Label title;
    AnchorPane root;
    AnchorPane selectFichas;
    FXMLLoader Loader;
       
    public conectToServer(Socket socket, Semaphore semA, Semaphore semB, Event event, ImageView error, Button btnAtras, Label title, FXMLLoader Loader, AnchorPane root){
        this.socket  = socket;
        this.noPlayer = noPlayer;
        this.semA= semA;
        this.semB= semB;
        this.event = event;
        this.error = error;
        this.btnAtras = btnAtras;
        this.title = title;
        this.root = root;
        this.selectFichas = selectFichas;
        this.Loader = Loader;
        
    }
    
    @Override
    public void run() {
        try {
            semB.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(conectToServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        socket.connect();
        
        socket.on("asignarNoPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                noPlayer=(String)args[0];
            }
        });
        socket.emit("nuevaConexion", "");
        
        
        
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(conectToServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(noPlayer!=null){
            Platform.runLater(new Runnable() {
                 @Override public void run() {
                     try {
                         Loader.load();
                     } catch (IOException ex) {
                         Logger.getLogger(conectToServer.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     selectFichaController sfc = Loader.getController();
                     sfc.setData(socket, noPlayer);
                     Parent p = Loader.getRoot();
                     Stage primaryStage = primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                     primaryStage.setScene(new Scene(p)); 
                 }
             });
            
                  
            
        }else{
            error.setVisible(true);
            btnAtras.setVisible(true);
            socket.disconnect();
            socket.close();
        }
        semA.release();
        
        
        
    }
    
    
}
