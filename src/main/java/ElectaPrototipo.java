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

    public void ingresarComoAdmin() {
        String clave = pedirString("Ingrese la contraseña del administrador \n> ");
        if (esCredencialAdministradorValida(clave)) {
            mostrarMenuAdministador();
        } else {
            System.out.println("Contraseña incorrecta");
        }
    }

    public void mostrarMenuAdministador() {
        System.out.println("Bienvenido/a al menú de administración");
        mostrarOpcionesMenuAdministador();
        salirMenu:
        while (true) {
            switch (pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> mostrarPanelDeControlDeVotaciones();
                case 2 -> mostrarMenuCreacionDeVotacion();
                case 3 -> mostrarMenuResultados();
                default -> {mostrarOpcionInvalida(); continue;}
            }
            mostrarOpcionesMenuAdministador();
        }
    }

    public void mostrarOpcionesMenuAdministador() {
        System.out.print("""
                Elija una opción
                [1] Ver, modificar, o eliminar votaciones
                [2] Crear nueva votación
                [3] Ver resultados de votaciones
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
    }

    public void ingresarComoUsuario() {
        String rutVotante = pedirString("Ingrese su rut\n> ");
        String claveVotante = pedirString("Ingrese su clave\n> ");

        try {
            if (esCredencialVotanteValida(rutVotante, claveVotante)) {
                mostrarMenuVotacionesVotante(obtenerIDDeRut(rutVotante));
            } else {
                System.out.println("RUT o contraseña incorrectos");
            }
        } catch (RuntimeException e) {
            System.out.println("El sistema no se encuentra disponible, intente más tarde");
        }
    }

    public void mostrarMenuVotacionesVotante(String IDVotante) {
        mostrarVotacionesEnCurso();
        mostrarOpcionesMenuVotacionesVotante();
        salirMenu:
        while (true) {
            switch (pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> mostrarMenuParaVotar(IDVotante);
                case 2 -> mostrarMenuResultados();
                default -> {mostrarOpcionInvalida(); continue;}
            }
            mostrarOpcionesMenuVotacionesVotante();
        }
    }

    public void mostrarOpcionesMenuVotacionesVotante() {
        System.out.print("""
                OPCIONES
                [1] Votar
                [2] Ver Resultados
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
    }

    public void mostrarTotalVotaciones() {
        List<String> titulosVotaciones = obtenerTitulosVotaciones();
        System.out.println("Votaciones");
        for (String titulo : titulosVotaciones) {
            System.out.printf("\"%s\"%n", titulo);
        }
    }

    public void mostrarVotacionesEnCurso() {
        System.out.println("Votaciones En Curso");
        mostrarTitulosVotaciones(obtenerTitulosVotacionesConEstado("EN CURSO"));
    }

    public void mostrarVotacionesFinalizadas() {
        System.out.println("Votaciones Finalizadas");
        mostrarTitulosVotaciones(obtenerTitulosVotacionesConEstado("FINALIZADO"));
    }

    public void mostrarVotacionesBorrador() {
        System.out.println("Votaciones Borrador");
        mostrarTitulosVotaciones(obtenerTitulosVotacionesConEstado("BORRADOR"));
    }

    public void mostrarVotacionesPendientes() {
        System.out.println("Votaciones Pendientes");
        mostrarTitulosVotaciones(obtenerTitulosVotacionesConEstado("PENDIENTE"));
    }

    public void mostrarTitulosVotaciones(List<String> titulosVotaciones) {
        for (String titulo : titulosVotaciones) {
            System.out.printf("\"%s\"%n", titulo);
        }
    }

    public List<String> obtenerIDsVotacionesConEstado(String estado) {
        return obtenerCamposVotacionesConEstado(estado, "id");
    }

    public List<String> obtenerTitulosVotacionesConEstado(String estado) {
        return obtenerCamposVotacionesConEstado(estado, "titulo");
    }

    public List<String> obtenerCamposVotacionesConEstado(String estado, String campo) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> IDsVotaciones = obtenerIDsVotaciones();
        List<String> nuevoCamposVotaciones = new ArrayList<>();
        for (String IDvotacion : IDsVotaciones) {
            JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDvotacion);
            if (votacion.get("estado").equals(estado)) {
                nuevoCamposVotaciones.add(String.valueOf(votacion.get(campo)));
            }
        }
        return nuevoCamposVotaciones;
    }

    public void mostrarMenuResultados() {
        List<String> IDsVotaciones = obtenerIDsVotacionesConEstado("FINALIZADO");
        List<String> titulosVotaciones = obtenerTitulosVotaciones(IDsVotaciones);
        mostrarOpcionesMenuResultados(titulosVotaciones);
        while (true) {
            int opcionElegida = pedirOpcion();
            if (opcionElegida == 0) break;
            if (esOpcionElegidaFueraDeRango(opcionElegida, titulosVotaciones.size())) continue;
            mostrarResultadosVotacion(titulosVotaciones.get(opcionElegida - 1));
            IDsVotaciones = obtenerIDsVotacionesConEstado("FINALIZADO");
            titulosVotaciones = obtenerTitulosVotaciones(IDsVotaciones);
            mostrarOpcionesMenuResultados(titulosVotaciones);
        }
    }

    public void mostrarOpcionesMenuResultados(List<String> titulosVotaciones) {
        System.out.println("Votaciones finalizadas disponibles para su revisión");
        mostrarListaOpciones(titulosVotaciones);
    }

    public void mostrarMenuParaVotar(String IDVotante) {
        List<String> IDsVotaciones = obtenerIDsVotacionesEnElQuePuedeVotarElVotante(IDVotante);
        List<String> titulosVotaciones = obtenerTitulosVotaciones(IDsVotaciones);
        mostrarVotacionesDisponiblesParaVotacion(titulosVotaciones);
        while (true) {
            int opcionElegida = pedirOpcion();
            if (opcionElegida == 0) break;
            if (esOpcionElegidaFueraDeRango(opcionElegida, IDsVotaciones.size())) continue;
            mostrarMenuOpcionesParaVotar(IDsVotaciones.get(opcionElegida - 1), IDVotante);
            IDsVotaciones = obtenerIDsVotacionesEnElQuePuedeVotarElVotante(IDVotante);
            titulosVotaciones = obtenerTitulosVotaciones(IDsVotaciones);
            mostrarVotacionesDisponiblesParaVotacion(titulosVotaciones);
        }
    }

    public void mostrarVotacionesDisponiblesParaVotacion(List<String> titulosVotaciones) {
        System.out.println("Votaciones disponibles para votación");
        mostrarListaOpciones(titulosVotaciones);
    }

    public void mostrarMenuOpcionesParaVotar(String IDVotacion, String IDVotante) {
        List<String> opciones = obtenerOpcionesDeVotacion(IDVotacion);
        opciones.add(0, "Abstenerse");
        mostrarOpcionesMenuOpcionesParaVotar(opciones);
        while (true) {
            int opcionElegida = pedirOpcion();
            if (opcionElegida == 0) break;
            if (opcionElegida == 1) {realizarVotoBlanco(IDVotacion, IDVotante); break;}
            if (esOpcionElegidaFueraDeRango(opcionElegida, opciones.size())) continue;
            realizarVotoPreferencial(IDVotacion, IDVotante, opciones.get(opcionElegida - 1));
            break;
        }
    }

    public void mostrarOpcionesMenuOpcionesParaVotar(List<String> opciones) {
        System.out.println("Opciones disponibles");
        mostrarListaOpciones(opciones);
    }

    public boolean esOpcionElegidaFueraDeRango(int opcionElegida, int cantidadOpciones) {
        if (opcionElegida > cantidadOpciones) {
            mostrarOpcionInvalida();
            return true;
        }
        return false;
    }

    public void mostrarVotoRealizadoConExito() {
        System.out.println("¡Voto realizado con exito!\n");
    }

    public void realizarVotoBlanco(String IDVotacion, String IDVotante) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        int votosBlancosOpcion = parsearObjectAInt(votacion.get("votos_blancos"));
        votacion.put("votos_blancos", votosBlancosOpcion + 1);
        realizarVoto(jsonArrayVotaciones, votacion, IDVotante);
    }

    public void realizarVotoPreferencial(String IDVotacion, String IDVotante, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        votarOpcionPreferencial(jsonArrayVotaciones, IDVotacion, opcionElegida);
        realizarVoto(jsonArrayVotaciones, votacion, IDVotante);
    }

    public void realizarVoto(JSONArray jsonArrayVotaciones, JSONObject votacion, String IDVotante) {
        JSONArray votantes = (JSONArray) votacion.get("votantes");
        votantes.add(Integer.parseInt(IDVotante));
        escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
        mostrarVotoRealizadoConExito();
    }

    public void escribirEnVotaciones(String contenido) {
        escribirArchivoJSON("src/main/datos/votaciones.json", contenido);
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

    public void votarOpcionPreferencial(JSONArray jsonArrayVotaciones, String IDVotacion, String opcionElegida) {
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        JSONObject opciones = (JSONObject) votacion.get("opciones");
        List<String> opcionesArray = new ArrayList<>(opciones.keySet());
        for (String opcion : opcionesArray) {
            if (opcion.equals(opcionElegida)) {
                int votosOpcion = parsearObjectAInt(opciones.get(opcion));
                opciones.put(opcion, votosOpcion + 1);
                int votosPreferenciales = parsearObjectAInt(votacion.get("votos_preferenciales"));
                votacion.put("votos_preferenciales", votosPreferenciales + 1);
            }
        }
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
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        JSONObject opciones = (JSONObject) votacion.get("opciones");
        return new ArrayList<>(opciones.keySet());
    }

    public void mostrarResultadosVotacion(String tituloVotacion) {
        JSONObject votacion = obtenerVotacionPorTitulo(tituloVotacion);
        mostrarResultadosDatos(votacion);
        mostrarResultadosVotosPorOpciones(votacion);
    }

    public void mostrarResultadosDatos(JSONObject votacion) {
        String titulo = String.valueOf(votacion.get("titulo"));
        int votoBlancos = parsearObjectAInt(votacion.get("votos_blancos"));
        int votoPreferenciales = parsearObjectAInt(votacion.get("votos_preferenciales"));
        int totalVotos = votoPreferenciales + votoBlancos;
        String fechaInicio = String.valueOf(votacion.get("fecha_inicio"));
        String horaInicio = String.valueOf(votacion.get("hora_inicio"));
        String fechaTermino = String.valueOf(votacion.get("fecha_termino"));
        String horaTermino = String.valueOf(votacion.get("hora_termino"));
        System.out.printf("""
                        Resultados para la votacion "%s"
                        Votos preferenciales %s %s
                        Votos blancos %s %s
                        Total votos %s %s
                        Fecha y hora de inicio %s %s %s hrs
                        Fecha y hora de término %s %s %s hrs
                        %n""", titulo,
                padTexto("", ".", 30 - 20), votoPreferenciales,
                padTexto("", ".", 30 - 13), votoBlancos,
                padTexto("", ".", 30 - 11), totalVotos,
                padTexto("", ".", 30 - 22), fechaInicio, horaInicio,
                padTexto("", ".", 30 - 23), fechaTermino, horaTermino);
    }

    public void mostrarResultadosVotosPorOpciones(JSONObject votacion) {
        JSONObject opciones = (JSONObject) votacion.get("opciones");
        List<String> opcionesList = new ArrayList<>(opciones.keySet());
        System.out.println("Votos por opciones");
        for (String opcion : opcionesList) {
            System.out.println(padTexto(opcion, ".", 30)
                    .concat(String.valueOf(opciones.get(opcion))));
        }
    }

    public List<String> obtenerTitulosVotaciones() {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> arrayListVotaciones = new ArrayList<>();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            arrayListVotaciones.add(String.valueOf(votacionSiguiente.get("titulo")));
        }
        return arrayListVotaciones;
    }

    public List<String> obtenerTitulosVotaciones(List<String> IDsVotaciones) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> arrayListVotaciones = new ArrayList<>();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            for (String IDVotacion : IDsVotaciones) {
                if (IDVotacion.equals(String.valueOf(votacionSiguiente.get("id")))) {
                    arrayListVotaciones.add(String.valueOf(votacionSiguiente.get("titulo")));
                    break;
                }
            }
        }
        return arrayListVotaciones;
    }

    public List<String> obtenerIDsVotaciones() {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> arrayListIDsVotaciones = new ArrayList<>();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            arrayListIDsVotaciones.add(String.valueOf(votacionSiguiente.get("id")));
        }
        return arrayListIDsVotaciones;
    }

    public List<String> obtenerIDsVotacionesEnElQuePuedeVotarElVotante(String IDVotante) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> arrayListIDsVotaciones = new ArrayList<>();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            JSONArray arrayVotantes = (JSONArray) votacionSiguiente.get("votantes");
            continuaBucle:
            {
                for (Object IDVotanteQueVoto : arrayVotantes) {
                    if (IDVotante.equals(String.valueOf(IDVotanteQueVoto))) {
                        break continuaBucle;
                    }
                }
                arrayListIDsVotaciones.add(String.valueOf(votacionSiguiente.get("id")));
            }
        }
        return arrayListIDsVotaciones;
    }

    public JSONArray parsearVotaciones() {
        try {
            String jsonVotaciones = leerContenidosJSON("src/main/datos/votaciones.json");
            JSONParser parser = new JSONParser();
            Object arrayVotaciones = parser.parse(jsonVotaciones);
            return (JSONArray) arrayVotaciones;
        } catch (ParseException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void mostrarPanelDeControlDeVotaciones() {
        List<String> IDsVotaciones = obtenerIDsVotaciones();
        mostrarOpcionesPanelDeControlDeVotaciones(IDsVotaciones);
        while (true) {
            int opcionElegida = pedirOpcion();
            if (opcionElegida == 0) break;
            if (esOpcionElegidaFueraDeRango(opcionElegida, IDsVotaciones.size())) continue;
            mostrarEditorDeVotacion(IDsVotaciones.get(opcionElegida - 1));
            IDsVotaciones = obtenerIDsVotaciones();
            mostrarOpcionesPanelDeControlDeVotaciones(IDsVotaciones);
        }
    }

    public void mostrarOpcionesPanelDeControlDeVotaciones(List<String> IDsVotaciones) {
        System.out.print("""
                Para modificar o eliminar una votación
                escriba el número correspondiente a su índice
                """);
        mostrarListaOpcionesDetallada(IDsVotaciones);
    }

    public void mostrarListaOpcionesDetallada(List<String> IDsVotaciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < IDsVotaciones.size(); indice++) {
            mostrarOpcionDetallada(IDsVotaciones.get(indice), indice + 1);
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    public void mostrarOpcionDetallada(String IDVotacion, int indiceAjustado) {
        HashMap<String, Object> mapaConCampos = obtenerCamposDeVotacion(IDVotacion);
        String titulo = String.valueOf(mapaConCampos.get("titulo"));
        String estado = String.valueOf(mapaConCampos.get("estado"));
        String fechaTermino = String.valueOf(mapaConCampos.get("fecha_termino"));
        String horaTermino = String.valueOf(mapaConCampos.get("hora_termino"));
        System.out.printf("[%s] %s %s ", indiceAjustado,
                padTexto(titulo, ".", 60), estado);
        switch (estado) {
            case "EN CURSO" -> System.out.printf("%s Terminará el %s a las %s hrs%n",
                    padTexto("", ".", 15 - estado.length()),
                    fechaTermino, horaTermino);
            case "FINALIZADO" -> System.out.printf("%s Finalizo el %s a las %s hrs%n",
                    padTexto("", ".", 15 - estado.length()),
                    fechaTermino, horaTermino);
            default -> System.out.printf("%n");
        }
    }

    public String padTexto(String texto, String patronRelleno, int largo) {
        String relleno = patronRelleno.repeat(largo);
        int largoDeTexto = texto.length();
        String corte = relleno.substring(largoDeTexto);
        return texto.concat(corte);
    }

    public void mostrarEditorDeVotacion(String IDVotacion) {
        mostrarOpcionesEditorDeVotacion(IDVotacion);
        salirMenu:
        while (true) {
            switch (pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> mostrarMenuEditarCamposDeVotacion(IDVotacion);
                case 2 -> agregarOpcionDeVotacion(IDVotacion);
                case 3 -> mostrarMenuEliminarOpcionesDeVotacion(IDVotacion);
                case 4 -> {eliminarVotacion(IDVotacion); break salirMenu;}
                default -> {mostrarOpcionInvalida(); continue;}
            }
            mostrarOpcionesEditorDeVotacion(IDVotacion);
        }
    }

    public void mostrarOpcionesEditorDeVotacion(String IDVotacion) {
        mostrarCamposDeVotacion(IDVotacion);
        System.out.print("""
                [1] Modificar algún campo
                [2] Para agregar una opción
                [3] Para eliminar alguna opción
                [4] Para eliminar la votación
                Si desea volver escriba [0]
                """.concat("> "));
    }

    public void mostrarMenuEditarCamposDeVotacion(String IDVotacion) {
        List<String> campos = List.of("titulo", "descripcion","fecha_inicio",
                "hora_inicio", "fecha_termino", "hora_termino");
        mostrarOpcionesMenuEditarCamposDeVotacion(campos);
        while (true) {
            int opcionElegida = pedirOpcion();
            if (opcionElegida == 0) break;
            if (esOpcionElegidaFueraDeRango(opcionElegida, campos.size())) continue;
            editarCampoDeVotacion(IDVotacion, campos.get(opcionElegida - 1));
            mostrarOpcionesMenuEditarCamposDeVotacion(campos);
        }
    }

    public void mostrarOpcionesMenuEditarCamposDeVotacion(List<String> campos) {
        System.out.print("""
                Escriba el índice del campo que desea modificar
                """);
        mostrarListaOpciones(campos);
    }

    public void editarCampoDeVotacion(String IDVotacion, String campo) {
        String texto = pedirString(campo.concat("> "));
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        actualizarCampoDeVotacion(jsonArrayVotaciones, IDVotacion, campo, texto);
    }

    public void actualizarCampoDeVotacion(JSONArray jsonArrayVotaciones, String IDVotacion, String campo, String texto) {
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        votacion.put(campo, texto);
        escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
    }

    public void mostrarMenuEliminarOpcionesDeVotacion(String IDVotacion) {
        List<String> opciones = obtenerOpcionesDeVotacion(IDVotacion);
        System.out.println("Escriba la opción que desea eliminar");
        mostrarListaOpciones(opciones);
        while (true) {
            int opcionElegida = pedirOpcion();
            if (opcionElegida == 0) break;
            if (esOpcionElegidaFueraDeRango(opcionElegida, opciones.size())) continue;
            eliminarOpcionDeVotacion(IDVotacion, opciones.get(opcionElegida - 1));
            System.out.println("Opción eliminada con exito");
            break;
        }
    }

    public void agregarOpcionDeVotacion(String IDVotacion) {
        String opcion = pedirString("Escriba la opción que desea agregar\n> ", 35);
        agregarOpcionAVotacion(IDVotacion, opcion);
    }

    public void eliminarVotacion(String IDVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        jsonArrayVotaciones.remove(votacion);
        escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
    }

    public void eliminarOpcionDeVotacion(String IDVotacion, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        JSONObject opciones = (JSONObject) votacion.get("opciones");
        opciones.remove(opcionElegida);
        escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
    }

    public void agregarOpcionAVotacion(String IDVotacion, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        JSONObject opciones = (JSONObject) votacion.get("opciones");
        opciones.put(opcionElegida, 0);
        escribirEnVotaciones(jsonArrayVotaciones.toJSONString());
    }

    public JSONObject obtenerVotacionPorID(JSONArray jsonArrayVotaciones, String IDVotacion) {
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get("id")).equals(IDVotacion)) {
                return votacionSiguiente;
            }
        }
        throw new RuntimeException();
    }

    public JSONObject obtenerVotacionPorTitulo(String tituloVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get("titulo")).equals(tituloVotacion)) {
                return votacionSiguiente;
            }
        }
        throw new RuntimeException();
    }

    public void mostrarCamposDeVotacion(String IDVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
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
            System.out.println(padTexto("", ".", 8).concat(opcion));
        }
        System.out.println();
    }

    public void mostrarMenuCreacionDeVotacion() {
        String IDVotacion = obtenerNuevaIDVotacion();
        HashMap<String, String> mapaConCampos = pedirCamposDeVotacion();
        JSONObject votacion = generarNuevaVotacionJSONObject(mapaConCampos, IDVotacion);

        JSONArray jsonArrayVotaciones = parsearVotaciones();
        jsonArrayVotaciones.add(votacion);
        escribirEnVotaciones(jsonArrayVotaciones.toJSONString());

        System.out.println("¡Votación creada!\n");
        mostrarMenuAgregacionDeOpciones(IDVotacion);
    }

    public HashMap<String, String> pedirCamposDeVotacion() {
        HashMap<String, String> mapaConCampos = new HashMap<>();
        String titulo = pedirString("Escriba el título de la votación que desea agregar\n> ", 50);
        System.out.println("Rellene los siguientes campos");
        String descripcion = pedirString("Descripción\n> ");
        String fechaInicio = pedirString("Fecha de inicio (dd-MM-aaaa)\n> ");
        String horaInicio = pedirString("Hora de inicio (hh:mm formato 24 horas)\n> ");
        String fechaTermino = pedirString("Fecha de término (dd-MM-aaaa)\n> ");
        String horaTermino = pedirString("Hora de término (hh:mm formato 24 horas)\n> ");
        mapaConCampos.put("titulo", titulo);
        mapaConCampos.put("descripcion", descripcion);
        mapaConCampos.put("fecha_inicio", fechaInicio);
        mapaConCampos.put("hora_inicio", horaInicio);
        mapaConCampos.put("fecha_termino", fechaTermino);
        mapaConCampos.put("hora_termino", horaTermino);
        return mapaConCampos;
    }

    public HashMap<String, Object> obtenerCamposDeVotacion(String IDVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        JSONObject votacion = obtenerVotacionPorID(jsonArrayVotaciones, IDVotacion);
        HashMap<String, Object> mapaConCampos = new HashMap<>();
        mapaConCampos.put("id", votacion.get("id"));
        mapaConCampos.put("titulo", votacion.get("titulo"));
        mapaConCampos.put("descripcion", votacion.get("descripcion"));
        mapaConCampos.put("fecha_inicio", votacion.get("fecha_inicio"));
        mapaConCampos.put("hora_inicio", votacion.get("hora_inicio"));
        mapaConCampos.put("fecha_termino", votacion.get("fecha_termino"));
        mapaConCampos.put("hora_termino", votacion.get("hora_termino"));
        mapaConCampos.put("estado", votacion.get("estado"));
        mapaConCampos.put("votantes", (JSONArray) votacion.get("votantes"));
        mapaConCampos.put("opciones", (JSONObject) votacion.get("opciones"));
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
        votacion.put("votantes", new JSONArray());
        JSONObject opciones = new JSONObject();
        votacion.put("opciones", opciones);
        return votacion;
    }

    public void mostrarMenuAgregacionDeOpciones(String IDVotacion) {
        mostrarCamposDeVotacion(IDVotacion);
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
            mostrarCamposDeVotacion(IDVotacion);
        }
    }

    public String obtenerNuevaIDVotacion() {
        int maxID = 0;
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            int id = parsearObjectAInt(votacionSiguiente.get("id"));
            if (id > maxID) {
                maxID = id;
            }
        }
        maxID++;
        return String.valueOf(maxID);
    }

    public String leerContenidosJSON(String ruta) throws FileNotFoundException {
        StringBuilder st = new StringBuilder();
        try {
            File archivoJSON = new File(ruta);
            Scanner scanner = new Scanner(archivoJSON);
            while (scanner.hasNextLine()) {
                st.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            return st.toString();
        } catch (FileNotFoundException exception) {
            throw exception;
        }
    }

    public boolean esCredencialAdministradorValida(String clave) {
        JSONArray credencialArray = parsearCredencialAdmin();
        JSONObject credencialObject = (JSONObject) credencialArray.get(0);
        String claveObtenida = String.valueOf(credencialObject.get("clave"));
        return clave.equals(claveObtenida);
    }

    public JSONArray parsearVotantes() throws RuntimeException {
        return parsearArchivoJSON("src/main/datos/votantes.json");
    }

    public JSONArray parsearCredencialAdmin() throws RuntimeException {
        return parsearArchivoJSON("src/main/datos/credencialesAdmin.json");
    }

    public JSONArray parsearArchivoJSON(String ruta) throws RuntimeException {
        try {
            String contenidosJSON = leerContenidosJSON(ruta);
            JSONParser parser = new JSONParser();
            return (JSONArray) parser.parse(contenidosJSON);
        } catch (ParseException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean esCredencialVotanteValida(String rut, String clave) throws RuntimeException {
        JSONArray arrayVotantes = parsearVotantes();
        for (Object arrayVotante : arrayVotantes) {
            JSONObject votanteSiguiente = (JSONObject) arrayVotante;
            if (votanteSiguiente.get("rut").equals(rut) && votanteSiguiente.get("clave").equals(clave)) {
                return true;
            }
        }
        return false;
    }

    public String obtenerIDDeRut(String rut) {
        JSONArray arrayVotantes = parsearVotantes();
        for (Object arrayVotante : arrayVotantes) {
            JSONObject votanteSiguiente = (JSONObject) arrayVotante;
            if (votanteSiguiente.get("rut").equals(rut)) {
                return String.valueOf(votanteSiguiente.get("id"));
            }
        }
        throw new RuntimeException();
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

    public String pedirString(String texto) {
        System.out.print(texto);
        return pedirString();
    }

    public String pedirString() {
        return new Scanner(System.in).nextLine();
    }

    public String pedirString(String texto, int limite) {
        System.out.print(texto);
        return pedirString(limite);
    }

    public String pedirString(int limite) {
        String entrada = new Scanner(System.in).nextLine();
        if (entrada.length() > limite) {
            mostrarTextoInvalido();
            return pedirString();
        }
        return entrada;
    }

    public void mostrarTextoInvalido() {
        System.out.print("El tamaño del texto escrito es muy largo\n> ");
    }

    public void mostrarOpcionInvalida() {
        System.out.print("Por favor, escoja una opción válida\n> ");
    }

    public int parsearObjectAInt(Object obj) {
        return Integer.parseInt(String.valueOf(obj));
    }

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
        JSONArray jsonArrayVotaciones = parsearVotaciones();
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
                                    continue;
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
                                    continue;
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
                                    continue;
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
