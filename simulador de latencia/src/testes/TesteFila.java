package classesDeTeste;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.PriorityQueue;

import modelo.Processo;
import controle.Simulador;

public class TesteFila {

    public static void main(String[] args) {
        

        Processo a = new Processo("A", "0", "240");
        Processo b = new Processo("B", "600", "180");
        Processo c = new Processo("C", "900", "120");
        Processo d = new Processo("D", "1200", "120");

        PriorityQueue<Processo> fila = new PriorityQueue<>();

        fila.add(a);
        System.out.println(fila.size());
        System.out.println(fila.peek());
        fila.add(b);
        System.out.println(fila.size());
        System.out.println(fila.peek());
        fila.add(c);
        System.out.println(fila.size());
        System.out.println(fila.peek());
        fila.add(d);
        System.out.println(fila.size());
        System.out.println(fila.peek());

        Simulador cpu = Simulador.getInstancia();

        List<Processo> resuktado = cpu.simular(fila, 0.6);
        
        System.out.println(resuktado.size());

        resuktado.stream().map((p) -> {
            System.out.println(p.getNome() + " " + p.getTempoExecucao() + " "+(p.getTempoChegada().divide(new BigDecimal("60"), (p.getTempoExecucao().compareTo(new BigDecimal("-1")) >= -1)?RoundingMode.UP:RoundingMode.DOWN)) +" "+ (p.getSaida().divide(new BigDecimal("60"), (p.getTempoExecucao().compareTo(new BigDecimal("-1")) != -1)?RoundingMode.UP:RoundingMode.DOWN)) + " " + p.isProcessado());
            return p;
        }).forEach((p) -> {
            System.out.println((p.getTempoExecucao().compareTo(new BigDecimal(-1))));
        });
    }

}
