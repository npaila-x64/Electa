import org.junit.jupiter.api.Test;
import utils.ValidadorDeDatos;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorDeDatosTest {

    @Test
    void esCredencialVotanteValidaTest() {
        /* La tupla rut/clave 111111111 / 1111 es parte de las pruebas del sistema */
        assertTrue(ValidadorDeDatos.esCredencialVotanteValida("111111111", "1111"));
    }

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

    @Test
    void esFechaValidaTest(){
        assertTrue(ValidadorDeDatos.esFechaValida("02-11-2003"));
    }

    @Test
    void esFechaValidaFallaTest(){
        /* La fecha entregada es de un año bisiesto, por lo que febrero llega hasta el día 29 */
        assertFalse(ValidadorDeDatos.esFechaValida("99-99-9999"));
    }

    @Test
    void esFechaValidaFallaTest2(){
        /* La fecha entregada es de un año bisiesto, por lo que febrero llega hasta el día 29 */
        assertFalse(ValidadorDeDatos.esFechaValida("30-02-2020"));
    }

    @Test
    void esHoraValidaTest(){
        assertTrue(ValidadorDeDatos.esHoraValida("23:59"));
    }

    @Test
    void esHoraValidaFallaTest(){
        assertFalse(ValidadorDeDatos.esHoraValida("24:00"));
    }
}