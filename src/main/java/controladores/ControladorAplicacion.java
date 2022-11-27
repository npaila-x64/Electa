package controladores;

import controladores.admin.ControladorAdministracion;
import controladores.votante.ControladorVotacion;
import controladores.votante.ControladorVotacionesEnCurso;
import modelos.Votacion;
import modelos.Votante;
import vistas.Marco;
import vistas.PanelLogin;
import vistas.admin.PanelAdministracion;
import vistas.votante.PanelOpciones;
import vistas.votante.PanelResultados;
import vistas.votante.PanelVotacionesEnCurso;

import javax.swing.*;

public class ControladorAplicacion {

    private Marco vista;
    private Votante usuario;
    private ControladorLogin login;
    private ControladorVotacionesEnCurso votacionesEnCurso;
    private ControladorResultados resultados;
    private ControladorVotacion votacion;
    private ControladorAdministracion administracion;

    public void iniciar() {
        vista = new Marco();
        crearControladores();
        vista.setVisible(true);
        login.abrir();
    }

    private void crearControladores() {
        login = new ControladorLogin(this);
        votacionesEnCurso = new ControladorVotacionesEnCurso(this);
        resultados = new ControladorResultados(this);
        votacion = new ControladorVotacion(this);
        administracion = new ControladorAdministracion(this);
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

    public void abrirAdministracion() {
        administracion.abrir();
    }

    public void abrirOpcion(Votacion votacion) {
        this.votacion.abrir(votacion, votacion.getOpciones());
    }

    public void asignarUsuarioDeSesion(Votante usuario) {
        this.usuario = usuario;
    }

    public Votante obtenerUsuario() {
        return usuario;
    }

    public void agregarPanel(JPanel panel, String nombre) {
        vista.agregarPanel(panel, nombre);
    }

    public JFrame getMarco() {
        return vista;
    }

    public void mostrarPanel(String panel) {
        vista.mostrarPanel(panel);
    }
}
