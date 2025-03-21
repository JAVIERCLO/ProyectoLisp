import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProyectoLispTest {

    @Test
    void testSuma() {
        Environment env = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(env);
        analizador.convertir_a_tokens("(+ 10 5)");
        assertEquals(15.0, analizador.ExpresionValida());
    }

    @Test
    void testSetq() {
        Environment env = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(env);
        analizador.convertir_a_tokens("(setq x 8)");
        assertEquals(8.0, analizador.ExpresionValida());
    }

    @Test
    void testQuoteAtom() {
        Environment env = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(env);
        analizador.convertir_a_tokens("(quote hola)");
        assertEquals("hola", analizador.ExpresionValida());
    }

    @Test
    void testQuoteList() {
        Environment env = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(env);
        analizador.convertir_a_tokens("(quote (1 2 3))");
        assertEquals("(1 2 3)", analizador.ExpresionValida());
    }

    @Test
    void testPredicadoEqual() {
        Environment env = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(env);
        analizador.convertir_a_tokens("(equal 4 4)");
        assertEquals(true, analizador.ExpresionValida());
    }

    @Test
    void testPredicadoMenorQue() {
        Environment env = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(env);
        analizador.convertir_a_tokens("(< 2 5)");
        assertEquals(true, analizador.ExpresionValida());
    }

    @Test
    void testPredicadoAtom() {
        Environment env = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(env);
        analizador.convertir_a_tokens("(atom hola)");
        assertEquals(true, analizador.ExpresionValida());
    }

    @Test
    void testCondicional() {
        Environment env = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(env);
        analizador.convertir_a_tokens("(setq x 3)");
        analizador.ExpresionValida();
        analizador.convertir_a_tokens("(cond ((> x 5) 100) ((< x 5) 50))");
        assertEquals(50.0, analizador.ExpresionValida());
    }

    @Test
    void testDefunYFuncionNormal() {
        Environment env = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(env);
        analizador.convertir_a_tokens("(defun suma (a b) (+ a b))");
        analizador.ExpresionValida();
        analizador.convertir_a_tokens("(suma 3 7)");
        assertEquals(10.0, analizador.ExpresionValida());
    }

    @Test
    void testFuncionRecursivaFactorial() {
        Environment env = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(env);
        analizador.convertir_a_tokens(
            "(defun factorial (n) (cond ((equal n 0) 1) (true (* n (factorial (- n 1))))))"
        );
        analizador.ExpresionValida();
        analizador.convertir_a_tokens("(factorial 5)");
        assertEquals(120.0, analizador.ExpresionValida());
    }

    @Test
    void testFuncionRecursivaSumatoria() {
        Environment env = new Environment();
        AnalizadorLexico analizador = new AnalizadorLexico(env);
        analizador.convertir_a_tokens(
            "(defun sumatoria (n) (cond ((equal n 0) 0) (true (+ n (sumatoria (- n 1))))))"
        );
        analizador.ExpresionValida();
        analizador.convertir_a_tokens("(sumatoria 5)");
        assertEquals(15.0, analizador.ExpresionValida());
    }
}