package dao;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.Opcion;
import modelos.Votacion;
import modelos.Usuario;
import modelos.Voto;
import modelos.enums.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.AccesoADatos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Provee acceso a los datos relacionados con la clase Votacion, almacenados en un archivo
 * de extensión JSON.
 */

public class VotacionDao {

    /**
     * Indica la ruta del archivo JSON donde se guardan los datos de las votaciones.
     */


    private static final String RUTA_VOTACIONES = "src/main/datos/votaciones.json";

    /**
     * Parsea los datos almacenados en un archivo JSON de las votaciones
     * a un JSONArray.
     *
     * @return un arreglo de tipo JSONArray.
     * @throws AccesoADatosInterrumpidoException cuando ocurre una excepción inesperada al intentar parsear
     * un archivo JSON.
     */

    private static JSONArray parsearVotaciones() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_VOTACIONES);
    }

    /**
     * Obtiene una lista de todas las votaciones almacenadas en un archivo JSON.
     *
     * @return una lista de tipo List que almacena objetos de tipo Votacion.
     */

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

    /**
     * Asigna atributos a un usuario votante, obtenidos a través de un JSONObject.
     *
     * @param votacion la votación a la cual asignar los datos.
     * @param votacionJSON el JSONObject que contiene información de una votación.
     */

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

    /**
     * Obtiene una lista de votos asociados a una votación.
     *
     * @param votacion la votación de la cual se busca obtener los votos.
     * @return una lista de tipo List que almacena objetos de tipo Voto.
     */

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

    /**
     * Parsea la fecha y hora de inicio de una votación.
     *
     * @param votacion la votación de la cual se obtiene la fecha.
     * @return una fecha y hora de tipo LocalDateTime.
     */

    private static LocalDateTime parsearFechaTiempoInicio(JSONObject votacion) {
        return parsearFechaTiempoVotacion(
                votacion.get(CampoDeVotacion.FECHA_INICIO.getTexto()),
                votacion.get(CampoDeVotacion.HORA_INICIO.getTexto()));
    }

    /**
     * Parsea la fecha y hora de término de una votación.
     *
     * @param votacion la votación de la cual se obtiene la fecha.
     * @return una fecha y hora de tipo LocalDateTime.
     */

    private static LocalDateTime parsearFechaTiempoTermino(JSONObject votacion) {
        return parsearFechaTiempoVotacion(
                votacion.get(CampoDeVotacion.FECHA_TERMINO.getTexto()),
                votacion.get(CampoDeVotacion.HORA_TERMINO.getTexto()));
    }

    /**
     * Parsea la información de fecha y hora obtenida a partir de un JSONObject,
     * a un objeto LocalDateTime.
     *
     * @param fecha el día, mes y año de una fecha.
     * @param tiempo la hora y minutos de un horario dado.
     * @return una fecha y hora de tipo LocalDateTime.
     */

    private static LocalDateTime parsearFechaTiempoVotacion(Object fecha, Object tiempo) {
        var fechaArr = fecha.toString().split("-");
        var dia = Integer.parseInt(fechaArr[0]);
        var mes = Integer.parseInt(fechaArr[1]);
        var anio = Integer.parseInt(fechaArr[2]);
        var tiempoArr = tiempo.toString().split(":");
        var hora = Integer.parseInt(tiempoArr[0]);
        var minutos = Integer.parseInt(tiempoArr[1]);
        return LocalDateTime.of(anio, mes, dia, hora, minutos);
    }

    /**
     * Obtiene una lista de las opciones asociadas a una votación.
     *
     * @param votacion la votación a la cual la opción pertenece.
     * @param votacionSiguiente la votación de la cual se obtiene la información de las opciones.
     * @return una lista de tipo List que almacena objetos de tipo Opcion.
     */

    public static List<Opcion> obtenerOpcionesDeVotacionJSON(Votacion votacion, JSONObject votacionSiguiente) {
        var opciones = obtenerOpcionesDeVotacionJSON(votacionSiguiente);
        for (var opcion : opciones) opcion.setVotacion(votacion);
        return opciones;
    }

    /**
     * Obtiene una lista de las opciones asociadas a una votación.
     *
     * @param votacionSiguiente la votación de la cual se ontiene la información de las opciones.
     * @return una lista de tipo List que almacena objetos de tipo opción.
     */

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

    /**
     * Obtiene y asigna una lista de los usuarios votantes asociados a una votación.
     *
     * @param votacion la votacion a la cual se le asignan votantes.
     * @param votacionSiguiente un JSONObject que representa una votación del cual se obtienen los votantes.
     * @return una lista de tipo List que almacena objetos de tipo Usuario.
     */

    public static List<Usuario> obtenerVotantesDeVotacionJSON(Votacion votacion, JSONObject votacionSiguiente) {
        List<Usuario> votantes = obtenerVotantesDeVotacionJSON(votacionSiguiente);
        votacion.setVotantes(votantes);
        return votantes;
    }

    /**
     * Obtiene cada votante de una votación en un JSONObject.
     *
     * @param votacionSiguiente un JSONObject que representa la votación de la cual se obtienen los votantes.
     * @return una lista de tipo List que almacena objetos de tipo Usuario.
     */

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

    /**
     * Obtiene votaciones en ciertos estados requeridos de la lista de votaciones totales.
     *
     * @param estados un mapa que contiene como llave un EstadoDeVotacion y como valor un Boolean indicando
     * si dicho estado es requerido.
     * @return una lista de tipo List que almacena objetos de tipo Votacion.
     */

    public static List<Votacion> obtenerVotacionesConEstados(HashMap<EstadoDeVotacion, Boolean> estados) {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> votacionesBorradores = new ArrayList<>();
        votaciones
                .stream()
                .filter(votacionSiguiente -> verificarSiTieneEstado(estados, votacionSiguiente))
                .forEach(votacionesBorradores::add);
        return votacionesBorradores;
    }

    /**
     * Verifica que una votación se encuentre en cierto estado requerido, mediante un HashMap que en su
     * llave indica el estado y en su valor indica con el Boolean si es requerido (true) o no (false).
     *
     * @param estados un mapa que contiene como llave un EstadoDeVotacion y como valor un Boolean indicando
     * si dicho estado es requerido.
     * @param votacion una votación de la que se verifica el estado.
     * @return un booleano que indica si tiene o no el estado solicitado.
     */

    private static boolean verificarSiTieneEstado(HashMap<EstadoDeVotacion, Boolean> estados, Votacion votacion) {
        for (EstadoDeVotacion estado : estados.keySet()) {
            if (votacion.getEstadoDeVotacion().equals(estado) && estados.get(estado)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene una lista con todas las votaciones que se encuentran en estado EN CURSO.
     *
     * @return una lista de tipo List que almacena objetos de tipo Votacion.
     */

    public static List<Votacion> obtenerVotacionesEnCurso() {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> votacionesEnCurso = new ArrayList<>();
        votaciones
                .stream()
                .filter(votacionSiguiente -> votacionSiguiente.getEstadoDeVotacion().equals(EstadoDeVotacion.EN_CURSO))
                .forEach(votacionesEnCurso::add);
        return votacionesEnCurso;
    }

    /**
     * Obtiene una lista con todas las votaciones que se encuentran en estado FINALIZADO.
     *
     * @return una lista de tipo List que almacena objetos de tipo Votacion.
     */

    public static List<Votacion> obtenerVotacionesFinalizadas() {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> votacionesEnCurso = new ArrayList<>();
        votaciones
                .stream()
                .filter(votacionSiguiente -> votacionSiguiente.getEstadoDeVotacion().equals(EstadoDeVotacion.FINALIZADO))
                .forEach(votacionesEnCurso::add);
        return votacionesEnCurso;
    }

    /**
     * Obtiene una lista de votaciones en las cuales el usuario votante está habilitado para votar.
     *
     * @param votante el usuario votante que se usa como parámetro para filtrar la lista.
     * @return una lista de tipo List que almacena objetos de tipo Votacion.
     */

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

    /**
     * Obtiene un nuevo identificador numérico único para una votación, a partir de la lista
     * de votaciones totales.
     *
     * @return un String que representa el valor del identificador numérico único.
     */

    private static String obtenerNuevaIdVotacion() {
        List<Votacion> votaciones = obtenerVotaciones();
        var maxID = votaciones.stream().max(Comparator.comparing(Votacion::getId))
                .orElse(new Votacion()).getId();
        maxID++;
        return String.valueOf(maxID);
    }

    /**
     * Verifica si un usuario votante ya emitió su voto en una votación dada.
     *
     * @param votacionSiguiente la votación de la que se comprueba si un usuario votante votó.
     * @param idVotante el identificador numérico único del votante para comprobar si votó.
     * @return un booleano que indica si el votante votó o no.
     */

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

    /**
     * Sobreescribe la lista de votaciones totales en un archivo JSON.
     *
     * @param votaciones la lista de votaciones a escribir.
     */

    public static void escribirVotaciones(List<Votacion> votaciones) {
        AccesoADatos.escribirArchivoJSON(RUTA_VOTACIONES, convertirListaDeVotacionesAJSONArray(votaciones).toJSONString());
    }

    /**
     * Convierte una lista de votaciones a un JSONArray.
     *
     * @param votaciones lista de votaciones a convertir en JSONArray.
     * @return un arreglo de tipo JSONArray.
     */

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

    /**
     * Convierte y almacena los campos de id, título, descripción, votos preferenciales, votos blancos y
     * estado de una votación en un JSONObject.
     *
     * @param votacion la votación de la cuál se obtienen los campos.
     * @param votacionObj el JSONObject en el cual se guardaran los campos.
     */

    private static void convertirJSONCampoInfoVotacion(Votacion votacion, JSONObject votacionObj) {
        votacionObj.put(CampoDeVotacion.ID.getTexto(), votacion.getId());
        votacionObj.put(CampoDeVotacion.TITULO.getTexto(), votacion.getTitulo());
        votacionObj.put(CampoDeVotacion.DESCRIPCION.getTexto(), votacion.getDescripcion());
        votacionObj.put(CampoDeVotacion.VOTOS_PREFERENCIALES.getTexto(), votacion.getVotosPreferenciales());
        votacionObj.put(CampoDeVotacion.VOTOS_BLANCOS.getTexto(), votacion.getVotosBlancos());
        votacionObj.put(CampoDeVotacion.ESTADO.getTexto(), votacion.getEstadoDeVotacion().getTexto());
    }

    /**
     * Convierte y almacena los campos de fecha y hora de inicio y fecha y hora de termino
     * de una votación en un JSONObject.
     *
     * @param votacion la votación de la cuál se obtienen los campos.
     * @param votacionObj el JSONObject en el cual se guardaran los campos.
     */

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

    /**
     * Convierte y almacena las opciones de una votación en un JSONObject.
     *
     * @param votacion la votación de la cuál se obtienen las opciones.
     * @param votacionObj el JSONObject en el cual se guardaran las opciones.
     */

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

    /**
     * Convierte y almacena los votos blancos y preferenciales de una votación en un JSONObject.
     *
     * @param votacion la votación de la cuál se obtienen los votos.
     * @param votacionObj el JSONObject en el cual se guardaran los votos.
     */

    private static void convertirJSONCampoVotos(Votacion votacion, JSONObject votacionObj) {
        votacionObj.put(CampoDeVotacion.VOTOS_BLANCOS.getTexto(), votacion.getVotosBlancos());
        votacionObj.put(CampoDeVotacion.VOTOS_PREFERENCIALES.getTexto(), votacion.getVotosPreferenciales());
    }

    /**
     * Convierte y almacena los votantes de una votación en un JSONObject.
     *
     * @param votacion la votación de la cuál se obtienen los votantes.
     * @param votacionObj el JSONObject en el cual se guardaran los votantes.
     */

    private static void convertirJSONCampoVotantes(Votacion votacion, JSONObject votacionObj) {
        JSONArray votantesArray = obtenerArrayIdVotantes(votacion);
        votacionObj.put(CampoDeVotacion.VOTANTES.getTexto(), votantesArray);
    }

    /**
     * Obtiene un JSONArray que contenga los identificadores numéricos únicos de todos los
     * usuarios votantes que votaron en una votación.
     *
     * @param votacion la votación de la cual se obtendrán los id de sus votantes.
     * @return un objeto de tipo JSONArray.
     */

    private static JSONArray obtenerArrayIdVotantes(Votacion votacion) {
        JSONArray votantesArray = new JSONArray();
        for (Usuario votante : votacion.getVotantes()) {
            votantesArray.add(votante.getId());
        }
        return votantesArray;
    }

    /**
     * Busca una votación en una lista, haciendo uso de un método que filtra las votaciones
     * por identificadores numéricos únicos.
     *
     * @param votaciones la lista de votaciones totales.
     * @param votacion la votación que se desea buscar.
     * @return
     */

    public static Votacion obtenerVotacionPorID(List<Votacion> votaciones, Votacion votacion) {
        return obtenerVotacionPorID(votaciones, votacion.getId());
    }

    /**
     * Busca una votación en específico en una lista de votaciones usando como filtro
     * un identificador numérico único para cada votación.
     *
     * @param votaciones la lista de votaciones totales.
     * @param idVotacion un identificador numérico único asociado a una votación.
     * @return
     */

    public static Votacion obtenerVotacionPorID(List<Votacion> votaciones, Integer idVotacion) {
        return votaciones
                .stream()
                .filter(votacionSiguiente -> votacionSiguiente.getId().equals(idVotacion))
                .findFirst()
                .orElseThrow(
                        () -> AccesoADatosInterrumpidoException
                                .talElementoNoExiste(CampoDeVotacion.ID.getTexto()));
    }

    /**
     * Guarda una nueva votación en el archivo JSON que las almacena. A esta votación se
     * le asigna un identificador numérico único que la diferenciará de las demás votaciones.
     *
     * @param votacion la nueva votación a guardar.
     */

    public static void crearVotacion(Votacion votacion) {
        votacion.setId(obtenerNuevaIdVotacion());
        List<Votacion> votaciones = obtenerVotaciones();
        votaciones.add(votacion);
        escribirVotaciones(votaciones);
    }

    /**
     * Actualiza los campos de una votación en específico de entre la lista de votaciones totales.
     *
     * @param votacion la votación que se quiere actualizar.
     */

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
