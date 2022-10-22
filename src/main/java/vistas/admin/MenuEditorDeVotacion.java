package vistas.admin;

import controladores.admin.ControladorCreacionDeOpcion;
import controladores.admin.ControladorEditorDeVotacion;
import modelos.Opcion;
import modelos.Votacion;
import modelos.enums.CampoDeVotacion;
import utils.AccesoADatos;
import utils.Utilidades;
import utils.ValidadorDeDatos;

import java.util.LinkedHashMap;

public class MenuEditorDeVotacion {

    private final ControladorEditorDeVotacion controlador;

    public MenuEditorDeVotacion(ControladorEditorDeVotacion controlador) {
        this.controlador = controlador;
    }

    public void mostrar() {
        //      FIXME A futuro se podría usar el siguiente formato para las opciones / menús
//        LinkedHashMap<String, Runnable> opciones = new LinkedHashMap<>();
//        opciones.put("Modificar algún campo",       () -> mostrarMenuEditarCamposDeVotacion(IDVotacion));
//        opciones.put("Para agregar una opción",     () -> agregarOpcionDeVotacion(IDVotacion));
//        opciones.put("Para eliminar alguna opción", () -> mostrarMenuEliminarOpcionesDeVotacion(IDVotacion));
//        opciones.put("Para eliminar la votación",   () -> utils.AccesoADatos.eliminarVotacion(IDVotacion));
//        LinkedHashSet<String> set = new LinkedHashSet(opciones.values());
//        int opcion = utils.ValidadorDeDatos.pedirOpcionHasta(set.size());
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
        System.out.print("""
                
                [1] Modificar algún campo
                [2] Para agregar una opción
                [3] Para eliminar alguna opción
                [4] Para eliminar la votación
                Si desea volver escriba [0]
                """.concat("> "));
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
        map.put("Estado ..................... %s", votacion.getEstado().getTexto());
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
