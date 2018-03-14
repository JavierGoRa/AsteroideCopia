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



public class Asteroide{
    int SCENE_TAM_X = 800;
    int SCENE_TAM_Y = 600;

    Pane root;
    
    double asteroideX;
    double asteroideY;
    Polygon asteroidePoligono;
            
    public void asteroideObj(Pane root){	
        asteroidePoligono = new Polygon();
        asteroidePoligono.getPoints().addAll(new Double []{
            0.0, 0.0,
            40.0, 20.0,
            60.0, 60.0,
            10.0, 60.0
        });
        asteroidePoligono.setFill(Color.RED);
        root.getChildren().add(asteroidePoligono);
    }
}