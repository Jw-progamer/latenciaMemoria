/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import java.math.BigDecimal;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import modelo.Processo;

/**
 * FXML Controller class
 *
 * @author tenshou
 */
public class TelaSimuladorController implements Initializable {
    
    ToggleGroup unidade = new ToggleGroup();
    List<Processo> listaProcesso = new LinkedList<>();
    
    @FXML
    Button botao;
    
    @FXML
    RadioButton minuto, segundo;
    
    @FXML
    LineChart<String, BigDecimal> grafico;
    
    @FXML
    TableView<Processo> tabela;
    
    @FXML
    TableColumn cnome,cchegada,cprocesso;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        minuto.setToggleGroup(unidade);
        segundo.setToggleGroup(unidade);
        //List<Processo> listaProcesso = new LinkedList<>();
        Processo a = new Processo("A", "30", "120");
        listaProcesso.add(a);
        Processo b = new Processo("B", "50", "90");
        listaProcesso.add(b);
        Processo c = new Processo("C", "30", "100");
        listaProcesso.add(c);
        
        tabela.setItems(FXCollections.observableArrayList(listaProcesso));
        
       
    } 
    
    public void addProcesso(){
        Processo d = new Processo("D", "60.5", "300");
        listaProcesso.add(d);
        tabela.setItems(FXCollections.observableArrayList(listaProcesso));
    }
    
}
