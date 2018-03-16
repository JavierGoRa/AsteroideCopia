/* To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates and open the template in the editor.*/

package asteroids;



import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Javier all right reserved
 */
public class Asteroids extends Application {
    
    int SCENE_TAM_X = 800;
    int SCENE_TAM_Y = 600;
    
    
    double misilAce;
    
    double misilVelocidadX;
    double misilVelocidadY;
    
    double posMisilX; 
    double posMisilY; 

    
    
    
    double textTamaño = 20;
    double textTamañoFrase = 30;
    Text textScore;
    double score;
    
    
    
    Nave nave;
    
    Astero asteroide;
    
    
    Misil misil;
    ArrayList <Astero> listaAsteroide = new ArrayList();
    ArrayList <Misil> listaMisil = new ArrayList();
    Pane root;
    
    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        Scene scene = new Scene(root, SCENE_TAM_X, SCENE_TAM_Y);
        
        primaryStage.setTitle("Asteroids");
        primaryStage.setScene(scene);
        primaryStage.show();
        
//      Creacion de los objetos asteroides
        
        for (int i= 0; i <3; i++){ 
            asteroide = new Astero(root);

            listaAsteroide.add(asteroide);

        }
        nave = new Nave(root);
        
        //Objeto fuego rojo
        Polygon furo = new Polygon();
        furo.getPoints().addAll(new Double []{
            0.0, 0.0,
            10.0, 0.0,
            5.0, 10.0
        });
        furo.setFill(Color.RED);
        root.getChildren().add(furo);
        
//      Objeto fuego amarillo
        Polygon fuam = new Polygon();
        fuam.getPoints().addAll(new Double []{
            0.0, 0.0,
            10.0, 0.0,
            5.0, 10.0
        });
        fuam.setFill(Color.YELLOW);
        root.getChildren().add(fuam);
        
        //Caja donde va a ir los textos
        HBox paneScores = new HBox();
        paneScores.setTranslateY(20);
        paneScores.setMinWidth(SCENE_TAM_X);
        paneScores.setAlignment(Pos.CENTER);
        paneScores.setSpacing(250);
        root.getChildren().add(paneScores);

        //
        HBox paneCurrentScore = new HBox();
        paneCurrentScore.setSpacing(10);
        paneScores.getChildren().add(paneCurrentScore);
        
        //Texto que recoje la puntuacion
        Text textoPuntuacion = new Text("Score:");
        textoPuntuacion.setFont(Font.font(textTamaño));
        textoPuntuacion.setFill(Color.BLACK);
        paneCurrentScore.getChildren().add(textoPuntuacion);
        textScore = new Text("0");
        textScore.setFont(Font.font(textTamaño));
        textScore.setFill(Color.BLACK);
        paneCurrentScore.getChildren().add(textScore);
        
        HBox paneVersion = new HBox();
        paneVersion.setSpacing(10);
        paneVersion.setAlignment(Pos.TOP_RIGHT);
        paneScores.getChildren().add(paneVersion);
        
        Text version = new Text("Version 1.0");
        version.setFont(Font.font(textTamaño));
        version.setFill(Color.BLACK);
        paneVersion.getChildren().add(version);
        
        AnimationTimer animationnave = new AnimationTimer(){
            @Override
            public void handle(long now) {
                
                nave.mover();
                nave.giro();
                movimientoMisil();
                
                for (int i= 0; i <listaMisil.size(); i++){ 
                    misil = listaMisil.get(i);
                    misil.getMisil().setTranslateX(posMisilX);
                    misil.getMisil().setTranslateY(posMisilY);         
                }
                
//              Movimiento asteroides
                for (int i= 0; i <listaAsteroide.size(); i++){ 
                    asteroide = listaAsteroide.get(i);
                    asteroide.mover();
                }
                for (int i= 0; i <listaAsteroide.size(); i++){ 
                    asteroide = listaAsteroide.get(i);
                    asteroide.limiteAsteroide();
                }
                //Detector de colision Nave con el Arteroide
                for (int i = 0 ; i < listaAsteroide.size() ; i++){
                    asteroide = listaAsteroide.get(i);
                    Shape colisionNaveAst = Shape.intersect(nave.getNave(), asteroide.getAsteroide());
                    boolean colisionVaciaNA = colisionNaveAst.getBoundsInLocal().isEmpty();
                    if (colisionVaciaNA == false) {
                        root.getChildren().remove(nave.getNave());
                        finPartida();
                    }
                }
                //Detector de colision Misil con Asteroide
                for (int i = 0 ; i < listaAsteroide.size() ; i++){
                asteroide = listaAsteroide.get(i);
                    for (int b = 0 ; b < listaMisil.size() ; b++){
                    misil = listaMisil.get(b);
                        Shape.intersect(misil.getMisil(),asteroide.getAsteroide());
                        Shape colisionMisilAst = Shape.intersect(misil.getMisil(), asteroide.getAsteroide());
                        boolean colisionVaciaMA = colisionMisilAst.getBoundsInLocal().isEmpty();
                        if (colisionVaciaMA == false) {
                            root.getChildren().remove(misil.getMisil());
                            root.getChildren().remove(asteroide.getAsteroide());
        //                    El Score incrementa en 1
                            score = score +1;
        //                    Muestra el actual score
                            textScore.setText(String.valueOf(score));                
                            System.out.println(score);
                        }
                    }
                }

            };
        };
        animationnave.start();        
//        Reaccion a los botones
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()) {
                case SPACE:
                    misil = new Misil(root);
    //              valora la bola para que la coloque donde este la nave
                    posMisilX = nave.posNaveX + 15;    
                    posMisilY = nave.posNaveY + 10;
                    //Coloca el misil donde este la nave
                    misil.getMisil().setTranslateX(posMisilX);
                    misil.getMisil().setTranslateY(posMisilY);
                    
                    //Valora la aceleracion del misil
                    misilAce = 5;
                    
 //                 Calcula el angulo de la nave para disparar en su dirección                   
                    double anguloMisilRadianes = Math.toRadians(nave.anguloNave);
                    misilVelocidadX = Math.cos(anguloMisilRadianes) * misilAce;
                    misilVelocidadY = Math.sin(anguloMisilRadianes) * misilAce;
                    listaMisil.add(misil);
                    break;
                case RIGHT:
//                  Si se pulsa derecha la nave gira a una velocidad de 5 grados//
                    nave.anguloVelNave = +5;
                    break;
                case LEFT:
//                    Si se pulsa izquierda la nave gira a una velocidad -5 grados//                   
                    nave.anguloVelNave = -5;
                    break;
                case UP:
                    nave.moverAngulo();
                    break;
                case ENTER:
//                    reinicioPartida();
            }
        });
//        Soltar boton
        scene.setOnKeyReleased((KeyEvent event) -> {
            switch(event.getCode()){
                case LEFT:
                case RIGHT:
                    nave.anguloVelNave = 0;
                    break;
            }
        });
    } 
    public void movimientoMisil(){
        //El misil aumenta la velocidad en X
        posMisilX += misilVelocidadX;
        //Posicion de la bola en Y
        posMisilY += misilVelocidadY;
    }    
    public void finPartida(){
        //Caja donde va a ir los textos
        HBox paneEnd = new HBox();
        paneEnd.setTranslateY(SCENE_TAM_Y/2);
        paneEnd.setMinWidth(SCENE_TAM_X);
        paneEnd.setAlignment(Pos.CENTER);
        paneEnd.setSpacing(100);
        root.getChildren().add(paneEnd);
        //Caja para la puntuacion máxima
        HBox paneMaxScore = new HBox();
        paneMaxScore.setSpacing(10);
        paneEnd.getChildren().add(paneMaxScore);
        //Texto que recoje la puntuacion máxima
        Text textoPuntuacion = new Text("MaxScore:");
        textoPuntuacion.setFont(Font.font(textTamaño));
        textoPuntuacion.setFill(Color.BLACK);
        paneMaxScore.getChildren().add(textoPuntuacion);
        textScore = new Text("score");
        textScore.setFont(Font.font(textTamaño));
        textScore.setFill(Color.BLACK);
        textScore.setText(String.valueOf(score));
        paneMaxScore.getChildren().add(textScore);
        //Caja donde ira el Game Over
        HBox paneGameOver = new HBox();
        paneGameOver.setSpacing(10);
        paneGameOver.setAlignment(Pos.TOP_RIGHT);
        paneEnd.getChildren().add(paneGameOver);
        //Texto del Game Over
        Text gameOver = new Text("Game Over");
        gameOver.setFont(Font.font(textTamaño));
        gameOver.setFill(Color.BLACK);
        paneGameOver.getChildren().add(gameOver);
    }
//    public void reinicioPartida(){
//        posNaveX = SCENE_TAM_X/2;
//        posNaveY = SCENE_TAM_Y/2;
//        root.getChildren().add(navePoligono);
//    }
}