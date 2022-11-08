package vistas.admin;

import controladores.admin.ControladorEditorDeVotacion;
import modelos.Votacion;
import utils.Utilidades;
import utils.ValidadorDeDatos;

import java.util.LinkedHashMap;

public class MenuEditorDeVotacion {

    private final ControladorEditorDeVotacion controlador;

    public MenuEditorDeVotacion(ControladorEditorDeVotacion controlador) {
        this.controlador = controlador;
    }

    public void mostrar() {
        while (true) {
            mostrarOpcionesEditorDeVotacion();
            switch (ValidadorDeDatos.pedirOpcionHasta(4)) {
                case 0 -> {return;}
                case 1 -> controlador.mostrarMenuEditarCamposDeVotacion();
                case 2 -> controlador.mostrarMenuCreacionDeOpcion();
                case 3 -> controlador.mostrarMenuEliminarOpcionesDeVotacion();
                case 4 -> {controlador.eliminarVotacion(); return;}
            }
        }
    }

    private void mostrarOpcionesEditorDeVotacion() {
        mostrarDatosDeVotacion();
        System.out.print(("\n" +
                          "[1] Modificar algún campo\n" +
                          "[2] Para agregar una opción\n" +
                          "[3] Para eliminar alguna opción\n" +
                          "[4] Para eliminar la votación\n" +
                          "Si desea volver escriba [0]\n").concat("> "));
    }

    private void mostrarDatosDeVotacion() {
        mostrarCamposDeVotacion();
        mostrarOpcionesDeVotacion();
    }

    private void mostrarCamposDeVotacion() {
        Votacion votacion = controlador.obtenerVotacion();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("Titulo ..................... %s", votacion.getTitulo());
        map.put("Descripcion ................ %s", votacion.getDescripcion());
        map.put("Fecha de inicio ............ %s", votacion.getFechaInicio());
        map.put("Hora de inicio ............. %s hrs", votacion.getTiempoInicio());
        map.put("Fecha de término ........... %s", votacion.getFechaTermino());
        map.put("Hora de término ............ %s hrs", votacion.getTiempoTermino());
        map.put("Estado ..................... %s", votacion.getEstadoDeVotacion().getTexto());
        for (var key : map.keySet()) {
            System.out.printf(key.concat("%n"), map.get(key));
        }
    }

    private void mostrarOpcionesDeVotacion() {
        var opciones = controlador.obtenerVotacion().getOpciones();
        System.out.println("\nOpciones");
        for (var opcion : opciones) {
            System.out.println(Utilidades.padTexto("", 8).concat(opcion.getNombre()));
        }
    }
}
