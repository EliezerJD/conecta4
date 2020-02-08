/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofx;

import hilos.SelectFicha;
import hilos.conectToServer;
import hilos.prepare;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author eliezer
 */
public class MainController implements Initializable{

    @FXML
    private ImageView btnPlay;
    @FXML
    private ImageView conectaLogo;
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane selectFichas;
    @FXML
    private ImageView loadingGif;
    @FXML
    private ImageView error;
    @FXML
    private Button btnAtras;
    @FXML
    private ImageView selectedR;
    @FXML
    private ImageView selectedA;
    @FXML
    Label title;
    @FXML
    private Circle fichaRed;
    @FXML
    private Circle fichaYellow;
    @FXML
    private Button btnContinuar;
    @FXML
    private Label alert;
    @FXML
    private Label alert2;
    
    
    
    Socket socket;
    @FXML
    public String fichaSelect;
    String playerTemp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            socket = IO.socket("http://localhost:3000", opts);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }    

    @FXML
    private void play(Event event) throws URISyntaxException, InterruptedException, IOException {
        Semaphore semA = new Semaphore(0);
        Semaphore semB = new Semaphore(0);
        prepare p = new prepare(btnPlay, conectaLogo, loadingGif, semA, semB);
        p.setDaemon(true);
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("prueba.fxml"));
        conectToServer threadCS = new conectToServer(socket, semA, semB, event, error, btnAtras, title, Loader, root);
        threadCS.setDaemon(true);
        p.start();
        threadCS.start();
    }
    
    
    
    
    
    @FXML
    private void volver(ActionEvent event) {
        error.setVisible(false);
        btnAtras.setVisible(false);
        loadingGif.setVisible(false);
        conectaLogo.setVisible(true);
        btnPlay.setVisible(true);
    }

    
}
