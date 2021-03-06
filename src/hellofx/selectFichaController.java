/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofx;

import hilos.SelectFicha;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

/**
 *
 * @author eliezer
 */
public class selectFichaController implements Initializable{
    
    @FXML
    private AnchorPane selectFichas;

    @FXML
    private Label title;

    @FXML
    private Circle fichaRed;

    @FXML
    private Circle fichaYellow;

    @FXML
    private ImageView selectedR;

    @FXML
    private ImageView selectedA;

    @FXML
    private Button btnContinuar;

    @FXML
    private Label alert;

    @FXML
    private Label alert2;
    
    String fichaSelect;
    Socket socket;
    String noPlayer;
    boolean error;
    @FXML
    Alert alertV = new Alert(AlertType.ERROR);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
    public void setData(Socket socket, String player){
        this.socket = socket;
        this.noPlayer = player;
    }
    
    @FXML
    public void fichaAmarilla() {
        selectedR.setVisible(false);
        selectedA.setVisible(true);
        fichaSelect = "Amarilla";
    }
    @FXML
    public void fichaRoja() {
        selectedA.setVisible(false);
        selectedR.setVisible(true);
        fichaSelect = "Roja";
    }
    
    @FXML
    public void pintarTablero(Event event) throws IOException, InterruptedException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("tablero.fxml"));
        SelectFicha sf = new SelectFicha(noPlayer, socket, fichaSelect, Loader, event);
        sf.setDaemon(true);
        sf.start();
        
    }
    
    
}
