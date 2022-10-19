package modelos.dao;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.Votacion;
import modelos.Votante;
import modelos.enums.CampoDeVotacion;
import modelos.enums.CampoDeVotante;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.AccesoADatos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UsuarioDao {

    private static final String RUTA_CREDENCIALES_ADMIN = "src/main/datos/credencialesAdmin.json";
    private final String RUTA_VOTANTES = "src/main/datos/votantes.json";

    private JSONArray parsearVotantes() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_VOTANTES);
    }

    private JSONArray parsearCredencialAdmin() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_CREDENCIALES_ADMIN);
    }

    private JSONArray obtenerArrayIdVotantes(Votacion votacion) {
        JSONArray votantesArray = new JSONArray();
        for (Votante votante : votacion.getVotantes()) {
            votantesArray.add(votante.getId());
        }
        return votantesArray;
    }

    public List<Votante> obtenerVotantes() {
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

    public String obtenerCredencialAdmin() {
        JSONArray credencialArray = parsearCredencialAdmin();
        JSONObject credencialObject = (JSONObject) credencialArray.get(0);
        String claveObtenida = String.valueOf(credencialObject.get(CampoDeVotante.CLAVE.getTexto()));
        return claveObtenida;
    }

    private void obtenerAtributosDeVotanteJSON(Votante votante, JSONObject votanteJSON) {
        votante.setId(votanteJSON.get(CampoDeVotante.ID.getTexto()));
        votante.setRut(votanteJSON.get(CampoDeVotante.RUT.getTexto()));
        votante.setClave(votanteJSON.get(CampoDeVotante.CLAVE.getTexto()));
    }
}