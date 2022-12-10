package dao;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.Usuario;
import modelos.enums.CampoDeVotacion;
import modelos.enums.CampoDeVotante;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    public static List<Usuario> obtenerVotantes() {
        return obtenerUsuarios(parsearVotantes());
    }

    public static List<Usuario> obtenerAdministradores(){
        return obtenerUsuarios(parsearAdministradores());
    }

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

    private static void obtenerAtributosDeVotanteJSON(Usuario usuario, JSONObject votanteJSON) {
        usuario.setId(votanteJSON.get(CampoDeVotante.ID.getTexto()));
        usuario.setRut(votanteJSON.get(CampoDeVotante.RUT.getTexto()));
        usuario.setClave(votanteJSON.get(CampoDeVotante.CLAVE.getTexto()));
    }
    
    public static Usuario obtenerVotantePorId(Integer idVotante){
        var votantes = obtenerVotantes();
        return votantes.stream().filter
                (votanteSiguiente -> votanteSiguiente.getId().equals(idVotante))
                .findFirst().orElseThrow(
                        () -> AccesoADatosInterrumpidoException
                        .talElementoNoExiste(CampoDeVotacion.ID.getTexto()));
    }
}