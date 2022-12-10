package dao;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.Opcion;
import modelos.Votacion;
import modelos.Usuario;
import modelos.Voto;
import modelos.enums.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class VotacionDao {

    private static final String RUTA_VOTACIONES = "src/main/datos/votaciones.json";

    private static JSONArray parsearVotaciones() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_VOTACIONES);
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

    private static void obtenerAtributosDeVotacionJSON(Votacion votacion, JSONObject votacionJSON) {
        votacion.setId(votacionJSON.get(CampoDeVotacion.ID.getTexto()));
        votacion.setTitulo(votacionJSON.get(CampoDeVotacion.TITULO.getTexto()));
        votacion.setDescripcion(votacionJSON.get(CampoDeVotacion.DESCRIPCION.getTexto()));
        votacion.setEstadoDeVotacion(votacionJSON.get(CampoDeVotacion.ESTADO.getTexto()));
        votacion.setFechaTiempoInicio(parsearFechaTiempoInicio(votacionJSON));
        votacion.setFechaTiempoTermino(parsearFechaTiempoTermino(votacionJSON));
        votacion.setOpciones(obtenerOpcionesDeVotacionJSON(votacion, votacionJSON));
        votacion.setVotantes(obtenerVotantesDeVotacionJSON(votacion, votacionJSON));
        votacion.setVotos(obtenerVotosDeVotacion(votacion));
    }

    private static List<Voto> obtenerVotosDeVotacion(Votacion votacion) {
        List<Voto> votos = VotoDao.obtenerVotos();
        List<Voto> votosDeVotacion = new ArrayList<>();
        for (Voto voto : votos) {
            if (voto.getVotacion().getId().equals(votacion.getId())) {
                votosDeVotacion.add(voto);
            }
        }
        return votosDeVotacion;
    }

    private static LocalDateTime parsearFechaTiempoInicio(JSONObject votacion) {
        return parsearFechaTiempoVotacion(
                votacion.get(CampoDeVotacion.FECHA_INICIO.getTexto()),
                votacion.get(CampoDeVotacion.HORA_INICIO.getTexto()));
    }

    private static LocalDateTime parsearFechaTiempoTermino(JSONObject votacion) {
        return parsearFechaTiempoVotacion(
                votacion.get(CampoDeVotacion.FECHA_TERMINO.getTexto()),
                votacion.get(CampoDeVotacion.HORA_TERMINO.getTexto()));
    }

    private static LocalDateTime parsearFechaTiempoVotacion(Object fecha, Object tiempo) {
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

    public static List<Usuario> obtenerVotantesDeVotacionJSON(Votacion votacion, JSONObject votacionSiguiente) {
        List<Usuario> votantes = obtenerVotantesDeVotacionJSON(votacionSiguiente);
        votacion.setVotantes(votantes);
        return votantes;
    }

    public static List<Usuario> obtenerVotantesDeVotacionJSON(JSONObject votacionSiguiente) {
        JSONArray votantesArray = (JSONArray) votacionSiguiente.get(CampoDeVotacion.VOTANTES.getTexto());
        List<Usuario> votantes = new ArrayList<>();
        for (Object IDvotante : votantesArray) {
            Usuario votante = new Usuario();
            votante.setId(Integer.parseInt(IDvotante.toString()));
            votantes.add(votante);
        }
        return votantes;
    }

    public static List<Votacion> obtenerVotacionesConEstados(HashMap<EstadoDeVotacion, Boolean> estados) {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> votacionesBorradores = new ArrayList<>();
        votaciones
                .stream()
                .filter(votacionSiguiente -> verificarSiTieneEstado(estados, votacionSiguiente))
                .forEach(votacionesBorradores::add);
        return votacionesBorradores;
    }

    private static boolean verificarSiTieneEstado(HashMap<EstadoDeVotacion, Boolean> estados, Votacion votacion) {
        for (EstadoDeVotacion estado : estados.keySet()) {
            if (votacion.getEstadoDeVotacion().equals(estado) && estados.get(estado)) {
                return true;
            }
        }
        return false;
    }

    public static List<Votacion> obtenerVotacionesEnCurso() {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> votacionesEnCurso = new ArrayList<>();
        votaciones
                .stream()
                .filter(votacionSiguiente -> votacionSiguiente.getEstadoDeVotacion().equals(EstadoDeVotacion.EN_CURSO))
                .forEach(votacionesEnCurso::add);
        return votacionesEnCurso;
    }

    public static List<Votacion> obtenerVotacionesFinalizadas() {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> votacionesEnCurso = new ArrayList<>();
        votaciones
                .stream()
                .filter(votacionSiguiente -> votacionSiguiente.getEstadoDeVotacion().equals(EstadoDeVotacion.FINALIZADO))
                .forEach(votacionesEnCurso::add);
        return votacionesEnCurso;
    }

    public static List<Votacion> obtenerVotacionesEnElQuePuedeVotarElVotante(Usuario votante) {
        int idVotante = votante.getId();
        List<Votacion> votacionesEnCurso = obtenerVotacionesEnCurso();
        List<Votacion> votacionesEnElQuePuedeVotarElVotante = new ArrayList<>();
        votacionesEnCurso
                .stream()
                .filter(votacionSiguiente -> !votanteVotoEnEstaVotacion(votacionSiguiente, idVotante))
                .forEach(votacionesEnElQuePuedeVotarElVotante::add);
        return votacionesEnElQuePuedeVotarElVotante;
    }

    private static String obtenerNuevaIdVotacion() {
        List<Votacion> votaciones = obtenerVotaciones();
        var maxID = votaciones.stream().max(Comparator.comparing(Votacion::getId))
                .orElse(new Votacion()).getId();
        maxID++;
        return String.valueOf(maxID);
    }

    public static boolean votanteVotoEnEstaVotacion(Votacion votacionSiguiente, Integer idVotante) {
        List<Usuario> votantes = votacionSiguiente.getVotantes();
        for (Usuario votanteQueVotoEnEstaVotacion : votantes) {
            if (idVotante.equals(votanteQueVotoEnEstaVotacion.getId())) {
                // Si esta condición se cumple se da a entender que el votante ya
                // está en la lista de votantes quienes votaron en esta votación
                return true;
            }
        }
        return false;
    }

    public static void escribirVotaciones(List<Votacion> votaciones) {
        AccesoADatos.escribirArchivoJSON(RUTA_VOTACIONES, convertirListaDeVotacionesAJSONArray(votaciones).toJSONString());
    }

    private static JSONArray convertirListaDeVotacionesAJSONArray(List<Votacion> votaciones) {
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
        votacionObj.put(CampoDeVotacion.VOTOS_PREFERENCIALES.getTexto(), votacion.getVotosPreferenciales());
        votacionObj.put(CampoDeVotacion.VOTOS_BLANCOS.getTexto(), votacion.getVotosBlancos());
        votacionObj.put(CampoDeVotacion.ESTADO.getTexto(), votacion.getEstadoDeVotacion().getTexto());
    }

    private static void convertirJSONCampoFechaYHora(Votacion votacion, JSONObject votacionObj){
        votacionObj.put(CampoDeVotacion.FECHA_INICIO.getTexto(), votacion.getFechaInicio()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        votacionObj.put(CampoDeVotacion.HORA_INICIO.getTexto(), votacion.getTiempoInicio()
                .format(DateTimeFormatter.ofPattern("HH:mm")));
        votacionObj.put(CampoDeVotacion.FECHA_TERMINO.getTexto(), votacion.getFechaTermino()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        votacionObj.put(CampoDeVotacion.HORA_TERMINO.getTexto(), votacion.getTiempoTermino()
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

    private static void convertirJSONCampoVotantes(Votacion votacion, JSONObject votacionObj) {
        JSONArray votantesArray = obtenerArrayIdVotantes(votacion);
        votacionObj.put(CampoDeVotacion.VOTANTES.getTexto(), votantesArray);
    }

    private static JSONArray obtenerArrayIdVotantes(Votacion votacion) {
        JSONArray votantesArray = new JSONArray();
        for (Usuario votante : votacion.getVotantes()) {
            votantesArray.add(votante.getId());
        }
        return votantesArray;
    }

    public static Votacion obtenerVotacionPorID(List<Votacion> votaciones, Votacion votacion) {
        return obtenerVotacionPorID(votaciones, votacion.getId());
    }

    public static Votacion obtenerVotacionPorID(List<Votacion> votaciones, Integer idVotacion) {
        return votaciones
                .stream()
                .filter(votacionSiguiente -> votacionSiguiente.getId().equals(idVotacion))
                .findFirst()
                .orElseThrow(
                        () -> AccesoADatosInterrumpidoException
                                .talElementoNoExiste(CampoDeVotacion.ID.getTexto()));
    }

    public static void crearVotacion(Votacion votacion) {
        votacion.setId(obtenerNuevaIdVotacion());
        List<Votacion> votaciones = obtenerVotaciones();
        votaciones.add(votacion);
        escribirVotaciones(votaciones);
    }

    public static void actualizarVotacion(Votacion votacion) {
        List<Votacion> votaciones = obtenerVotaciones();
        Votacion votacionCopia = obtenerVotacionPorID(votaciones, votacion);
        votacionCopia.setTitulo(votacion.getTitulo());
        votacionCopia.setDescripcion(votacion.getDescripcion());
        votacionCopia.setFechaTiempoInicio(votacion.getFechaTiempoInicio());
        votacionCopia.setFechaTiempoTermino(votacion.getFechaTiempoTermino());
        votacionCopia.setOpciones(votacion.getOpciones());
        votacionCopia.setEstadoDeVotacion(votacion.getEstadoDeVotacion());
        escribirVotaciones(votaciones);
    }
}
