package controle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import modelo.Processo;
/**
 * Classe singletoon que simula uma CPU. Seu único método é simular, que dada uma fila ordenada de processos,
 * Irá, analisa-los e retornar uma fila eficiente, com suas saidas.
 */
public class Simulador {

    private static Simulador instancia;

    private Simulador() {

    }

    public static Simulador getInstancia() {
        if (instancia == null) {
            instancia = new Simulador();
            return instancia;
        } else {
            return instancia;
        }
    }
/**
 * Método que irá simular o ambiente multi progamação.  Recebe um fila ordenada de processos por tempo de chegada.
 * e os analisa. Os processos são modificados, ganhando tempo de saida, e tendo seu tempo de execução modificados.
 * 
 * 
     * @param pExecutar
     * @param ociosidade
     * @return Uma fila com o resultado de tempo de saida dos processos.
 */
    public List<Processo> simular(PriorityQueue<Processo> pExecutar, double ociosidade) {
        HashMap<Integer, BigDecimal> ociosidades = new HashMap<>();
        List<Processo> fila = new ArrayList<>();
        List<Processo> resultado = new ArrayList<>();
        Processo exclusivo = null;
        boolean teste = true;

        BigDecimal tempoTotal = BigDecimal.ZERO;
        for (int i = 1; i <= pExecutar.size(); i++) {
            BigDecimal ocCpu = new BigDecimal(Math.pow(ociosidade, i));
            BigDecimal tCpu = BigDecimal.ONE.subtract(ocCpu);
            ociosidades.put(i, (tCpu.divide(new BigDecimal(i), 2)));
        }

        while (teste) {
            //System.out.println("entrou");
            tempoTotal = tempoTotal.add(BigDecimal.ONE);
            //System.out.println(pExecutar.peek().getTempoChegada().compareTo(tempoTotal));
            if (!pExecutar.isEmpty()) {
                if (pExecutar.peek().getTempoChegada().compareTo(tempoTotal) <= 0) {
                    //System.out.println("fez checagem");
                    fila.add(pExecutar.poll());
                }
            }

            for (Processo p : fila) {
                //System.out.println(p.getTempoExecucao());
               // System.out.println((ociosidades.get(fila.size()).multiply(BigDecimal.ONE)));
                p.setTempoExecucao(p.getTempoExecucao().subtract(ociosidades.get(fila.size()).multiply(BigDecimal.ONE)));
                //System.out.println(p.getTempoExecucao() - (ociosidades.get(fila.size())));
//System.out.println(ociosidades.get(fila.size()));
               // System.out.println(p.getNome()+" "+p.getTempoExecucao());
                if (p.getTempoExecucao().compareTo(BigDecimal.ZERO) <= 0 && !p.isProcessado()) {
                    //System.out.println("Checou");
                    p.setSaida(tempoTotal);
                    p.setProcessado(true);
                    resultado.add(p);
                    exclusivo = p;

                }
            }
            if (exclusivo != null) {
                fila.remove(exclusivo);
                exclusivo = null;
            }

            if (pExecutar.isEmpty() & fila.isEmpty()) {
                teste = false;
            }
        }

        return resultado;

    }
}
