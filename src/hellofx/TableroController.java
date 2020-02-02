/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofx;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author eliezer
 */
public class TableroController {
    Color color;
    String noPlayer;
    
    public void setData(Color color, String noPlayer){
        this.color = color;
        this.noPlayer = noPlayer;
    }
    @FXML
    public void tiro(Event event) {
        Circle c = (Circle) event.getSource();
        c.setFill(color);
    }
    
    
}
