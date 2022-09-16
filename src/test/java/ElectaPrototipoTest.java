import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectaPrototipoTest {

    ElectaPrototipo app;

    @BeforeEach
    void init() {
        app = new ElectaPrototipo();
    }

    @Test
    void esCredencialVotanteValidaTest() {
        /* La tupla rut/clave 111111111 / 1111 es parte de las pruebas del sistema */
        assertTrue(app.esCredencialVotanteValida("111111111", "1111"));
    }
}