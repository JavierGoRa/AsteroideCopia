/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Javier
 */
public class Misil {
    Circle misilCirculo;
    public void misilObj(Pane root){
        misilCirculo = new Circle();
        misilCirculo.setFill(Color.RED);
        misilCirculo.setRadius(3);
        root.getChildren().add(misilCirculo); 
    }
    
}
