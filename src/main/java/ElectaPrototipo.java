import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class ElectaPrototipo {
    public void iniciar() {
        mostrarMenuDeIngreso();
    }

    public void mostrarOpcionesDeIngreso() {
        System.out.print("""
                
                ¡Bienvenido/a al sistema Electa!
                [1] Ingresar como Votante
                [2] Ingresar como Administrador
                Si desea salir escriba [0]
                """.concat("> "));
    }

    public void mostrarMenuDeIngreso() {
        mostrarOpcionesDeIngreso();
        salirMenu:
        while (true) {
            switch (pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> ingresarComoUsuario();
                case 2 -> ingresarComoAdmin();
                default -> {mostrarOpcionInvalida();continue;}
            }
            mostrarOpcionesDeIngreso();
        }
    }

    public void mostrarOpcionInvalida() {
        System.out.print("Por favor, escoja una opción válida\n> ");
    }

    public void ingresarComoAdmin() {
        System.out.print("Ingrese la contraseña del administrador \n> ");
        String clave = pedirString();
        if (esCredencialAdminValida(clave)) {
            mostrarMenuAdministador();
        } else {
            System.out.println("Contraseña incorrecta");
        }
    }

    public void mostrarOpcionesAdmin() {
        System.out.print("""
                Elija una opción
                [1] Ver, modificar, o eliminar votaciones
                [2] Crear nueva votación
                [3] Ver resultados de votaciones
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
    }

    public void mostrarMenuAdministador() {
        System.out.println("Bienvenido al menú del administrador");
        mostrarOpcionesAdmin();
        salirMenu:
        while (true) {
            switch (pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> mostrarPanelDeControlDeVotaciones();
                case 2 -> mostrarMenuCreacionDeVotacion();
                case 3 -> mostrarMenuResultados();
                default -> {mostrarOpcionInvalida(); continue;}
            }
            mostrarOpcionesAdmin();
        }
    }

    public void ingresarComoUsuario() {
        System.out.print("Ingrese su rut\n> ");
        String rutVotante = pedirString();
        System.out.print("Ingrese su clave\n> ");
        String claveVotante = pedirString();

        if (esCredencialVotanteValida(rutVotante, claveVotante)) {
            mostrarMenuVotacionesVotante(rutVotante);
        } else {
            System.out.println("RUT o contraseña incorrectos");
        }
    }

    public void mostrarMenuVotacionesVotante(String rut) {
        mostrarVotacionesEnCurso();
        mostrarOpcionesVotante();
        salirMenu:
        while (true) {
            switch (pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> mostrarMenuParaVotar();
                case 2 -> mostrarMenuResultados();
                default -> {mostrarOpcionInvalida(); continue;}
            }
            mostrarOpcionesVotante();
        }
    }

    public void mostrarOpcionesVotante() {
        System.out.print("""
                OPCIONES
                [1] Votar
                [2] Ver Resultados
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
    }

    public void mostrarVotacionesEnCurso() {
        List<String> titulosVotaciones = obtenerTitulosVotaciones();
        System.out.println("Votaciones En Curso");
        for (String titulo : titulosVotaciones) {
            System.out.printf("\"%s\"%n", titulo);
        }
    }

    public void mostrarMenuResultados() {
        List<String> titulosVotaciones = obtenerTitulosVotaciones();
        mostrarVotacionesDisponiblesParaRevision(titulosVotaciones);
        while (true) {
            int opcionElegida = pedirOpcion();
            if (opcionElegida == 0) break;
            if (esOpcionElegidaFueraDeRango(opcionElegida, titulosVotaciones.size())) continue;
            mostrarResultadosVotacion(titulosVotaciones.get(opcionElegida - 1));
            titulosVotaciones = obtenerTitulosVotaciones();
            mostrarVotacionesDisponiblesParaRevision(titulosVotaciones);
        }
    }

    public void mostrarVotacionesDisponiblesParaRevision(List<String> titulosVotaciones) {
        System.out.println("Votaciones disponibles para revisión");
        mostrarListaOpciones(titulosVotaciones);
    }

    public void mostrarMenuParaVotar() {
        List<String> IDsVotaciones = obtenerIDsVotaciones();
        List<String> titulosVotaciones = obtenerTitulosVotaciones();
        mostrarVotacionesDisponiblesParaVotacion(titulosVotaciones);
        while (true) {
            int opcionElegida = pedirOpcion();
            if (opcionElegida == 0) break;
            if (esOpcionElegidaFueraDeRango(opcionElegida, IDsVotaciones.size())) continue;
            mostrarMenuOpcionesParaVotar(IDsVotaciones.get(opcionElegida - 1));
            IDsVotaciones = obtenerIDsVotaciones();
            titulosVotaciones = obtenerTitulosVotaciones();
            mostrarVotacionesDisponiblesParaVotacion(titulosVotaciones);
        }
    }

    public void mostrarVotacionesDisponiblesParaVotacion(List<String> titulosVotaciones) {
        System.out.println("Votaciones disponibles para votación");
        mostrarListaOpciones(titulosVotaciones);
    }

    public boolean realizarVotoBlanco(String IDVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        if (votarOpcionBlanco(jsonArrayVotaciones, IDVotacion)) {
            escribirArchivoJSON("src/main/datos/votaciones.json", jsonArrayVotaciones.toJSONString());
            return true;
        } else {
            System.err.println("Error, no se pudo realizar el voto.");
            return false;
        }
    }

    public boolean votarOpcionBlanco(JSONArray jsonArrayVotaciones, String IDVotacion) {
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get("id")).equals(IDVotacion)) {
                int votosBlancosOpcion = Integer.parseInt(String.valueOf(votacionSiguiente.get("votos_blancos")));
                votacionSiguiente.put("votos_blancos", votosBlancosOpcion + 1);
                return true;
            }
        }
        return false;
    }

    public void mostrarMenuOpcionesParaVotar(String IDVotacion) {
        List<String> opciones = obtenerOpcionesDeVotacion(IDVotacion);
        opciones.add(0, "Abstenerse");
        mostrarListaOpciones(opciones);
        salirBucle:
        while (true) {
            System.out.println("Opciones disponibles");
            int opcionElegida = pedirOpcion();
            switch (opcionElegida) {
                case 0 -> {break salirBucle;}
                case 1 -> {
                    if (realizarVotoBlanco(IDVotacion)) {
                        mostrarVotoRealizadoConExito();
                        break salirBucle;
                    }
                }
                default -> {
                    if (esOpcionElegidaFueraDeRango(opcionElegida, opciones.size())) continue;
                    if (realizarVoto(IDVotacion, opciones.get(opcionElegida))) {
                        mostrarVotoRealizadoConExito();
                        break salirBucle;
                    }
                }
            }
            mostrarListaOpciones(opciones);
        }
    }

    public void mostrarMenuOpcionesParaVotarTest(String IDVotacion) {
        List<String> opciones = obtenerOpcionesDeVotacion(IDVotacion);
        opciones.add(0, "Abstenerse");
        mostrarListaOpciones(opciones);
        salirBucle:
        while (true) {
            System.out.println("Opciones disponibles");
            int opcionElegida = pedirOpcion();
            switch (opcionElegida) {
                case 0 -> {break salirBucle;}
                case 1 -> {
                    if (realizarVotoBlanco(IDVotacion)) {
                        mostrarVotoRealizadoConExito();
                        break salirBucle;
                    }
                }
                default -> {
                    if (esOpcionElegidaFueraDeRango(opcionElegida, opciones.size())) continue;
                    if (realizarVoto(IDVotacion, opciones.get(opcionElegida))) {
                        mostrarVotoRealizadoConExito();
                        break salirBucle;
                    }
                }
            }
            mostrarListaOpciones(opciones);
        }
    }

    public boolean esOpcionElegidaFueraDeRango(int opcionElegida, int cantidadOpciones) {
        if (opcionElegida > cantidadOpciones) {
            mostrarOpcionInvalida();
            return true;
        }
        return false;
    }

    public void mostrarVotoRealizadoConExito() {
        System.out.println("¡Voto realizado con exito!");
    }

    public boolean realizarVoto(String IDVotacion, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        if (votarOpcionPreferencial(jsonArrayVotaciones, IDVotacion, opcionElegida)) {
            escribirArchivoJSON("src/main/datos/votaciones.json", jsonArrayVotaciones.toJSONString());
            return true;
        } else {
            System.err.println("Error, no se pudo realizar el voto.");
            return false;
        }
    }

    public void escribirArchivoJSON(String ruta, String contenido) {
        try {
            FileWriter myWriter = new FileWriter(ruta);
            myWriter.write(contenido);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean votarOpcionPreferencial(JSONArray jsonArrayVotaciones, String IDsVotacion, String opcionElegida) {
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get("id")).equals(IDsVotacion)) {
                JSONObject opciones = (JSONObject) votacionSiguiente.get("opciones");
                List<String> opcionesArray = new ArrayList<>(opciones.keySet());
                for (String opcion : opcionesArray) {
                    if (opcion.equals(opcionElegida)) {
                        int votosOpcion = Integer.parseInt(String.valueOf(opciones.get(opcion)));
                        opciones.put(opcion, votosOpcion + 1);
                        int votosPreferenciales = Integer.parseInt(String.valueOf(votacionSiguiente.get("votos_preferenciales")));
                        votacionSiguiente.put("votos_preferenciales", votosPreferenciales + 1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void mostrarListaOpciones(List<String> opciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < opciones.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, opciones.get(indice));
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    public List<String> obtenerOpcionesDeVotacion(String IDVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject nextVotacion = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(nextVotacion.get("id")).equals(IDVotacion)) {
                JSONObject opciones = (JSONObject) nextVotacion.get("opciones");
                return new ArrayList<>(opciones.keySet());
            }
        }
        return new ArrayList<>();
    }

    public void mostrarResultadosVotacion(String tituloVotacion) {
        JSONObject votacion = obtenerJSONObjectVotacionPorTitulo(tituloVotacion);
        mostrarResultadosDatos(votacion);
        mostrarResultadosVotosPorOpciones(votacion);
    }

    public JSONObject obtenerJSONObjectVotacionPorTitulo(String tituloVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get("titulo")).equals(tituloVotacion)) {
                return votacionSiguiente;
            }
        }
        return null;
    }

    public JSONObject obtenerJSONObjectVotacionPorID(String IDVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get("id")).equals(IDVotacion)) {
                return votacionSiguiente;
            }
        }
        return null;
    }

    public void mostrarResultadosDatos(JSONObject votacion) {
        String titulo = String.valueOf(votacion.get("titulo"));
        int votoBlancos = Integer.parseInt(String.valueOf(votacion.get("votos_blancos")));
        int votoPreferenciales = Integer.parseInt(String.valueOf(votacion.get("votos_preferenciales")));
        int totalVotos = votoPreferenciales + votoBlancos;
        String fechaInicio = String.valueOf(votacion.get("fecha_inicio"));
        String horaInicio = String.valueOf(votacion.get("hora_inicio"));
        String fechaTermino = String.valueOf(votacion.get("fecha_termino"));
        String horaTermino = String.valueOf(votacion.get("hora_termino"));
        System.out.printf("""
                        Resultados para la votacion "%s"
                        Votos preferenciales   %s
                        Votos blancos          %s
                        Total votos            %s
                        Fecha y hora de inicio   %s %s hrs
                        Fecha y hora de término  %s %s hrs
                        %n""", titulo, votoPreferenciales, votoBlancos, totalVotos,
                fechaInicio, horaInicio, fechaTermino, horaTermino);
    }

    public void mostrarResultadosVotosPorOpciones(JSONObject votacion) {
        JSONObject opciones = (JSONObject) votacion.get("opciones");
        List<String> opcionesList = new ArrayList<>(opciones.keySet());
        System.out.println("Votos por opciones");
        for (String opcion : opcionesList) {
            System.out.println(
                    opcion.concat(" ")
                            .concat(String.valueOf(opciones.get(opcion))));
        }
    }

    public List<String> obtenerTitulosVotaciones() {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> arrayListVotaciones = new ArrayList<>();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject nextVotacion = (JSONObject) jsonArrayVotacion;
            arrayListVotaciones.add(String.valueOf(nextVotacion.get("titulo")));
        }
        return arrayListVotaciones;
    }

    public List<String> obtenerIDsVotaciones() {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> arrayListIDsVotaciones = new ArrayList<>();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject nextVotacion = (JSONObject) jsonArrayVotacion;
            arrayListIDsVotaciones.add(String.valueOf(nextVotacion.get("id")));
        }
        return arrayListIDsVotaciones;
    }

    public JSONArray parsearVotaciones() {
        String jsonVotaciones = leerContenidosJSON("src/main/datos/votaciones.json");
        JSONParser parser = new JSONParser();
        try {
            Object arrayVotaciones = parser.parse(jsonVotaciones);
            return (JSONArray) arrayVotaciones;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void mostrarPanelDeControlDeVotaciones() {
        List<String> IDsVotaciones = obtenerIDsVotaciones();
        List<String> titulosVotaciones = obtenerTitulosVotaciones();
        System.out.print("""
                Para modificar o eliminar una votación
                escriba el número correspondiente a su índice
                """);
        mostrarListaOpciones(titulosVotaciones);
        salirMenu:
        while (true) {
            int opcionElegida = pedirOpcion();
            switch (opcionElegida) {
                case 0 -> {break salirMenu;}
                default -> {
                    if (esOpcionElegidaFueraDeRango(opcionElegida, IDsVotaciones.size())) continue;
                    mostrarEditorDeVotacion(IDsVotaciones.get(opcionElegida - 1));
                }
            }
            IDsVotaciones = obtenerIDsVotaciones();
            titulosVotaciones = obtenerTitulosVotaciones();
            System.out.print("""
                Para modificar o eliminar una votación
                escriba el número correspondiente a su índice
                """);
            mostrarListaOpciones(titulosVotaciones);

        }
    }

    public void mostrarEditorDeVotacion(String IDVotacion) {
        mostrarDatosDeVotacion(IDVotacion);
        salirMenu:
        while (true) {
            System.out.print("""
                [1] Modificar algún campo
                [2] Para agregar una opción
                [3] Para eliminar alguna opción
                [4] Para eliminar la votación
                Si desea volver escriba [0]
                """.concat("> "));
            switch (pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> mostrarMenuEditarCamposDeVotacion(IDVotacion);
                case 2 -> agregarOpcionDeVotacion(IDVotacion);
                case 3 -> mostrarMenuEliminarOpcionesDeVotacion(IDVotacion);
                case 4 -> {eliminarVotacion(IDVotacion); break salirMenu;}
                default -> {mostrarOpcionInvalida(); continue;}
            }
            mostrarDatosDeVotacion(IDVotacion);
        }
    }

    public void mostrarMenuEditarCamposDeVotacion(String IDVotacion) {
        List<String> campos = List.of("titulo", "descripcion","fecha_inicio",
                "hora_inicio", "fecha_termino", "hora_termino");
        mostrarListaOpciones(campos);
        salirMenu:
        while (true) {
            System.out.print("""
                Escriba el índice del campo que desea modificar
                """);
            int opcionElegida = pedirOpcion();
            switch (opcionElegida) {
                case 0 -> {break salirMenu;}
                default -> {
                    if (esOpcionElegidaFueraDeRango(opcionElegida, campos.size())) continue;
                    editarCampoDeVotacion(IDVotacion, campos.get(opcionElegida - 1));
                }
            }
            mostrarListaOpciones(campos);
        }
    }

    public boolean editarCampoDeVotacion(String IDVotacion, String campo) {
        System.out.print(campo.concat("> "));
        String texto = pedirString();
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        if (actualizarCampoDeVotacion(jsonArrayVotaciones, IDVotacion, campo, texto)) {
            escribirArchivoJSON("src/main/datos/votaciones.json", jsonArrayVotaciones.toJSONString());
            return true;
        } else {
            System.err.println("Error, no se pudo realizar el voto.");
            return false;
        }
    }

    public boolean actualizarCampoDeVotacion(JSONArray jsonArrayVotaciones, String IDVotacion, String campo, String texto) {
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject nextVotacion = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(nextVotacion.get("id")).equals(IDVotacion)) {
                nextVotacion.put(campo, texto);
                return true;
            }
        }
        return false;
    }

    public void mostrarMenuEliminarOpcionesDeVotacion(String IDVotacion) {
        List<String> opciones = obtenerOpcionesDeVotacion(IDVotacion);
        System.out.println("Escriba la opción que desea eliminar");
        mostrarListaOpciones(opciones);
        salirMenu:
        while (true) {
            int opcionElegida = pedirOpcion();
            switch (opcionElegida) {
                case 0 -> {break salirMenu;}
                default -> {
                    if (esOpcionElegidaFueraDeRango(opcionElegida, opciones.size())) continue;
                    eliminarOpcionDeVotacion(IDVotacion, opciones.get(opcionElegida - 1));
                    System.out.println("Opción eliminada con exito");
                    break salirMenu;
                }
            }
        }
    }

    public boolean agregarOpcionDeVotacion(String IDVotacion) {
        System.out.print("Escriba la opción que desea agregar\n> ");
        String opcion = pedirString();
        return agregarOpcionAVotacion(IDVotacion, opcion);
    }

    public boolean eliminarVotacion(String IDVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get("id")).equals(IDVotacion)) {
                jsonArrayVotaciones.remove(votacionSiguiente);
                escribirArchivoJSON("src/main/datos/votaciones.json", jsonArrayVotaciones.toJSONString());
                return true;
            }
        }
        return false;
    }

    public boolean eliminarOpcionDeVotacion(String IDVotacion, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get("id")).equals(IDVotacion)) {
                JSONObject opciones = (JSONObject) votacionSiguiente.get("opciones");
                opciones.remove(opcionElegida);
                escribirArchivoJSON("src/main/datos/votaciones.json", jsonArrayVotaciones.toJSONString());
                return true;
            }
        }
        return false;
    }

    public boolean agregarOpcionAVotacion(String IDVotacion, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get("id")).equals(IDVotacion)) {
                JSONObject opciones = (JSONObject) votacionSiguiente.get("opciones");
                opciones.put(opcionElegida, 0);
                escribirArchivoJSON("src/main/datos/votaciones.json", jsonArrayVotaciones.toJSONString());
                return true;
            }
        }
        return false;
    }

    public void mostrarDatosDeVotacion(String IDVotacion) {
        JSONObject votacion = obtenerJSONObjectVotacionPorID(IDVotacion);
        String titulo = String.valueOf(votacion.get("titulo"));
        String descripcion = String.valueOf(votacion.get("descripcion"));
        String fechaInicio = String.valueOf(votacion.get("fecha_inicio"));
        String horaInicio = String.valueOf(votacion.get("hora_inicio"));
        String fechaTermino = String.valueOf(votacion.get("fecha_termino"));
        String horaTermino = String.valueOf(votacion.get("hora_termino"));
        String estado = String.valueOf(votacion.get("estado"));
        System.out.printf("""
                        Titulo.........................%s
                        Descripcion....................%s
                        Fecha de inicio................%s
                        Hora de inicio.................%s hrs
                        Fecha de término...............%s
                        Hora de término................%s hrs
                        Estado.........................%s
                        %n""", titulo, descripcion, fechaInicio,
                horaInicio, fechaTermino, horaTermino, estado);
        JSONObject opciones = (JSONObject) votacion.get("opciones");
        List<String> opcionesList =  new ArrayList<>(opciones.keySet());
        System.out.println("Opciones");
        for (String opcion : opcionesList) {
            System.out.println(".......".concat(opcion));
        }
        System.out.println();
    }

    public void mostrarMenuCreacionDeVotacion() {
        String IDVotacion = obtenerNuevaIDVotacion();
        HashMap<String, String> mapaConCampos = pedirCamposDeVotacion();
        JSONObject votacion = generarNuevaVotacionJSONObject(mapaConCampos, IDVotacion);

        JSONArray jsonArrayVotaciones = parsearVotaciones();
        jsonArrayVotaciones.add(votacion);
        escribirArchivoJSON("src/main/datos/votaciones.json", jsonArrayVotaciones.toJSONString());

        System.out.println("¡Votación creada!\n");
        mostrarMenuAgregacionDeOpciones(IDVotacion);
    }

    public HashMap<String, String> pedirCamposDeVotacion() {
        HashMap<String, String> mapaConCampos = new HashMap<>();
        System.out.print("Escriba el título de la votación que desea agregar\n> ");
        String titulo = pedirString();
        System.out.println("Rellene los siguientes campos");
        System.out.print("Descripción\n> ");
        String descripcion = pedirString();
        System.out.print("Fecha de inicio (dd-MM-aaaa)\n> ");
        String fechaInicio = pedirString();
        System.out.print("Hora de inicio (hh:mm formato 24 horas)\n> ");
        String horaInicio = pedirString();
        System.out.print("Fecha de término (dd-MM-aaaa)\n> ");
        String fechaTermino = pedirString();
        System.out.print("Hora de término (hh:mm formato 24 horas)\n> ");
        String horaTermino = pedirString();
        mapaConCampos.put("titulo", titulo);
        mapaConCampos.put("descripcion", descripcion);
        mapaConCampos.put("fecha_inicio", fechaInicio);
        mapaConCampos.put("hora_inicio", horaInicio);
        mapaConCampos.put("fecha_termino", fechaTermino);
        mapaConCampos.put("hora_termino", horaTermino);
        return mapaConCampos;
    }

    public JSONObject generarNuevaVotacionJSONObject(HashMap<String, String> mapaConCampos, String IDVotacion) {
        JSONObject votacion = new JSONObject();
        votacion.put("id", IDVotacion);
        votacion.put("titulo", mapaConCampos.get("titulo"));
        votacion.put("descripcion", mapaConCampos.get("descripcion"));
        votacion.put("fecha_inicio", mapaConCampos.get("fecha_inicio"));
        votacion.put("hora_inicio", mapaConCampos.get("hora_inicio"));
        votacion.put("fecha_termino", mapaConCampos.get("fecha_termino"));
        votacion.put("hora_termino", mapaConCampos.get("hora_termino"));
        votacion.put("estado", "BORRADOR");
        votacion.put("votos_preferenciales", 0);
        votacion.put("votos_blancos", 0);
        JSONObject opciones = new JSONObject();
        votacion.put("opciones", opciones);
        return votacion;
    }

    public void mostrarMenuAgregacionDeOpciones(String IDVotacion) {
        mostrarDatosDeVotacion(IDVotacion);
        salirMenu:
        while (true) {
            System.out.print("""
                    Para agregar una opción escriba [1]
                    Para finalizar y volver escriba [0]
                    Elija una opción
                    """.concat("> "));
            switch (pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> agregarOpcionDeVotacion(IDVotacion);
                default -> {mostrarOpcionInvalida(); continue;}
            }
            mostrarDatosDeVotacion(IDVotacion);
        }
    }

    public String obtenerNuevaIDVotacion() {
        int maxID = 0;
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            int id = Integer.parseInt(String.valueOf(votacionSiguiente.get("id")));
            if (id > maxID) {
                maxID = id;
            }
        }
        maxID++;
        return String.valueOf(maxID);
    }

    public String leerContenidosJSON(String ruta) {
        StringBuilder st = new StringBuilder();
        try {
            File archivoJSON = new File(ruta);
            Scanner scanner = new Scanner(archivoJSON);
            while (scanner.hasNextLine()) {
                st.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            return st.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return st.toString();
    }

    public JSONObject parsearCredencialAdministrador() {
        String jsonAdmin = leerContenidosJSON("src/main/datos/credencialesAdmin.json");
        JSONParser parser = new JSONParser();
        try {
            Object credencialAdmin = parser.parse(jsonAdmin);
            return (JSONObject) credencialAdmin;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean esCredencialAdminValida(String clave) {
        JSONObject credencialAdmin = parsearCredencialAdministrador();
        return clave.equals(String.valueOf(credencialAdmin.get("clave")));
    }

    public JSONArray parsearVotantes() {
        String jsonVotantes = leerContenidosJSON("src/main/datos/votantes.json");
        JSONParser parser = new JSONParser();
        try {
            Object arrayVotantes = parser.parse(jsonVotantes);
            return (JSONArray) arrayVotantes;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean esCredencialVotanteValida(String rut, String clave) {
        JSONArray arrayVotantes = parsearVotantes();
        for (Object arrayVotante : arrayVotantes) {
            JSONObject nextVotante = (JSONObject) arrayVotante;
            if (nextVotante.get("rut").equals(rut) && nextVotante.get("clave").equals(clave)) {
                return true;
            }
        }
        return false;
    }

    public int pedirValorEntero() throws InputMismatchException {
        return new Scanner(System.in).nextInt();
    }

    public int pedirOpcion() {
        try {
            return pedirValorEntero();
        } catch (InputMismatchException e) {
            mostrarOpcionInvalida();
            return pedirOpcion();
        }
    }

    public String pedirString(){
        return new Scanner(System.in).nextLine();
    }
}
