import java.util.List;

public class Expresion {
    private String tipo;
    private List<String> operandos;

    public Expresion(String tipo, List<String> operandos) {
        this.tipo = tipo;
        this.operandos = operandos;
    }

    // Método para evaluar la expresión
    public double evaluar() {
        double resultado = Double.parseDouble(operandos.get(0)); // Primer número

        for (int i = 1; i < operandos.size(); i++) {
            double valor = Double.parseDouble(operandos.get(i));

            switch (tipo) {
                case "+":
                    resultado += valor;
                    break;
                case "-":
                    resultado -= valor;
                    break;
                case "*":
                    resultado *= valor;
                    break;
                case "/":
                    if (valor != 0) {
                        resultado /= valor;
                    } else {
                        System.out.println("Error: División por cero.");
                        return 0;
                    }
                    break;
            }
        }
        return resultado;
    }
}
