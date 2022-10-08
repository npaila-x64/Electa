import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public static Votacion pedirCamposDeVotacion() {
        Votacion votacion = new Votacion();
        String titulo = pedirEntrada("Escriba el título de la votación que desea agregar\n> ", 50);
        System.out.println("Rellene los siguientes campos");
        String descripcion = pedirEntrada("Descripción\n> ");
        String fechaInicio = pedirEntrada("Fecha de inicio (dd-MM-aaaa)\n> ");
        String horaInicio = pedirEntrada("Hora de inicio (hh:mm formato 24 horas)\n> ");
        String fechaTermino = pedirEntrada("Fecha de término (dd-MM-aaaa)\n> ");
        String horaTermino = pedirEntrada("Hora de término (hh:mm formato 24 horas)\n> ");
        votacion.setTitulo(titulo);
        votacion.setDescripcion(descripcion);
        LocalDate fechaInicioDate = LocalDate.parse(fechaInicio, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime horaInicioTime = LocalTime.parse(horaInicio, DateTimeFormatter.ofPattern("HH:mm"));
        votacion.setFechaTiempoInicio(fechaInicioDate.atTime(horaInicioTime));
        LocalDate fechaTerminoDate = LocalDate.parse(fechaTermino, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime horaTerminoTime = LocalTime.parse(horaTermino, DateTimeFormatter.ofPattern("HH:mm"));
        votacion.setFechaTiempoTermino(fechaTerminoDate.atTime(horaTerminoTime));
        List<Opcion> opciones = new ArrayList<>();
        List<Votante> votantes = new ArrayList<>();
        votacion.setOpciones(opciones);
        votacion.setVotantes(votantes);
        votacion.setVotosBlancos(0);
        votacion.setVotosPreferenciales(0);
        return votacion;
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
