import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.sql.SQLOutput;

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

//    @Test
//    void verificaQueVotoOpcionPreferencialNuloTiraExcepcion(){
//        var exception = assertThrows(NullPointerException.class, () ->
//                AccesoADatos.votarOpcionPreferencial(null, "1", "1")
//                , "Se ha ingresado una entrada nula");
//        logger.info("Se ha lanzado la excepción NullPointerException, dado " +
//                "que el objeto JSONObject de votaciones es nulo. " + exception.getMessage());
//    }

    @Test
    void verificaQueCargarArchivoJSONNoExistenteTiraExcepcion() {
        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
                () -> AccesoADatos.parsearArchivoJSON("ruta/que/no/existe"));
        logger.info("Se ha lanzado la excepción AccesoADatosInterrumpidoException, dado " +
                "que el archivo JSON no puede ser parseado. " + exception.getMessage());
    }

//    @Test
//    void verificaQueIntentarObtenerRutNoExistenteTiraExcepcion() {
//        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
//                () -> AccesoADatos.obtenerIDDeRut("123"));
//        logger.info("Se ha lanzado la excepción AccesoADatosInterrumpidoException, dado " +
//                "que la ID asociada al RUT dado no existe. " + exception.getMessage());
//    }

//    @Test
//    void verificaQueMetodoTiraExcepcionSiSeLeEntregaCampoNoExistente() {
//        var exception = assertThrows(NullPointerException.class,
//                () -> AccesoADatos
//                        .obtenerVotacionesConEstado(Estado.FINALIZADO));
//        logger.info("Se ha lanzado la excepción NullPointerException, dado " +
//                "que campo de votaciones dado no existe. " + exception.getMessage());
//    }

    @Test
    void verificaQueMetodoTiraExcepcionSiSeLeEntregaCampoNoExistente2() {
        JSONArray jsonArrayVotaciones = AccesoADatos.parsearVotaciones();
        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
                () -> AccesoADatos
                        .obtenerVotacionPorCampo(
                                jsonArrayVotaciones, "no_existe", "1"));
        logger.info("Se ha lanzado la excepción AccesoADatosInterrumpidoException, dado " +
                "que campo de votaciones dado no existe. " + exception.getMessage());
    }

    @Test
    void verificaQueMetodoTiraExcepcionSiSeLeEntregaRutaNoExistente() {
        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
                () -> AccesoADatos.escribirArchivoJSON("ruta/que/no/existe", "123"));
        logger.info("Se ha lanzado la excepción AccesoADatosInterrumpidoException, dado " +
                "que la ruta entregada no existe. " + exception.getMessage());
    }

    @Test
    void verificaQueMetodoTiraExcepcionSiSeLeEntregaContenidoNulo() {
        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
                () -> AccesoADatos.escribirArchivoJSON
                        ("src/main/datos-de-prueba/votaciones_test.json", (String) null));
        logger.info("Se ha lanzado la excepción AccesoADatosInterrumpidoException, dado " +
                "que el contenido entregado es nulo. " + exception.getMessage());
    }
//    @Test
//    void verificaQueMetodoTiraExcepcionSiSeLeEntregaCampoNoExistente2() {
//        JSONArray jsonArrayVotaciones = AccesoADatos.parsearVotaciones();
//        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
//                () -> AccesoADatos
//                        .obtenerVotacionPorCampo(
//                                jsonArrayVotaciones, "no_existe", "1"));
//        logger.info("Se ha lanzado la excepción AccesoADatosInterrumpidoException, dado " +
//                "que campo de votaciones dado no existe. " + exception.getMessage());
//    }
}