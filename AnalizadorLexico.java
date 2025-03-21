import java.io.*;
import java.util.*;

public class AnalizadorLexico {
    public Environment entorno;
    private List<String> tokens;

    public AnalizadorLexico(Environment entorno) {
        this.entorno = entorno;
        this.tokens = new ArrayList<>();
    }

    public void leerCodigo(String nombreArchivo) throws IOException {
        StringBuilder codigo = new StringBuilder();
        
        try (BufferedReader br = new BufferedReader(new FileReader("nombre del archivo"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                codigo.append(linea).append(" ");
            }
        }

        convertir_a_tokens(codigo.toString().trim());
    }



    // Método para dividir el código en tokens
    void convertir_a_tokens(String codigo) {
        tokens.clear();
        codigo = codigo.replace("(", " ( ").replace(")", " ) ").trim();
        String[] partes = codigo.split("\\s+");

        tokens.addAll(Arrays.asList(partes));
    }

    public List<String> getTokens() {
        return tokens;
    }

    public boolean ExpresionValida() {
        if (tokens.isEmpty()) return false;
        
        String tipoFuncion = tokens.get(1);

        switch (tipoFuncion) {
            case "defun":
                return validarDefun();
            case "setq":
                return validarSetq();
            case "quote":
                return validarQuote();
            case "cond":
                return validarCond();
            case "+": case "-": case "*": case "/":
                return validarOperacion();
            case "equal": case "<": case ">": case "atom": case "list":
                return validarPredicados();
            default:
                return false;
        }
    }

    //Revisar si la estructura de un defun esta bien
    private boolean validarDefun() {
        //Si tiene menos de 5 tokens no tiene la estructura correcta
        if(tokens.size() < 5){
            return false;
        }
        //Verifica que empiece con parentesis
        if(!tokens.get(0).equals("(")){
            return false;
        }
        //Verifica que haya parentesis antes de los parametros
        if(!tokens.get(3).equals("(")){
            return false;
        }
        //Verifica que haya parentesis despues de los parametros
        int parentesisParametros = -1;
        for (int i = 4; i < tokens.size(); i++) {
            if (tokens.get(i).equals(")")) {
                parentesisParametros = i;
                break;
            }
        }
        //Si no hay parentesis es falso
        if(parentesisParametros == -1) {
            return false;
        }
        // Verificar que el parentesis de los parametros no sea el ultimo token
        if(parentesisParametros + 1 >= tokens.size() - 1) {
            return false;
        }
        //Verificar que el cuerpo este entre parentesis
        if(!tokens.get(parentesisParametros + 1).equals("(")) {
            return false;
        }
        //Verificar que el cuerpo tenga parentesis de cierre
        int parentesisCuerpo = -1;
        for (int i = parentesisParametros + 2; i < tokens.size(); i++) {
            if (tokens.get(i).equals(")")) {
                parentesisCuerpo = i;
            }
        }//Si no hay parentesis es falso
        if (parentesisCuerpo == -1) {
            return false;
        }
        //Verificar parentesis final
        if(!tokens.getLast().equals(")")){
            return false;
        }
        //Obtener nombre y parametros de la funcion (para que el usuario defina y llame a una funcion)
        String nombreFuncion = tokens.get(2);
        List<String> parametros = new ArrayList<>();
        int i = 4;
        while (i < tokens.size() && !tokens.get(i).equals(")")) {
            if (!tokens.get(i).equals("(")) {
                parametros.add(tokens.get(i));
            }
            i++;
        }

        // Obtener el cuerpo (todo lo que hay después hasta antes del paréntesis final)
        List<String> cuerpo = tokens.subList(i + 1, tokens.size() - 1);

        // Guardar la función en el entorno
        entorno.definirFuncion(nombreFuncion, parametros, cuerpo);
    return true;
    }

    //Revisar si la estructura de un setq esta bien
    private boolean validarSetq(){
        //Verificar que empiece con parentesis
        if(!tokens.get(0).equals("(")){
            return false;
        }
        //Si hay menos de 4 tokens la estructura no es correcta.
        if(tokens.size() < 4){
            return false;
        }
        //Verificar parentesis final
        if(!tokens.getLast().equals(")")){
            return false;
        }
    return true;
    }

    //Revisar la estructura de un quote
    private boolean validarQuote(){
        // Verificar que haya 4 tokens
        if (tokens.size() != 4) {
            return false;
        }
        //Verificar que empiece con parentesis
        if(!tokens.get(0).equals("(")){
            return false;
        }
        //Verificar parentesis final
        if(!tokens.getLast().equals(")")){
            return false;
        }
    return true;
    }

    //Revisar cond
    private boolean validarCond(){
        // Si hay menos de 5 tokens la estructura no es correcta
        if (tokens.size() < 5) {
            return false;
        }
        //Verificar que empiece con parentesis
        if(!tokens.get(0).equals("(")){
            return false;
        }
        //Verificar parentesis final
        if(!tokens.getLast().equals(")")){
            return false;
        }
        
        //Verificar que haya una condicion en medio dentro de parentesis
        boolean condicionValida = false;

        for (int i = 2; i < tokens.size() - 1; i++) {
            if (tokens.get(i).equals("(")) {
                
                for (int j = i + 1; j < tokens.size() - 1; j++) {
                    if (tokens.get(j).equals(")")) {
                        condicionValida = true;
                        break;
                    }
                }
                break;
            }
            return condicionValida;
        }
    return true;
    }

    //Revisar operaciones aritmeticas
    private boolean validarOperacion() {
        // Si no hay al menos 4 tokens la estructura esta mal
        if (tokens.size() < 5) {
            return false;
        }
        //Verificar parenteisis inicial
        if (!tokens.get(0).equals("(")) {
            return false;
        }
        //Verificar parentesis de cierre
        if (!tokens.getLast().equals(")")) {
            return false;
        }
        return true;
    }

    //Revisar predicados
    private boolean validarPredicados(){
        //Verificar parenteisis inicial
        if (!tokens.get(0).equals("(")) {
            return false;
        }
        //Verificar parentesis de cierre
        if (!tokens.getLast().equals(")")) {
            return false;
        }
        //Revisar la cantidad de tokens dependiendo del predicado
        String predicado = tokens.get(1);
        if(predicado.equals("equal") || predicado.equals("<") || predicado.equals(">")){
            if(tokens.size() != 5){
                return false;
            }
        }
        else if(predicado.equals("atom") || predicado.equals("list")){
            if(tokens.size() != 4){
                return false;
            }
        }
    return true;
    }
}


