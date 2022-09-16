import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectaPrototipoTest {

    @Test
    void esCredencialVotanteValidaTest() {
        /* La tupla rut/clave 111111111 / 1111 es parte de las pruebas del sistema */
        assertTrue(ElectaPrototipo.esCredencialVotanteValida("111111111", "1111"));
    }
}