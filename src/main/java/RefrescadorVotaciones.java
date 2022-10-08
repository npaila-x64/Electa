import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;

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
            JSONArray jsonArrayVotaciones = AccesoADatos.parsearVotaciones();
            for (Object jsonArrayVotacion : jsonArrayVotaciones) {
                JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
                asignarEstadoAVotacion(obtenerFechaTiempoAhora(), votacionSiguiente);
            }
            AccesoADatos.escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
            System.out.println("Estados actualizados: " + obtenerFechaTiempoAhora());
            Thread.sleep(1000);
        }
    }

    public static void asignarEstadoAVotacion(LocalDateTime fechaTiempoAhora, JSONObject votacion) {
        String estado = votacion.get(CampoDeVotacion.ESTADO.getTexto()).toString();
        if (estado.equals(Estado.BORRADOR.getTexto())) return;
        var fechaTiempoInicio = AccesoADatos.parsearFechaTiempoInicio(votacion);
        var fechaTiempoTermino = AccesoADatos.parsearFechaTiempoTermino(votacion);
        if (fechaTiempoAhora.isAfter(fechaTiempoTermino)) {
            estado = Estado.FINALIZADO.getTexto();
        } else if (fechaTiempoAhora.isBefore(fechaTiempoInicio)) {
            estado = Estado.PENDIENTE.getTexto();
        } else {
            estado = Estado.EN_CURSO.getTexto();
        }
        votacion.put(CampoDeVotacion.ESTADO.getTexto(), estado);
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