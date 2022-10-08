public class AccesoADatosInterrumpidoException extends RuntimeException {

    private AccesoADatosInterrumpidoException(String mensaje) {
        super(mensaje);
    }

    public static AccesoADatosInterrumpidoException noSePudoCargarArchivo(String ruta) {
        return new AccesoADatosInterrumpidoException("No se pudo cargar el archivo " + ruta);
    }

    public static AccesoADatosInterrumpidoException noSePudoParsearArchivo(String ruta) {
        return new AccesoADatosInterrumpidoException("No se pudo parsear el archivo " + ruta);
    }

    public static AccesoADatosInterrumpidoException talElementoNoExiste(String dato) {
        return new AccesoADatosInterrumpidoException(
                "El elemento asociado con el dato " + dato + " no existe");
    }

    public static AccesoADatosInterrumpidoException noSePudoEscribirArchivo(String ruta) {
        return new AccesoADatosInterrumpidoException("No se pudo escribir el archivo " + ruta);
    }
}
