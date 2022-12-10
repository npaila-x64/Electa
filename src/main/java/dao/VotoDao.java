package dao;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.Opcion;
import modelos.Votacion;
import modelos.Usuario;
import modelos.Voto;
import modelos.enums.CampoDeVoto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VotoDao {

    private static final String RUTA_VOTOS = "src/main/datos/votos.json";

    public static List<Voto> obtenerVotos() {
        List<Voto> votos = new ArrayList<>();
        // TODO ¿será necesario cargar todos los datos de los modelos a que hace referencia el voto?
        JSONArray jsonArrayVotos = parsearVotos();
        for (Object jsonArrayVoto : jsonArrayVotos) {
            JSONObject votoSiguiente = (JSONObject) jsonArrayVoto;
            Voto voto = new Voto();
            asignarCamposDelVoto(votoSiguiente, voto);
            votos.add(voto);
        }
        return votos;
    }

    private static void asignarCamposDelVoto(JSONObject votoSiguiente, Voto voto) {
        voto.setId(votoSiguiente.get(CampoDeVoto.ID.getTexto()));
        asignarVotanteDelVoto(votoSiguiente, voto);
        asignarVotacionDelVoto(votoSiguiente, voto);
        asignarOpcionDelVoto(votoSiguiente, voto);
    }

    private static void asignarVotanteDelVoto(JSONObject votoSiguiente, Voto voto) {
        Usuario votante = new Usuario();
        votante.setId(votoSiguiente.get(CampoDeVoto.VOTANTE.getTexto()));
        voto.setVotante(votante);
    }

    private static void asignarVotacionDelVoto(JSONObject votoSiguiente, Voto voto) {
        Votacion votacion = new Votacion();
        votacion.setId(votoSiguiente.get(CampoDeVoto.VOTACION.getTexto()));
        voto.setVotacion(votacion);
    }

    private static void asignarOpcionDelVoto(JSONObject votoSiguiente, Voto voto) {
        Opcion opcion = new Opcion();
        opcion.setId(votoSiguiente.get(CampoDeVoto.OPCION.getTexto()));
        voto.setOpcion(opcion);
    }

    public static String obtenerNuevaIdVoto() {
        List<Voto> votos = obtenerVotos();
        var maxID = votos.stream().max(Comparator.comparing(Voto::getId)).orElse(new Voto()).getId();
        maxID++;
        return String.valueOf(maxID);
    }

    public static JSONArray parsearVotos() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_VOTOS);
    }

    public static void escribirVotos(List<Voto> votos) {
        AccesoADatos.escribirArchivoJSON(RUTA_VOTOS, convertirListaDeVotosAJSONArray(votos).toJSONString());
    }

    private static void incrementarCantidadDeVotosDeOpcionEnUno(Opcion opcion) {
        int votosOpcion = opcion.getCantidadDeVotos();
        votosOpcion++;
        opcion.setCantidadDeVotos(votosOpcion);
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
}
