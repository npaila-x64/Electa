package vistas.votante;

import controladores.votante.ControladorVotacion;
import modelos.Opcion;
import modelos.Votacion;
import modelos.Votante;
import utils.AccesoADatos;
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
            var votaciones = controlador.obtenerVotaciones();
            mostrarVotacionesDisponiblesParaVotacion(votaciones);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(votaciones.size());
            if (opcionElegida == 0) break;
            var votante = controlador.obtenerVotante();
            controlador.mostrarMenuOpcionesParaVotar(opcionElegida);
        }
    }

    private void mostrarVotacionesDisponiblesParaVotacion(List<Votacion> votaciones) {
        System.out.println("Votaciones disponibles para votación");
        List<String> titulos = new ArrayList<>();
        for (var votacionSiguiente : votaciones) {
            titulos.add(votacionSiguiente.getTitulo());
        }
        mostrarListaDeCampos(titulos);
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
