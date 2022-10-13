import org.junit.jupiter.api.Test;
import utils.ValidadorRut;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorRutTest {

    @Test
    void esRutValidoTest() {
        assertTrue(ValidadorRut.esRutValido("11.365.814-2"));
    }

    @Test
    void esRutConKValidoTest() {
        assertTrue(ValidadorRut.esRutValido("10.564.650-k"));
    }

    @Test
    void esRutValidoFallaTest() {
        assertFalse(ValidadorRut.esRutValido("1.365.814-2"));
    }

    @Test
    void esRutConKValidoFallaTest() {
        assertFalse(ValidadorRut.esRutValido("5.564.650-k"));
    }

    @Test
    void esFormatoDeRutCorrectoTest() {
        assertTrue(ValidadorRut.esFormatoDeRutCorrecto("11.365.814-2"));
    }

    @Test
    void esFormatoDeRutConKCorrectoTest() {
        assertTrue(ValidadorRut.esFormatoDeRutCorrecto("10.564.650-k"));
    }

    @Test
    void esFormatoDeRutCorrectoFallaTest() {
        assertFalse(ValidadorRut.esFormatoDeRutCorrecto(".234.478-8"));
    }

    @Test
    void esFormatoDeRutCorrectoConDVInvalidoFallaTest() {
        assertFalse(ValidadorRut.esFormatoDeRutCorrecto("42.005-a"));
    }
}