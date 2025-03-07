package Codes;

public class Predicados {

    public static boolean isAtom(Object expr) {
        return !(expr instanceof java.util.List);
    }

    // Verifica si es una lista
    public static boolean isList(Object expr) {
        return expr instanceof java.util.List;
    }

    // Compara igualdad
    public static boolean areEqual(Object a, Object b) {
        return a.equals(b);
    }

    // Menor que
    public static boolean lessThan(double a, double b) {
        return a < b;
    }

    // Mayor que
    public static boolean greaterThan(double a, double b) {
        return a > b;
    }
}
