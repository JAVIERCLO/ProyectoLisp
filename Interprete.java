import java.util.Scanner;
public class Interprete {
public static void main(String[] args) {
        AnalizadorLexico analizador = new AnalizadorLexico();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese una expresión en LISP:");
        String expresionLisp = scanner.nextLine();

        analizador.convertir_a_tokens(expresionLisp);

        System.out.println("Tokens obtenidos: " + analizador.getTokens());

        boolean esValida = analizador.ExpresionValida();
        if (esValida) {
            System.out.println("La expresión LISP es válida.");
        } else {
            System.out.println("La expresión LISP NO es válida.");
        }

        scanner.close();
    }
}
