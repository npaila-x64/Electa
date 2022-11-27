package vistas.votante;

import controladores.votante.ControladorVotacion;
import modelos.Votacion;
import utils.ValidadorDeDatos;

import java.util.ArrayList;
import java.util.List;

public class MenuVotacion {

    private final ControladorVotacion controlador;

    public MenuVotacion(ControladorVotacion controlador) {
        this.controlador = controlador;
    }

    public void mostrar() {
        while (true) {
            var votaciones = controlador.obtenerVotacionesEnElQuePuedeVotarElVotante();
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(votaciones.size());
            if (opcionElegida == 0) break;
            controlador.mostrarMenuOpcionesParaVotar(opcionElegida);
        }
    }

    private void mostrarListaDeCampos(List<String> campos) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < campos.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, campos.get(indice));
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }
}
