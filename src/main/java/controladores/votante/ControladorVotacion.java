package controladores.votante;

import modelos.Votacion;
import modelos.Votante;
import modelos.dao.VotacionDao;
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

    private void refrescarVotaciones() {
        this.votacionesEnElQuePuedeVotarElVotante = this.votacionDao
                .obtenerVotacionesEnElQuePuedeVotarElVotante(votante);
    }
}
