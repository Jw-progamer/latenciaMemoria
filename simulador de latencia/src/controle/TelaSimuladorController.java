/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dialogos.InserirProcesso;
import java.math.BigDecimal;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import modelo.Processo;
import sun.misc.Queue;

/**
 * FXML Controller class
 *
 * @author tenshou
 */
public class TelaSimuladorController implements Initializable {

    ToggleGroup unidade = new ToggleGroup();
    List<Processo> listaProcesso = new LinkedList<>();

    @FXML
    Button botao, inicia;

    @FXML
    TextField ociosidade;

    @FXML
    RadioButton minuto, segundo;

    @FXML
    VBox root;

    @FXML
    TableView<Processo> tabela;

    @FXML
    TableColumn cnome, cchegada, cprocesso;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        minuto.setToggleGroup(unidade);
        segundo.setToggleGroup(unidade);

    }

    public void addProcesso() {
        InserirProcesso dialogo = null;
        if(minuto.isSelected())
            dialogo = new InserirProcesso(true);
        if(segundo.isSelected())
            dialogo = new InserirProcesso(false);
        try {
            Optional<Processo> pp = dialogo.showAndWait();
            listaProcesso.add(pp.get());
            tabela.setItems(FXCollections.observableArrayList(listaProcesso));
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro ao inserir processo");
            alerta.setHeaderText("O processo nao  foi inserido corretamente\nocorreu o seguinte: " + e.getMessage());
        }
    }

    public void callSimulacao() {
        
        Simulador cpu = Simulador.getInstancia();
        PriorityQueue<Processo> fila = new PriorityQueue<>();
        LineChart<BigDecimal, String> grafico = new LineChart(new NumberAxis(), new CategoryAxis());
        listaProcesso.stream().forEach((p) -> {
            fila.add(p);
        });
        List<Processo> processosEncerrados = cpu.simular(fila, Double.parseDouble(ociosidade.getText()));

        processosEncerrados.stream().map((p) -> {

            return p;
        }).map((p) -> {
            XYChart.Series serie = new XYChart.Series();
            serie.setName(p.getNome());
            serie.getData().add(new XYChart.Data(p.getTempoChegada().doubleValue(), p.getNome()));
            serie.getData().add(new XYChart.Data(p.getSaida().doubleValue(), p.getNome()));
            return serie;
        }).forEach((serie) -> {
            grafico.getData().add(serie);
        });
        if (root.getChildren().size() >= 2) {
            root.getChildren().remove(1);
            root.getChildren().add(grafico);
            
        } else {
            root.getChildren().add(grafico);
            
        }

    }

}
