import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorRutTest {

    @Test
    void validaCaracter_funcionan() {
        assertTrue(ValidadorRut.esCaracterValido('a'));
        assertTrue(ValidadorRut.esCaracterValido('m'));
        assertTrue(ValidadorRut.esCaracterValido('k'));
        assertTrue(ValidadorRut.esCaracterValido('1'));
    }

    @Test
    void validaCaracter_fallan() {
        assertFalse(ValidadorRut.esCaracterValido(' '));
        assertFalse(ValidadorRut.esCaracterValido('_'));
        assertFalse(ValidadorRut.esCaracterValido('-'));
    }

    @Test
    void esRutValido_funcionan() {
        assertTrue(ValidadorRut.esRutValido("11.365.814-2"));
        assertTrue(ValidadorRut.esRutValido("10.564.650-k"));
        assertTrue(ValidadorRut.esRutValido("4.217.742-3"));
    }

    @Test
    void esRutValido_fallan() {
        assertFalse(ValidadorRut.esRutValido("1.365.814-2"));
        assertFalse(ValidadorRut.esRutValido("5.564.650-k"));
        assertFalse(ValidadorRut.esRutValido("217.742-3"));
    }

    @Test
    void esFormatoDeRutCorrecto_funcionan() {
        assertTrue(ValidadorRut.esFormatoDeRutCorrecto("11.365.814-2"));
        assertTrue(ValidadorRut.esFormatoDeRutCorrecto("10.564.650-k"));
        assertTrue(ValidadorRut.esFormatoDeRutCorrecto("4.217.742-3"));

        assertTrue(ValidadorRut.esFormatoDeRutCorrecto("20.42.005-3"));
    }

    @Test
    void esFormatoDeRutCorrecto_fallan() {
        assertFalse(ValidadorRut.esFormatoDeRutCorrecto(".234.478-8"));
        assertFalse(ValidadorRut.esFormatoDeRutCorrecto("12042.005-3"));
        assertFalse(ValidadorRut.esFormatoDeRutCorrecto("2.122.042.005-k"));
        assertFalse(ValidadorRut.esFormatoDeRutCorrecto("42.005-a"));

        assertFalse(ValidadorRut.esFormatoDeRutCorrecto("20.003.385-K"));
    }

    @Test
    void input() {
        String input = "";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //assertEquals("20.234.478.k", App.s(in));
    }
}