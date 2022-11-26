package controladores;

import controladores.votante.ControladorResultados;
import controladores.votante.ControladorVotacionesEnCurso;
import modelos.Votante;
import vistas.Marco;
import vistas.PanelLogin;
import vistas.votante.PanelResultados;
import vistas.votante.PanelVotacionesEnCurso;

public class ControladorAplicacion {

    private Marco vista;
    private Votante usuario;
    private ControladorLogin login;
    private ControladorVotacionesEnCurso votacionesEnCurso;
    private ControladorResultados resultados;

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
        resultados = new ControladorResultados(this);
    }

    public void mostrarLogin() {
        vista.mostrarLogin();
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

    public void abrirResultados() {
        resultados.abrir();
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

    public void agregarResultados(PanelResultados resultados) {
        vista.agregarResultados(resultados);
    }

    public void mostrarResultados() {
        vista.mostrarResultados();
    }
}
