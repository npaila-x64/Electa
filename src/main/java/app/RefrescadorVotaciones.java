package app;

import modelos.enums.Estado;
import modelos.Votacion;
import utils.AccesoADatos;

import java.time.LocalDateTime;
import java.util.List;

/*
    Clase con métodos dedicados a refrescar
    contínuamente los estados de las votaciones,
    requiere ser ejecutado en un hilo aparte
 */

public class RefrescadorVotaciones {

    public static void main(String[] args) {
        try {
            refrescarEstadosDeVotaciones();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void refrescarEstadosDeVotaciones() throws InterruptedException {
        while (true) {
            List<Votacion> votaciones = AccesoADatos.obtenerVotaciones();
            for (Votacion votacionSiguiente : votaciones) {
                asignarEstadoAVotacion(obtenerFechaTiempoAhora(), votacionSiguiente);
            }
            AccesoADatos.escribirVotaciones(votaciones);
            System.out.println("Estados actualizados: " + obtenerFechaTiempoAhora());
            Thread.sleep(1000);
        }
    }

    public static void asignarEstadoAVotacion(LocalDateTime fechaTiempoAhora, Votacion votacion) {
        // TODO Arreglar este desorden
        Estado estado = votacion.getEstado();
        if (estado.equals(Estado.BORRADOR)) return;
        var fechaTiempoInicio = votacion.getFechaTiempoInicio();
        var fechaTiempoTermino = votacion.getFechaTiempoTermino();
        if (fechaTiempoAhora.isAfter(fechaTiempoTermino)) {
            estado = Estado.FINALIZADO;
        } else if (fechaTiempoAhora.isBefore(fechaTiempoInicio)) {
            estado = Estado.PENDIENTE;
        } else {
            estado = Estado.EN_CURSO;
        }
        votacion.setEstado(estado);
    }

    public static LocalDateTime obtenerFechaTiempoAhora() {
        return LocalDateTime.now();
    }

    public static boolean esMayor(int[] fechaActual, int[] fechaTermino, int indice) {
        if (fechaTermino[indice] < fechaActual[indice]) return true;
        if (fechaTermino[indice] == fechaActual[indice]) {
            if (fechaActual.length - 1 == indice) return true;
            return esMayor(fechaActual, fechaTermino, indice + 1);
        }
        return false;
    }
}