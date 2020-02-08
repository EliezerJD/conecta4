/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofx;

import hilos.Tiro;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author eliezer
 */
public class TableroController implements Initializable{
    Color color;
    String noPlayer;
    Socket socket;
    String otherPlayer;
    Alert alertV = new Alert(Alert.AlertType.CONFIRMATION);
    Color otherColor;
    @FXML
    private Circle c0;
    @FXML
    private Circle c3;
    @FXML
    private Circle c6;
    @FXML
    private Circle c1;
    @FXML
    private Circle c4;
    @FXML
    private Circle c7;
    @FXML
    private Circle c2;
    @FXML
    private Circle c5;
    @FXML
    private Circle c8;
    @FXML
    private Label tuturno;
    @FXML
    private Label noestuturno;
    boolean turno = false;
    String[][] arreglo = new String[3][3];
    boolean ganador = false;
    Event event;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
   
    public void setData(Color color, Color otherColor, String noPlayer, Socket socket, Event event){
        this.color = color;
        this.otherColor = otherColor;
        this.noPlayer = noPlayer;
        this.socket = socket;
        this.event = event;
        switch(noPlayer){
            case "player1":{
                tuturno.setVisible(true);
                otherPlayer = "player2";
                turno = true;
                break;
            }
            case "player2":{
                noestuturno.setVisible(true);
                otherPlayer = "player1";
                turno = false;
                break;
            }
        }
        initArreglo();
        socket.on(otherPlayer+"gettiro",  new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                pintarOther((String) args[0]);
            }
        });
        socket.on(noPlayer+"win",  new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                 Platform.runLater(new Runnable() {
                 @Override public void run() {
                    tuturno.setVisible(false);
                    noestuturno.setVisible(false);
                    turno = false;
                    alertV.setTitle("Ganador");
                    alertV.setHeaderText(null);
                    alertV.setContentText("Has ganado el juego");
                    alertV.showAndWait();
                    System.exit(0);
                     
                 }
             });
                
            }
                 
        });
        socket.on(noPlayer+"lose",  new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                 Platform.runLater(new Runnable() {
                 @Override public void run() {
                   alertV.setTitle("Perdedor");
                   alertV.setHeaderText(null);
                   alertV.setContentText("Has perdido el juego");
                   alertV.showAndWait();
                   System.exit(0);
                 }
             });
                
            }
                 
        });
    }
    @FXML
    public void tiro(Event event) throws InterruptedException {
        Tiro t = new Tiro(turno, color, socket, noPlayer, tuturno, noestuturno, event);
        t.setDaemon(true);
        t.start();
        t.join();
        turno = t.getTurno();
    }
    
    public void pintarOther(String c){
        noestuturno.setVisible(false);
        tuturno.setVisible(true);
        turno = true;
        
        switch(c){
            case "1":{
                c0.setFill(otherColor);
                arreglo[0][0] = otherPlayer;
                if(arreglo[0][1].equals(otherPlayer)){
                    if(arreglo[0][2].equals(otherPlayer)){
                        ganador=true;
                    } 
                }else if(arreglo[1][0].equals(otherPlayer)){
                    if(arreglo[2][0].equals(otherPlayer)){
                        ganador=true;
                    }
                }else if(arreglo[1][1].equals(otherPlayer)){
                    if(arreglo[2][2].equals(otherPlayer)){
                        ganador=true;
                    }
                }
                break;
            }
            case "2":{
                c1.setFill(otherColor);
                arreglo[0][1] = otherPlayer;
                if(arreglo[1][1].equals(otherPlayer)){
                    if(arreglo[2][1].equals(otherPlayer)){
                        ganador=true;
                    } 
                }else if(arreglo[0][0].equals(otherPlayer)){
                    if(arreglo[0][2].equals(otherPlayer)){
                        ganador=true;
                    }
                }
                break;
            }
            case "3":{
                c2.setFill(otherColor);
                arreglo[0][2] = otherPlayer;
                if(arreglo[1][2].equals(otherPlayer)){
                    if(arreglo[2][2].equals(otherPlayer)){
                        ganador=true;
                    } 
                }else if(arreglo[0][0].equals(otherPlayer)){
                    if(arreglo[0][1].equals(otherPlayer)){
                        ganador=true;
                    }
                }else if(arreglo[1][1].equals(otherPlayer)){
                    if(arreglo[2][0].equals(otherPlayer)){
                        ganador=true;
                    }
                }
                break;
            }
            case "4":{
                c3.setFill(otherColor);
                arreglo[1][0] = otherPlayer;
                if(arreglo[0][0].equals(otherPlayer)){
                    if(arreglo[2][0].equals(otherPlayer)){
                        ganador=true;
                    } 
                }else if(arreglo[1][1].equals(otherPlayer)){
                    if(arreglo[1][2].equals(otherPlayer)){
                        ganador=true;
                    }
                }
                break;
            }
            case "5":{
                c4.setFill(otherColor);
                arreglo[1][1] = otherPlayer;
                if(arreglo[0][0].equals(otherPlayer)){
                    if(arreglo[2][2].equals(otherPlayer)){
                        ganador=true;
                    } 
                }else if(arreglo[0][1].equals(otherPlayer)){
                    if(arreglo[2][1].equals(otherPlayer)){
                        ganador=true;
                    }
                }else if(arreglo[1][0].equals(otherPlayer)){
                    if(arreglo[1][2].equals(otherPlayer)){
                        ganador=true;
                    }
                }else if(arreglo[0][2].equals(otherPlayer)){
                    if(arreglo[2][0].equals(otherPlayer)){
                        ganador=true;
                    }
                }
                break;
            }
            case "6":{
                c5.setFill(otherColor);
                arreglo[1][2] = otherPlayer;
                if(arreglo[0][2].equals(otherPlayer)){
                    if(arreglo[2][2].equals(otherPlayer)){
                        ganador=true;
                    } 
                }else if(arreglo[1][0].equals(otherPlayer)){
                    if(arreglo[1][1].equals(otherPlayer)){
                        ganador=true;
                    }
                }
                break;
            }
            case "7":{
                c6.setFill(otherColor);
                arreglo[2][0] = otherPlayer;
                if(arreglo[0][0].equals(otherPlayer)){
                    if(arreglo[1][0].equals(otherPlayer)){
                        ganador=true;
                    } 
                }else if(arreglo[2][1].equals(otherPlayer)){
                    if(arreglo[2][2].equals(otherPlayer)){
                        ganador=true;
                    }
                }else if(arreglo[0][2].equals(otherPlayer)){
                    if(arreglo[1][1].equals(otherPlayer)){
                        ganador=true;
                    }
                }
                break;
            }
            case "8":{
                c7.setFill(otherColor);
                arreglo[2][1] = otherPlayer;
                if(arreglo[0][1].equals(otherPlayer)){
                    if(arreglo[1][1].equals(otherPlayer)){
                        ganador=true;
                    } 
                }else if(arreglo[2][0].equals(otherPlayer)){
                    if(arreglo[2][2].equals(otherPlayer)){
                        ganador=true;
                    }
                }
                break;
            }
            case "9":{
                c8.setFill(otherColor);
                arreglo[2][2] = otherPlayer;
                if(arreglo[0][2].equals(otherPlayer)){
                    if(arreglo[1][2].equals(otherPlayer)){
                        ganador=true;
                    } 
                }else if(arreglo[2][0].equals(otherPlayer)){
                    if(arreglo[2][1].equals(otherPlayer)){
                        ganador=true;
                    }
                }else if(arreglo[0][0].equals(otherPlayer)){
                    if(arreglo[1][1].equals(otherPlayer)){
                        ganador=true;
                    }
                }
                break;
            }
            
        }
        if(ganador){
            tuturno.setVisible(false);
            noestuturno.setVisible(false);
            turno = false;
            socket.emit(otherPlayer+"win"+ "");
        }
    }
    
    
    public void initArreglo(){
        for(int x=0; x<3; x++){
            for(int y=0; y<3; y++){
                arreglo[x][y]="-";
            }
        }
    }

    
    
    
}
