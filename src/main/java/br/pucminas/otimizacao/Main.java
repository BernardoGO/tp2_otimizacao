package br.pucminas.otimizacao;

import org.gnu.glpk.GLPK;

public class Main {

    public static void main(String[] args) {
        System.out.println(GLPK.glp_version());
    }
}
