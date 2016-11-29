package br.pucminas.otimizacao.model;

import java.util.List;

/**
 * Created by josue on 11/28/16.
 */
public class Constraint {

    private String name;
    private String type;
    private Double bound;
    private List<Variable> variables;

    public Constraint(String name, String type, Double bound, List<Variable> variables) {
        this.name = name;
        this.type = type;
        this.bound = bound;
        this.variables = variables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBound() {
        return bound;
    }

    public void setBound(Double bound) {
        this.bound = bound;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }
}
