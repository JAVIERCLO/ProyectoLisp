import java.io.*;
import java.util.*;

public class AnalizadorLexico {
    
    private List<String> tokens;

    public AnalizadorLexico() {
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
        //Si el codigo tiene menos de 5 tokens la funcion no esta bien declarada
        if (tokens.size() < 5) {
            return false;
        }
        //Una funcion debe empezar con (defun
        if (!tokens.get(0).equals("(") || !tokens.get(1).equals("defun")) {
            return false;
        }
        //Verifica el parentesis de cierre
        int ultimoToken = tokens.lastIndexOf(")");
        if (ultimoToken != tokens.size() - 1) {
            return false;
        }
        //Verifica ue todos los parametros esten dentro de un parentesis
        if (!tokens.get(3).equals("(")) {
            return false;
        }

        int indexParametrosCierre = -1;
        for (int i = 4; i < tokens.size(); i++) {
            if (tokens.get(i).equals(")")) {
                indexParametrosCierre = i;
                break;
            }
        }

        // Verifica que el cuerpo esté despues de los parámetros
        if (indexParametrosCierre + 1 >= tokens.size() - 1) {
            return false;
        }

        return true;
    }
}


