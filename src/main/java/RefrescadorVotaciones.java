import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
    Clase con métodos dedicados a refrescar
    contínuamente los estados de las votaciones,
    requiere ser ejecutado en un hilo aparte
 */

public class RefrescadorVotaciones {

    public String queFechaEsHoy() {
        return "19-09-2022";
    }

    public String queHoraEs() {
        return "21:00";
    }

    public void refrescarEstadosDeVotaciones() {
        var fechaActual = queFechaEsHoy();
        var horaActual = queHoraEs();
        var diaActual = Integer.parseInt(fechaActual.split("-")[0]);
        var mesActual = Integer.parseInt(fechaActual.split("-")[1]);
        var aniosActual = Integer.parseInt(fechaActual.split("-")[2]);
        var horasActual = Integer.parseInt(horaActual.split(":")[0]);
        var minutosActual = Integer.parseInt(horaActual.split(":")[1]);
        JSONArray jsonArrayVotaciones = AccesoADatos.parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            String fechaInicio = String.valueOf(votacionSiguiente.get("fecha_inicio"));
            String horaInicio = String.valueOf(votacionSiguiente.get("hora_inicio"));
            String fechaTermino = String.valueOf(votacionSiguiente.get("fecha_termino"));
            String horaTermino = String.valueOf(votacionSiguiente.get("hora_termino"));
            var diaInicio = Integer.parseInt(fechaInicio.split("-")[0]);
            var mesInicio = Integer.parseInt(fechaInicio.split("-")[1]);
            var anioInicio = Integer.parseInt(fechaInicio.split("-")[2]);
            var horasInicio = Integer.parseInt(horaInicio.split(":")[0]);
            var minutosInicio = Integer.parseInt(horaInicio.split(":")[1]);
            var diaTermino = Integer.parseInt(fechaTermino.split("-")[0]);
            var mesTermino = Integer.parseInt(fechaTermino.split("-")[1]);
            var anioTermino = Integer.parseInt(fechaTermino.split("-")[2]);
            var horasTermino = Integer.parseInt(horaTermino.split(":")[0]);
            var minutosTermino = Integer.parseInt(horaTermino.split(":")[1]);
            boolean esFechaYHoraDentroDeRango = true;
            continua: {
                if (anioInicio <= aniosActual && anioTermino >= aniosActual) {
                    if (mesInicio <= mesActual && mesTermino >= mesActual) {
                        if (diaInicio <= diaActual && diaTermino >= diaActual) {
                            if (horasInicio <= horasActual && horasTermino >= horasActual) {
                                if (minutosInicio <= minutosActual && minutosTermino >= minutosActual) {
                                    break continua;
                                }
                            }
                        }
                    }
                }
                esFechaYHoraDentroDeRango = false;
            }
            if (esFechaYHoraDentroDeRango) {

            }

            boolean esFechaYHoraMenorQueRango = true;
            continua: {
                if (anioInicio >= aniosActual) {
                    if (mesInicio >= mesActual) {
                        if (diaInicio >= diaActual) {
                            if (horasInicio >= horasActual) {
                                if (minutosInicio >= minutosActual) {
                                    break continua;
                                }
                            }
                        }
                    }
                }
                esFechaYHoraMenorQueRango = false;
            }
            if (esFechaYHoraMenorQueRango) {

            }

            boolean esFechaYHoraMayorQueRango = true;
            continua: {
                if (anioTermino <= aniosActual) {
                    if (mesTermino <= mesActual) {
                        if (diaTermino <= diaActual) {
                            if (horasTermino <= horasActual) {
                                if (minutosTermino <= minutosActual) {
                                    break continua;
                                }
                            }
                        }
                    }
                }
                esFechaYHoraMayorQueRango = false;
            }
            if (esFechaYHoraMayorQueRango) {

            }
        }
        throw new RuntimeException();
    }

    public boolean esFechaDentroDeRango() {
        return false;
    }

    public boolean esFechaMayorQueRango() {
        return false;
    }

    public boolean esFechaMenorQueRango() {
        return false;
    }
}
