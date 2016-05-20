/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import dialogos.InserirProcesso;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import modelo.Processo;

/**
 *
 * @author tenshou
 */
public class TesteDialogo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        InserirProcesso dialogo = new InserirProcesso(false);
        
        Optional<Processo> novoProcesso =  dialogo.showAndWait();
        if(novoProcesso.isPresent()){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setResizable(true);
            alerta.setContentText("o processo "+ novoProcesso + " Foi criado. suas informações\n" + novoProcesso.get().getNome() + novoProcesso.get().getTempoChegada()+ novoProcesso.get().getTempoExecucao());
            alerta.showAndWait();
        }
        
        Heranca teste = new Heranca();
       // teste.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
