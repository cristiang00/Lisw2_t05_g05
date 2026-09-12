package co.edu.unicauca.pipeline;

public class TrimFilter implements Filter<String> {

    @Override
    public String process(String input) {

        System.out.println("Filtro 1: Eliminando espacios...");
        System.out.println("Entrada: [" + input + "]");

        String result = input.trim();

        System.out.println("Salida:  [" + result + "]");
        System.out.println();

        return result;
    }

}
