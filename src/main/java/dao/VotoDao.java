package dao;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.Opcion;
import modelos.Votacion;
import modelos.Usuario;
import modelos.Voto;
import modelos.enums.CampoDeVoto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.AccesoADatos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Provee acceso a los datos relacionados con la clase Voto, almacenados en un archivo
 * de extensión JSON.
 */

public class VotoDao {

    /**
     * Indica la ruta del archivo JSON donde se guardan los datos de los votos.
     */

    private static final String RUTA_VOTOS = "src/main/datos/votos.json";

    /**
     * Obtiene una lista de todos los votos almacenados en un archivo JSON.
     *
     * @return una lista de tipo List que almacena objetos de tipo Voto.
     */

    public static List<Voto> obtenerVotos() {
        List<Voto> votos = new ArrayList<>();
        JSONArray jsonArrayVotos = parsearVotos();
        for (Object jsonArrayVoto : jsonArrayVotos) {
            JSONObject votoSiguiente = (JSONObject) jsonArrayVoto;
            Voto voto = new Voto();
            asignarCamposDelVoto(votoSiguiente, voto);
            votos.add(voto);
        }
        return votos;
    }

    /**
     * Asigna atributos a un voto, obtenidos a través de un JSONObject.
     *
     * @param votoSiguiente el JSONObject que contiene información sobre un voto.
     * @param voto el voto al cual se le asignan los datos.
     */

    private static void asignarCamposDelVoto(JSONObject votoSiguiente, Voto voto) {
        voto.setId(votoSiguiente.get(CampoDeVoto.ID.getTexto()));
        asignarVotanteDelVoto(votoSiguiente, voto);
        asignarVotacionDelVoto(votoSiguiente, voto);
        asignarOpcionDelVoto(votoSiguiente, voto);
    }

    /**
     * Asigna un usuario votante a un voto, obtenido a través de un JSONObject.
     *
     * @param votoSiguiente el JSONObject que contiene información sobre un voto.
     * @param voto el voto al cual se le asigna el votante.
     */

    private static void asignarVotanteDelVoto(JSONObject votoSiguiente, Voto voto) {
        Usuario votante = new Usuario();
        votante.setId(votoSiguiente.get(CampoDeVoto.VOTANTE.getTexto()));
        voto.setVotante(votante);
    }

    /**
     * Asigna una votación a un voto, obtenida a través de un JSONObject.
     *
     * @param votoSiguiente el JSONObject que contiene información sobre un voto.
     * @param voto el voto al cual se le asigna la votación.
     */

    private static void asignarVotacionDelVoto(JSONObject votoSiguiente, Voto voto) {
        Votacion votacion = new Votacion();
        votacion.setId(votoSiguiente.get(CampoDeVoto.VOTACION.getTexto()));
        voto.setVotacion(votacion);
    }

    /**
     * Asigna una opción a un voto, obtenida a través de un JSONObject.
     *
     * @param votoSiguiente el JSONObject que contiene información sobre una opción.
     * @param voto el voto al cual se le asigna la opción.
     */

    private static void asignarOpcionDelVoto(JSONObject votoSiguiente, Voto voto) {
        Opcion opcion = new Opcion();
        opcion.setId(votoSiguiente.get(CampoDeVoto.OPCION.getTexto()));
        voto.setOpcion(opcion);
    }

    /**
     * Obtiene un nuevo identificador numérico único para un voto.
     *
     * @return un String con el valor del nuevo identificador numérico único.
     */

    public static String obtenerNuevaIdVoto() {
        List<Voto> votos = obtenerVotos();
        var maxID = votos.stream().max(Comparator.comparing(Voto::getId)).orElse(new Voto()).getId();
        maxID++;
        return String.valueOf(maxID);
    }

    /**
     * Parsea los datos almacenados en un archivo JSON de los votos a un JSONArray.
     *
     * @return un arreglo de tipo JSONArray.
     * @throws AccesoADatosInterrumpidoException cuando ocurre una excepción inesperada al intentar parsear
     * un archivo JSON.
     */

    public static JSONArray parsearVotos() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_VOTOS);
    }

    /**
     * Guarda una lista de votos en un archivo JSON.
     *
     * @param votos la lista de votos a escribir en el archivo.
     */

    public static void escribirVotos(List<Voto> votos) {
        AccesoADatos.escribirArchivoJSON(RUTA_VOTOS, convertirListaDeVotosAJSONArray(votos).toJSONString());
    }

    /**
     * Convierte una lista de tipo List que almacena objetos de tipo Voto a un JSONArray.
     *
     * @param votos la lista de votos que será convertida a JSONArray.
     * @return un arreglo de tipo JSONArray.
     */

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
}
