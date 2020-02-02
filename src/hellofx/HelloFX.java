/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 *
 * @author eliezer
 */
public class HelloFX extends Application{

    /**
     * @param args the command line arguments
     */
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("menu.fxml"));
    	Scene scene = new Scene(root);
    	primaryStage.setScene(scene);
        primaryStage.setTitle("Conecta4");
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(o -> {
            System.exit(0);});
    }
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
    
    
    
}
