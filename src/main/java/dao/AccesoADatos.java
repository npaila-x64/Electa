package dao;

import excepciones.AccesoADatosInterrumpidoException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
}
