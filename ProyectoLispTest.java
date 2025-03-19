import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ProyectoLispTest {

    @Test
    public void testExpresionValida() {
        AnalizadorLexico analizador = new AnalizadorLexico();
        String expresionValida = "(defun suma (a b) (+ a b))";
        
        analizador.convertir_a_tokens(expresionValida);
        assertTrue(analizador.ExpresionValida(), "La expresión debería ser válida.");
    }

    @Test
    public void testParentesis() {
        AnalizadorLexico analizador = new AnalizadorLexico();
        String expresionInvalida = "(defun suma 9 7 (+ 9 7))";
        
        analizador.convertir_a_tokens(expresionInvalida);
        assertFalse(analizador.ExpresionValida(), "Los parámetros no están entre parentesis");
    }

    @Test
    public void testDefun() {
        AnalizadorLexico analizador = new AnalizadorLexico();
        String expresionInvalida = "(suma (7 9) (+ 7 9))";
        
        analizador.convertir_a_tokens(expresionInvalida);
        assertFalse(analizador.ExpresionValida(), "La expresión no tiene defun.");
    }

    @Test
    public void testCuerpo() {
        AnalizadorLexico analizador = new AnalizadorLexico();
        String expresionInvalida = "(defun suma (7 9))";
        
        analizador.convertir_a_tokens(expresionInvalida);
        assertFalse(analizador.ExpresionValida(), "La expresión no tiene cuerpo");
    }
}
