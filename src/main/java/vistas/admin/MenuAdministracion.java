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
                case 3 -> controlador.mostrarMenuResultados();
            }
        }
    }

    private void mostrarOpcionesMenuAdministador() {
        System.out.print("""
                
                Elija una opción
                [1] Ver, modificar, o eliminar votaciones
                [2] Crear nueva votación
                [3] Ver resultados de votaciones
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
    }
}
