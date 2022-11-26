package controladores;

import controladores.votante.ControladorVotacionesEnCurso;
import modelos.Votante;
import vistas.Marco;
import vistas.admin.PanelLogin;
import vistas.votante.PanelVotacionesEnCurso;

public class ControladorAplicacion {

    private Marco vista;
    private Votante usuario;
    private ControladorLogin login;
    private ControladorVotacionesEnCurso votacionesEnCurso;

    public void iniciar() {
        vista = new Marco();
        crearControladores();
        login.abrir();
        vista.mostrarLogin();
        vista.setVisible(true);
    }

    private void crearControladores() {
        login = new ControladorLogin(this);
        votacionesEnCurso = new ControladorVotacionesEnCurso(this);
    }

    public void mostrarLogin() {
        vista.mostrarLogin();
    }

    public void cerrar() {
        vista.dispose();
    }

    public void agregarLogin(PanelLogin login) {
        vista.agregarLogin(login);
    }

    public void mostrarVotacionesEnCurso() {
        vista.mostrarVotacionesEnCurso();
    }

    public void abrirVotacionesEnCurso() {
        votacionesEnCurso.abrir();
    }

    public void abrirLogin() {
        login.abrir();
    }

    public void agregarVotacionesEnCurso(PanelVotacionesEnCurso votacionesEnCurso) {
        vista.agregarVotacionesEnCurso(votacionesEnCurso);
    }

    public void asignarUsuarioDeSesion(Votante usuario) {
        this.usuario = usuario;
    }

    public Votante obtenerUsuario() {
        return usuario;
    }
}
