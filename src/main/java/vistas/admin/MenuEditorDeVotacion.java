package vistas.admin;

import controladores.admin.ControladorEditorDeVotacion;
import modelos.Opcion;
import modelos.Votacion;
import modelos.enums.CampoDeVotacion;
import utils.Utilidades;
import utils.ValidadorDeDatos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
                case 1 -> mostrarMenuEditarCamposDeVotacion();
                case 2 -> controlador.mostrarMenuCreacionDeOpcion();
                case 3 -> mostrarMenuEliminarOpcionesDeVotacion();
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

    public void mostrarMenuEditarCamposDeVotacion() {
        List<CampoDeVotacion> campos =
                new ArrayList<>(CampoDeVotacion.getCamposDeVotacionEditablesPorAdministrador());
        while (true) {
            mostrarOpcionesMenuEditarCamposDeVotacion(campos);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(campos.size());
            if (opcionElegida == 0) break;
            controlador.editarCampoDeVotacion(campos.get(opcionElegida - 1));
        }
    }

    private void mostrarOpcionesMenuEditarCamposDeVotacion(List<CampoDeVotacion> campos) {
        System.out.print("\n" +
                "Escriba el índice del campo que desea modificar\n");
        List<String> camposTexto = new ArrayList<>();
        for (CampoDeVotacion c : campos) camposTexto.add(c.getTexto());
        mostrarListaDeCampos(camposTexto);
    }

    private void mostrarListaDeCampos(List<String> campos) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < campos.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, campos.get(indice));
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    public void mostrarMenuEliminarOpcionesDeVotacion() {
        List<Opcion> opciones = controlador.obtenerOpcionesDeVotacion();
        System.out.println("Escriba la opción que desea eliminar");
        mostrarListaOpciones(opciones);
        int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(opciones.size());
        if (opcionElegida == 0) return;
        controlador.eliminarOpcionDeVotacion(opcionElegida);
        System.out.println("Opción eliminada con exito");
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
