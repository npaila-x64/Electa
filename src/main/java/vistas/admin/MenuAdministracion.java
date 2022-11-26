package vistas.admin;

import controladores.admin.ControladorAdministracion;
import utils.ValidadorDeDatos;

public class MenuAdministracion {

    private final ControladorAdministracion controlador;

    public MenuAdministracion(ControladorAdministracion controlador) {
        this.controlador = controlador;
    }

    public void mostrar() {
        System.out.println("\nBienvenido/a al menú de administración");
        while (true) {
            mostrarOpcionesMenuAdministador();
            switch (ValidadorDeDatos.pedirOpcionHasta(3)) {
                case 0 -> {return;}
                case 1 -> controlador.mostrarPanelDeControlDeVotaciones();
                case 2 -> controlador.mostrarMenuCreacionDeVotacion();
            }
        }
    }

    private void mostrarOpcionesMenuAdministador() {
        System.out.print(("\n" +
                          "Elija una opción\n" +
                          "[1] Ver, modificar, o eliminar votaciones\n" +
                          "[2] Crear nueva votación\n" +
                          "[3] Ver resultados de votaciones\n" +
                          "Si desea cerrar su sesión escriba [0]\n").concat("> "));
    }
}
