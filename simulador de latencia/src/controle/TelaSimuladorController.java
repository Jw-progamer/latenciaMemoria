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
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.BigDecimalStringConverter;
import modelo.ModeloProcesso;
import modelo.Processo;

/**
 * FXML Controller class
 *
 * @author tenshou
 */
public class TelaSimuladorController implements Initializable {

    ToggleGroup unidade = new ToggleGroup();
    List<Processo> listaProcesso = new LinkedList<>();
    boolean comeco = false;

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

        cnome.setCellFactory(TextFieldTableCell.forTableColumn());
        cchegada.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        cprocesso.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));

    }

    public void addProcesso() {
        InserirProcesso dialogo = null;
        try {
            if (minuto.isSelected()) {
                dialogo = new InserirProcesso(true);
            }
            if (segundo.isSelected()) {
                dialogo = new InserirProcesso(false);
            }

            Optional<Processo> pp = dialogo.showAndWait();
            for (Processo p : listaProcesso) {
                if (p.getNome().equals(pp.get().getNome())) {
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("Problema ao inserir processo");
                    alerta.setHeaderText("Desculpe. Você não pode inserir mais de um processo com mesmo nome.");
                    alerta.show();

                    return;
                }

            }
            listaProcesso.add(pp.get());
            // modelosDeProcesso.add(new ModeloProcesso(pp.get()));
            tabela.setItems(FXCollections.observableArrayList(listaProcesso));
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro ao inserir processo");
            alerta.setHeaderText("O processo nao  foi inserido corretamente\nocorreu o seguinte: " + e.getMessage());
            //alerta.show();
        }
    }

    public void callSimulacao() {
        comeco = true;
        Simulador cpu = Simulador.getInstancia();
        PriorityQueue<Processo> fila = new PriorityQueue<>();
        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();
        LineChart<BigDecimal, String> grafico = new LineChart(xAxis, yAxis);
        xAxis.setLabel("Tempo(Em segundos)");
        yAxis.setLabel("Processos");
        Double ociosidade = null;
        try {
            ociosidade = Double.parseDouble(this.ociosidade.getText());
        } catch (NumberFormatException e) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Problema ao iniciar a simulação");
            alerta.setHeaderText("Verifique se a porcentagem do ociosidade é válida, ou não estar vazia.");
            alerta.show();
            return;
        }

        if (ociosidade >= 1 || ociosidade < 0) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Problema ao iniciar a simulação");
            alerta.setHeaderText("Você não pode começar a ociosidade em 1(100%). Digite um valor entre 0 e 0.9999...");
            alerta.show();
            return;
        }

        listaProcesso.stream().forEach((p) -> {
            fila.add(p);
        });

        List<Processo> processosEncerrados = cpu.simular(fila, ociosidade);

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

    public void callUpdate(TableColumn.CellEditEvent event) {
        Processo p = (Processo) event.getRowValue();
        try {
            if (p.getNome().equals(event.getOldValue())) {

                for (Processo po : listaProcesso) {
                    if (po.getNome().equals(event.getNewValue())) {
                        Alert alerta = new Alert(Alert.AlertType.WARNING);
                        alerta.setTitle("Problema ao inserir processo");
                        alerta.setHeaderText("Desculpe. Você não pode deiar mais de dois processo com mesmo nome.\n"
                                + "Se deseja trocar o nome do processo, corriga. senão, tente editar novamente para\n "
                                + "voltar ao estado original");
                        alerta.show();
                        //refrescar();
                        return;
                    }
                }
                p.setNome((String) event.getNewValue());

            } else if (p.getTempoChegada().equals(event.getOldValue())) {
                if (minuto.isSelected()) {
                    BigDecimal d = (BigDecimal) event.getNewValue();
                    BigDecimal dd = d.multiply(new BigDecimal("60"));
                    p.setTempoChegada(dd);
                } else {
                    p.setTempoChegada((BigDecimal) event.getNewValue());
                }
            } else if (minuto.isSelected()) {
                BigDecimal d = (BigDecimal) event.getNewValue();
                BigDecimal dd = d.multiply(new BigDecimal("60"));
                p.setTempoExecucao(dd);
            } else {
                p.setTempoExecucao((BigDecimal) event.getNewValue());
            }

            if (comeco) {
                callSimulacao();
            }

        } catch (NumberFormatException e) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Problema ao iniciar a simulação");
            alerta.setHeaderText("Você digitou uma letra no lugar de número, quando editou os processos. Corriga\n"
                    + "se deseja não modificar, apenas tente editar o processo para voltar ao estado anterior");
            alerta.show();
            return;
        }

    }

    public void deletar() {
        Processo del = tabela.getSelectionModel().getSelectedItem();
        listaProcesso.remove(del);
        tabela.setItems(FXCollections.observableArrayList(listaProcesso));

        if (comeco) {
            callSimulacao();
        }
    }

}
