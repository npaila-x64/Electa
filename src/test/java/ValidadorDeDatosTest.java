import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorDeDatosTest {

    @Test
    void esFormatoFechaValidoTest() {
        assertTrue(ValidadorDeDatos.esFormatoFechaValido("23-02-2022"));
    }

    @Test
    void esFormatoFechaValidoFallaTest() {
        assertFalse(ValidadorDeDatos.esFormatoFechaValido("11-10-202"));
    }

    @Test
    void esFormatoHoraValidoTest() {
        assertTrue(ValidadorDeDatos.esFormatoHoraValido("99:99"));
    }

    @Test
    void esFormatoHoraValidoFallaTest() {
        assertFalse(ValidadorDeDatos.esFormatoHoraValido("9:99"));
    }
}