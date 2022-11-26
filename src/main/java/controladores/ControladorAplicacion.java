package controladores;

import vistas.Marco;
import vistas.admin.PanelLogin;

public class ControladorAplicacion {

    private Marco vista;
    private ControladorLogin login;

    public void iniciar() {
        vista = new Marco();
        crearControladores();
        login.iniciar();
        vista.setVisible(true);
    }

    private void crearControladores() {
        login = new ControladorLogin(this);
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
        vista.mostrarLogin();
    }

//    public void agregarVotacionesEnCurso(PanelVotacionesEnCurso votacionesEnCurso) {
//        vista.agregarVotacionesEnCurso(votacionesEnCurso);
//    }
}
