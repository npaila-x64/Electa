package controladores.votante;

import modelos.Opcion;
import modelos.Votacion;
import modelos.Votante;
import modelos.dao.VotacionDao;
import utils.ValidadorDeDatos;
import vistas.votante.MenuVotacion;

import java.util.List;

public class ControladorVotacion {

    private final MenuVotacion vista;
    private final VotacionDao votacionDao;
    private List<Votacion> votacionesEnElQuePuedeVotarElVotante;
    private final Votante votante;

    public ControladorVotacion(Votante votante) {
        this.vista = new MenuVotacion(this);
        this.votacionDao = new VotacionDao();
        this.votante = votante;
        refrescarVotaciones();
    }

    public void iniciar() {
        this.vista.mostrar();
    }

    public List<Votacion> obtenerVotaciones() {
        refrescarVotaciones();
        return this.votacionesEnElQuePuedeVotarElVotante;
    }

    public Votante obtenerVotante() {
        return this.votante;
    }

    public void mostrarMenuOpcionesParaVotar(Integer opcionElegida) {
        Votacion votacion = votacionesEnElQuePuedeVotarElVotante.get(opcionElegida - 1);
        List<Opcion> opciones = votacion.getOpciones();
        mostrarOpcionesMenuOpcionesParaVotar(opciones);
        int nuevaOpcion = ValidadorDeDatos.pedirOpcionHasta(opciones.size());
        if (nuevaOpcion == 0) return;
        if (nuevaOpcion == 1) {
            votacionDao.registrarVotoBlanco(votacion, votante);
        } else {
            votacionDao.registrarVotoPreferencial(votacion, votante, opciones.get(nuevaOpcion - 1));
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

    public void mostrarListaOpciones(List<Opcion> opciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < opciones.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, opciones.get(indice).getNombre());
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    private void refrescarVotaciones() {
        this.votacionesEnElQuePuedeVotarElVotante = this.votacionDao
                .obtenerVotacionesEnElQuePuedeVotarElVotante(votante);
    }
}
