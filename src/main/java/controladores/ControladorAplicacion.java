package controladores;

import controladores.admin.ControladorAdministracion;
import controladores.admin.ControladorEditorDeVotacion;
import controladores.votante.ControladorOpciones;
import controladores.votante.ControladorVotacionesEnCurso;
import modelos.Votacion;
import modelos.Usuario;
import vistas.Marco;

import javax.swing.*;
import java.awt.*;
/**
 * Crea el controlador con el cual se administra la lógica del resto de controladores.
 */
public class ControladorAplicacion {
    /**
     * Indica la ventana principal.
     */
    private Marco vista;
    /**
     * Indica el usuario de sesión.
     */
    private Usuario usuario;
    /**
     * Indica el controlador de la vista del login.
     */
    private ControladorLogin login;
    /**
     * Indica el controlador de la vista de votaciones en curso.
     */
    private ControladorVotacionesEnCurso votacionesEnCurso;
    /**
     * Indica el controlador de las vistas para escoger resultados y ver resultados.
     */
    private ControladorResultados resultados;
    /**
     * Indica el controlador de la vista de opciones.
     */
    private ControladorOpciones votacion;
    /**
     * Indica el controlador de la vista del menú administración.
     */
    private ControladorAdministracion administracion;
    /**
     * Indica el controlador de la vista del editor de votación.
     */
    private ControladorEditorDeVotacion editor;

    /**
     * Instancia una nueva ventana y los controladores. Visualiza la ventana y muestra el
     * panel del login.
     */
    public void iniciar() {
        vista = new Marco();
        crearControladores();
        vista.setVisible(true);
        login.abrir();
    }

    /**
     * Instancia cada uno de los controladores que utilizan las vistas del programa.
     */
    private void crearControladores() {
        login = new ControladorLogin(this);
        votacionesEnCurso = new ControladorVotacionesEnCurso(this);
        resultados = new ControladorResultados(this);
        votacion = new ControladorOpciones(this);
        administracion = new ControladorAdministracion(this);
        editor = new ControladorEditorDeVotacion(this);
    }

    /**
     * Muestra el panel de votaciones en curso.
     */
    public void abrirVotacionesEnCurso() {
        votacionesEnCurso.abrir();
    }
    /**
     * Muestra el panel del login.
     */
    public void abrirLogin() {
        login.abrir();
    }
    /**
     * Muestra el panel de escoger resultados.
     */
    public void abrirEscogerResultado() {
        resultados.abrirEscogerResultado();
    }

    /**
     * Muestra el panel los resultados.
     * @param fila un Integer que indica la votación escogida.
     */
    public void abrirResultados(Integer fila) {
        resultados.abrirResultados(fila);
    }

    /**
     * Muestra el panel del menú administración.
     */
    public void abrirAdministracion() {
        administracion.abrir();
    }

    /**
     * Muestra el panel de opciones.
     * @param votacion una votación escogida.
     */
    public void abrirOpcion(Votacion votacion) {
        this.votacion.abrir(votacion, votacion.getOpciones());
    }

    /**
     * Muestra el panel del editor de votaciónes.
     * @param votacion una votación escogida.
     * @param esNuevaVotacion un boolean que indica si es una nueva votación.
     * @param modoLectura un boolean que indica la votación se abre en modo letura.
     */
    public void abrirEditor(Votacion votacion, boolean esNuevaVotacion, boolean modoLectura) {
        editor.abrir(votacion, esNuevaVotacion, modoLectura);
    }

    /**
     * Asigna el usuario activo en la sesión.
     * @param usuario un usuario ingresado.
     */
    public void asignarUsuarioDeSesion(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el usuario de sesión.
     * @return un usuario activo en la sesión.
     */
    public Usuario obtenerUsuario() {
        return usuario;
    }

    /**
     * Agrega un panel en la ventana principal.
     * @param panel un panel que se busca agregar.
     * @param nombre un nombre para identificar el panel.
     */
    public void agregarPanel(Container panel, String nombre) {
        vista.agregarPanel(panel, nombre);
    }

    /**
     * Obtiene la ventana principal.
     * @return una ventana de tipo JFrame.
     */
    public JFrame getMarco() {
        return vista;
    }

    /**
     * Muestra un panel en la ventana principal.
     * @param panel un String cuyo valor identifica un panel.
     */
    public void mostrarPanel(String panel) {
        vista.mostrarPanel(panel);
    }

    /**
     * Asigna un panel predecesor en la vista de los resultados.
     * @param nombre un String cuyo valor identifica un panel.
     */
    public void asignarPanelPredecesorDeResultados(String nombre) {
        resultados.setPanelPredecesor(nombre);
    }
}
