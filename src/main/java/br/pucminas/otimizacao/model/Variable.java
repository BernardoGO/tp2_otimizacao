package br.pucminas.otimizacao.model;

/**
 * Created by josue on 11/28/16.
 */
public class Variable {

    private String name;
    private Double coefficient;

    public Variable(String name, Double coefficient) {
        this.name = name;
        this.coefficient = coefficient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }
}
