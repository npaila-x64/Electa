import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/*
    Clase que contiene los métodos que interactúan
    directamente con los archivos de datos JSON
 */

public class AccesoADatos {

    private static final String RUTA_VOTANTES = "src/main/datos/votantes.json";
    private static final String RUTA_CREDENCIALES_ADMIN = "src/main/datos/credencialesAdmin.json";
    private static final String RUTA_VOTACIONES = "src/main/datos/votaciones.json";

    public static JSONArray parsearVotantes() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON(RUTA_VOTANTES);
    }

    public static JSONArray parsearCredencialAdmin() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON(RUTA_CREDENCIALES_ADMIN);
    }

    public static JSONArray parsearVotaciones() throws AccesoADatosInterrumpidoException {
        return parsearArchivoJSON(RUTA_VOTACIONES);
    }

    public static JSONArray parsearArchivoJSON(String ruta) throws AccesoADatosInterrumpidoException {
        try {
            String contenidosJSON = leerContenidosJSON(ruta);
            JSONParser parser = new JSONParser();
            return (JSONArray) parser.parse(contenidosJSON);
        } catch (ParseException e) {
            throw AccesoADatosInterrumpidoException.noSePudoParsearArchivo(ruta);
        } catch (FileNotFoundException e) {
            throw AccesoADatosInterrumpidoException.noSePudoCargarArchivo(ruta);
        }
    }

    public static List<Votacion> obtenerVotacionesConEstado(Estado estado) {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> nuevasVotaciones = new ArrayList<>();
        for (Votacion votacionSiguiente : votaciones) {
            if (votacionSiguiente.getEstado().equals(estado)) {
                nuevasVotaciones.add(votacionSiguiente);
            }
        }
        return nuevasVotaciones;
    }

    public static List<String> obtenerTitulosVotaciones() {
        List<Votacion> votaciones = obtenerVotaciones();
        List<String> tituloVotaciones = new ArrayList<>();
        for (var votacion : votaciones) {
            tituloVotaciones.add(votacion.getTitulo());
        }
        return tituloVotaciones;
    }

    public static List<Votacion> obtenerVotaciones() {
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

    public static List<Votante> obtenerVotantes() {
        List<Votante> votantes = new ArrayList<>();
        JSONArray jsonArrayVotantes = parsearVotantes();
        for (Object jsonArrayVotante : jsonArrayVotantes) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotante;
            Votante votante = new Votante();
            obtenerAtributosDeVotanteJSON(votante, votacionSiguiente);
            votantes.add(votante);
        }
        return votantes;
    }

    private static void obtenerAtributosDeVotacionJSON(Votacion votacion, JSONObject votacionJSON) {
        votacion.setId(votacionJSON.get(CampoDeVotacion.ID.getTexto()));
        votacion.setTitulo(votacionJSON.get(CampoDeVotacion.TITULO.getTexto()));
        votacion.setDescripcion(votacionJSON.get(CampoDeVotacion.DESCRIPCION.getTexto()));
        votacion.setEstado(votacionJSON.get(CampoDeVotacion.ESTADO.getTexto()));
        votacion.setFechaTiempoInicio(parsearFechaTiempoInicio(votacionJSON));
        votacion.setFechaTiempoTermino(parsearFechaTiempoTermino(votacionJSON));
        votacion.setOpciones(obtenerOpcionesDeVotacionJSON(votacion, votacionJSON));
        votacion.setVotantes(obtenerVotantesDeVotacionJSON(votacion, votacionJSON));
        votacion.setVotosPreferenciales(
                votacionJSON.get(CampoDeVotacion.VOTOS_PREFERENCIALES.getTexto()));
        votacion.setVotosBlancos(votacionJSON.get(CampoDeVotacion.VOTOS_BLANCOS.getTexto()));
    }

    private static void obtenerAtributosDeVotanteJSON(Votante votante, JSONObject votanteJSON) {
        votante.setId(votanteJSON.get(CampoDeVotante.ID.getTexto()));
        votante.setRut(votanteJSON.get(CampoDeVotante.RUT.getTexto()));
        votante.setClave(votanteJSON.get(CampoDeVotante.CLAVE.getTexto()));
    }

    public static List<Votante> obtenerVotantesDeVotacionJSON(Votacion votacion, JSONObject votacionSiguiente) {
        List<Votante> votantes = obtenerVotantesDeVotacionJSON(votacionSiguiente);
        votacion.setVotantes(votantes);
        return votantes;
    }

    public static List<Votante> obtenerVotantesDeVotacionJSON(JSONObject votacionSiguiente) {
        JSONArray votantesArray = (JSONArray) votacionSiguiente.get(CampoDeVotacion.VOTANTES.getTexto());
        List<Votante> votantes = new ArrayList<>();
        for (Object IDvotante : votantesArray) {
            Votante votante = new Votante();
            votante.setId(Integer.parseInt(IDvotante.toString()));
            votantes.add(votante);
        }
        return votantes;
    }

    public static List<Opcion> obtenerOpcionesDeVotacionJSON(Votacion votacion, JSONObject votacionSiguiente) {
        var opciones = obtenerOpcionesDeVotacionJSON(votacionSiguiente);
        for (var opcion : opciones) opcion.setVotacion(votacion);
        return opciones;
    }

    public static List<Opcion> obtenerOpcionesDeVotacionJSON(JSONObject votacionSiguiente) {
        JSONObject opciones = (JSONObject) votacionSiguiente.get(CampoDeVotacion.OPCIONES.getTexto());
        List<Opcion> listaOpciones = new ArrayList<>();
        for (Object opcionStr : opciones.keySet()) {
            Opcion opcion = new Opcion();
            opcion.setNombre(opcionStr);
            opcion.setCantidadDeVotos(opciones.get(opcionStr));
            listaOpciones.add(opcion);
        }
        return listaOpciones;
    }

    public static List<Votacion> obtenerVotacionesEnElQuePuedeVotarElVotante(Votante votante) {
        List<Votacion> votacionesEnCurso = filtrarVotacionesEnCurso();
        List<Votacion> votacionesEnElQuePuedeVotarElVotante = new ArrayList<>();
        for (var votacionSiguiente : votacionesEnCurso) {
            filtrarVotacionVotadaAVotante(
                    votacionesEnElQuePuedeVotarElVotante, votacionSiguiente, votante);
        }
        return votacionesEnElQuePuedeVotarElVotante;
    }

    public static List<Votacion> filtrarVotacionesEnCurso() {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Votacion> votacionesEnCurso = new ArrayList<>();
        for (var votacionSiguiente : votaciones) {
            if (votacionSiguiente.getEstado().equals(Estado.EN_CURSO)) {
                votacionesEnCurso.add(votacionSiguiente);
            }
        }
        return votacionesEnCurso;
    }

    public static void filtrarVotacionVotadaAVotante(List<Votacion> IDsVotaciones, Votacion votacion, Votante votante) {
        List<Votante> votantes = votacion.getVotantes();
        for (var IDVotanteQueYaVotoEnEstaVotacion : votantes) {
            if (votante.getId().equals(IDVotanteQueYaVotoEnEstaVotacion.getId())) {
                // Se da a entender que el votante ya está en la
                // lista de personas quienes votaron en esta votación
                return;
            }
        }
        IDsVotaciones.add(votacion);
    }

    public static Votacion obtenerVotacionPorID(List<Votacion> votaciones, Votacion votacion) {
        for (var votacionSiguiente : votaciones) {
            if (votacionSiguiente.getId().equals(votacion.getId())) {
                return votacionSiguiente;
            }
        }
        throw AccesoADatosInterrumpidoException.talElementoNoExiste(CampoDeVotacion.ID.getTexto());
    }

    public static Votante obtenerVotantePorRut(String rut) {
        List<Votante> votantes = obtenerVotantes();
        for (Votante votanteSiguiente : votantes) {
            if (votanteSiguiente.getRut().equals(rut)) {
                return votanteSiguiente;
            }
        }
        throw AccesoADatosInterrumpidoException.talElementoNoExiste(rut);
    }

    public static String obtenerNuevaIDVotacion() {
        int maxID = 0;
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            int id = parsearObjectAInt(votacionSiguiente.get(CampoDeVotacion.ID.getTexto()));
            if (id > maxID) {
                maxID = id;
            }
        }
        maxID++;
        return String.valueOf(maxID);
    }

    public static String leerContenidosJSON(String ruta) throws FileNotFoundException {
        StringBuilder st = new StringBuilder();
        File archivoJSON = new File(ruta);
        Scanner scanner = new Scanner(archivoJSON);
        while (scanner.hasNextLine()) {
            st.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return st.toString();
    }

    public static void escribirEnVotaciones(String contenido) {
        escribirArchivoJSON(RUTA_VOTACIONES, contenido);
    }

    public static void escribirArchivoJSON(String ruta, String contenido) {
        try {
            FileWriter myWriter = new FileWriter(ruta);
            myWriter.write(contenido);
            myWriter.close();
        } catch (IOException e) {
            throw AccesoADatosInterrumpidoException.noSePudoEscribirArchivo(ruta);
        } catch (NullPointerException e){
            throw AccesoADatosInterrumpidoException.noSePudoEscribirArchivo(ruta);
        }
    }

    public static void realizarVotoBlanco(Votacion votacion, Votante votante) {
        votarOpcionBlanco(votacion);
        realizarVoto(votacion, votante);
    }

    private static void votarOpcionBlanco(Votacion votacion) {
        int votosBlancos = votacion.getVotosBlancos();
        votosBlancos++;
        votacion.setVotosBlancos(votosBlancos);
    }

    public static void realizarVotoPreferencial(Votacion votacion, Votante votante, Opcion opcionElegida) {
        votarOpcionPreferencial(votacion, opcionElegida);
        realizarVoto(votacion, votante);
    }

    public static void realizarVoto(Votacion votacion, Votante votante) {
        List<Votacion> votaciones = obtenerVotaciones();
        for (var votacionSiguiente : votaciones) {
            if (votacionSiguiente.getId().equals(votacion.getId())) {
                List<Votante> votantes = votacionSiguiente.getVotantes();
                votantes.add(votante);
                votacionSiguiente.setVotantes(votantes);
                escribirEnVotaciones(convertirListaDeVotacionesAJSONArray(votaciones).toJSONString());
                return;
            }
        }
    }

    public static void votarOpcionPreferencial(Votacion votacion, Opcion opcionElegida) {
        List<Opcion> opciones = votacion.getOpciones();
        for (var opcion : opciones) {
            if (opcion.equals(opcionElegida)) {
                int votosOpcion = opcion.getCantidadDeVotos();
                votosOpcion++;
                opcion.setCantidadDeVotos(votosOpcion);
                int votosPreferencialesVotacion = votacion.getVotosPreferenciales();
                votosPreferencialesVotacion++;
                votacion.setVotosPreferenciales(votosPreferencialesVotacion);
            }
        }
        votacion.setOpciones(opciones);
    }

    public static JSONArray convertirListaDeVotacionesAJSONArray(List<Votacion> votaciones) {
        JSONArray array = new JSONArray();
        for (Votacion votacion : votaciones) {
            JSONObject votacionObj = new JSONObject();
            votacionObj.put(CampoDeVotacion.ID.getTexto(), votacion.getId());
            votacionObj.put(CampoDeVotacion.TITULO.getTexto(), votacion.getTitulo());
            votacionObj.put(CampoDeVotacion.DESCRIPCION.getTexto(), votacion.getDescripcion());
            votacionObj.put(CampoDeVotacion.ESTADO.getTexto(), votacion.getEstado().getTexto());
            votacionObj.put(CampoDeVotacion.FECHA_INICIO.getTexto(),
                    Optional.ofNullable(votacion.getFechaInicio())
                            .orElse(LocalDate.of(1900,1,1))
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            votacionObj.put(CampoDeVotacion.HORA_INICIO.getTexto(),
                    Optional.ofNullable(votacion.getTiempoInicio())
                            .orElse(LocalTime.of(0,0))
                            .format(DateTimeFormatter.ofPattern("HH:mm")));
            votacionObj.put(CampoDeVotacion.FECHA_TERMINO.getTexto(),
                    Optional.ofNullable(votacion.getFechaTermino())
                            .orElse(LocalDate.of(2100,1,1))
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            votacionObj.put(CampoDeVotacion.HORA_TERMINO.getTexto(),
                    Optional.ofNullable(votacion.getTiempoTermino())
                            .orElse(LocalTime.of(0,0))
                            .format(DateTimeFormatter.ofPattern("HH:mm")));
            JSONArray votantesArray = new JSONArray();
            for (Votante votante : votacion.getVotantes()) {
                votantesArray.add(votante.getId());
            }
            votacionObj.put(CampoDeVotacion.VOTANTES.getTexto(), votantesArray);
            JSONObject opcionesObj = new JSONObject();
            for (Opcion opcion : votacion.getOpciones()) {
                opcionesObj.put(opcion.getNombre(), opcion.getCantidadDeVotos());
            }
            votacionObj.put(CampoDeVotacion.OPCIONES.getTexto(), opcionesObj);
            votacionObj.put(CampoDeVotacion.VOTOS_BLANCOS.getTexto(), votacion.getVotosBlancos());
            votacionObj.put(CampoDeVotacion
                    .VOTOS_PREFERENCIALES.getTexto(), votacion.getVotosPreferenciales());
            array.add(votacionObj);
        }
        return array;
    }

    public static void eliminarVotacion(Votacion votacion) {
        List<Votacion> votaciones = obtenerVotaciones();
        votaciones.removeIf(votacionSiguiente -> votacionSiguiente.getId().equals(votacion.getId()));
        escribirEnVotaciones(convertirListaDeVotacionesAJSONArray(votaciones).toJSONString());
    }

    public static void eliminarOpcionDeVotacion(Votacion votacion, Opcion opcionElegida) {
        List<Votacion> votaciones = obtenerVotaciones();
        Votacion nuevaVotacion = obtenerVotacionPorID(votaciones, votacion);
        List<Opcion> opciones = nuevaVotacion.getOpciones();
        opciones.removeIf(opcion -> opcion.getNombre().equals(opcionElegida.getNombre()));
        nuevaVotacion.setOpciones(opciones);
        escribirEnVotaciones(convertirListaDeVotacionesAJSONArray(votaciones).toJSONString());
    }

    public static void actualizarCampoDeVotacion(Votacion viejaVotacion, CampoDeVotacion campo, Object valor) {
        List<Votacion> votaciones = obtenerVotaciones();
        Votacion votacion = obtenerVotacionPorID(votaciones, viejaVotacion);
        votacion.setAttributo(campo, valor);
        escribirEnVotaciones(convertirListaDeVotacionesAJSONArray(votaciones).toJSONString());
    }

    public static void agregarOpcionAVotacion(Votacion votacion, String nombreOpcion) {
        List<Votacion> votaciones = obtenerVotaciones();
        List<Opcion> opciones = votacion.getOpciones();
        Opcion opcion = new Opcion();
        opcion.setNombre(nombreOpcion);
        opcion.setCantidadDeVotos(0);
        opciones.add(opcion);
        votacion.setOpciones(opciones);
        escribirEnVotaciones(convertirListaDeVotacionesAJSONArray(votaciones).toJSONString());
    }

    public static LocalDateTime parsearFechaTiempoInicio(JSONObject votacion) {
        return parsearFechaTiempoVotacion(
                votacion.get(CampoDeVotacion.FECHA_INICIO.getTexto()),
                votacion.get(CampoDeVotacion.HORA_INICIO.getTexto()));
    }

    public static LocalDateTime parsearFechaTiempoTermino(JSONObject votacion) {
        return parsearFechaTiempoVotacion(
                votacion.get(CampoDeVotacion.FECHA_TERMINO.getTexto()),
                votacion.get(CampoDeVotacion.HORA_TERMINO.getTexto()));
    }

    public static LocalDateTime parsearFechaTiempoVotacion(Object fecha, Object tiempo) {
        var fechaArr = fecha.toString().split("-");
        var dia = Integer.parseInt(fechaArr[0]);
        var mes = Integer.parseInt(fechaArr[1]);
        var anio = Integer.parseInt(fechaArr[2]);
        var tiempoArr = tiempo.toString().split(":");
        var hora = Integer.parseInt(tiempoArr[0]);
        var minutos = Integer.parseInt(tiempoArr[1]);
        return LocalDateTime.of(anio, mes, dia, hora, minutos);
    }

    public static int parsearObjectAInt(Object obj) {
        return Integer.parseInt(String.valueOf(obj));
    }
}
