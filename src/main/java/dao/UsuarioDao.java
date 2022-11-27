package dao;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.Votante;
import modelos.enums.CampoDeVotacion;
import modelos.enums.CampoDeVotante;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.AccesoADatos;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    private static final String RUTA_ADMINISTRADORES = "src/main/datos/administradores.json";
    private static final String RUTA_VOTANTES = "src/main/datos/votantes.json";

    private static JSONArray parsearVotantes() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_VOTANTES);
    }

    private static JSONArray parsearAdministradores() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_ADMINISTRADORES);
    }

    public static List<Votante> obtenerVotantes() {
        return obtenerUsuarios(parsearVotantes());
    }

    public static List<Votante> obtenerAdministradores(){
        return obtenerUsuarios(parsearAdministradores());
    }

    public static List<Votante> obtenerUsuarios(JSONArray jsonArrayUsuarios){
        List<Votante> votantes = new ArrayList<>();
        for (Object jsonArrayUsuario : jsonArrayUsuarios) {
            JSONObject jsonArrayUsuarioSiguiente = (JSONObject) jsonArrayUsuario;
            Votante votante = new Votante();
            obtenerAtributosDeVotanteJSON(votante, jsonArrayUsuarioSiguiente);
            votantes.add(votante);
        }
        return votantes;
    }

    private static void obtenerAtributosDeVotanteJSON(Votante votante, JSONObject votanteJSON) {
        votante.setId(votanteJSON.get(CampoDeVotante.ID.getTexto()));
        votante.setRut(votanteJSON.get(CampoDeVotante.RUT.getTexto()));
        votante.setClave(votanteJSON.get(CampoDeVotante.CLAVE.getTexto()));
    }
    
    public static Votante obtenerVotantePorId(Integer idVotante){
        var votantes = obtenerVotantes();
        return votantes.stream().filter
                (votanteSiguiente -> votanteSiguiente.getId().equals(idVotante))
                .findFirst().orElseThrow(
                        () -> AccesoADatosInterrumpidoException
                        .talElementoNoExiste(CampoDeVotacion.ID.getTexto()));
    }
}