import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AccesoADatosTest {

    @Test
    void verificaQueVotoOpcionPreferencialNuloTiraExcepcion(){
        assertThrows(NullPointerException.class, () ->
                AccesoADatos.votarOpcionPreferencial(null, null, null)
                , "Se ha ingresado una entrada nula");
    }

    @Test
    void verificaQueCargarArchivoJSONNoExistenteTiraExcepcion() {
        assertThrows(AccesoADatosInterrumpidoException.class,
                () -> AccesoADatos.parsearArchivoJSON("ruta/que/no/existe"));
    }

    @Test
    void verificaQueIntentarObtenerRutNoExistenteTiraExcepcion() {
        assertThrows(NoSuchElementException.class,
                () -> AccesoADatos.obtenerIDDeRut("123"));
    }
}