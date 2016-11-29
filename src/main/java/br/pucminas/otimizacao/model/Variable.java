package br.pucminas.otimizacao.model;

/**
 * Created by josue on 11/28/16.
 */
public class DecisionVariable {

    private String name;
    private Double coeficient;

    public DecisionVariable(String name, Double coeficient) {
        this.name = name;
        this.coeficient = coeficient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCoeficient() {
        return coeficient;
    }

    public void setCoeficient(Double coeficient) {
        this.coeficient = coeficient;
    }
}
