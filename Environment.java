import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {
    public Map<String, Object> variables = new HashMap<>();
    public Map<String, DefinicionFunciones> functions = new HashMap<>();

    //Buscar variables
    public Object buscarVariables(String name) {
        return variables.get(name);
    }
    //Definir funciones
    public void definirFuncion(String nombre, List<String> parametros, Object cuerpo) {
        functions.put(nombre, new DefinicionFunciones(parametros, cuerpo));
    }
    //Verificar si la funcion existe
    public boolean funcionExiste(String nombre) {
        return functions.containsKey(nombre);
    }
    //Obtener la cantidad de parametros
    public int cantParametros(String nombre) {
        if (!functions.containsKey(nombre)) return -1;
        return functions.get(nombre).params.size();
    }
    //Obtener la definicion de una funcion
    public DefinicionFunciones obtenerFuncion(String nombre) {
        return functions.get(nombre);
    }
}