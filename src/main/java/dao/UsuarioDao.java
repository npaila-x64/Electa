package dao;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.Usuario;
import modelos.enums.CampoDeVotacion;
import modelos.enums.CampoDeVotante;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.AccesoADatos;

import java.util.ArrayList;
import java.util.List;

/**
 * Provee acceso a los datos relacionados con la clase Usuario, almacenados en un archivo
 * de tipo JSON.
 */

public class UsuarioDao {

    /**
     * Indica la ruta del archivo JSON donde se guardan los datos de los administradores.
     */

    private static final String RUTA_ADMINISTRADORES = "src/main/datos/administradores.json";

    /**
     * Indica la ruta del archivo JSON donde se guardan los datos de los votantes.
     */

    private static final String RUTA_VOTANTES = "src/main/datos/votantes.json";

    /**
     * Parsea los datos almacenados en un archivo JSON de los usuarios votantes a un JSONArray.
     *
     * @return un arreglo de tipo JSONArray.
     * @throws AccesoADatosInterrumpidoException cuando ocurre una excepción inesperada al intentar parsear
     * un archivo JSON.
     */

    private static JSONArray parsearVotantes() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_VOTANTES);
    }

    /**
     * Parsea los datos almacenados en un archivo JSON de los usuarios administradores a un JSONArray.
     *
     * @return un arreglo de tipo JSONArray.
     * @throws AccesoADatosInterrumpidoException cuando ocurre una excepción inesperada al intentar parsear
     * un archivo JSON.
     */

    private static JSONArray parsearAdministradores() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_ADMINISTRADORES);
    }

    /**
     * Obtiene una lista de todos los usuarios votantes almacenados en un archivo JSON.
     *
     * @return una lista de tipo List que almacena objetos de tipo Usuario.
     */

    public static List<Usuario> obtenerVotantes() {
        return obtenerUsuarios(parsearVotantes());
    }

    /**
     * Obtiene una lista de todos los usuarios administradores almacenados en un archivo JSON.
     *
     * @return una lista de tipo List que almacena objetos de tipo Usuario.
     */

    public static List<Usuario> obtenerAdministradores(){
        return obtenerUsuarios(parsearAdministradores());
    }

    /**
     * Almacena en una lista cada uno de los usuarios contenidos en un JSONArray.
     *
     * @param jsonArrayUsuarios el JSONArray que contiene información de los usuarios.
     * @return una lista de tipo List que almacena objetos de tipo Usuario.
     */

    public static List<Usuario> obtenerUsuarios(JSONArray jsonArrayUsuarios){
        List<Usuario> usuarios = new ArrayList<>();
        for (Object jsonArrayUsuario : jsonArrayUsuarios) {
            JSONObject jsonArrayUsuarioSiguiente = (JSONObject) jsonArrayUsuario;
            Usuario usuario = new Usuario();
            obtenerAtributosDeVotanteJSON(usuario, jsonArrayUsuarioSiguiente);
            usuarios.add(usuario);
        }
        return usuarios;
    }

    /**
     * Asigna atributos a un usuario votante, obtenidos a través de un JSONObject.
     *
     * @param usuario el usuario al cual asignar los datos.
     * @param votanteJSON el objeto JSONObject que contiene información de un usuario votante.
     */

    private static void obtenerAtributosDeVotanteJSON(Usuario usuario, JSONObject votanteJSON) {
        usuario.setId(votanteJSON.get(CampoDeVotante.ID.getTexto()));
        usuario.setRut(votanteJSON.get(CampoDeVotante.RUT.getTexto()));
        usuario.setClave(votanteJSON.get(CampoDeVotante.CLAVE.getTexto()));
    }
}