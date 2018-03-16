/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Javier
 */
public class Nave {
    Polygon navePoligono;
    int SCENE_TAM_X = 800;
    int SCENE_TAM_Y = 600;
    double posNaveX = SCENE_TAM_X/2;
    double posNaveY = SCENE_TAM_Y/2;
    double anguloNave = 360;
    double naveVelocidadX;
    double naveVelocidadY;
    int anguloVelNave;
    double naveAceleracion;
    public Nave( Pane root){
        //Objeto nave
        navePoligono = new Polygon();
        navePoligono.getPoints().addAll(new Double []{
            0.0, 0.0,
            30.0, 10.0,
            0.0, 20.0
        });
        //posicion de la nave
        navePoligono.setTranslateX(posNaveX);
        navePoligono.setTranslateY(posNaveY);
        navePoligono.setFill(Color.BLACK);
        navePoligono.setRotate(anguloNave);
        root.getChildren().add(navePoligono);
    }
    public void mover(){
        //La nave se mueve en X constantemente
        posNaveX += naveVelocidadX;
        //La nave se mueve en Y constantemente  
        posNaveY += naveVelocidadY; 
        navePoligono.setTranslateX(posNaveX);
        navePoligono.setTranslateY(posNaveY);
    }
    public void giro(){
        //              La nave gira constantemente
                anguloNave += anguloVelNave;
                navePoligono.setRotate(anguloNave);
//                Si el valor es mayor que 360 cambia a 0
                if (anguloNave>360){
                    anguloNave=0;
                } 
//                Si el valor es menor de 0 cambia a 360
                else if (anguloNave<0){
                    anguloNave=360;
                }
//              En case de que la nave toque cada borde, vuelve al otro lado de la pantalla
                if(posNaveX > SCENE_TAM_X){
                    posNaveX = 0;
                }
                if(posNaveX < 0){
                    posNaveX = SCENE_TAM_X;
                }
                if(posNaveY > SCENE_TAM_Y){
                    posNaveY = 0;
                }
                if(posNaveY < 0){
                    posNaveY = SCENE_TAM_Y;
                }
    }
    public Polygon getNave(){
        return navePoligono;
    }
    public void moverAngulo(){
        //                    Cuando pulsa arriba la nave acelera y modifica la velocidad dependiendo del ángulo
                    naveAceleracion = 0.5;
                    //Si se pulsa arriba la nave avanza segun donde este mirando// 
                    double anguloNaveRadianes = Math.toRadians(anguloNave);
                    naveVelocidadX += Math.cos(anguloNaveRadianes) * naveAceleracion;
                    naveVelocidadY += Math.sin(anguloNaveRadianes) * naveAceleracion;       
                    //Limite de velocidad para los 4 sentidos
                    if(naveVelocidadX > 4){
                    naveVelocidadX = 4;
                    }
                    if(naveVelocidadY > 4){
                    naveVelocidadY = 4;
                    }
                    if(naveVelocidadX < -4){
                    naveVelocidadX = -4;
                    }
                    if(naveVelocidadY < -4){
                    naveVelocidadY = -4;
                    }
        
    }
}
