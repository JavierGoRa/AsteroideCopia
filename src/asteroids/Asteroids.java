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
    
    double anguloNave = 360;
    int anguloVelNave;
    
    double posNaveX = SCENE_TAM_X/2;
    double posNaveY = SCENE_TAM_Y/2;
    
    double naveVelocidadX;
    double naveVelocidadY;
    
    double naveAceleracion;
    
    double misilAce;
    
    double misilVelocidadX;
    double misilVelocidadY;
    
    double posMisilX; 
    double posMisilY; 
    
    double posAsteroideX;
    double posAsteroideY;
    
    double asteroideVelX = 1;
    double asteroideVelY = 1;
    
    double textTamaño = 20;
    double textTamañoFrase = 30;
    Text textScore;
    double score;
    
    double rotateAsteroide;
    double velRotateAsteroide = 0.1;
    
    Asteroide ast = new Asteroide();
    
    Polygon navePoligono;
    
    Circle misilCirculo = new Circle();
    ArrayList <Asteroide> listaAsteroide = new ArrayList();
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
            ast.asteroideObj(root);
            Random randomAsteroide = new Random();
            posAsteroideX = randomAsteroide.nextInt(800);
            posAsteroideY = randomAsteroide.nextInt(600);
            ast.asteroidePoligono.setTranslateX(posAsteroideX);
            ast.asteroidePoligono.setTranslateY(posAsteroideY);
            listaAsteroide.add(ast);
        }        
        
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
//        nave.setCenterX(15);
//        nave.setCenterY(5);
        root.getChildren().add(navePoligono);
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
                
                movimientoNave();
                navePoligono.setTranslateX(posNaveX);
                navePoligono.setTranslateY(posNaveY);

                movimientoMisil();
                misilCirculo.setTranslateX(posMisilX);
                misilCirculo.setTranslateY(posMisilY);                
                
//              Movimiento asteroides
                for (int i= 0; i <listaAsteroide.size(); i++){ 
                ast = listaAsteroide.get(i);
                posAsteroideX += asteroideVelX;
                posAsteroideY += asteroideVelY;
                ast.asteroidePoligono.setTranslateX(posAsteroideX);
                ast.asteroidePoligono.setTranslateY(posAsteroideY);
                }
                //Giro del asteroide
                ast.asteroidePoligono.setRotate(rotateAsteroide += velRotateAsteroide);
                
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
                
                
//              La nave gira constantemente
                anguloNave += anguloVelNave;
                navePoligono.setRotate(anguloNave);
                
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
                
//                Si el valor es mayor que 360 cambia a 0
                if (anguloNave>360){
                    anguloNave=0;
                } 
//                Si el valor es menor de 0 cambia a 360
                else if (anguloNave<0){
                    anguloNave=360;
                }
                //Detector de colision Nave con el Arteroide
                Shape.intersect(navePoligono,ast.asteroidePoligono);
                Shape colisionNaveAst = Shape.intersect(navePoligono, ast.asteroidePoligono);
                boolean colisionVaciaNA = colisionNaveAst.getBoundsInLocal().isEmpty();
                if (colisionVaciaNA == false) {
                    root.getChildren().remove(navePoligono);
                    finPartida();
                }
                //Detector de colision Misil con Asteroide
                Shape.intersect(misilCirculo,ast.asteroidePoligono);
                Shape colisionMisilAst = Shape.intersect(misilCirculo, ast.asteroidePoligono);
                boolean colisionVaciaMA = colisionMisilAst.getBoundsInLocal().isEmpty();
                if (colisionVaciaMA == false) {
                    root.getChildren().remove(misilCirculo);
                    root.getChildren().remove(ast.asteroidePoligono);
//                    El Score incrementa en 1
                    score = score +1;
//                    Muestra el actual score
                    textScore.setText(String.valueOf(score));                
                    System.out.println(score);
                }
            };
        };
        animationnave.start();        
//        Reaccion a los botones
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()) {
                case SPACE:
                    misilCirculo = new Circle();
                    misilCirculo.setFill(Color.RED);
                    misilCirculo.setRadius(3);
                    root.getChildren().add(misilCirculo);
                    
    //              valora la bola para que la coloque donde este la nave
                    posMisilX = posNaveX + 15;    
                    posMisilY = posNaveY + 10;
                    
                    //Coloca el misil donde este la nave
                    misilCirculo.setTranslateX(posMisilX);
                    misilCirculo.setTranslateY(posMisilY);
                    
                    //Valora la aceleracion del misil
                    misilAce = 5;
                    
 //                 Calcula el angulo de la nave para disparar en su dirección                   
                    double anguloMisilRadianes = Math.toRadians(anguloNave);
                    misilVelocidadX = Math.cos(anguloMisilRadianes) * misilAce;
                    misilVelocidadY = Math.sin(anguloMisilRadianes) * misilAce;
                    break;
                case RIGHT:
//                  Si se pulsa derecha la nave gira a una velocidad de 5 grados//
                    anguloVelNave = +5;
                    break;
                case LEFT:
//                    Si se pulsa izquierda la nave gira a una velocidad -5 grados//                   
                    anguloVelNave = -5;
                    break;
                case UP:
//                    Cuando pulsa arriba la nave acelera y modifica la velocidad dependiendo del ángulo
                    naveAceleracion = 0.5;
                    //Si se pulsa arriba la nave avanza segun donde este mirando// 
                    moverAngulo();
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
                case ENTER:
//                    reinicioPartida();
            }
        });
//        Soltar boton
        scene.setOnKeyReleased((KeyEvent event) -> {
            switch(event.getCode()){
                case LEFT:
                    anguloVelNave = 0;
                case RIGHT:
                    anguloVelNave = 0;
            }
        });
    }
    public void moverAngulo(){
        double anguloNaveRadianes = Math.toRadians(anguloNave);
        naveVelocidadX += Math.cos(anguloNaveRadianes) * naveAceleracion;
        naveVelocidadY += Math.sin(anguloNaveRadianes) * naveAceleracion;
    }
    public void movimientoNave(){
        //La nave se mueve en X constantemente
        posNaveX += naveVelocidadX;
        //La nave se mueve en Y constantemente  
        posNaveY += naveVelocidadY; 
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
    public void reinicioPartida(){
        posNaveX = SCENE_TAM_X/2;
        posNaveY = SCENE_TAM_Y/2;
        root.getChildren().add(navePoligono);
    }
}