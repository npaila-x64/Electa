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

    @Test
    void votarOpcionPreferencialTest(){
        assertThrows(NullPointerException.class, () ->
                app.votarOpcionPreferencial(null, null, null)
                , "Se ha ingresado una entrada nula");

    }

    @Test
    void mostrarIndiceOpcionesTest(){
        assertThrows(NullPointerException.class, () ->
                        app.mostrarListaOpciones(null)
                , "Se ha ingresado una entrada nula");
    }
    @Test
    void mostrarResultadosDatosTest(){
        assertThrows(NullPointerException.class, () ->
                        app.mostrarResultadosDatos(null)
                , "Se ha ingresado una entrada nula");
    }
    @Test
    void mostrarResultadosVotosPorOpcionesTest(){
        assertThrows(NullPointerException.class, () ->
                        app.mostrarResultadosVotosPorOpciones(null)
                , "Se ha ingresado una entrada nula");
    }
}