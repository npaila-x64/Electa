package utils;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.*;
import modelos.enums.CampoDeVotacion;
import modelos.enums.CampoDeVotante;
import modelos.enums.Estado;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/*
    Clase que contiene los métodos que interactúan
    directamente con los archivos de datos JSON

    Para modificar los datos de las votaciones se realiza una llamada al método
    obtenerVotaciones() donde estas se parsean desde el archivo votaciones.json
    a un List<modelos.Votacion>
    Luego para escribirlos de nuevo al archivo votaciones.json se realiza una llamada
    al método escribirVotaciones(), que recibe como argumento una List<modelos.Votacion> que
    contiene todas las votaciones
 */

public class AccesoADatos {

    private static final String RUTA_VOTANTES = "src/main/datos/votantes.json";
    private static final String RUTA_CREDENCIALES_ADMIN = "src/main/datos/credencialesAdmin.json";
    private static final String RUTA_VOTACIONES = "src/main/datos/votaciones.json";

    public static JSONArray parsearVotantes() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON(RUTA_VOTANTES);
    }

    public static JSONArray parsearCredencialAdmin() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON(RUTA_CREDENCIALES_ADMIN);
    }

    public static JSONArray parsearVotaciones() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON(RUTA_VOTACIONES);
    }

    public static JSONArray parsearArchivoJSON(String ruta) throws AccesoADatosInterrumpidoException {
        try {
            String contenidosJSON = leerContenidosJSON(ruta);
            JSONParser parser = new JSONParser();
            return (JSONArray) parser.parse(contenidosJSON);
        } catch (ParseException e) {
            throw AccesoADatosInterrumpidoException.noSePudoParsearArchivo(ruta);
        } catch (FileNotFoundException e) {
            throw AccesoADatosInterrumpidoException.noSePudoCargarArchivo(ruta);
        }
    }

    public static List<Votacion> obtenerVotacionesConEstado(Estado estado) {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> votacionesCopia = new ArrayList<>();
        for (Votacion votacionSiguiente : votaciones) {
            if (votacionSiguiente.getEstado().equals(estado)) {
                votacionesCopia.add(votacionSiguiente);
            }
        }
        return votacionesCopia;
    }

    public static List<Votacion> obtenerVotaciones() {
        List<Votacion> votaciones = new ArrayList<>();
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            Votacion votacion = new Votacion();
            obtenerAtributosDeVotacionJSON(votacion, votacionSiguiente);
            votaciones.add(votacion);
        }
        return votaciones;
    }

    public static List<Votante> obtenerVotantes() {
        List<Votante> votantes = new ArrayList<>();
        JSONArray jsonArrayVotantes = parsearVotantes();
        for (Object jsonArrayVotante : jsonArrayVotantes) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotante;
            Votante votante = new Votante();
            obtenerAtributosDeVotanteJSON(votante, votacionSiguiente);
            votantes.add(votante);
        }
        return votantes;
    }

    private static void obtenerAtributosDeVotacionJSON(Votacion votacion, JSONObject votacionJSON) {
        votacion.setId(votacionJSON.get(CampoDeVotacion.ID.getTexto()));
        votacion.setTitulo(votacionJSON.get(CampoDeVotacion.TITULO.getTexto()));
        votacion.setDescripcion(votacionJSON.get(CampoDeVotacion.DESCRIPCION.getTexto()));
        votacion.setEstado(votacionJSON.get(CampoDeVotacion.ESTADO.getTexto()));
        votacion.setFechaTiempoInicio(parsearFechaTiempoInicio(votacionJSON));
        votacion.setFechaTiempoTermino(parsearFechaTiempoTermino(votacionJSON));
        votacion.setOpciones(obtenerOpcionesDeVotacionJSON(votacion, votacionJSON));
        votacion.setVotantes(obtenerVotantesDeVotacionJSON(votacion, votacionJSON));
        votacion.setVotosPreferenciales(
                votacionJSON.get(CampoDeVotacion.VOTOS_PREFERENCIALES.getTexto()));
        votacion.setVotosBlancos(votacionJSON.get(CampoDeVotacion.VOTOS_BLANCOS.getTexto()));
    }

    private static void obtenerAtributosDeVotanteJSON(Votante votante, JSONObject votanteJSON) {
        votante.setId(votanteJSON.get(CampoDeVotante.ID.getTexto()));
        votante.setRut(votanteJSON.get(CampoDeVotante.RUT.getTexto()));
        votante.setClave(votanteJSON.get(CampoDeVotante.CLAVE.getTexto()));
    }

    public static List<Votante> obtenerVotantesDeVotacionJSON(Votacion votacion, JSONObject votacionSiguiente) {
        List<Votante> votantes = obtenerVotantesDeVotacionJSON(votacionSiguiente);
        votacion.setVotantes(votantes);
        return votantes;
    }

    public static List<Votante> obtenerVotantesDeVotacionJSON(JSONObject votacionSiguiente) {
        JSONArray votantesArray = (JSONArray) votacionSiguiente.get(CampoDeVotacion.VOTANTES.getTexto());
        List<Votante> votantes = new ArrayList<>();
        for (Object IDvotante : votantesArray) {
            Votante votante = new Votante();
            votante.setId(Integer.parseInt(IDvotante.toString()));
            votantes.add(votante);
        }
        return votantes;
    }

    public static List<Opcion> obtenerOpcionesDeVotacionJSON(Votacion votacion, JSONObject votacionSiguiente) {
        var opciones = obtenerOpcionesDeVotacionJSON(votacionSiguiente);
        for (var opcion : opciones) opcion.setVotacion(votacion);
        return opciones;
    }

    public static List<Opcion> obtenerOpcionesDeVotacionJSON(JSONObject votacionSiguiente) {
        JSONArray opciones = (JSONArray) votacionSiguiente.get(CampoDeVotacion.OPCIONES.getTexto());
        List<Opcion> listaOpciones = new ArrayList<>();
        for (Object jsonArrayOpcion : opciones) {
            JSONObject opcionSiguiente = (JSONObject) jsonArrayOpcion;
            Opcion opcion = new Opcion();
            opcion.setId(opcionSiguiente.get("id"));
            opcion.setNombre(opcionSiguiente.get("nombre"));
            opcion.setCantidadDeVotos(opcionSiguiente.get("votos"));
            listaOpciones.add(opcion);
        }
        return listaOpciones;
    }

    public static List<Votacion> obtenerVotacionesEnElQuePuedeVotarElVotante(Votante votante) {
        List<Votacion> votacionesEnCurso = ObtenerVotacionesEnCurso();
        List<Votacion> votacionesEnElQuePuedeVotarElVotante = new ArrayList<>();
        votacionesEnCurso
                .stream()
                .filter(votacionSiguiente -> !votanteVotoEnEstaVotacion(votacionSiguiente, votante))
                .forEach(votacionesEnElQuePuedeVotarElVotante::add);
        return votacionesEnElQuePuedeVotarElVotante;
    }

    public static List<Votacion> ObtenerVotacionesEnCurso() {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> votacionesEnCurso = new ArrayList<>();
        votaciones
                .stream()
                .filter(votacionSiguiente -> votacionSiguiente.getEstado().equals(Estado.EN_CURSO))
                .forEach(votacionesEnCurso::add);
        return votacionesEnCurso;
    }

    public static boolean votanteVotoEnEstaVotacion(Votacion votacionSiguiente, Votante votante) {
        List<Votante> votantes = votacionSiguiente.getVotantes();
        for (Votante votanteQueVotoEnEstaVotacion : votantes) {
            if (votante.getId().equals(votanteQueVotoEnEstaVotacion.getId())) {
                // Si esta condición se cumple se da a entender que el votante ya
                // está en la lista de votantes quienes votaron en esta votación
                return true;
            }
        }
        return false;
    }

    public static Votacion obtenerVotacionPorID(List<Votacion> votaciones, Votacion votacion) {
        return votaciones
                .stream()
                .filter(votacionSiguiente -> votacionSiguiente.getId().equals(votacion.getId()))
                .findFirst()
                .orElseThrow(
                        () -> AccesoADatosInterrumpidoException
                                .talElementoNoExiste(CampoDeVotacion.ID.getTexto()));
    }

    public static Votante obtenerVotantePorRut(String rut) {
        return obtenerVotantes()
                .stream()
                .filter(votacionSiguiente -> votacionSiguiente.getRut().equals(rut))
                .findFirst()
                .orElseThrow(
                        () -> AccesoADatosInterrumpidoException
                                .talElementoNoExiste(rut));
    }

    public static String obtenerNuevaIdVotacion() {
        // TODO Se puede convertir a método funcional para mejorar legibilidad (1)
        int maxID = 0;
        List<Votacion> votaciones = obtenerVotaciones();
        for (Votacion votacionSiguiente : votaciones) {
            int id = votacionSiguiente.getId();
            if (id > maxID) {
                maxID = id;
            }
        }
        maxID++;
        return String.valueOf(maxID);
    }

    public static String obtenerNuevaIdOpcion(Votacion votacion) {
        // TODO Se puede convertir a método funcional para mejorar legibilidad (2)
        int maxID = 0;
        Votacion votacionCopia = obtenerVotacionPorID(obtenerVotaciones(), votacion);
        for (Opcion opcionSiguiente : votacionCopia.getOpciones()) {
            int id = opcionSiguiente.getId();
            if (id > maxID) {
                maxID = id;
            }
        }
        maxID++;
        return String.valueOf(maxID);
    }

    public static String leerContenidosJSON(String ruta) throws FileNotFoundException {
        StringBuilder st = new StringBuilder();
        File archivoJSON = new File(ruta);
        Scanner scanner = new Scanner(archivoJSON);
        while (scanner.hasNextLine()) {
            st.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return st.toString();
    }

    public static void escribirVotaciones(List<Votacion> votaciones) {
        escribirArchivoJSON(RUTA_VOTACIONES, convertirListaDeVotacionesAJSONArray(votaciones).toJSONString());
    }

    public static void escribirArchivoJSON(String ruta, String contenido) {
        try {
            FileWriter myWriter = new FileWriter(ruta);
            myWriter.write(contenido);
            myWriter.close();
        } catch (IOException | NullPointerException e) {
            // TODO Averiguar donde es más conveniente atrapar esta excepción, ya que al usuario simplemente lo devuelve al menú principal (durante el transcurso de toda la aplicación)
            throw AccesoADatosInterrumpidoException.noSePudoEscribirArchivo(ruta);
        }
    }

    public static void realizarVotoBlanco(Votacion votacion, Votante votante) {
        votarOpcionBlanco(votacion);
        realizarVoto(votacion, votante);
    }

    private static void votarOpcionBlanco(Votacion votacion) {
        int votosBlancos = votacion.getVotosBlancos();
        votosBlancos++;
        votacion.setVotosBlancos(votosBlancos);
    }

    public static void realizarVotoPreferencial(Votacion votacion, Votante votante, Opcion opcionElegida) {
        votarOpcionPreferencial(votacion, opcionElegida);
        realizarVoto(votacion, votante);
    }

    public static void realizarVoto(Votacion votacion, Votante votante) {
        List<Votacion> votaciones = obtenerVotaciones();
        for (var votacionSiguiente : votaciones) {
            if (votacionSiguiente.getId().equals(votacion.getId())) {
                List<Votante> votantes = votacionSiguiente.getVotantes();
                votantes.add(votante);
                votacionSiguiente.setVotantes(votantes);
                escribirVotaciones(votaciones);
                return;
            }
        }
    }

    public static void votarOpcionPreferencial(Votacion votacion, Opcion opcionElegida) {
        // TODO Se puede refactorizar
        List<Opcion> opciones = votacion.getOpciones();
        for (Opcion opcion : opciones) {
            System.out.println(opcion.getId());
            if (opcion.getId().equals(opcionElegida.getId())) {
                int votosOpcion = opcion.getCantidadDeVotos();
                votosOpcion++;
                opcion.setCantidadDeVotos(votosOpcion);
                int votosPreferencialesVotacion = votacion.getVotosPreferenciales();
                votosPreferencialesVotacion++;
                votacion.setVotosPreferenciales(votosPreferencialesVotacion);
            }
        }
        votacion.setOpciones(opciones);
    }

    public static JSONArray convertirListaDeVotacionesAJSONArray(List<Votacion> votaciones) {
        JSONArray array = new JSONArray();
        for (Votacion votacion : votaciones) {
            JSONObject votacionObj = new JSONObject();
            convertirJSONCampoInfoVotacion(votacion, votacionObj);
            convertirJSONCampoFechaYHora(votacion, votacionObj);
            convertirJSONCampoVotantes(votacion, votacionObj);
            convertirJSONCampoOpciones(votacion, votacionObj);
            convertirJSONCampoVotos(votacion, votacionObj);
            array.add(votacionObj);
        }
        return array;
    }

    private static void convertirJSONCampoInfoVotacion(Votacion votacion, JSONObject votacionObj) {
        votacionObj.put(CampoDeVotacion.ID.getTexto(), votacion.getId());
        votacionObj.put(CampoDeVotacion.TITULO.getTexto(), votacion.getTitulo());
        votacionObj.put(CampoDeVotacion.DESCRIPCION.getTexto(), votacion.getDescripcion());
        votacionObj.put(CampoDeVotacion.ESTADO.getTexto(), votacion.getEstado().getTexto());
    }

    private static void convertirJSONCampoFechaYHora(Votacion votacion, JSONObject votacionObj){
        // TODO Refactorizar esto, se ve horrible >:(
        votacionObj.put(CampoDeVotacion.FECHA_INICIO.getTexto(),
                Optional.ofNullable(votacion.getFechaInicio())
                        .orElse(LocalDate.of(1900,1,1))
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        votacionObj.put(CampoDeVotacion.HORA_INICIO.getTexto(),
                Optional.ofNullable(votacion.getTiempoInicio())
                        .orElse(LocalTime.of(0,0))
                        .format(DateTimeFormatter.ofPattern("HH:mm")));
        votacionObj.put(CampoDeVotacion.FECHA_TERMINO.getTexto(),
                Optional.ofNullable(votacion.getFechaTermino())
                        .orElse(LocalDate.of(2099,1,1))
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        votacionObj.put(CampoDeVotacion.HORA_TERMINO.getTexto(),
                Optional.ofNullable(votacion.getTiempoTermino())
                        .orElse(LocalTime.of(0,0))
                        .format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    private static void convertirJSONCampoVotantes(Votacion votacion, JSONObject votacionObj) {
        JSONArray votantesArray = obtenerArrayIdVotantes(votacion);
        votacionObj.put(CampoDeVotacion.VOTANTES.getTexto(), votantesArray);
    }

    private static JSONArray obtenerArrayIdVotantes(Votacion votacion) {
        JSONArray votantesArray = new JSONArray();
        for (Votante votante : votacion.getVotantes()) {
            votantesArray.add(votante.getId());
        }
        return votantesArray;
    }

    private static void convertirJSONCampoOpciones(Votacion votacion, JSONObject votacionObj) {
        JSONArray opcionesArray = new JSONArray();
        for (Opcion opcion : votacion.getOpciones()) {
            JSONObject opcionSiguiente = new JSONObject();
            opcionSiguiente.put("id", opcion.getId());
            opcionSiguiente.put("nombre", opcion.getNombre());
            opcionSiguiente.put("votos", opcion.getCantidadDeVotos());
            opcionesArray.add(opcionSiguiente);
        }
        votacionObj.put(CampoDeVotacion.OPCIONES.getTexto(), opcionesArray);
    }

    private static void convertirJSONCampoVotos(Votacion votacion, JSONObject votacionObj) {
        votacionObj.put(CampoDeVotacion.VOTOS_BLANCOS.getTexto(), votacion.getVotosBlancos());
        votacionObj.put(CampoDeVotacion.VOTOS_PREFERENCIALES.getTexto(), votacion.getVotosPreferenciales());
    }

    public static void crearVotacion(Votacion votacion) {
        votacion.setId(obtenerNuevaIdVotacion());
        votacion.setEstado(Estado.PENDIENTE);
        List<Votacion> votaciones = obtenerVotaciones();
        votaciones.add(votacion);
        escribirVotaciones(votaciones);
    }

    public static void eliminarVotacion(Votacion votacion) {
        List<Votacion> votaciones = obtenerVotaciones();
        votaciones.removeIf(votacionSiguiente -> votacionSiguiente.getId().equals(votacion.getId()));
        escribirVotaciones(votaciones);
    }

    public static void eliminarOpcionDeVotacion(Votacion votacionElegida, Opcion opcionElegida) {
        List<Votacion> votaciones = obtenerVotaciones();
        Votacion votacionCopia = obtenerVotacionPorID(votaciones, votacionElegida);
        List<Opcion> opciones = votacionCopia.getOpciones();
        opciones.removeIf(opcion -> opcion.getId().equals(opcionElegida.getId()));
        votacionCopia.setOpciones(opciones);
        votacionElegida.setOpciones(opciones);
        escribirVotaciones(votaciones);
    }

    public static void actualizarCampoDeVotacion(Votacion votacionElegida, CampoDeVotacion campo, Object valor) {
        List<Votacion> votaciones = obtenerVotaciones();
        Votacion votacionCopia = obtenerVotacionPorID(votaciones, votacionElegida);
        votacionCopia.setAttributo(campo, valor);
        votacionElegida.setAttributo(campo, valor);
        escribirVotaciones(votaciones);
    }

    public static void agregarOpcionAVotacion(Votacion votacion, String nombreOpcion) {
        // TODO Evitar que se agregue una opcion duplicada (entregarle un mensaje al usuario)
        List<Votacion> votaciones = obtenerVotaciones();
        Votacion votacionCopia = obtenerVotacionPorID(votaciones, votacion);
        List<Opcion> opciones = votacionCopia.getOpciones();
        Opcion opcion = new Opcion();
        opcion.setId(obtenerNuevaIdOpcion(votacionCopia));
        opcion.setNombre(nombreOpcion);
        opcion.setCantidadDeVotos(0);
        opciones.add(opcion);
        votacionCopia.setOpciones(opciones);
        votacion.setOpciones(opciones);
        escribirVotaciones(votaciones);
    }

    public static LocalDateTime parsearFechaTiempoInicio(JSONObject votacion) {
        return parsearFechaTiempoVotacion(
                votacion.get(CampoDeVotacion.FECHA_INICIO.getTexto()),
                votacion.get(CampoDeVotacion.HORA_INICIO.getTexto()));
    }

    public static LocalDateTime parsearFechaTiempoTermino(JSONObject votacion) {
        return parsearFechaTiempoVotacion(
                votacion.get(CampoDeVotacion.FECHA_TERMINO.getTexto()),
                votacion.get(CampoDeVotacion.HORA_TERMINO.getTexto()));
    }

    public static LocalDateTime parsearFechaTiempoVotacion(Object fecha, Object tiempo) {
        // TODO Desarrollar manejo de excepciones y crear pruebas unitarias
        var fechaArr = fecha.toString().split("-");
        var dia = Integer.parseInt(fechaArr[0]);
        var mes = Integer.parseInt(fechaArr[1]);
        var anio = Integer.parseInt(fechaArr[2]);
        var tiempoArr = tiempo.toString().split(":");
        var hora = Integer.parseInt(tiempoArr[0]);
        var minutos = Integer.parseInt(tiempoArr[1]);
        return LocalDateTime.of(anio, mes, dia, hora, minutos);
    }
}
