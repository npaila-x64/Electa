package dao;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.Votacion;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.AccesoADatos;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VotacionDaoTest {

    private Logger logger;
    private List<Votacion> votaciones;

    @BeforeEach
    void init() {
        logger = Logger.getLogger("AccesoADatosTest.class");
        votaciones = new ArrayList<>();
        Votacion votacion = new Votacion();
        votacion.setId(0);
        votacion.setTitulo("Votacion A");
        votaciones.add(new Votacion(votacion));
        votacion.setId(1);
        votacion.setTitulo("Votacion B");
        votaciones.add(new Votacion(votacion));
    }

    @Test
    void verificaQueSeObtieneVotacionPorID() {
        assertDoesNotThrow(() -> VotacionDao.obtenerVotacionPorID(votaciones, 1));
    }

    @Test
    void verificaQueSeTiraExcepcionPorIDDeVotacionNoExistente() {
        var exception = assertThrows(AccesoADatosInterrumpidoException.class,
                () -> VotacionDao.obtenerVotacionPorID(votaciones, 2));
        logger.info("Se ha lanzado la excepción excepciones.AccesoADatosInterrumpidoException, dado " +
                "que al votación con la id entregada no existe. " + exception.getMessage());
    }
}