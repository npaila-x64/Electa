package utils;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.*;
import modelos.enums.*;
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
import java.util.*;

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

    private static final String RUTA_VOTACIONES = "src/main/datos/votaciones.json";
    private static final String RUTA_VOTOS = "src/main/datos/votos.json";

    public static JSONArray parsearVotaciones() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON(RUTA_VOTACIONES);
    }

    public static JSONArray parsearVotos() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON(RUTA_VOTOS);
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

    public static List<Voto> obtenerVotos() {
        List<Voto> votos = new ArrayList<>();
        // TODO ¿será necesario cargar todos los datos de los modelos a que hace referencia el voto?
        JSONArray jsonArrayVotos = parsearVotos();
        for (Object jsonArrayVoto : jsonArrayVotos) {
            // TODO Refactorizar!
            JSONObject votoSiguiente = (JSONObject) jsonArrayVoto;
            Voto voto = new Voto();
            voto.setId(votoSiguiente.get(CampoDeVoto.ID.getTexto()));

            Votante votante = new Votante();
            votante.setId(votoSiguiente.get(CampoDeVoto.VOTANTE.getTexto()));
            voto.setVotante(votante);

            Votacion votacion = new Votacion();
            votacion.setId(votoSiguiente.get(CampoDeVoto.VOTACION.getTexto()));
            voto.setVotacion(votacion);

            Opcion opcion = new Opcion();
            opcion.setId(votoSiguiente.get(CampoDeVoto.OPCION.getTexto()));
            voto.setOpcion(opcion);

            votos.add(voto);
        }
        return votos;
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
        votacion.setVotos(obtenerVotosDeVotacion(votacion));
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
            opcion.setId(opcionSiguiente.get(CampoDeOpcion.ID.getTexto()));
            opcion.setNombre(opcionSiguiente.get(CampoDeOpcion.NOMBRE.getTexto()));
            opcion.setCantidadDeVotos(opcionSiguiente.get(CampoDeOpcion.VOTOS.getTexto()));
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

    public static String obtenerNuevaIdVotacion() {
        List<Votacion> votaciones = obtenerVotaciones();
        var maxID = votaciones.stream().max(Comparator.comparing(Votacion::getId)).get().getId();
        maxID++;
        return String.valueOf(maxID);
    }

    public static String obtenerNuevaIdOpcion(Votacion votacion) {
        Votacion votacionCopia = obtenerVotacionPorID(obtenerVotaciones(), votacion);
        List<Opcion> opciones = votacionCopia.getOpciones();
        var maxID = opciones.stream().max(Comparator.comparing(Opcion::getId)).get().getId();
        maxID++;
        return String.valueOf(maxID);
    }

    public static String obtenerNuevaIdVoto() {
        List<Voto> votos = obtenerVotos();
        var maxID = votos.stream().max(Comparator.comparing(Voto::getId)).get().getId();
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

    public static void escribirVotos(List<Voto> votos) {
        escribirArchivoJSON(RUTA_VOTOS, convertirListaDeVotosAJSONArray(votos).toJSONString());
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

    public static void registrarVotoBlanco(Votacion votacion, Votante votante) {
        Opcion opcionBlanco = new Opcion(TipoDeVoto.VOTO_BLANCO);
        registrarVotoEnVotos(votacion, votante, opcionBlanco);
        registrarVotanteEnVotaciones(votacion, votante);
    }

    public static void registrarVotoPreferencial(Votacion votacion, Votante votante, Opcion opcionElegida) {
        votarOpcionPreferencial(votacion, opcionElegida);
        registrarVotoEnVotos(votacion, votante, opcionElegida);
        registrarVotanteEnVotaciones(votacion, votante);
    }

    public static void registrarVotanteEnVotaciones(Votacion votacion, Votante votante) {
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

    public static void registrarVotoEnVotos(Votacion votacion, Votante votante, Opcion opcion) {
        List<Voto> votos = obtenerVotos();
        Voto voto = new Voto();
        voto.setId(obtenerNuevaIdVoto());
        voto.setVotacion(votacion);
        voto.setVotante(votante);
        voto.setOpcion(opcion);
        votos.add(voto);
        votacion.setVotos(votos);
        escribirVotos(votos);
    }

    public static void votarOpcionPreferencial(Votacion votacion, Opcion opcionElegida) {
        List<Opcion> opciones = votacion.getOpciones();
        opciones.stream()
                .filter(opcion -> opcion.getId().equals(opcionElegida.getId()))
                .forEach(AccesoADatos::incrementarCantidadDeVotosDeOpcionEnUno);
        votacion.setOpciones(opciones);
    }

    private static void incrementarCantidadDeVotosDeOpcionEnUno(Opcion opcion) {
        int votosOpcion = opcion.getCantidadDeVotos();
        votosOpcion++;
        opcion.setCantidadDeVotos(votosOpcion);
    }

    private static List<Voto> obtenerVotosDeVotacion(Votacion votacion) {
        List<Voto> votos = obtenerVotos();
        List<Voto> votosDeVotacion = new ArrayList<>();
        for (Voto voto : votos) {
            if (voto.getVotacion().getId().equals(votacion.getId())) {
                votosDeVotacion.add(voto);
            }
        }
        return votosDeVotacion;
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

    public static JSONArray convertirListaDeVotosAJSONArray(List<Voto> votos) {
        JSONArray array = new JSONArray();
        for (Voto voto : votos) {
            JSONObject votoObj = new JSONObject();
            votoObj.put(CampoDeVoto.ID.getTexto(), voto.getId());
            votoObj.put(CampoDeVoto.VOTANTE.getTexto(), voto.getVotante().getId());
            votoObj.put(CampoDeVoto.VOTACION.getTexto(), voto.getVotacion().getId());
            votoObj.put(CampoDeVoto.OPCION.getTexto(), voto.getOpcion().getId());
            array.add(votoObj);
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

    private static void convertirJSONCampoOpciones(Votacion votacion, JSONObject votacionObj) {
        JSONArray opcionesArray = new JSONArray();
        for (Opcion opcion : votacion.getOpciones()) {
            JSONObject opcionSiguiente = new JSONObject();
            opcionSiguiente.put(CampoDeOpcion.ID.getTexto(), opcion.getId());
            opcionSiguiente.put(CampoDeOpcion.NOMBRE.getTexto(), opcion.getNombre());
            opcionSiguiente.put(CampoDeOpcion.VOTOS.getTexto(), opcion.getCantidadDeVotos());
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

    public static boolean opcionYaExiste(Votacion votacion, String  nombreOpcion){
        List<Opcion> opciones = votacion.getOpciones();
        return opciones.stream().anyMatch(opcion -> opcion.getNombre().equals(nombreOpcion));
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
