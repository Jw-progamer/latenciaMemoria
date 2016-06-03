/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author tenshou
 */
public class ModeloProcesso {

    Processo original;
    SimpleStringProperty nomeIlustrado,
            chegadaIlustrada,
            execucaoIlustrada;

    public ModeloProcesso(Processo processo) {
        this.original = processo;
        this.nomeIlustrado = new SimpleStringProperty(original.getNome());
        this.chegadaIlustrada = new SimpleStringProperty(original.getTempoChegada().toString());
        this.execucaoIlustrada = new SimpleStringProperty(original.getTempoChegada().toString());
    }

}
