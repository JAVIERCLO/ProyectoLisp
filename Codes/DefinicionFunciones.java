package Codes;

import java.util.*;

public class DefinicionFunciones {
    // Clase interna para almacenar definiciones de funciones
    public static class FunctionDef {
        public List<String> params;
        public Object body;

        public FunctionDef(List<String> params, Object body) {
            this.params = params;
            this.body = body;
        }
    }

    // Define una función en el entorno
    public static void defineFunction(Environment env, String name, List<String> params, Object body) {
        env.functions.put(name, new FunctionDef(params, body));
    }
}