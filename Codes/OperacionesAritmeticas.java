package Codes;

import java.util.*;

public class OperacionesAritmeticas {
    public static double evaluate(String operator, List<Double> operands) {
        switch (operator) {
            case "+":
                return operands.stream().mapToDouble(Double::doubleValue).sum();
            case "-":
                return operands.get(0) - operands.get(1);
            case "*":
                return operands.stream().reduce(1.0, (a, b) -> a * b);
            case "/":
                return operands.get(0) / operands.get(1);
            default:
                throw new IllegalArgumentException("Operador no soportado: " + operator);
        }
    }
}