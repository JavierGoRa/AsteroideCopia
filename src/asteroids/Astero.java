/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

/**
 *
 * @author Javier
 */
import java.util.Random;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Javier
 */



public class Astero{
    int SCENE_TAM_X = 800;
    int SCENE_TAM_Y = 600;

    Pane root;
    
    double asteroideX;
    double asteroideY;
    Polygon asteroidePoligono;

    double posAsteroideX;
    double posAsteroideY;
    
    double rotateAsteroide;
    double velRotateAsteroide = 0.1;
    
    double asteroideVelX = 1;
    double asteroideVelY = 1;
    
    public Astero(Pane root){	
        asteroidePoligono = new Polygon();
        asteroidePoligono.getPoints().addAll(new Double []{
            0.0, 0.0,
            40.0, 20.0,
            60.0, 60.0,
            10.0, 60.0
        });
        asteroidePoligono.setFill(Color.RED);
        root.getChildren().add(asteroidePoligono);
        Random randomAsteroide = new Random();
        posAsteroideX = randomAsteroide.nextInt(800);
        posAsteroideY = randomAsteroide.nextInt(600);
    }
    public Polygon getAsteroide(){
        return asteroidePoligono;
    }
    public void limiteAsteroide(){
        //En caso de que el asteroide sobrepase el limite vuelve a la pantalla        
        if(posAsteroideX > SCENE_TAM_X){
            posAsteroideX = 0;
        }
        if(posAsteroideX < 0){
            posAsteroideX = SCENE_TAM_X;
        }
        if(posAsteroideY > SCENE_TAM_Y){
            posAsteroideY = 0;
        }
        if(posAsteroideY < 0){
            posAsteroideY = SCENE_TAM_Y;
        }
    }
    public void mover(){
        posAsteroideX += asteroideVelX;
        posAsteroideY += asteroideVelY;
        asteroidePoligono.setTranslateX(posAsteroideX);
        asteroidePoligono.setTranslateY(posAsteroideY);
        //Giro del asteroide
        asteroidePoligono.setRotate(rotateAsteroide += velRotateAsteroide);
    }
}