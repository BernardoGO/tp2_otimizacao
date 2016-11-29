package br.pucminas.otimizacao;

import br.pucminas.otimizacao.model.Constraint;
import br.pucminas.otimizacao.model.ProblemType;
import br.pucminas.otimizacao.model.Variable;
import org.gnu.glpk.*;

import java.util.List;

import static org.gnu.glpk.GLPKConstants.GLP_DB;
import static org.gnu.glpk.GLPKConstants.GLP_FX;
import static org.gnu.glpk.GLPKConstants.GLP_LO;

/**
 * Created by josue on 11/28/16.
 */
public class Problem {

    private ProblemType type;
    private List<Variable> objective;
    private List<Constraint> constraints;
    private glp_prob lp;
    private glp_smcp parm;
    private SWIGTYPE_p_int ind;
    private SWIGTYPE_p_double val;
    private int ret;

    public Problem(ProblemType type, List<Variable> objective, List<Constraint> constraints) {
        this.type = type;
        this.objective = objective;
        this.constraints = constraints;
    }

    public Double getValue(String var) {
        int n = GLPK.glp_get_num_cols(lp);
        double val;
        String name;

        name = GLPK.glp_get_obj_name(lp);
        val = GLPK.glp_get_obj_val(lp);

        if (name.equals(var)) {
            return val;
        }

        for (int i = 1; i <= n; i++) {
            name = GLPK.glp_get_col_name(lp, i);
            val = GLPK.glp_get_col_prim(lp, i);

            if (name.equals(var)) {
                return val;
            }
        }

        return 0.0;
    }

    public void solve() {

        setupProblem();

        try {
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            if (ret == 0) {
                write_lp_solution(lp);
            } else {
                System.out.println("The problem could not be solved");
            }

            GLPK.glp_delete_prob(lp);
        } catch (GlpkException e) {
            e.printStackTrace();
        }
    }

    private void setupProblem() {
        lp = GLPK.glp_create_prob();
        System.out.println("Problem created");
        GLPK.glp_set_prob_name(lp, "myProblem");
        GLPK.glp_add_cols(lp, 2);

        int n = objective.size();

        for (int i = 1; i <= n; i++) {
            GLPK.glp_set_col_name(lp, i, objective.get(i-1).getName());
            GLPK.glp_set_col_kind(lp, i, GLPKConstants.GLP_IV);
            GLPK.glp_set_col_bnds(lp, i, GLPKConstants.GLP_LO, 0, 0);
        }

        defineConstraints();
        defineObjective();
    }

    private void defineObjective() {
        GLPK.glp_set_obj_name(lp, "z");

        if (type == ProblemType.MIN)
            GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MIN);
        else
            GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MAX);

        GLPK.glp_set_obj_coef(lp, 0, 0);

        GLPK.glp_set_obj_coef(lp, 0, 0);
        for (int i = 1; i <= objective.size(); i++) {
            GLPK.glp_set_obj_coef(lp, i, objective.get(i-1).getCoefficient());
        }
    }

    private void defineConstraints() {
        int n = constraints.size();
        GLPK.glp_add_rows(lp, n+1);

        for (int i = 1; i <= n; i++) {
            Constraint c =  constraints.get(i-1);

            GLPK.glp_set_row_name(lp, i, c.getName());

            if (c.getType().equals("<=")) {
                GLPK.glp_set_row_bnds(lp, i, GLP_DB, 0.0, c.getBound());
            } else if (c.getType().equals(">=")) {
                GLPK.glp_set_row_bnds(lp, i, GLP_LO, c.getBound(), 0.0);
            } else {
                GLPK.glp_set_row_bnds(lp, i, GLP_FX, c.getBound(), c.getBound());
            }

            List<Variable> vars = c.getVariables();
            int nc = c.getVariables().size();
            ind = GLPK.new_intArray(nc+1);
            val = GLPK.new_doubleArray(nc+1);

            for (int j = 1; j <= nc; j++) {
                GLPK.intArray_setitem(ind, j, j);
            }

            for (int j = 1; j <= nc; j++) {
                Variable var = vars.get(j - 1);
                GLPK.doubleArray_setitem(val, j, var.getCoefficient());
                GLPK.glp_set_mat_row(lp, i, nc, ind, val);
            }
        }

    }

    private void write_lp_solution(glp_prob lp) {
        int i;
        int n;
        String name;
        double val;
        name = GLPK.glp_get_obj_name(lp);
        val = GLPK.glp_get_obj_val(lp);
        System.out.print(name);
        System.out.print(" = ");
        System.out.println(val);
        n = GLPK.glp_get_num_cols(lp);
        for (i = 1; i <= n; i++) {
            name = GLPK.glp_get_col_name(lp, i);
            val = GLPK.glp_get_col_prim(lp, i);
            System.out.print(name);
            System.out.print(" = ");
            System.out.println(val);
        }
    }
}
