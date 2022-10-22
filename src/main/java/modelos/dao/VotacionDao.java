package modelos.dao;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.Opcion;
import modelos.Votacion;
import modelos.Votante;
import modelos.Voto;
import modelos.enums.CampoDeOpcion;
import modelos.enums.CampoDeVotacion;
import modelos.enums.Estado;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.AccesoADatos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static utils.AccesoADatos.obtenerVotos;

public class VotacionDao {

    private static final String RUTA_VOTACIONES = "src/main/datos/votaciones.json";

    private JSONArray parsearVotaciones() throws AccesoADatosInterrumpidoException {
        return AccesoADatos.parsearArchivoJSON(RUTA_VOTACIONES);
    }

    public List<Votacion> obtenerVotaciones() {
        List<Votacion> votaciones = new ArrayList<>();
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            Votacion votacion = new Votacion();
            obtenerAtributosDeVotacionJSON(votacion, votacionSiguiente);
            votaciones.add(votacion);
        }
        return votaciones;
    }


    private void obtenerAtributosDeVotacionJSON(Votacion votacion, JSONObject votacionJSON) {
        votacion.setId(votacionJSON.get(CampoDeVotacion.ID.getTexto()));
        votacion.setTitulo(votacionJSON.get(CampoDeVotacion.TITULO.getTexto()));
        votacion.setDescripcion(votacionJSON.get(CampoDeVotacion.DESCRIPCION.getTexto()));
        votacion.setEstado(votacionJSON.get(CampoDeVotacion.ESTADO.getTexto()));
        votacion.setFechaTiempoInicio(parsearFechaTiempoInicio(votacionJSON));
        votacion.setFechaTiempoTermino(parsearFechaTiempoTermino(votacionJSON));
        votacion.setOpciones(obtenerOpcionesDeVotacionJSON(votacion, votacionJSON));
        votacion.setVotantes(obtenerVotantesDeVotacionJSON(votacion, votacionJSON));
        votacion.setVotos(obtenerVotosDeVotacion(votacion));
    }

    private List<Voto> obtenerVotosDeVotacion(Votacion votacion) {
        List<Voto> votos = obtenerVotos();
        List<Voto> votosDeVotacion = new ArrayList<>();
        for (Voto voto : votos) {
            if (voto.getVotacion().getId().equals(votacion.getId())) {
                votosDeVotacion.add(voto);
            }
        }
        return votosDeVotacion;
    }

    private LocalDateTime parsearFechaTiempoInicio(JSONObject votacion) {
        return parsearFechaTiempoVotacion(
                votacion.get(CampoDeVotacion.FECHA_INICIO.getTexto()),
                votacion.get(CampoDeVotacion.HORA_INICIO.getTexto()));
    }

    private LocalDateTime parsearFechaTiempoTermino(JSONObject votacion) {
        return parsearFechaTiempoVotacion(
                votacion.get(CampoDeVotacion.FECHA_TERMINO.getTexto()),
                votacion.get(CampoDeVotacion.HORA_TERMINO.getTexto()));
    }

    private LocalDateTime parsearFechaTiempoVotacion(Object fecha, Object tiempo) {
        // TODO Desarrollar manejo de excepciones y crear pruebas unitarias
        var fechaArr = fecha.toString().split("-");
        var dia = Integer.parseInt(fechaArr[0]);
        var mes = Integer.parseInt(fechaArr[1]);
        var anio = Integer.parseInt(fechaArr[2]);
        var tiempoArr = tiempo.toString().split(":");
        var hora = Integer.parseInt(tiempoArr[0]);
        var minutos = Integer.parseInt(tiempoArr[1]);
        return LocalDateTime.of(anio, mes, dia, hora, minutos);
    }

    public List<Opcion> obtenerOpcionesDeVotacionJSON(Votacion votacion, JSONObject votacionSiguiente) {
        var opciones = obtenerOpcionesDeVotacionJSON(votacionSiguiente);
        for (var opcion : opciones) opcion.setVotacion(votacion);
        return opciones;
    }

    public List<Votante> obtenerVotantesDeVotacionJSON(Votacion votacion, JSONObject votacionSiguiente) {
        List<Votante> votantes = obtenerVotantesDeVotacionJSON(votacionSiguiente);
        votacion.setVotantes(votantes);
        return votantes;
    }

    public List<Votante> obtenerVotantesDeVotacionJSON(JSONObject votacionSiguiente) {
        JSONArray votantesArray = (JSONArray) votacionSiguiente.get(CampoDeVotacion.VOTANTES.getTexto());
        List<Votante> votantes = new ArrayList<>();
        for (Object IDvotante : votantesArray) {
            Votante votante = new Votante();
            votante.setId(Integer.parseInt(IDvotante.toString()));
            votantes.add(votante);
        }
        return votantes;
    }

    public List<Votacion> ObtenerVotacionesEnCurso() {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> votacionesEnCurso = new ArrayList<>();
        votaciones
                .stream()
                .filter(votacionSiguiente -> votacionSiguiente.getEstado().equals(Estado.EN_CURSO))
                .forEach(votacionesEnCurso::add);
        return votacionesEnCurso;
    }

    public List<Votacion> obtenerVotacionesConEstado(Estado estado) {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> votacionesCopia = new ArrayList<>();
        for (Votacion votacionSiguiente : votaciones) {
            if (votacionSiguiente.getEstado().equals(estado)) {
                votacionesCopia.add(votacionSiguiente);
            }
        }
        return votacionesCopia;
    }

    public List<Votacion> obtenerVotacionesEnElQuePuedeVotarElVotante(Votante votante) {
        List<Votacion> votacionesEnCurso = ObtenerVotacionesEnCurso();
        List<Votacion> votacionesEnElQuePuedeVotarElVotante = new ArrayList<>();
        votacionesEnCurso
                .stream()
                .filter(votacionSiguiente -> !votanteVotoEnEstaVotacion(votacionSiguiente, votante))
                .forEach(votacionesEnElQuePuedeVotarElVotante::add);
        return votacionesEnElQuePuedeVotarElVotante;
    }

    private String obtenerNuevaIdVotacion() {
        List<Votacion> votaciones = obtenerVotaciones();
        var maxID = votaciones.stream().max(Comparator.comparing(Votacion::getId)).get().getId();
        maxID++;
        return String.valueOf(maxID);
    }

    public String obtenerNuevaIdOpcion(Votacion votacion) {
        Votacion votacionCopia = obtenerVotacionPorID(obtenerVotaciones(), votacion);
        List<Opcion> opciones = votacionCopia.getOpciones();
        var maxID = opciones.stream().max(Comparator.comparing(Opcion::getId)).get().getId();
        maxID++;
        return String.valueOf(maxID);
    }

    public boolean votanteVotoEnEstaVotacion(Votacion votacionSiguiente, Votante votante) {
        List<Votante> votantes = votacionSiguiente.getVotantes();
        for (Votante votanteQueVotoEnEstaVotacion : votantes) {
            if (votante.getId().equals(votanteQueVotoEnEstaVotacion.getId())) {
                // Si esta condición se cumple se da a entender que el votante ya
                // está en la lista de votantes quienes votaron en esta votación
                return true;
            }
        }
        return false;
    }

    public void escribirVotaciones(List<Votacion> votaciones) {
        AccesoADatos.escribirArchivoJSON(RUTA_VOTACIONES, convertirListaDeVotacionesAJSONArray(votaciones).toJSONString());
    }

    private JSONArray convertirListaDeVotacionesAJSONArray(List<Votacion> votaciones) {
        JSONArray array = new JSONArray();
        for (Votacion votacion : votaciones) {
            JSONObject votacionObj = new JSONObject();
            convertirJSONCampoInfoVotacion(votacion, votacionObj);
            convertirJSONCampoFechaYHora(votacion, votacionObj);
            convertirJSONCampoVotantes(votacion, votacionObj);
            convertirJSONCampoOpciones(votacion, votacionObj);
            convertirJSONCampoVotos(votacion, votacionObj);
            array.add(votacionObj);
        }
        return array;
    }

    private void convertirJSONCampoInfoVotacion(Votacion votacion, JSONObject votacionObj) {
        votacionObj.put(CampoDeVotacion.ID.getTexto(), votacion.getId());
        votacionObj.put(CampoDeVotacion.TITULO.getTexto(), votacion.getTitulo());
        votacionObj.put(CampoDeVotacion.DESCRIPCION.getTexto(), votacion.getDescripcion());
        votacionObj.put(CampoDeVotacion.ESTADO.getTexto(), votacion.getEstado().getTexto());
    }

    private void convertirJSONCampoFechaYHora(Votacion votacion, JSONObject votacionObj){
        votacionObj.put(CampoDeVotacion.FECHA_INICIO.getTexto(), votacion.getFechaInicio()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        votacionObj.put(CampoDeVotacion.HORA_INICIO.getTexto(), votacion.getTiempoInicio()
                .format(DateTimeFormatter.ofPattern("HH:mm")));
        votacionObj.put(CampoDeVotacion.FECHA_TERMINO.getTexto(), votacion.getFechaTermino()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        votacionObj.put(CampoDeVotacion.HORA_TERMINO.getTexto(), votacion.getTiempoTermino()
                .format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    private void convertirJSONCampoOpciones(Votacion votacion, JSONObject votacionObj) {
        JSONArray opcionesArray = new JSONArray();
        for (Opcion opcion : votacion.getOpciones()) {
            JSONObject opcionSiguiente = new JSONObject();
            opcionSiguiente.put(CampoDeOpcion.ID.getTexto(), opcion.getId());
            opcionSiguiente.put(CampoDeOpcion.NOMBRE.getTexto(), opcion.getNombre());
            opcionSiguiente.put(CampoDeOpcion.VOTOS.getTexto(), opcion.getCantidadDeVotos());
            opcionesArray.add(opcionSiguiente);
        }
        votacionObj.put(CampoDeVotacion.OPCIONES.getTexto(), opcionesArray);
    }

    private void convertirJSONCampoVotos(Votacion votacion, JSONObject votacionObj) {
        votacionObj.put(CampoDeVotacion.VOTOS_BLANCOS.getTexto(), votacion.getVotosBlancos());
        votacionObj.put(CampoDeVotacion.VOTOS_PREFERENCIALES.getTexto(), votacion.getVotosPreferenciales());
    }

    private void convertirJSONCampoVotantes(Votacion votacion, JSONObject votacionObj) {
        JSONArray votantesArray = obtenerArrayIdVotantes(votacion);
        votacionObj.put(CampoDeVotacion.VOTANTES.getTexto(), votantesArray);
    }

    private JSONArray obtenerArrayIdVotantes(Votacion votacion) {
        JSONArray votantesArray = new JSONArray();
        for (Votante votante : votacion.getVotantes()) {
            votantesArray.add(votante.getId());
        }
        return votantesArray;
    }

    public void registrarVotanteEnVotaciones(Votacion votacion, Votante votante) {
        List<Votacion> votaciones = obtenerVotaciones();
        for (var votacionSiguiente : votaciones) {
            if (votacionSiguiente.getId().equals(votacion.getId())) {
                List<Votante> votantes = votacionSiguiente.getVotantes();
                votantes.add(votante);
                votacionSiguiente.setVotantes(votantes);
                escribirVotaciones(votaciones);
                return;
            }
        }
    }

    public Votacion obtenerVotacionPorID(List<Votacion> votaciones, Votacion votacion) {
        return votaciones
                .stream()
                .filter(votacionSiguiente -> votacionSiguiente.getId().equals(votacion.getId()))
                .findFirst()
                .orElseThrow(
                        () -> AccesoADatosInterrumpidoException
                                .talElementoNoExiste(CampoDeVotacion.ID.getTexto()));
    }

    public void crearVotacion(Votacion votacion) {
        votacion.setId(obtenerNuevaIdVotacion());
        votacion.setEstado(Estado.PENDIENTE);
        List<Votacion> votaciones = obtenerVotaciones();
        votaciones.add(votacion);
        escribirVotaciones(votaciones);
    }

    public void eliminarVotacion(Votacion votacion) {
        List<Votacion> votaciones = obtenerVotaciones();
        votaciones.removeIf(votacionSiguiente -> votacionSiguiente.getId().equals(votacion.getId()));
        escribirVotaciones(votaciones);
    }

    public void eliminarOpcionDeVotacion(Votacion votacionElegida, Opcion opcionElegida) {
        List<Votacion> votaciones = obtenerVotaciones();
        Votacion votacionCopia = obtenerVotacionPorID(votaciones, votacionElegida);
        List<Opcion> opciones = votacionCopia.getOpciones();
        opciones.removeIf(opcion -> opcion.getId().equals(opcionElegida.getId()));
        votacionCopia.setOpciones(opciones);
        votacionElegida.setOpciones(opciones);
        escribirVotaciones(votaciones);
    }

    public void actualizarCampoDeVotacion(Votacion votacionElegida, CampoDeVotacion campo, Object valor) {
        List<Votacion> votaciones = obtenerVotaciones();
        Votacion votacionCopia = obtenerVotacionPorID(votaciones, votacionElegida);
        votacionCopia.setAttributo(campo, valor);
        votacionElegida.setAttributo(campo, valor);
        escribirVotaciones(votaciones);
    }

    public void agregarOpcionAVotacion(Votacion votacion, String nombreOpcion) {
        List<Votacion> votaciones = obtenerVotaciones();
        Votacion votacionCopia = obtenerVotacionPorID(votaciones, votacion);
        List<Opcion> opciones = votacionCopia.getOpciones();
        Opcion opcion = new Opcion();
        opcion.setId(obtenerNuevaIdOpcion(votacionCopia));
        opcion.setNombre(nombreOpcion);
        opcion.setCantidadDeVotos(0);
        opciones.add(opcion);
        votacionCopia.setOpciones(opciones);
        votacion.setOpciones(opciones);
        escribirVotaciones(votaciones);
    }

    public boolean opcionYaExiste(Votacion votacion, String  nombreOpcion){
        List<Opcion> opciones = votacion.getOpciones();
        return opciones.stream().anyMatch(opcion -> opcion.getNombre().equals(nombreOpcion));
    }
}
