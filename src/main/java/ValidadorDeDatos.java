import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
    Clase que contiene los métodos dedicados a validar datos
 */

public class ValidadorDeDatos {

    public static boolean esFormatoFechaValido(String fecha) {
        return fecha.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    public static boolean esFormatoHoraValido(String hora) {
        return hora.matches("\\d{2}:\\d{2}");
    }



    public static HashMap<String, String> pedirCamposDeVotacion() {
        HashMap<String, String> mapaConCampos = new HashMap<>();
        String titulo = pedirString("Escriba el título de la votación que desea agregar\n> ", 50);
        System.out.println("Rellene los siguientes campos");
        String descripcion = pedirString("Descripción\n> ");
        String fechaInicio = pedirString("Fecha de inicio (dd-MM-aaaa)\n> ");
        String horaInicio = pedirString("Hora de inicio (hh:mm formato 24 horas)\n> ");
        String fechaTermino = pedirString("Fecha de término (dd-MM-aaaa)\n> ");
        String horaTermino = pedirString("Hora de término (hh:mm formato 24 horas)\n> ");
        mapaConCampos.put("titulo", titulo);
        mapaConCampos.put("descripcion", descripcion);
        mapaConCampos.put("fecha_inicio", fechaInicio);
        mapaConCampos.put("hora_inicio", horaInicio);
        mapaConCampos.put("fecha_termino", fechaTermino);
        mapaConCampos.put("hora_termino", horaTermino);
        return mapaConCampos;
    }

    public static int pedirValorEntero() throws InputMismatchException {
        return new Scanner(System.in).nextInt();
    }

    public static int pedirOpcion() {
        try {
            return pedirValorEntero();
        } catch (InputMismatchException e) {
            mostrarOpcionInvalida();
            return pedirOpcion();
        }
    }

    public static String pedirString(String texto) {
        System.out.print(texto);
        return pedirString();
    }

    public static String pedirString() {
        return new Scanner(System.in).nextLine();
    }

    public static String pedirString(String texto, int limite) {
        System.out.print(texto);
        return pedirString(limite);
    }

    public static String pedirString(int limite) {
        String entrada = new Scanner(System.in).nextLine();
        if (entrada.length() > limite) {
            mostrarTextoInvalido();
            return pedirString();
        }
        return entrada;
    }

    public static void mostrarTextoInvalido() {
        System.out.print("El tamaño del texto escrito es muy largo\n> ");
    }

    public static void mostrarOpcionInvalida() {
        System.out.print("Por favor, escoja una opción válida\n> ");
    }
}
