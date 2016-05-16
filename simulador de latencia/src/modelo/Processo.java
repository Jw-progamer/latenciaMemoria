package modelo;

import java.math.BigDecimal;
import java.math.MathContext;
/**
 * Classe modelo de processo. Representa um processo a ser analisado na CPU. a Idéia é que essa classe seja 
 * modificada ao longo da execuçãodo progama.
 */
public class Processo implements Comparable<Processo> {

    String nome;
    BigDecimal tempoChegada, tempoExecucao, chegada, saida;

    boolean processado;

    public Processo(String nome, String tempoChegada, String tempoExecucao) {
        super();
        this.nome = nome;
        this.tempoChegada = new BigDecimal(tempoChegada,MathContext.UNLIMITED);
        this.tempoExecucao = new BigDecimal(tempoExecucao,MathContext.UNLIMITED);
        //	this.chegada = 
        this.saida = BigDecimal.ZERO;
        this.processado = false;
    }

    public void setChegada(BigDecimal chegada) {
        this.chegada = chegada;
    }

    public void setSaida(BigDecimal saida) {
        this.saida = saida;
    }

    public BigDecimal getChegada() {
        return chegada;
    }

    public BigDecimal getSaida() {
        return saida;
    }

    public boolean isProcessado() {
        return processado;
    }

    public void setProcessado(boolean processado) {
        this.processado = processado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getTempoChegada() {
        return tempoChegada;
    }

    public void setTempoChegada(BigDecimal tempoChegada) {
        this.tempoChegada = tempoChegada;
    }

    public BigDecimal getTempoExecucao() {
        return tempoExecucao;
    }

    public void setTempoExecucao(BigDecimal tempoExecucao) {
        this.tempoExecucao = tempoExecucao;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.nome;
    }

    @Override
    public int compareTo(Processo o) {
        return this.tempoChegada.compareTo(o.tempoChegada);
    }

}
