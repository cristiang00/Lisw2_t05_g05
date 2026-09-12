package com.unicauca.taller2.usuarios;

/**
 * Punto de entrada alternativo de la aplicación.
 * Delega al Main principal integrado con el banco de preguntas.
 */
public class Main {

    /**
     * Método principal que inicializa la aplicación.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        co.edu.unicauca.bancopreguntas.Main.main(args);
    }
}
