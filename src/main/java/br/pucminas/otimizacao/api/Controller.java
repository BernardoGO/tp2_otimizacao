package br.pucminas.otimizacao.api;

import br.pucminas.otimizacao.Problem;
import br.pucminas.otimizacao.utils.JsonTransformer;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by josue on 11/29/16.
 */
public class Controller {

    Gson gson;

    public Controller() {
        gson = new Gson();
    }

    public void startApi() {

        get("/", (req, res) -> "GLPK Simplex Solver");

        get("/hello", (req, res) -> "Hello World");

        post("/solve", "application/json", this::solve,  new JsonTransformer());
    }

    private Object solve(Request req, Response res) {
        Problem problem = gson.fromJson(req.body(), Problem.class);
        problem.solve();
        res.type("application/json");
        return problem.getSolution();
    }
}
