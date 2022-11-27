package controladores;

import controladores.votante.ControladorResultados;
import controladores.votante.ControladorVotacion;
import controladores.votante.ControladorVotacionesEnCurso;
import modelos.Votacion;
import modelos.Usuario;
import vistas.Marco;
import vistas.PanelLogin;
import vistas.votante.PanelOpciones;
import vistas.votante.PanelResultados;
import vistas.votante.PanelVotacionesEnCurso;

import javax.swing.*;

public class ControladorAplicacion {

    private Marco vista;
    private Usuario usuario;
    private ControladorLogin login;
    private ControladorVotacionesEnCurso votacionesEnCurso;
    private ControladorResultados resultados;
    private ControladorVotacion votacion;

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
        votacion = new ControladorVotacion(this);
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

    public void asignarUsuarioDeSesion(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario obtenerUsuario() {
        return usuario;
    }

    public void agregarResultados(PanelResultados resultados) {
        vista.agregarResultados(resultados);
    }

    public void mostrarResultados() {
        vista.mostrarResultados();
    }

    public void agregarOpciones(PanelOpciones opciones) {
        vista.agregarOpciones(opciones);
    }

    public void abrirOpcion(Votacion votacion) {
        this.votacion.abrir(votacion, votacion.getOpciones());
    }

    public void mostrarOpciones() {
        vista.mostrarOpciones();
    }

    public JFrame getMarco() {
        return vista;
    }
}
