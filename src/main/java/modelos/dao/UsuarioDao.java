package modelos.dao;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.Votante;
import modelos.enums.CampoDeVotante;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.AccesoADatos;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private static final String RUTA_CREDENCIALES_ADMIN = "src/main/datos/credencialesAdmin.json";
    private static final String RUTA_VOTANTES = "src/main/datos/votantes.json";

    private static JSONArray parsearVotantes() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_VOTANTES);
    }

    private static JSONArray parsearCredencialAdmin() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_CREDENCIALES_ADMIN);
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

    public static String obtenerCredencialAdmin() {
        JSONArray credencialArray = parsearCredencialAdmin();
        JSONObject credencialObject = (JSONObject) credencialArray.get(0);
        return String.valueOf(credencialObject.get(CampoDeVotante.CLAVE.getTexto()));
    }

    private static void obtenerAtributosDeVotanteJSON(Votante votante, JSONObject votanteJSON) {
        votante.setId(votanteJSON.get(CampoDeVotante.ID.getTexto()));
        votante.setRut(votanteJSON.get(CampoDeVotante.RUT.getTexto()));
        votante.setClave(votanteJSON.get(CampoDeVotante.CLAVE.getTexto()));
    }
}