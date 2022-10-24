package app;

import modelos.enums.Estado;
import modelos.Votacion;

import java.time.LocalDateTime;

/*
    Clase con métodos dedicados a refrescar
    contínuamente los estados de las votaciones,
    requiere ser ejecutado en un hilo aparte
 */

public class RefrescadorVotaciones {

//    public static void main(String[] args) {
//        try {
//            refrescarEstadosDeVotaciones();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void refrescarEstadosDeVotaciones() throws InterruptedException {
//        while (true) {
//            List<Votacion> votaciones = AccesoADatos.obtenerVotaciones();
//            for (Votacion votacionSiguiente : votaciones) {
//                asignarEstadoAVotacion(obtenerFechaTiempoAhora(), votacionSiguiente);
//            }
//            escribirVotaciones(votaciones);
//            System.out.println("Estados actualizados: " + obtenerFechaTiempoAhora());
//            Thread.sleep(1000);
//        }
//    }

    public static void asignarEstadoAVotacion(LocalDateTime fechaTiempoAhora, Votacion votacion) {
        Estado estado = votacion.getEstado();
        if (estado.equals(Estado.BORRADOR)) return;
        estado = determinarEstado(fechaTiempoAhora, votacion);
        votacion.setEstado(estado);
    }

    private static Estado determinarEstado(LocalDateTime fechaTiempoAhora, Votacion votacion) {
        var fechaTiempoInicio = votacion.getFechaTiempoInicio();
        var fechaTiempoTermino = votacion.getFechaTiempoTermino();

        return fechaTiempoAhora.isAfter(fechaTiempoTermino) ? Estado.FINALIZADO :
                fechaTiempoAhora.isBefore(fechaTiempoInicio) ? Estado.PENDIENTE : Estado.EN_CURSO;
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