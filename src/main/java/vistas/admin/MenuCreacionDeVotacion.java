package vistas.admin;

import controladores.admin.ControladorCreacionDeOpcion;
import controladores.admin.ControladorCreacionDeVotacion;

import static utils.ValidadorDeDatos.*;

public class MenuCreacionDeVotacion {

    private final ControladorCreacionDeVotacion controlador;
    private String campoTitulo;
    private String campoDescripcion;
    private String campoFechaInicio;
    private String campoHoraInicio;
    private String campoFechaTermino;
    private String campoHoraTermino;

    public MenuCreacionDeVotacion(ControladorCreacionDeVotacion controlador) {
        this.controlador = controlador;
    }

    public void mostrarVotacionCreadaConExito() {
        System.out.println("¡Votación creada con exito!\n");
    }

    public void ingresarDatosCampo(){
        this.campoTitulo = pedirEntrada(
                "Escriba el título de la votación que desea agregar\n> ",
                50);
        System.out.println("Rellene los siguientes campos");
        this.campoDescripcion = pedirEntrada("Descripción\n> ");
        this.campoFechaInicio = pedirEntradaFormatoFecha("Fecha de inicio (dd-MM-aaaa)\n> ");
        this.campoHoraInicio = pedirEntradaFormatoHora("Hora de inicio (hh:mm formato 24 horas)\n> ");
        this.campoFechaTermino = pedirEntradaFormatoFecha("Fecha de término (dd-MM-aaaa)\n> ");
        this.campoHoraTermino = pedirEntradaFormatoHora("Hora de término (hh:mm formato 24 horas)\n> ");
    }

    public String getCampoTitulo() {
        return campoTitulo;
    }

    public String getCampoDescripcion() {
        return campoDescripcion;
    }

    public String getCampoFechaInicio() {
        return campoFechaInicio;
    }

    public String getCampoHoraInicio() {
        return campoHoraInicio;
    }

    public String getCampoFechaTermino() {
        return campoFechaTermino;
    }

    public String getCampoHoraTermino() {
        return campoHoraTermino;
    }
}
