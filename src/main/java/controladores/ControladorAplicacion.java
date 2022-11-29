package controladores;

import controladores.admin.ControladorAdministracion;
import controladores.admin.ControladorEditorDeVotacion;
import controladores.votante.ControladorVotacion;
import controladores.votante.ControladorVotacionesEnCurso;
import modelos.Votacion;
import modelos.Usuario;
import vistas.Marco;

import javax.swing.*;
import java.awt.*;

public class ControladorAplicacion {

    private Marco vista;
    private Usuario usuario;
    private ControladorLogin login;
    private ControladorVotacionesEnCurso votacionesEnCurso;
    private ControladorResultados resultados;
    private ControladorVotacion votacion;
    private ControladorAdministracion administracion;
    private ControladorEditorDeVotacion editor;

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
        editor = new ControladorEditorDeVotacion(this);
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

    public void abrirEditor(Votacion votacion) {
        editor.abrir(votacion);
    }

    public void asignarUsuarioDeSesion(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario obtenerUsuario() {
        return usuario;
    }

    public void agregarPanel(Container panel, String nombre) {
        vista.agregarPanel(panel, nombre);
    }

    public JFrame getMarco() {
        return vista;
    }

    public void mostrarPanel(String panel) {
        vista.mostrarPanel(panel);
    }

    public void asignarPanelPredecesorDeResultados(String nombre) {
        resultados.setPanelPredecesor(nombre);
    }
}
