import excepciones.AccesoADatosInterrumpidoException;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.AccesoADatos;

import java.io.File;
import java.nio.file.Files;

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

    @BeforeAll
    static void resetearDatosTests() {
        try {
            var respaldo = new File("src/main/datos-de-prueba/votaciones_respaldo.json");
            var test = new File("src/main/datos-de-prueba/votaciones_test.json");
            test.delete();
            Files.copy(respaldo.toPath(), test.toPath());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void verificaQueCargarArchivoJSONNoExistenteTiraExcepcion() {
        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
                () -> AccesoADatos.parsearArchivoJSON("ruta/que/no/existe"));
        logger.info("Se ha lanzado la excepción excepciones.AccesoADatosInterrumpidoException, dado " +
                "que el archivo JSON no puede ser parseado. " + exception.getMessage());
    }

    @Test
    void verificaQueMetodoTiraExcepcionSiSeLeEntregaRutaNoExistente() {
        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
                () -> AccesoADatos.escribirArchivoJSON("ruta/que/no/existe", "123"));
        logger.info("Se ha lanzado la excepción excepciones.AccesoADatosInterrumpidoException, dado " +
                "que la ruta entregada no existe. " + exception.getMessage());
    }

    @Test
    void verificaQueMetodoTiraExcepcionSiSeLeEntregaContenidoNulo() {
        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
                () -> AccesoADatos.escribirArchivoJSON
                        ("src/main/datos-de-prueba/votaciones_test.json", (String) null));
        logger.info("Se ha lanzado la excepción excepciones.AccesoADatosInterrumpidoException, dado " +
                "que el contenido entregado es nulo. " + exception.getMessage());
    }
}