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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static modelos.dao.VotacionDao.escribirVotaciones;
import static modelos.dao.VotacionDao.obtenerVotaciones;

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

    private static final String RUTA_VOTOS = "src/main/datos/votos.json";

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

    public static String obtenerNuevaIdVoto() {
        List<Voto> votos = obtenerVotos();
        var maxID = votos.stream().max(Comparator.comparing(Voto::getId)).orElse(new Voto()).getId();
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
        registrarVoto(votacion, opcionBlanco);
        registrarVotoEnVotos(votacion, votante, opcionBlanco);
        registrarVotanteEnVotaciones(votacion, votante);
    }

    public static void registrarVotoPreferencial(Votacion votacion, Votante votante, Opcion opcionElegida) {
        registrarVoto(votacion, opcionElegida);
        registrarVotoEnVotos(votacion, votante, opcionElegida);
        registrarVotanteEnVotaciones(votacion, votante);
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

    public static void registrarVoto(Votacion votacion, Opcion opcionElegida) {
        List<Votacion> votaciones = obtenerVotaciones();
        for (var votacionSiguiente : votaciones) {
            if (votacionSiguiente.getId().equals(votacion.getId())) {
                List<Opcion> opciones = votacionSiguiente.getOpciones();
                opciones.stream()
                        .filter(opcion -> opcion.getId().equals(opcionElegida.getId()))
                        .forEach(AccesoADatos::incrementarCantidadDeVotosDeOpcionEnUno);
                votacionSiguiente.setOpciones(opciones);
                escribirVotaciones(votaciones);
                return;
            }
        }
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
