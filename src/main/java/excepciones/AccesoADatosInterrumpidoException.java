package excepciones;

/**
 * Crea una excepción personalizada para interrupciones inesperadas en los procesos
 * de acceso a datos.
 *
 */

public class AccesoADatosInterrumpidoException extends RuntimeException {

    /**
     * Construye un objeto de AccesoADatosInterrumpidoException con un mensaje que da detalles de la excepción.
     *
     *
     * @param mensaje dato específico del mensaje que se mostrará al lanzarse la excepción, el formato
     * en el cual es mostrado varía dependiendo de la situación.
     */

    private AccesoADatosInterrumpidoException(String mensaje) {
        super(mensaje);
    }

    /**
     * lanza una excepción cuando un archivo no pudo ser cargado.
     *
     * @param ruta la ruta del archivo que no se pudo cargar.
     * @return una excepción de tipo AccesoADatosInterrumpidoException.
     */

    public static AccesoADatosInterrumpidoException noSePudoCargarArchivo(String ruta) {
        return new AccesoADatosInterrumpidoException("No se pudo cargar el archivo " + ruta);
    }

    /**
     * lanza una excepción cuando un archivo no se pudo parsear.
     *
     * @param ruta la ruta del archivo que no se pudo parsear.
     * @return una excepción de tipo AccesoADatosInterrumpidoException.
     */

    public static AccesoADatosInterrumpidoException noSePudoParsearArchivo(String ruta) {
        return new AccesoADatosInterrumpidoException("No se pudo parsear el archivo " + ruta);
    }

    /**
     * lanza una excepción cuando un dato en un archivo no fue encontrado.
     *
     * @param dato el dato que no pudo ser encontrado.
     * @return una excepción de tipo AccesoADatosInterrumpidoException.
     */

    public static AccesoADatosInterrumpidoException talElementoNoExiste(String dato) {
        return new AccesoADatosInterrumpidoException(
                "El elemento asociado con el dato " + dato + " no existe");
    }

    /**
     * Se encarga de lanzar una excepción cuando no se pudo escribir un archivo.
     *
     * @param ruta la ruta del archivo que no pudo ser escrito.
     * @return una excepción de tipo AccesoADatosInterrumpidoException.
     */

    public static AccesoADatosInterrumpidoException noSePudoEscribirArchivo(String ruta) {
        return new AccesoADatosInterrumpidoException("No se pudo escribir el archivo " + ruta);
    }
}
