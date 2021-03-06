/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialogos;

import java.math.BigDecimal;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import modelo.Processo;

/**
 *
 * @author tenshou
 */
public class InserirProcesso extends Dialog<Processo> {

    public InserirProcesso(boolean tempo) {
        setTitle("Inserir Processo");
        setHeaderText("Digite as informações para o novo processo.");

        Label nome = new Label("Nome: ");
        Label tempoChegada = new Label("Tempo de chegada: ");
        Label tempoSaida = new Label("Tempo de execução: ");
        TextField nomet = new TextField();
        TextField chegadat = new TextField();
        TextField execucaot = new TextField();

        GridPane dialogoRoot = new GridPane();
        dialogoRoot.add(nome, 1, 1);
        dialogoRoot.add(nomet, 2, 1);
        dialogoRoot.add(tempoChegada, 1, 2);
        dialogoRoot.add(chegadat, 2, 2);
        dialogoRoot.add(tempoSaida, 1, 3);
        dialogoRoot.add(execucaot, 2, 3);

        getDialogPane().setContent(dialogoRoot);

        ButtonType tipoOk = new ButtonType("Inserir processo", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(tipoOk);

        setResultConverter(new Callback<ButtonType, Processo>() {
            @Override
            public Processo call(ButtonType param) {
                if (param == tipoOk) {
                    
                    try {
                        if (tempo) {
                            Processo p = new Processo(nomet.getText(), chegadat.getText(), execucaot.getText());
                            p.setTempoChegada(p.getTempoChegada().multiply(new BigDecimal("60")));
                            p.setTempoExecucao(p.getTempoExecucao().multiply(new BigDecimal("60")));
                            return p;
                        } else {
                            return new Processo(nomet.getText(), chegadat.getText(), execucaot.getText());
                        }
                    } catch (NumberFormatException e) {
                        close();
                        Alert alerta = new Alert(Alert.AlertType.WARNING);
                        alerta.setTitle("Erro ao inserir processo");
                        alerta.setHeaderText("Erro. Você digitou um valor vazio, ou inválido nos campos de tempo.\n Por favor, insira apenas números nos campos de tempo.");
                        alerta.show();
                    }

                }

                return null;
            }
        });
    }

}
