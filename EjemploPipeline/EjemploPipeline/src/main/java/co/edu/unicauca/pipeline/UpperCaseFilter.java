package co.edu.unicauca.pipeline;

public class UpperCaseFilter implements Filter<String> {

    @Override
    public String process(String input) {

        System.out.println("Filtro 2: Convirtiendo a mayúsculas...");
        System.out.println("Entrada: [" + input + "]");

        String result = input.toUpperCase();

        System.out.println("Salida:  [" + result + "]");
        System.out.println();

        return result;
    }

}
