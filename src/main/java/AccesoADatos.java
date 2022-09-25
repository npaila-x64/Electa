import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    Clase que contiene los métodos que interactúan
    directamente con los archivos de datos JSON
 */

public class AccesoADatos {

    public static JSONArray parsearVotantes() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON("src/main/datos/votantes.json");
    }

    public static JSONArray parsearCredencialAdmin() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON("src/main/datos/credencialesAdmin.json");
    }

    public static JSONArray parsearVotaciones() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON("src/main/datos/votaciones.json");
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

    public static List<String> obtenerIDsVotacionesConEstado(String estado) {
        return obtenerCamposVotacionesConEstado(estado, "id");
    }

    public static List<String> obtenerTitulosVotacionesConEstado(String estado) {
        return obtenerCamposVotacionesConEstado(estado, "titulo");
    }

    public static List<String> obtenerCamposVotacionesConEstado(String estado, String campo) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> IDsVotaciones = obtenerIDsVotaciones();
        List<String> nuevoCamposVotaciones = new ArrayList<>();
        for (String IDvotacion : IDsVotaciones) {
            JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDvotacion);
            if (votacion.get("estado").equals(estado)) {
                nuevoCamposVotaciones.add(String.valueOf(votacion.get(campo)));
            }
        }
        return nuevoCamposVotaciones;
    }

    public static List<String> obtenerOpcionesDeVotacion(String IDVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        JSONObject opciones = (JSONObject) votacion.get("opciones");
        return new ArrayList<>(opciones.keySet());
    }

    public static List<String> obtenerTitulosVotaciones(List<String> IDsVotaciones) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> arrayListVotaciones = new ArrayList<>();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            for (String IDVotacion : IDsVotaciones) {
                if (IDVotacion.equals(String.valueOf(votacionSiguiente.get("id")))) {
                    arrayListVotaciones.add(String.valueOf(votacionSiguiente.get("titulo")));
                    break;
                }
            }
        }
        return arrayListVotaciones;
    }

    public static List<String> obtenerTitulosVotaciones() {
        return obtenerKeysDeCampoDeVotaciones("titulo");
    }

    public static List<String> obtenerIDsVotaciones() {
        return obtenerKeysDeCampoDeVotaciones("id");
    }

    public static List<String> obtenerKeysDeCampoDeVotaciones(String campo) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> arrayListIDsVotaciones = new ArrayList<>();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            arrayListIDsVotaciones.add(String.valueOf(votacionSiguiente.get(campo)));
        }
        return arrayListIDsVotaciones;
    }

    public static List<String> obtenerIDsVotacionesEnElQuePuedeVotarElVotante(String IDVotante) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> arrayListIDsVotaciones = new ArrayList<>();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            JSONArray arrayVotantes = (JSONArray) votacionSiguiente.get("votantes");
            salirBucle:
            {
                for (Object IDVotanteQueYaVotoEnEstaVotacion : arrayVotantes) {
                    if (IDVotante.equals(String.valueOf(IDVotanteQueYaVotoEnEstaVotacion))) {
                        // Se da a entender que el votante ya está en la
                        // lista de personas quienes votaron en esta votación
                        break salirBucle;
                    }
                }
                arrayListIDsVotaciones.add(String.valueOf(votacionSiguiente.get("id")));
            }
        }
        return arrayListIDsVotaciones;
    }

    public static JSONObject obtenerVotacionPorID(JSONArray jsonArrayVotaciones, String IDVotacion) {
        return obtenerVotacionPorCampo(jsonArrayVotaciones, "id", IDVotacion);
    }

    public static JSONObject obtenerVotacionPorTitulo(JSONArray jsonArrayVotaciones, String tituloVotacion) {
        return obtenerVotacionPorCampo(jsonArrayVotaciones, "titulo", tituloVotacion);
    }

    public static JSONObject obtenerVotacionPorCampo(JSONArray jsonArrayVotaciones, String campo, String valor) {
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get(campo)).equals(valor)) {
                return votacionSiguiente;
            }
        }
        throw AccesoADatosInterrumpidoException.talElementoNoExiste(campo);
    }

    public static String obtenerIDDeRut(String rut) {
        JSONArray arrayVotantes = AccesoADatos.parsearVotantes();
        for (Object arrayVotante : arrayVotantes) {
            JSONObject votanteSiguiente = (JSONObject) arrayVotante;
            if (votanteSiguiente.get("rut").equals(rut)) {
                return String.valueOf(votanteSiguiente.get("id"));
            }
        }
        throw AccesoADatosInterrumpidoException.talElementoNoExiste(rut);
    }

    public static String obtenerNuevaIDVotacion() {
        int maxID = 0;
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            int id = parsearObjectAInt(votacionSiguiente.get("id"));
            if (id > maxID) {
                maxID = id;
            }
        }
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

    public static void escribirEnVotaciones(String contenido) {
        escribirArchivoJSON("src/main/datos/votaciones.json", contenido);
    }

    public static void escribirArchivoJSON(String ruta, String contenido) {
        try {
            FileWriter myWriter = new FileWriter(ruta);
            myWriter.write(contenido);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void realizarVotoBlanco(String IDVotacion, String IDVotante) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        int votosBlancosOpcion = parsearObjectAInt(votacion.get("votos_blancos"));
        votacion.put("votos_blancos", votosBlancosOpcion + 1);
        realizarVoto(jsonArrayVotaciones, votacion, IDVotante);
    }

    public static void realizarVotoPreferencial(String IDVotacion, String IDVotante, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        votarOpcionPreferencial(jsonArrayVotaciones, IDVotacion, opcionElegida);
        realizarVoto(jsonArrayVotaciones, votacion, IDVotante);
    }

    public static void realizarVoto(JSONArray jsonArrayVotaciones, JSONObject votacion, String IDVotante) {
        JSONArray votantes = (JSONArray) votacion.get("votantes");
        votantes.add(Integer.parseInt(IDVotante));
        AccesoADatos.escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
    }

    public static void votarOpcionPreferencial(JSONArray jsonArrayVotaciones, String IDVotacion, String opcionElegida) {
        JSONObject votacion = AccesoADatos.obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        JSONObject opciones = (JSONObject) votacion.get("opciones");
        List<String> opcionesArray = new ArrayList<>(opciones.keySet());
        for (String opcion : opcionesArray) {
            if (opcion.equals(opcionElegida)) {
                int votosOpcion = parsearObjectAInt(opciones.get(opcion));
                opciones.put(opcion, votosOpcion + 1);
                int votosPreferenciales = parsearObjectAInt(votacion.get("votos_preferenciales"));
                votacion.put("votos_preferenciales", votosPreferenciales + 1);
            }
        }
    }

    public static int parsearObjectAInt(Object obj) {
        return Integer.parseInt(String.valueOf(obj));
    }
}
