package lanzador;

import controladores.ControladorAplicacion;
import controladores.ControladorLogin;

/**
 * Ejecuta/lanza la aplicación principal Electa.
 */

public class Electa {

    /**
     * Inicia la aplicación haciendo uso de un controlador de tipo ControladorAplicación.
     * @param args
     */

    public static void main(String[] args) {
        new ControladorAplicacion().iniciar();
    }

}
