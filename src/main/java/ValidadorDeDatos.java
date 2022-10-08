import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

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

    public static boolean esCredencialAdministradorValida(String clave) throws AccesoADatosInterrumpidoException {
        JSONArray credencialArray = AccesoADatos.parsearCredencialAdmin();
        JSONObject credencialObject = (JSONObject) credencialArray.get(0);
        String claveObtenida = String.valueOf(credencialObject.get(CampoDeVotante.CLAVE.getTexto()));
        return clave.equals(claveObtenida);
    }

    public static boolean esCredencialVotanteValida(String rut, String clave) throws AccesoADatosInterrumpidoException {
        JSONArray arrayVotantes = AccesoADatos.parsearVotantes();
        for (Object arrayVotante : arrayVotantes) {
            JSONObject votanteSiguiente = (JSONObject) arrayVotante;
            if (votanteSiguiente.get(CampoDeVotante.RUT.getTexto()).equals(rut)
                    && votanteSiguiente.get(CampoDeVotante.CLAVE.getTexto()).equals(clave)) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<String, String> pedirCamposDeVotacion() {
        HashMap<String, String> mapaConCampos = new HashMap<>();
        String titulo = pedirEntrada("Escriba el título de la votación que desea agregar\n> ", 50);
        System.out.println("Rellene los siguientes campos");
        String descripcion = pedirEntrada("Descripción\n> ");
        String fechaInicio = pedirEntrada("Fecha de inicio (dd-MM-aaaa)\n> ");
        String horaInicio = pedirEntrada("Hora de inicio (hh:mm formato 24 horas)\n> ");
        String fechaTermino = pedirEntrada("Fecha de término (dd-MM-aaaa)\n> ");
        String horaTermino = pedirEntrada("Hora de término (hh:mm formato 24 horas)\n> ");
        mapaConCampos.put(CampoDeVotacion.TITULO.getTexto(), titulo);
        mapaConCampos.put(CampoDeVotacion.DESCRIPCION.getTexto(), descripcion);
        mapaConCampos.put(CampoDeVotacion.FECHA_INICIO.getTexto(), fechaInicio);
        mapaConCampos.put(CampoDeVotacion.HORA_INICIO.getTexto(), horaInicio);
        mapaConCampos.put(CampoDeVotacion.FECHA_TERMINO.getTexto(), fechaTermino);
        mapaConCampos.put(CampoDeVotacion.HORA_TERMINO.getTexto(), horaTermino);
        return mapaConCampos;
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
        String entrada = new Scanner(System.in).nextLine();
        if (entrada.length() > limite) {
            mostrarTextoInvalido();
            return pedirEntrada();
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
