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

    private static final String RUTA_VOTANTES = "src/main/datos/votantes.json";
    private static final String RUTA_CREDENCIALES_ADMIN = "src/main/datos/credencialesAdmin.json";
    private static final String RUTA_VOTACIONES = "src/main/datos/votaciones.json";

    public static JSONArray parsearVotantes() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON(RUTA_VOTANTES);
    }

    public static JSONArray parsearCredencialAdmin() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON(RUTA_CREDENCIALES_ADMIN);
    }

    public static JSONArray parsearVotaciones() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON(RUTA_VOTACIONES);
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
        return obtenerCamposVotacionesConEstado(estado, CampoDeVotacion.ID.getTexto());
    }

    public static List<String> obtenerTitulosVotacionesConEstado(String estado) {
        return obtenerCamposVotacionesConEstado(estado, CampoDeVotacion.TITULO.getTexto());
    }

    public static List<String> obtenerCamposVotacionesConEstado(String estado, String campo) {
        List<String> IDsVotaciones = obtenerIDsVotaciones();
        List<String> nuevoCamposVotaciones = new ArrayList<>();
        for (String IDvotacion : IDsVotaciones) {
            JSONObject votacion = obtenerVotacionPorID(IDvotacion);
            if (votacion.get(CampoDeVotacion.ESTADO.getTexto()).equals(estado)) {
                if (votacion.get(campo) == null) throw new NullPointerException();
                nuevoCamposVotaciones.add(String.valueOf(votacion.get(campo)));
            }
        }
        return nuevoCamposVotaciones;
    }

    public static List<String> obtenerOpcionesDeVotacion(String IDVotacion) {
        JSONObject votacion = obtenerVotacionPorID(IDVotacion);
        JSONObject opciones = (JSONObject) votacion.get(CampoDeVotacion.OPCIONES.getTexto());
        return new ArrayList<>(opciones.keySet());
    }

    public static List<String> obtenerTitulosVotaciones(List<String> IDsVotaciones) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> arrayListVotaciones = new ArrayList<>();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            for (String IDVotacion : IDsVotaciones) {
                if (IDVotacion.equals(String.valueOf(
                        votacionSiguiente.get(CampoDeVotacion.ID.getTexto())))) {
                    arrayListVotaciones.add(String.valueOf(
                            votacionSiguiente.get(CampoDeVotacion.TITULO.getTexto())));
                    break;
                }
            }
        }
        return arrayListVotaciones;
    }

    public static List<String> obtenerTitulosVotaciones() {
        return obtenerKeysDeCampoDeVotaciones(CampoDeVotacion.TITULO.getTexto());
    }

    public static List<String> obtenerIDsVotaciones() {
        return obtenerKeysDeCampoDeVotaciones(CampoDeVotacion.ID.getTexto());
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
        JSONArray jsonArrayVotaciones = filtrarVotacionesEnCurso();
        List<String> arrayListIDsVotaciones = new ArrayList<>();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            filtrarVotacionVotadaAVotante(arrayListIDsVotaciones, votacionSiguiente, IDVotante);
        }
        return arrayListIDsVotaciones;
    }

    public static JSONArray filtrarVotacionesEnCurso() {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONArray jsonArrayVotacionesEnCurso = new JSONArray();
        for (var jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (votacionSiguiente.get(
                    CampoDeVotacion.ESTADO.getTexto()).equals(Estado.EN_CURSO.getTexto())) {
                jsonArrayVotacionesEnCurso.add(votacionSiguiente);
            }
        }
        return jsonArrayVotacionesEnCurso;
    }

    public static void filtrarVotacionVotadaAVotante(List<String> IDsVotaciones, JSONObject votacion, String IDVotante) {
        JSONArray arrayVotantes = (JSONArray) votacion.get(CampoDeVotacion.VOTANTES.getTexto());
        salirBucle:
        {
            for (Object IDVotanteQueYaVotoEnEstaVotacion : arrayVotantes) {
                if (IDVotante.equals(String.valueOf(IDVotanteQueYaVotoEnEstaVotacion))) {
                    // Se da a entender que el votante ya está en la
                    // lista de personas quienes votaron en esta votación
                    break salirBucle;
                }
            }
            IDsVotaciones.add(String.valueOf(votacion.get(CampoDeVotacion.ID.getTexto())));
        }
    }

    public static JSONObject obtenerVotacionPorID(JSONArray jsonArrayVotaciones, String IDVotacion) {
        return obtenerVotacionPorCampo(jsonArrayVotaciones, CampoDeVotacion.ID.getTexto(), IDVotacion);
    }

    public static JSONObject obtenerVotacionPorID(String IDVotacion) {
        return obtenerVotacionPorCampo(CampoDeVotacion.ID.getTexto(), IDVotacion);
    }

    public static JSONObject obtenerVotacionPorTitulo(String tituloVotacion) {
        return obtenerVotacionPorCampo(CampoDeVotacion.TITULO.getTexto(), tituloVotacion);
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

    public static JSONObject obtenerVotacionPorCampo(String campo, String valor) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get(campo)).equals(valor)) {
                return votacionSiguiente;
            }
        }
        throw AccesoADatosInterrumpidoException.talElementoNoExiste(campo);
    }

    public static String obtenerIDDeRut(String rut) {
        JSONArray jsonArrayVotaciones = parsearVotantes();
        for (Object arrayVotante : jsonArrayVotaciones) {
            JSONObject votanteSiguiente = (JSONObject) arrayVotante;
            if (votanteSiguiente.get(CampoDeVotante.RUT.getNombre()).equals(rut)) {
                return String.valueOf(votanteSiguiente.get(CampoDeVotante.ID.getNombre()));
            }
        }
        throw AccesoADatosInterrumpidoException.talElementoNoExiste(rut);
    }

    public static String obtenerNuevaIDVotacion() {
        int maxID = 0;
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            int id = parsearObjectAInt(votacionSiguiente.get(CampoDeVotacion.ID.getTexto()));
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
        escribirArchivoJSON(RUTA_VOTACIONES, contenido);
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
        int votosBlancosOpcion = parsearObjectAInt(votacion.get(CampoDeVotacion.VOTOS_BLANCOS.getTexto()));
        votacion.put(CampoDeVotacion.VOTOS_BLANCOS.getTexto(), votosBlancosOpcion + 1);
        realizarVoto(jsonArrayVotaciones, votacion, IDVotante);
    }

    public static void realizarVotoPreferencial(String IDVotacion, String IDVotante, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        votarOpcionPreferencial(jsonArrayVotaciones, IDVotacion, opcionElegida);
        realizarVoto(jsonArrayVotaciones, votacion, IDVotante);
    }

    public static void realizarVoto(JSONArray jsonArrayVotaciones, JSONObject votacion, String IDVotante) {
        JSONArray votantes = (JSONArray) votacion.get(CampoDeVotacion.VOTANTES.getTexto());
        votantes.add(Integer.parseInt(IDVotante));
        AccesoADatos.escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
    }

    public static void votarOpcionPreferencial(JSONArray jsonArrayVotaciones, String IDVotacion, String opcionElegida) {
        JSONObject votacion = AccesoADatos.obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        JSONObject opciones = (JSONObject) votacion.get(CampoDeVotacion.OPCIONES.getTexto());
        List<String> opcionesArray = new ArrayList<>(opciones.keySet());
        for (String opcion : opcionesArray) {
            if (opcion.equals(opcionElegida)) {
                int votosOpcion = parsearObjectAInt(opciones.get(opcion));
                opciones.put(opcion, votosOpcion + 1);
                int votosPreferenciales = parsearObjectAInt(
                        votacion.get(CampoDeVotacion.VOTOS_PREFERENCIALES.getTexto()));
                votacion.put(CampoDeVotacion.VOTOS_PREFERENCIALES.getTexto(), votosPreferenciales + 1);
            }
        }
    }

    public static void eliminarVotacion(String IDVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        jsonArrayVotaciones.remove(votacion);
        escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
    }

    public static void eliminarOpcionDeVotacion(String IDVotacion, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        JSONObject opciones = (JSONObject) votacion.get(CampoDeVotacion.OPCIONES.getTexto());
        opciones.remove(opcionElegida);
        escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
    }

    public static void actualizarCampoDeVotacion(String IDVotacion, String campo, String texto) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        votacion.put(campo, texto);
        escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
    }

    public static void agregarOpcionAVotacion(String IDVotacion, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        JSONObject opciones = (JSONObject) votacion.get(CampoDeVotacion.OPCIONES.getTexto());
        opciones.put(opcionElegida, 0);
        escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
    }

    public static int parsearObjectAInt(Object obj) {
        return Integer.parseInt(String.valueOf(obj));
    }
}
