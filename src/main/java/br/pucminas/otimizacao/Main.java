package br.pucminas.otimizacao;

import br.pucminas.otimizacao.api.Controller;
import org.gnu.glpk.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(GLPK.glp_version());
        new Controller().startApi();
    }
}
