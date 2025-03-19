import java.util.HashMap;
import java.util.Map;

public class Environment {
    public Map<String, Object> variables = new HashMap<>();
    public Map<String, DefinicionFunciones.FunctionDef> functions = new HashMap<>();

    // Método para buscar variables
    public Object lookupVariable(String name) {
        return variables.get(name);
    }
}