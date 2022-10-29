package vistas.admin;

import controladores.admin.ControladorCreacionDeOpcion;
import modelos.Votacion;
import utils.Utilidades;
import utils.ValidadorDeDatos;

import java.util.LinkedHashMap;

public class MenuCreacionDeOpcion {

    private final ControladorCreacionDeOpcion controlador;

    public MenuCreacionDeOpcion(ControladorCreacionDeOpcion controlador) {
        this.controlador = controlador;
    }

    public void mostrar() {
        while (true) {
            mostrarDatosDeVotacion();
            mostrarMenuMenuAgregacionDeOpciones();
            switch (ValidadorDeDatos.pedirOpcionHasta(1)) {
                case 0 -> {
                    return;
                }
                case 1 -> controlador.agregarOpcionDeVotacion();
            }
        }
    }

    private void mostrarMenuMenuAgregacionDeOpciones() {
        System.out.print(("\n" +
                          "Para agregar una opción escriba [1]\n" +
                          "Para finalizar y volver escriba [0]\n" +
                          "Elija una opción\n").concat("> "));
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

    public void mostrarOpcionYaExiste(String opcion) {
        System.out.println("La opcion " + opcion + " ya existe, por favor ingrese otra");
    }
}
