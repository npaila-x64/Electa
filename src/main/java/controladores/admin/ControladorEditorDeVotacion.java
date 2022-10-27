package controladores.admin;

import modelos.Opcion;
import modelos.Votacion;
import modelos.dao.VotacionDao;
import modelos.enums.CampoDeVotacion;
import utils.ValidadorDeDatos;
import vistas.admin.MenuEditorDeVotacion;

import java.util.ArrayList;
import java.util.List;

public class ControladorEditorDeVotacion {

    // TODO Terminar de descoplar a clase vista
    private final MenuEditorDeVotacion vista;
    private final Integer idVotacion;

    public ControladorEditorDeVotacion(Integer idVotacion) {
        this.vista = new MenuEditorDeVotacion(this);
        this.idVotacion = idVotacion;
    }

    public void iniciar() {
        this.vista.mostrar();
    }

    public Votacion obtenerVotacion() {
        return VotacionDao.obtenerVotacionPorID(VotacionDao.obtenerVotaciones(), idVotacion);
    }

    public void mostrarMenuCreacionDeOpcion() {
        new ControladorCreacionDeOpcion(idVotacion).iniciar();
    }

    public void mostrarMenuEditarCamposDeVotacion() {
        List<CampoDeVotacion> campos =
                new ArrayList<>(CampoDeVotacion.getCamposDeVotacionEditablesPorAdministrador());
        while (true) {
            mostrarOpcionesMenuEditarCamposDeVotacion(campos);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(campos.size());
            if (opcionElegida == 0) break;
            editarCampoDeVotacion(obtenerVotacion(), campos.get(opcionElegida - 1));
        }
    }

    private void editarCampoDeVotacion(Votacion votacion, CampoDeVotacion campo) {
        Object texto = ValidadorDeDatos.pedirEntrada(campo.getTexto().concat("> "));
        VotacionDao.actualizarCampoDeVotacion(votacion, campo, texto);
    }

    private void mostrarOpcionesMenuEditarCamposDeVotacion(List<CampoDeVotacion> campos) {
        System.out.print("\n" +
                         "Escriba el índice del campo que desea modificar\n");
        List<String> camposTexto = new ArrayList<>();
        for (CampoDeVotacion c : campos) camposTexto.add(c.getTexto());
        mostrarListaDeCampos(camposTexto);
    }

    public void mostrarMenuEliminarOpcionesDeVotacion() {
        List<Opcion> opciones = obtenerVotacion().getOpciones();
        System.out.println("Escriba la opción que desea eliminar");
        mostrarListaOpciones(opciones);
        int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(opciones.size());
        if (opcionElegida == 0) return;
        VotacionDao.eliminarOpcionDeVotacion(obtenerVotacion(), opciones.get(opcionElegida - 1));
        System.out.println("Opción eliminada con exito");
    }

    public void eliminarVotacion() {
        VotacionDao.eliminarVotacion(obtenerVotacion());
    }

    public void mostrarListaOpciones(List<Opcion> opciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < opciones.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, opciones.get(indice).getNombre());
        }
        System.out.print("Si desea volver escriba [0]\n> ");
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
