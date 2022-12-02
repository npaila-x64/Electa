package lanzador;

import controladores.ControladorAplicacion;
import controladores.ControladorLogin;

/**
 * Clase dedicada a lanzar la aplicación principal Electa.
 */

public class Electa {

    /**
     * Se encarga de iniciar la aplicación haciendo uso de un controlador
     * de tipo ControladorAplicación.
     * @param args
     */

    public static void main(String[] args) {
        new ControladorAplicacion().iniciar();
    }

}
