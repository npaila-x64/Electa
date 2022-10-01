import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class MenuPrincipal {

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
            switch (ValidadorDeDatos.pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> ingresarComoVotante();
                case 2 -> ingresarComoAdministrador();
                default -> {ValidadorDeDatos.mostrarOpcionInvalida();continue;}
            }
            mostrarOpcionesDeIngreso();
        }
    }

    public void ingresarComoAdministrador() {
        try {
            String clave = ValidadorDeDatos.pedirString("Ingrese la contraseña del administrador \n> ");
            if (ValidadorDeDatos.esCredencialAdministradorValida(clave)) {
                mostrarMenuAdministador();
            } else {
                System.out.println("Contraseña incorrecta");
            }
        } catch (AccesoADatosInterrumpidoException e) {
            mostrarSistemaNoDisponible(e.getMessage());
        }
    }

    public void mostrarMenuAdministador() {
        System.out.println("Bienvenido/a al menú de administración");
        mostrarOpcionesMenuAdministador();
        salirMenu:
        while (true) {
            switch (ValidadorDeDatos.pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> mostrarPanelDeControlDeVotaciones();
                case 2 -> mostrarMenuCreacionDeVotacion();
                case 3 -> mostrarMenuResultados();
                default -> {
                    ValidadorDeDatos.mostrarOpcionInvalida(); continue;}
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

    public void ingresarComoVotante() {
        try {
            String rutVotante = ValidadorDeDatos.pedirString("Ingrese su rut\n> ");
            String claveVotante = ValidadorDeDatos.pedirString("Ingrese su clave\n> ");
            if (ValidadorDeDatos.esCredencialVotanteValida(rutVotante, claveVotante)) {
                mostrarMenuVotacionesVotante(AccesoADatos.obtenerIDDeRut(rutVotante));
            } else {
                System.out.println("RUT o contraseña incorrectos");
            }
        } catch (AccesoADatosInterrumpidoException e) {
            mostrarSistemaNoDisponible(e.getMessage());
        }
    }

    public void mostrarMenuVotacionesVotante(String IDVotante) {
        mostrarVotacionesEnCurso();
        mostrarOpcionesMenuVotacionesVotante();
        salirMenu:
        while (true) {
            switch (ValidadorDeDatos.pedirOpcion()) {
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
        List<String> titulosVotaciones = AccesoADatos.obtenerTitulosVotaciones();
        System.out.println("Votaciones");
        for (String titulo : titulosVotaciones) {
            System.out.printf("\"%s\"%n", titulo);
        }
    }

    public void mostrarVotacionesEnCurso() {
        System.out.println("Votaciones En Curso");
        mostrarTitulosVotaciones(AccesoADatos.obtenerTitulosVotacionesConEstado("EN CURSO"));
    }

    public void mostrarVotacionesFinalizadas() {
        System.out.println("Votaciones Finalizadas");
        mostrarTitulosVotaciones(AccesoADatos.obtenerTitulosVotacionesConEstado("FINALIZADO"));
    }

    public void mostrarVotacionesBorrador() {
        System.out.println("Votaciones Borrador");
        mostrarTitulosVotaciones(AccesoADatos.obtenerTitulosVotacionesConEstado("BORRADOR"));
    }

    public void mostrarVotacionesPendientes() {
        System.out.println("Votaciones Pendientes");
        mostrarTitulosVotaciones(AccesoADatos.obtenerTitulosVotacionesConEstado("PENDIENTE"));
    }

    public void mostrarTitulosVotaciones(List<String> titulosVotaciones) {
        for (String titulo : titulosVotaciones) {
            System.out.printf("\"%s\"%n", titulo);
        }
    }

    public void mostrarMenuResultados() {
        List<String> IDsVotaciones = AccesoADatos.obtenerIDsVotacionesConEstado("FINALIZADO");
        List<String> titulosVotaciones = AccesoADatos.obtenerTitulosVotaciones(IDsVotaciones);
        mostrarOpcionesMenuResultados(titulosVotaciones);
        while (true) {
            int opcionElegida = ValidadorDeDatos.pedirOpcion();
            if (opcionElegida == 0) break;
            if (esOpcionElegidaFueraDeRango(opcionElegida, titulosVotaciones.size())) continue;
            mostrarResultadosVotacion(titulosVotaciones.get(opcionElegida - 1));
            IDsVotaciones = AccesoADatos.obtenerIDsVotacionesConEstado("FINALIZADO");
            titulosVotaciones = AccesoADatos.obtenerTitulosVotaciones(IDsVotaciones);
            mostrarOpcionesMenuResultados(titulosVotaciones);
        }
    }

    public void mostrarOpcionesMenuResultados(List<String> titulosVotaciones) {
        System.out.println("Votaciones finalizadas disponibles para su revisión");
        mostrarListaOpciones(titulosVotaciones);
    }

    public void mostrarMenuParaVotar(String IDVotante) {
        List<String> IDsVotaciones = AccesoADatos.obtenerIDsVotacionesEnElQuePuedeVotarElVotante(IDVotante);
        List<String> titulosVotaciones = AccesoADatos.obtenerTitulosVotaciones(IDsVotaciones);
        mostrarVotacionesDisponiblesParaVotacion(titulosVotaciones);
        while (true) {
            int opcionElegida = ValidadorDeDatos.pedirOpcion();
            if (opcionElegida == 0) break;
            if (esOpcionElegidaFueraDeRango(opcionElegida, IDsVotaciones.size())) continue;
            mostrarMenuOpcionesParaVotar(IDsVotaciones.get(opcionElegida - 1), IDVotante);
            IDsVotaciones = AccesoADatos.obtenerIDsVotacionesEnElQuePuedeVotarElVotante(IDVotante);
            titulosVotaciones = AccesoADatos.obtenerTitulosVotaciones(IDsVotaciones);
            mostrarVotacionesDisponiblesParaVotacion(titulosVotaciones);
        }
    }

    public void mostrarVotacionesDisponiblesParaVotacion(List<String> titulosVotaciones) {
        System.out.println("Votaciones disponibles para votación");
        mostrarListaOpciones(titulosVotaciones);
    }

    public void mostrarMenuOpcionesParaVotar(String IDVotacion, String IDVotante) {
        List<String> opciones = AccesoADatos.obtenerOpcionesDeVotacion(IDVotacion);
        opciones.add(0, "Abstenerse");
        mostrarOpcionesMenuOpcionesParaVotar(opciones);
        while (true) {
            int opcionElegida = ValidadorDeDatos.pedirOpcion();
            if (opcionElegida == 0) break;
            if (opcionElegida == 1) {
                AccesoADatos.realizarVotoBlanco(IDVotacion, IDVotante);
                mostrarVotoRealizadoConExito();
                break;}
            if (esOpcionElegidaFueraDeRango(opcionElegida, opciones.size())) continue;
            AccesoADatos.realizarVotoPreferencial(IDVotacion, IDVotante, opciones.get(opcionElegida - 1));
            mostrarVotoRealizadoConExito();
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

    public void mostrarListaOpciones(List<String> opciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < opciones.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, opciones.get(indice));
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    public void mostrarResultadosVotacion(String tituloVotacion) {
        JSONObject votacion = AccesoADatos.obtenerVotacionPorTitulo(tituloVotacion);
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

    public void mostrarPanelDeControlDeVotaciones() {
        List<String> IDsVotaciones = AccesoADatos.obtenerIDsVotaciones();
        mostrarOpcionesPanelDeControlDeVotaciones(IDsVotaciones);
        while (true) {
            int opcionElegida = ValidadorDeDatos.pedirOpcion();
            if (opcionElegida == 0) break;
            if (esOpcionElegidaFueraDeRango(opcionElegida, IDsVotaciones.size())) continue;
            mostrarEditorDeVotacion(IDsVotaciones.get(opcionElegida - 1));
            IDsVotaciones = AccesoADatos.obtenerIDsVotaciones();
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
            switch (ValidadorDeDatos.pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> mostrarMenuEditarCamposDeVotacion(IDVotacion);
                case 2 -> agregarOpcionDeVotacion(IDVotacion);
                case 3 -> mostrarMenuEliminarOpcionesDeVotacion(IDVotacion);
                case 4 -> {AccesoADatos.eliminarVotacion(IDVotacion); break salirMenu;}
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
            int opcionElegida = ValidadorDeDatos.pedirOpcion();
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
        String texto = ValidadorDeDatos.pedirString(campo.concat("> "));
        AccesoADatos.actualizarCampoDeVotacion(IDVotacion, campo, texto);
    }

    public void mostrarMenuEliminarOpcionesDeVotacion(String IDVotacion) {
        List<String> opciones = AccesoADatos.obtenerOpcionesDeVotacion(IDVotacion);
        System.out.println("Escriba la opción que desea eliminar");
        mostrarListaOpciones(opciones);
        while (true) {
            int opcionElegida = ValidadorDeDatos.pedirOpcion();
            if (opcionElegida == 0) break;
            if (esOpcionElegidaFueraDeRango(opcionElegida, opciones.size())) continue;
            AccesoADatos.eliminarOpcionDeVotacion(IDVotacion, opciones.get(opcionElegida - 1));
            System.out.println("Opción eliminada con exito");
            break;
        }
    }

    public void agregarOpcionDeVotacion(String IDVotacion) {
        String opcion = ValidadorDeDatos.pedirString("Escriba la opción que desea agregar\n> ", 35);
        AccesoADatos.agregarOpcionAVotacion(IDVotacion, opcion);
    }

    public void mostrarCamposDeVotacion(String IDVotacion) {
        JSONObject votacion = AccesoADatos.obtenerVotacionPorID(IDVotacion);
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
        String IDVotacion = AccesoADatos.obtenerNuevaIDVotacion();
        HashMap<String, String> mapaConCampos = ValidadorDeDatos.pedirCamposDeVotacion();
        JSONObject votacion = generarNuevaVotacionJSONObject(mapaConCampos, IDVotacion);

        JSONArray jsonArrayVotaciones = AccesoADatos.parsearVotaciones();
        jsonArrayVotaciones.add(votacion);
        AccesoADatos.escribirEnVotaciones(jsonArrayVotaciones.toJSONString());

        System.out.println("¡Votación creada!\n");
        mostrarMenuAgregacionDeOpciones(IDVotacion);
    }

    public HashMap<String, Object> obtenerCamposDeVotacion(String IDVotacion) {
        JSONObject votacion = AccesoADatos.obtenerVotacionPorID(IDVotacion);
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
            switch (ValidadorDeDatos.pedirOpcion()) {
                case 0 -> {break salirMenu;}
                case 1 -> agregarOpcionDeVotacion(IDVotacion);
                default -> {mostrarOpcionInvalida(); continue;}
            }
            mostrarCamposDeVotacion(IDVotacion);
        }
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

    public void mostrarSistemaNoDisponible(String mensaje) {
        System.err.println("El sistema no se encuentra disponible por ahora, disculpe las molestias\n" +
                "Mensaje de error: " + mensaje);
    }

    public static void mostrarOpcionInvalida() {
        System.out.print("Por favor, escoja una opción válida\n> ");
    }

    public int parsearObjectAInt(Object obj) {
        return Integer.parseInt(String.valueOf(obj));
    }
}
