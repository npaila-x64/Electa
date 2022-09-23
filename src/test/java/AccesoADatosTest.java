import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class AccesoADatosTest {

    private Logger logger;

    @BeforeEach
    void init() {
        logger = Logger.getLogger("AccesoADatosTest.class");
    }

    @BeforeAll
    static void limpiarLog() {
        try {
            new File("src/test/resources/testinglogs.log").delete();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void verificaQueVotoOpcionPreferencialNuloTiraExcepcion(){
        var exception = assertThrows(NullPointerException.class, () ->
                AccesoADatos.votarOpcionPreferencial(null, "1", "1")
                , "Se ha ingresado una entrada nula");
        logger.info("Se ha lanzado la excepción NullPointerException, dado " +
                "que el objeto JSONObject de votaciones es nulo. " + exception.getMessage());
    }

    @Test
    void verificaQueCargarArchivoJSONNoExistenteTiraExcepcion() {
        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
                () -> AccesoADatos.parsearArchivoJSON("ruta/que/no/existe"));
        logger.info("Se ha lanzado la excepción AccesoADatosInterrumpidoException, dado " +
                "que el archivo JSON no puede ser parseado. " + exception.getMessage());
    }

    @Test
    void verificaQueIntentarObtenerRutNoExistenteTiraExcepcion() {
        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
                () -> AccesoADatos.obtenerIDDeRut("123"));
        logger.info("Se ha lanzado la excepción AccesoADatosInterrumpidoException, dado " +
                "que la ID asociada al RUT dado no existe. " + exception.getMessage());
    }
}