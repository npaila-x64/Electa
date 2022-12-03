package utils;

import excepciones.AccesoADatosInterrumpidoException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Interactúa de forma directa con archivos de tipo JSON. Realiza acciones de lectura,
 * escritura y parseo.
 *
 */

public class AccesoADatos {

    /**
     * Lee y parsea el contenido escrito en un archivo JSON.
     *
     * @param ruta la ruta del archivo JSON.
     * @return un objeto de tipo JSONArray.
     * @throws AccesoADatosInterrumpidoException cuando ocurre un error al buscar o parsear el archivo.
     */

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

    /**
     * Lee el contenido de un archivo JSON linea por linea y lo almacena en un String.
     *
     * @param ruta la ruta del archivo JSON.
     * @return un string con el contenido del archivo.
     * @throws FileNotFoundException cuando no se encuentra ningún archivo JSON con la ruta dada.
     */

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

    /**
     * Escribe un archivo JSON dada una ruta y contenido que deba ser guardado.
     *
     * @param ruta la ruta del archivo JSON.
     * @param contenido un string con el contenido que debe ser escrito en el archivo.
     */

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
