/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *Classe principal da apkicação FX
 * @author tenshou
 */
public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        /**
         * Classe principal do progama fx. Irá instanciar a tela principal
         */
        VBox principal =(VBox)FXMLLoader.load(getClass().getResource("/fxml/TelaSimulador.fxml"));
        Scene root = new Scene(principal);
        primaryStage.setScene(root);
        primaryStage.setTitle("Simulador multiprogamação");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
