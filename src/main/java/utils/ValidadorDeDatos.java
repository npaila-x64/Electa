package utils;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.enums.CampoDeVotante;
import modelos.Votacion;
import modelos.Votante;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.format.ResolverStyle;

/*
    Clase que contiene métodos dedicados a validar datos
 */

public class ValidadorDeDatos {

    private static final String PATRON_DE_FECHA = "\\d{2}-\\d{2}-\\d{4}";
    private static final String PATRON_DE_HORA = "\\d{2}:\\d{2}";

    public static boolean esFormatoFechaValido(String fecha) {
        return fecha.matches(PATRON_DE_FECHA);
    }

    public static boolean esFormatoHoraValido(String hora) {
        return hora.matches(PATRON_DE_HORA);
    }

    public static int pedirValorEnteroEnIntervalo(int limite) throws InputMismatchException {
        int valor = new Scanner(System.in).nextInt();
        if (valor < 0 || valor > limite) {
            throw new InputMismatchException("El parámetro dado sobrepasa el límite");
        }
        return valor;
    }

    public static int pedirOpcionHasta(int limite) {
        try {
            return pedirValorEnteroEnIntervalo(limite);
        } catch (InputMismatchException e) {
            mostrarOpcionInvalida();
            return pedirOpcionHasta(limite);
        }
    }

    public static String pedirEntradaFormatoFecha(String texto){
        System.out.print(texto);
        String entrada = new Scanner(System.in).nextLine();
        if (!esFormatoFechaValido(entrada)) {
            mostrarFormatoInvalido();
            return pedirEntradaFormatoFecha(texto);
        }

        if(!esFechaValida(entrada)){
            mostrarFechaInvalida();
            return pedirEntradaFormatoFecha(texto);
        }

        return entrada;
    }

    public static boolean esFechaValida(String fecha) {
        boolean fechaValida;
        String s1 = fecha.substring(0,2), s2 = fecha.substring(3,5), s3 = fecha.substring(6,10);
        String fechaGringa = s3 + "-" + s2 + "-" + s1;

        try {
            LocalDate.parse(fechaGringa, DateTimeFormatter.ofPattern("uuuu-M-d")
                    .withResolverStyle(ResolverStyle.STRICT));
            fechaValida = true;
        } catch (DateTimeParseException e) {
            fechaValida = false;
        }

        return fechaValida;
    }

    public static String pedirEntradaFormatoHora(String texto){
        System.out.print(texto);
        String entrada = new Scanner(System.in).nextLine();
        if (!esFormatoHoraValido(entrada)) {
            mostrarFormatoInvalido();
            return pedirEntradaFormatoHora(texto);
        }
        if(!esHoraValida(entrada)){
            mostrarHoraInvalida();
            return pedirEntradaFormatoHora(texto);
        }

        return entrada;
    }
    public static boolean esHoraValida(String hora) {
        return hora.matches("([01]\\d|2[0-3]):[0-5]\\d");
    }

    public static String pedirEntrada(String texto) {
        System.out.print(texto);
        return pedirEntrada();
    }

    public static String pedirEntrada() {
        return new Scanner(System.in).nextLine();
    }

    public static String pedirEntrada(String texto, int limite) {
        System.out.print(texto);
        return pedirEntrada(limite);
    }

    public static String pedirEntrada(int limite) {
        String entrada = pedirEntradaNoVacia();
        if (entrada.length() > limite) {
            mostrarTextoInvalido();
            return pedirEntrada();
        }
        return entrada;
    }

    public static String pedirEntradaNoVacia() {
        String texto = pedirEntrada();
        if(texto.isBlank()){
            mostrarTextoVacio();
            return pedirEntradaNoVacia();
        }else {
            return texto;
        }
    }

    public static void mostrarTextoVacio(){
        System.out.print("Por favor, ingrese una entrada no vacia\n> ");
    }

    public static void mostrarTextoInvalido() {
        System.out.print("El tamaño del texto escrito es muy largo\n> ");
    }

    public static void mostrarOpcionInvalida() {
        System.out.print("Por favor, escoja una opción válida\n> ");
    }

    public static void mostrarFormatoInvalido(){
        System.out.println("Por favor, ingrese el texto en el formato indicado");
    }

    public static void mostrarFechaInvalida(){
        System.out.println("Por favor, ingrese una fecha existente");
    }

    public static void mostrarHoraInvalida(){
        System.out.println("Por favor, ingrese una hora valida");
    }
}
