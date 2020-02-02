/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;

/**
 *
 * @author eliezer
 */
public class prepare extends Thread{
    ImageView btnPlay;
    ImageView conectaLogo;
    ImageView loadingGif;
    Semaphore semA;
    Semaphore semB;
    public prepare(ImageView btnPlay, ImageView conectaLogo, ImageView loadingGif, Semaphore semA, Semaphore semB){
        this.btnPlay = btnPlay;
        this.conectaLogo = conectaLogo;
        this.loadingGif = loadingGif;
        this.semA= semA;
        this.semB= semB;
    }
    @Override
    public void run() {
        btnPlay.setVisible(false);
        conectaLogo.setVisible(false);
        loadingGif.setVisible(true);
        semB.release();
        try {
            semA.acquire();
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(prepare.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
