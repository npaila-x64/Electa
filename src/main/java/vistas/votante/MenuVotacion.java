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
            mostrarMenuOpcionesParaVotar(
                    votaciones.get(opcionElegida - 1), votante);
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

    private void mostrarMenuOpcionesParaVotar(Votacion votacion, Votante votante) {
        List<Opcion> opciones = votacion.getOpciones();
        mostrarOpcionesMenuOpcionesParaVotar(opciones);
        int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(opciones.size());
        if (opcionElegida == 0) return;
        if (opcionElegida == 1) {
            AccesoADatos.registrarVotoBlanco(votacion, votante);
        } else {
            AccesoADatos.registrarVotoPreferencial(votacion, votante, opciones.get(opcionElegida - 1));
        }
        mostrarVotoRealizadoConExito();
    }

    private void mostrarOpcionesMenuOpcionesParaVotar(List<Opcion> opciones) {
        System.out.println("Opciones disponibles");
        mostrarListaOpciones(opciones);
    }

    private void mostrarVotoRealizadoConExito() {
        System.out.println("¡Voto realizado con exito!\n");
    }

    private void mostrarListaDeCampos(List<String> campos) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < campos.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, campos.get(indice));
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    public void mostrarListaOpciones(List<Opcion> opciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < opciones.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, opciones.get(indice).getNombre());
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }
}
