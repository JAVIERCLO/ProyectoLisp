import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Interprete {
    public static void main(String[] args) {
        Environment entorno = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(entorno);

        String nombreArchivo = "programa.lisp";

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            StringBuilder expresion = new StringBuilder();
            int balanceParentesis = 0;

            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                for (char c : linea.toCharArray()) {
                    if (c == '(') balanceParentesis++;
                    else if (c == ')') balanceParentesis--;
                    expresion.append(c);
                }
                expresion.append(" ");

                if (balanceParentesis == 0 && expresion.length() > 0) {
                    analizador.convertir_a_tokens(expresion.toString().trim());
                    System.out.println("Código LISP: " + expresion.toString().trim());
                    Object resultado = analizador.ExpresionValida();
                    System.out.println(">>> Resultado: " + resultado + "\n");
                    expresion.setLength(0);
                }
            }

            if (balanceParentesis != 0) {
                System.out.println("Error: Paréntesis desbalanceados en el archivo.");
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo '" + nombreArchivo + "': " + e.getMessage());
        }
    }
}
