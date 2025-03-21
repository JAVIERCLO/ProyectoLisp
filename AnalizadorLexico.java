import java.util.*;

public class AnalizadorLexico {
    private Environment entorno;
    public List<String> tokens = new ArrayList<>();

    public AnalizadorLexico(Environment entorno) {
        this.entorno = entorno;
    }

    public void convertir_a_tokens(String expresion) {
        tokens.clear();
        StringBuilder token = new StringBuilder();
        for (char c : expresion.toCharArray()) {
            if (c == '(' || c == ')') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else if (Character.isWhitespace(c)) {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
            } else {
                token.append(c);
            }
        }
        if (token.length() > 0) {
            tokens.add(token.toString());
        }
    }

    public Object ExpresionValida() {
        if (tokens.isEmpty()) return false;

        // ✅ Verificar si es una función definida antes del switch
        if (tokens.size() >= 2 && entorno.funcionExiste(tokens.get(1))) {
            return evaluarLlamadaFuncion(tokens.get(1));
        }

        String tipoFuncion = tokens.get(1);

        switch (tipoFuncion) {
            case "defun": return validarDefun();
            case "setq": return evaluarSetq();
            case "quote": return evaluarQuote();
            case "cond": return evaluarCond();
            case "+": case "-": case "*": case "/": return evaluarOperacionAritmetica();
            case "equal": case "<": case ">": case "atom": case "list": return evaluarPredicados();
            default: return "No se reconoce la expresion";
        }
    }

    private boolean validarDefun() {
        if (tokens.size() < 7) return false;
        if (!tokens.get(0).equals("(")) return false;
        if (!tokens.get(1).equals("defun")) return false;
        if (!tokens.get(3).equals("(")) return false;

        int indexCierreParametros = -1;
        for (int i = 4; i < tokens.size(); i++) {
            if (tokens.get(i).equals(")")) {
                indexCierreParametros = i;
                break;
            }
        }

        if (indexCierreParametros == -1 || indexCierreParametros + 1 >= tokens.size()) return false;
        if (!tokens.get(indexCierreParametros + 1).equals("(")) return false;

        int indexCierreCuerpo = -1;
        for (int i = indexCierreParametros + 2; i < tokens.size(); i++) {
            if (tokens.get(i).equals(")")) {
                indexCierreCuerpo = i;
            }
        }

        if (indexCierreCuerpo == -1) return false;
        if (!tokens.get(tokens.size() - 1).equals(")")) return false;

        String nombreFuncion = tokens.get(2);
        List<String> parametros = new ArrayList<>();
        for (int i = 4; i < indexCierreParametros; i++) {
            parametros.add(tokens.get(i));
        }

        List<String> cuerpo = new ArrayList<>();
        for (int i = indexCierreParametros + 2; i < tokens.size() - 1; i++) {
            cuerpo.add(tokens.get(i));
        }

        entorno.definirFuncion(nombreFuncion, parametros, cuerpo);
        return true;
    }

    private Object evaluarSetq() {
        if (tokens.size() != 5) return "Error: setq requiere una variable y un valor.";
        String variable = tokens.get(2);
        Object valor = parseValor(tokens.get(3));
        entorno.variables.put(variable, valor);
        return valor;
    }

    private Object evaluarOperacionAritmetica() {
        if (tokens.size() < 5) return "Error: Se requieren al menos dos operandos.";

        String operador = tokens.get(1);
        double resultado = parseValor(tokens.get(2));

        for (int i = 3; i < tokens.size() - 1; i++) {
            double valor = parseValor(tokens.get(i));
            switch (operador) {
                case "+": resultado += valor; break;
                case "-": resultado -= valor; break;
                case "*": resultado *= valor; break;
                case "/":
                    if (valor == 0) return "Error: Division por cero.";
                    resultado /= valor;
                    break;
            }
        }
        return resultado;
    }

    private Object evaluarQuote() {
        if (tokens.size() < 4) {
            return "Una expresion quote debe tener un argumento.";
        }

        StringBuilder resultado = new StringBuilder();
        for (int i = 2; i < tokens.size() - 1; i++) {
            resultado.append(tokens.get(i)).append(" ");
        }

        return resultado.toString().trim();
    }

    private Object evaluarPredicados() {
        String predicado = tokens.get(1);

        if (predicado.equals("equal") || predicado.equals("<") || predicado.equals(">")) {
            if (tokens.size() != 5) return "Este predicado requiere dos argumentos.";

            double a = parseValor(tokens.get(2));
            double b = parseValor(tokens.get(3));

            switch (predicado) {
                case "equal": return a == b;
                case "<": return a < b;
                case ">": return a > b;
            }
        }

        if (predicado.equals("atom")) {
            if (tokens.size() != 4) return "Este predicado requiere un argumento.";
            String valor = tokens.get(2);
            return !valor.startsWith("(");
        }

        if (predicado.equals("list")) {
            StringBuilder lista = new StringBuilder();
            for (int i = 2; i < tokens.size() - 1; i++) {
                lista.append(tokens.get(i)).append(" ");
            }
            return "(" + lista.toString().trim() + ")";
        }

        return "Predicado no valido.";
    }

    private Object evaluarCond() {
        int i = 2;
        while (i < tokens.size()) {
            if (tokens.get(i).equals("(")) {
                int j = i + 1;
                int balance = 1;
                StringBuilder condicionStr = new StringBuilder();
                condicionStr.append("(");
                while (j < tokens.size() && balance > 0) {
                    condicionStr.append(tokens.get(j)).append(" ");
                    if (tokens.get(j).equals("(")) balance++;
                    if (tokens.get(j).equals(")")) balance--;
                    j++;
                }

                AnalizadorLexico analizadorCond = new AnalizadorLexico(entorno);
                analizadorCond.convertir_a_tokens(condicionStr.toString().trim());
                Object resultadoCond = analizadorCond.ExpresionValida();

                if (resultadoCond instanceof Boolean && (Boolean) resultadoCond) {
                    if (j < tokens.size() - 1) {
                        String valor = tokens.get(j);
                        return parseValor(valor);
                    }
                }

                i = j + 1;
            } else {
                i++;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private Object evaluarLlamadaFuncion(String nombre) {
        DefinicionFunciones funcion = entorno.obtenerFuncion(nombre);

        if (funcion == null) {
            return "La funcion no esta definida.";
        }

        if (tokens.size() - 3 != funcion.params.size()) {
            return "Numero incorrecto de argumentos.";
        }

        Environment entornoLocal = new Environment();
        entornoLocal.variables.putAll(entorno.variables);
        entornoLocal.functions.putAll(entorno.functions); // ✅ Soporta recursividad

        for (int i = 0; i < funcion.params.size(); i++) {
            entornoLocal.variables.put(funcion.params.get(i), parseValor(tokens.get(i + 2)));
        }

        AnalizadorLexico analizador = new AnalizadorLexico(entornoLocal);
        analizador.convertir_a_tokens(String.join(" ", (List<String>) funcion.body));
        return analizador.ExpresionValida();
    }

    private double parseValor(String valor) {
        if (entorno.variables.containsKey(valor)) {
            try {
                return Double.parseDouble(entorno.variables.get(valor).toString());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
