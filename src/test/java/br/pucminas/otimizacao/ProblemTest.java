package br.pucminas.otimizacao;

import br.pucminas.otimizacao.model.Constraint;
import br.pucminas.otimizacao.model.ProblemType;
import br.pucminas.otimizacao.model.Variable;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by josue on 11/28/16.
 */
public class ProblemTest {

    @org.junit.Test
    public void solveTest1() throws Exception {
        /**
         * With x,y being integers
         * F.O Z → MIN Z = x1 + 2x2
         *
         * c1: 8x1 + 2x2 ≥ 16
         * c2: x1 + x2 ≤ 6
         * c3: 2x1 + 7x2 ≥ 28
         *
         * Resposta: Z = 8, 46; x1 = 1, 07; x2 = 3, 69; x3 = 0; x4 = 1, 23; x5 = 0
         */
        Variable x1 = new Variable("x1", 1.0);
        Variable x2 = new Variable("x2", 2.0);

        Variable c1x1 = new Variable("x1", 8.0);
        Variable c1x2 = new Variable("x2", 2.0);

        Variable c2x1 = new Variable("x1", 1.0);
        Variable c2x2 = new Variable("x2", 1.0);

        Variable c3x1 = new Variable("x1", 2.0);
        Variable c3x2 = new Variable("x2", 7.0);

        ArrayList<Variable> objective = new ArrayList();
        objective.add(x1);
        objective.add(x2);

        ArrayList<Variable> c1Vars = new ArrayList();
        c1Vars.add(c1x1);
        c1Vars.add(c1x2);
        Constraint c1 = new Constraint("c1", ">=", 16.0, c1Vars);

        ArrayList<Variable> c2Vars = new ArrayList();
        c2Vars.add(c2x1);
        c2Vars.add(c2x2);
        Constraint c2 = new Constraint("c2", "<=", 6.0, c2Vars);

        ArrayList<Variable> c3Vars = new ArrayList();
        c3Vars.add(c3x1);
        c3Vars.add(c3x2);
        Constraint c3 = new Constraint("c3", ">=", 28.0, c3Vars);

        ArrayList<Constraint> constraints = new ArrayList();
        constraints.add(c1);
        constraints.add(c2);
        constraints.add(c3);

        Problem p = new Problem("MIN", objective, constraints);
        p.solve();

        assertEquals(8.461538461538462, p.getValue("z"), 0.001);
        assertEquals(1.0769230769230769, p.getValue("x1"), 0.001);
        assertEquals(3.6923076923076925, p.getValue("x2"), 0.001);
    }

}