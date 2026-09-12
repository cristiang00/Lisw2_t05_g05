package co.edu.unicauca.pipeline;

import java.util.List;

/**
 *
 * @author libardo
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println(" PATRÓN TUBERÍAS Y FILTROS");
        System.out.println("====================================");
        System.out.println();

        // Crear los filtros
        Filter<String> trimFilter
                = new TrimFilter();

        Filter<String> upperCaseFilter
                = new UpperCaseFilter();

        Filter<String> greetingFilter
                = new GreetingFilter();

        // Crear la tubería (Pipeline)
        Pipeline<String> pipeline
                = new Pipeline<>(
                        List.of(
                                trimFilter,
                                upperCaseFilter,
                                greetingFilter
                        )
                );

        // Datos de entrada
        String input
                = "   hola mundo java   ";

        System.out.println("DATOS DE ENTRADA");
        System.out.println("[" + input + "]");
        System.out.println();
        System.out.println("------------------------------------");
        System.out.println();

        // Ejecutar la tubería
        String result
                = pipeline.execute(input);

        // Mostrar resultado final
        System.out.println("====================================");
        System.out.println(" RESULTADO FINAL");
        System.out.println("====================================");

        System.out.println(result);
    }
}
