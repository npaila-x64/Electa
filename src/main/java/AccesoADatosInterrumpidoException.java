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

    public static AccesoADatosInterrumpidoException talElementoNoExiste(String elemento) {
        return new AccesoADatosInterrumpidoException(
                "El elemento buscado con el dato " + elemento + " no existe");
    }
}
