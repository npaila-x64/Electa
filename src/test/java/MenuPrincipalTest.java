import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MenuPrincipalTest {

    MenuPrincipal app;

    @BeforeEach
    void init() {
        app = new MenuPrincipal();
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

    @Test
    void padTextoTest() {
        assertEquals("test..........................",
                Utilidades.padTexto("test", ".", 30));
    }
}