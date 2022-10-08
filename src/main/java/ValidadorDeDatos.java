import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
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
        List<Opcion> opciones = new ArrayList<>();
        List<Votante> votantes = new ArrayList<>();
        situarDatosCampo(votacion, ingresarDatosCampo());
        situarDatosVotacion(votacion, opciones, votantes);
        return votacion;
    }

    public static void situarDatosVotacion(Votacion votacion, List<Opcion> opciones, List<Votante> votantes) {
        votacion.setOpciones(opciones);
        votacion.setVotantes(votantes);
        votacion.setVotosBlancos(0);
        votacion.setVotosPreferenciales(0);
    }

    public static void situarDatosCampo(Votacion votacion, HashMap<String, String> datos){
        votacion.setTitulo(datos.get("titulo"));
        votacion.setDescripcion(datos.get("descripción"));
        LocalDate fechaInicioDate = LocalDate.parse(datos.get("fechaInicio"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime horaInicioTime = LocalTime.parse(datos.get("horaInicio"), DateTimeFormatter.ofPattern("HH:mm"));
        votacion.setFechaTiempoInicio(fechaInicioDate.atTime(horaInicioTime));
        LocalDate fechaTerminoDate = LocalDate.parse(datos.get("fechaTermino"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime horaTerminoTime = LocalTime.parse(datos.get("horaTermino"), DateTimeFormatter.ofPattern("HH:mm"));
        votacion.setFechaTiempoTermino(fechaTerminoDate.atTime(horaTerminoTime));
    }

    public static HashMap<String, String> ingresarDatosCampo(){
        HashMap<String, String> datosCampo = new HashMap<>();
        datosCampo.put("titulo", pedirEntrada("Escriba el título de la votación que desea agregar\n> ", 50));
        System.out.println("Rellene los siguientes campos");
        datosCampo.put("descripción", pedirEntrada("Descripción\n> "));
        datosCampo.put("fechaInicio", pedirEntrada("Fecha de inicio (dd-MM-aaaa)\n> "));
        datosCampo.put("horaInicio", pedirEntrada("Hora de inicio (hh:mm formato 24 horas)\n> "));
        datosCampo.put("fechaTermino", pedirEntrada("Fecha de término (dd-MM-aaaa)\n> "));
        datosCampo.put("horaTermino", pedirEntrada("Hora de término (hh:mm formato 24 horas)\n> "));
        return datosCampo;
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
