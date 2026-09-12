package co.edu.unicauca.pipeline;

public class GreetingFilter implements Filter<String> {

    @Override
    public String process(String input) {

        System.out.println("Filtro 3: Agregando mensaje...");
        System.out.println("Entrada: [" + input + "]");

        String result = "¡" + input + "!";

        System.out.println("Salida:  [" + result + "]");
        System.out.println();

        return result;
    }

}
