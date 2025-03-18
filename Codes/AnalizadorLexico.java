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
    private void convertir_a_tokens(String codigo) {
        tokens.clear();
        codigo = codigo.replace("(", " ( ").replace(")", " ) ").trim();
        String[] partes = codigo.split("\\s+");

        tokens.addAll(Arrays.asList(partes));
    }

    public List<String> getTokens() {
        return tokens;
    }
}


