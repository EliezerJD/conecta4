/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import io.socket.client.Socket;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author eliezer
 */
public class Tiro extends Thread{
    boolean turno;
    Color color;
    String noPlayer;
    Socket socket;
    String noplayer;
    Label tuturno;
    Label noestuturno;
    Event event;

    public Tiro(boolean turno, Color color, Socket socket, String noPlayer, Label tuturno, Label noestuturno, Event event) {
        this.turno = turno;
        this.color = color;
        this.socket = socket;
        this.noPlayer = noPlayer;
        this.tuturno = tuturno;
        this.noestuturno = noestuturno;
        this.event = event;
    }

    @Override
    public void run() {
        if(turno){
            Circle c = (Circle) event.getSource();
            if(c.getFill()==Color.WHITE){
                c.setFill(color);
                socket.emit(noPlayer+"settiro", c.getAccessibleText());
                tuturno.setVisible(false);
                noestuturno.setVisible(true);
                turno = false;
            }
        }
    }
    public boolean getTurno(){
        return turno;
    }
}
