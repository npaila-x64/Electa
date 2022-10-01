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

    public static LocalDateTime obtenerFechaTiempoAhora() {
        return LocalDateTime.now();
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
        String estado = votacion.get("estado").toString();
        if (estado.equals("BORRADOR")) return;
        var fechaTiempoInicio = obtenerFechaTiempo(
                votacion.get("fecha_inicio"),
                votacion.get("hora_inicio"));
        var fechaTiempoTermino = obtenerFechaTiempo(
                votacion.get("fecha_termino"),
                votacion.get("hora_termino"));
        if (fechaTiempoAhora.isAfter(fechaTiempoTermino)) {
            estado = "FINALIZADO";
        } else if (fechaTiempoAhora.isBefore(fechaTiempoInicio)) {
            estado = "PENDIENTE";
        } else {
            estado = "EN CURSO";
        }
        votacion.put("estado", estado);
    }

    public static LocalDateTime obtenerFechaTiempo(Object fecha, Object tiempo) {
        var fechaArr = fecha.toString().split("-");
        var dia = Integer.parseInt(fechaArr[0]);
        var mes = Integer.parseInt(fechaArr[1]);
        var anio = Integer.parseInt(fechaArr[2]);
        var tiempoArr = tiempo.toString().split(":");
        var hora = Integer.parseInt(tiempoArr[0]);
        var minutos = Integer.parseInt(tiempoArr[1]);
        return LocalDateTime.of(anio, mes, dia, hora, minutos);
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