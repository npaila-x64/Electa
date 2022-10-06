import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class MenuPrincipal {

    public void iniciar() {
        mostrarMenuDeIngreso();
    }

    private void mostrarOpcionesDeIngreso() {
        System.out.print("""
                
                ¡Bienvenido/a al sistema Electa!
                [1] Ingresar como Votante
                [2] Ingresar como Administrador
                Si desea salir escriba [0]
                """.concat("> "));
    }

    private void mostrarMenuDeIngreso() {
        while (true) {
            mostrarOpcionesDeIngreso();
            switch (ValidadorDeDatos.pedirOpcionHasta(2)) {
                case 0 -> {return;}
                case 1 -> ingresarComoVotante();
                case 2 -> ingresarComoAdministrador();
            }
        }
    }

    private void ingresarComoAdministrador() {
        try {
            String clave = ValidadorDeDatos
                    .pedirString("Ingrese la contraseña del administrador \n> ");
            if (ValidadorDeDatos.esCredencialAdministradorValida(clave)) {
                mostrarMenuAdministador();
            } else {
                System.out.println("Contraseña incorrecta");
            }
        } catch (AccesoADatosInterrumpidoException e) {
            mostrarSistemaNoDisponible(e.getMessage());
        }
    }

    private void mostrarMenuAdministador() {
        System.out.println("\nBienvenido/a al menú de administración");
        while (true) {
            mostrarOpcionesMenuAdministador();
            switch (ValidadorDeDatos.pedirOpcionHasta(3)) {
                case 0 -> {return;}
                case 1 -> mostrarPanelDeControlDeVotaciones();
                case 2 -> mostrarMenuCreacionDeVotacion();
                case 3 -> mostrarMenuResultados();
            }
        }
    }

    private void mostrarOpcionesMenuAdministador() {
        System.out.print("""
                
                Elija una opción
                [1] Ver, modificar, o eliminar votaciones
                [2] Crear nueva votación
                [3] Ver resultados de votaciones
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
    }

    private void ingresarComoVotante() {
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

    private void mostrarMenuVotacionesVotante(String IDVotante) {
        while (true) {
            mostrarVotacionesEnCurso();
            mostrarOpcionesMenuVotacionesVotante();
            switch (ValidadorDeDatos.pedirOpcionHasta(2)) {
                case 0 -> {return;}
                case 1 -> mostrarMenuParaVotar(IDVotante);
                case 2 -> mostrarMenuResultados();
            }
        }
    }

    private void mostrarOpcionesMenuVotacionesVotante() {
        System.out.print("""
                
                OPCIONES
                [1] Votar
                [2] Ver Resultados
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
    }

    private void mostrarTotalVotaciones() {
        List<String> titulosVotaciones = AccesoADatos.obtenerTitulosVotaciones();
        System.out.println("Votaciones");
        for (String titulo : titulosVotaciones) {
            System.out.printf("\"%s\"%n", titulo);
        }
    }

    private void mostrarVotacionesEnCurso() {
        System.out.println("Votaciones En Curso");
        mostrarTitulosVotaciones(AccesoADatos
                .obtenerTitulosVotacionesConEstado(Estado.EN_CURSO.getTexto()));
    }

    private void mostrarVotacionesFinalizadas() {
        System.out.println("Votaciones Finalizadas");
        mostrarTitulosVotaciones(AccesoADatos
                .obtenerTitulosVotacionesConEstado(Estado.FINALIZADO.getTexto()));
    }

    private void mostrarVotacionesBorrador() {
        System.out.println("Votaciones Borrador");
        mostrarTitulosVotaciones(AccesoADatos
                .obtenerTitulosVotacionesConEstado(Estado.BORRADOR.getTexto()));
    }

    private void mostrarVotacionesPendientes() {
        System.out.println("Votaciones Pendientes");
        mostrarTitulosVotaciones(AccesoADatos
                .obtenerTitulosVotacionesConEstado(Estado.PENDIENTE.getTexto()));
    }

    private void mostrarTitulosVotaciones(List<String> titulosVotaciones) {
        for (String titulo : titulosVotaciones) {
            System.out.printf("\"%s\"%n", titulo);
        }
    }

    private void mostrarMenuResultados() {
        while (true) {
            List<String> IDsVotaciones = AccesoADatos
                    .obtenerIDsVotacionesConEstado(Estado.FINALIZADO.getTexto());
            List<String> titulosVotaciones = AccesoADatos.obtenerTitulosVotaciones(IDsVotaciones);
            mostrarOpcionesMenuResultados(titulosVotaciones);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(titulosVotaciones.size());
            if (opcionElegida == 0) break;
            mostrarResultadosVotacion(titulosVotaciones.get(opcionElegida - 1));
        }
    }

    private void mostrarOpcionesMenuResultados(List<String> titulosVotaciones) {
        System.out.println("Votaciones finalizadas disponibles para su revisión");
        mostrarListaOpciones(titulosVotaciones);
    }

    private void mostrarMenuParaVotar(String IDVotante) {
        while (true) {
            List<String> IDsVotaciones = AccesoADatos
                    .obtenerIDsVotacionesEnElQuePuedeVotarElVotante(IDVotante);
            List<String> titulosVotaciones = AccesoADatos.obtenerTitulosVotaciones(IDsVotaciones);
            mostrarVotacionesDisponiblesParaVotacion(titulosVotaciones);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(IDsVotaciones.size());
            if (opcionElegida == 0) break;
            mostrarMenuOpcionesParaVotar(IDsVotaciones.get(opcionElegida - 1), IDVotante);
        }
    }

    private void mostrarVotacionesDisponiblesParaVotacion(List<String> titulosVotaciones) {
        System.out.println("Votaciones disponibles para votación");
        mostrarListaOpciones(titulosVotaciones);
    }

    private void mostrarMenuOpcionesParaVotar(String IDVotacion, String IDVotante) {
        List<String> opciones = AccesoADatos.obtenerOpcionesDeVotacion(IDVotacion);
        opciones.add(0, "Abstenerse");
        mostrarOpcionesMenuOpcionesParaVotar(opciones);
        int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(opciones.size());
        if (opcionElegida == 0) return;
        if (opcionElegida == 1) {
            AccesoADatos.realizarVotoBlanco(IDVotacion, IDVotante);
            mostrarVotoRealizadoConExito();
            return;
        }
        AccesoADatos.realizarVotoPreferencial(IDVotacion, IDVotante, opciones.get(opcionElegida - 1));
        mostrarVotoRealizadoConExito();
    }

    private void mostrarOpcionesMenuOpcionesParaVotar(List<String> opciones) {
        System.out.println("Opciones disponibles");
        mostrarListaOpciones(opciones);
    }

    private void mostrarVotoRealizadoConExito() {
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

    private void mostrarResultadosVotacion(String tituloVotacion) {
        JSONObject votacion = AccesoADatos.obtenerVotacionPorTitulo(tituloVotacion);
        mostrarResultadosDatos(votacion);
        mostrarResultadosVotosPorOpciones(votacion);
    }

    public void mostrarResultadosDatos(JSONObject votacion) {
        var titulo = votacion.get(CampoDeVotacion.TITULO.getTexto());
        int votoBlancos = Utilidades.parsearObjectAInt(
                votacion.get(CampoDeVotacion.VOTOS_BLANCOS.getTexto()));
        int votoPreferenciales = Utilidades.parsearObjectAInt(
                votacion.get(CampoDeVotacion.VOTOS_PREFERENCIALES.getTexto()));
        int totalVotos = votoPreferenciales + votoBlancos;
        var fechaInicio = votacion.get(CampoDeVotacion.FECHA_INICIO.getTexto());
        var horaInicio = votacion.get(CampoDeVotacion.HORA_INICIO.getTexto());
        var fechaTermino = votacion.get(CampoDeVotacion.FECHA_TERMINO.getTexto());
        var horaTermino = votacion.get(CampoDeVotacion.HORA_TERMINO.getTexto());
        System.out.printf("""
                        Resultados para la votacion "%s"
                        Votos preferenciales %s %s
                        Votos blancos %s %s
                        Total votos %s %s
                        Fecha y hora de inicio %s %s %s hrs
                        Fecha y hora de término %s %s %s hrs
                        %n""", titulo,
                Utilidades.padTexto("", ".", 30 - 20), votoPreferenciales,
                Utilidades.padTexto("", ".", 30 - 13), votoBlancos,
                Utilidades.padTexto("", ".", 30 - 11), totalVotos,
                Utilidades.padTexto("", ".", 30 - 22), fechaInicio, horaInicio,
                Utilidades.padTexto("", ".", 30 - 23), fechaTermino, horaTermino);
    }

    private void mostrarPanelDeControlDeVotaciones() {
        while (true) {
            List<String> IDsVotaciones = AccesoADatos.obtenerIDsVotaciones();
            mostrarOpcionesPanelDeControlDeVotaciones(IDsVotaciones);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(IDsVotaciones.size());
            if (opcionElegida == 0) break;
            mostrarEditorDeVotacion(IDsVotaciones.get(opcionElegida - 1));
        }
    }

    private void mostrarOpcionesPanelDeControlDeVotaciones(List<String> IDsVotaciones) {
        System.out.print("""
                Para modificar o eliminar una votación
                escriba el número correspondiente a su índice
                """);
        mostrarListaOpcionesDetallada(IDsVotaciones);
    }

    private void mostrarListaOpcionesDetallada(List<String> IDsVotaciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < IDsVotaciones.size(); indice++) {
            mostrarOpcionDetallada(IDsVotaciones.get(indice), indice + 1);
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    private void mostrarOpcionDetallada(String IDVotacion, int indiceAjustado) {
        HashMap<String, Object> mapaConCampos = obtenerCamposDeVotacion(IDVotacion);
        var titulo = mapaConCampos.get(CampoDeVotacion.TITULO.getTexto());
        Estado estado = Estado.fromObject(mapaConCampos.get(CampoDeVotacion.ESTADO.getTexto()));
        var fechaTermino = mapaConCampos.get(CampoDeVotacion.FECHA_TERMINO.getTexto());
        var horaTermino = mapaConCampos.get(CampoDeVotacion.HORA_TERMINO.getTexto());
        var fechaInicio = mapaConCampos.get(CampoDeVotacion.FECHA_INICIO.getTexto());
        var horaInicio = mapaConCampos.get(CampoDeVotacion.HORA_INICIO.getTexto());
        System.out.printf("[%s] %s %s ", indiceAjustado,
                Utilidades.padTexto(titulo.toString(), ".", 60), estado);
        switch (estado) {
            case EN_CURSO -> System.out.printf("%s Terminará el %s a las %s hrs%n",
                    Utilidades.padTexto("", ".", 15 - estado.toString().length()),
                    fechaTermino, horaTermino);
            case FINALIZADO -> System.out.printf("%s Finalizó el %s a las %s hrs%n",
                    Utilidades.padTexto("", ".", 15 - estado.toString().length()),
                    fechaTermino, horaTermino);
            case PENDIENTE -> System.out.printf("%s Iniciará el %s a las %s hrs%n",
                    Utilidades.padTexto("", ".", 15 - estado.toString().length()),
                    fechaInicio, horaInicio);
            default -> System.out.printf("%n");
        }
    }

    private void mostrarEditorDeVotacion(String IDVotacion) {
//  FIXME A futuro se podría usar el siguiente formato para las opciones / menús
//        LinkedHashMap<String, Runnable> opciones = new LinkedHashMap<>();
//        opciones.put("Modificar algún campo",       () -> mostrarMenuEditarCamposDeVotacion(IDVotacion));
//        opciones.put("Para agregar una opción",     () -> agregarOpcionDeVotacion(IDVotacion));
//        opciones.put("Para eliminar alguna opción", () -> mostrarMenuEliminarOpcionesDeVotacion(IDVotacion));
//        opciones.put("Para eliminar la votación",   () -> AccesoADatos.eliminarVotacion(IDVotacion));
//        LinkedHashSet<String> set = new LinkedHashSet(opciones.values());
//        int opcion = ValidadorDeDatos.pedirOpcionHasta(set.size());
        while (true) {
            mostrarOpcionesEditorDeVotacion(IDVotacion);
            switch (ValidadorDeDatos.pedirOpcionHasta(4)) {
                case 0 -> {return;}
                case 1 -> mostrarMenuEditarCamposDeVotacion(IDVotacion);
                case 2 -> agregarOpcionDeVotacion(IDVotacion);
                case 3 -> mostrarMenuEliminarOpcionesDeVotacion(IDVotacion);
                case 4 -> {AccesoADatos.eliminarVotacion(IDVotacion); return;}
            }
        }
    }

    private void mostrarOpcionesEditorDeVotacion(String IDVotacion) {
        mostrarDatosDeVotacion(IDVotacion);
        System.out.print("""
                
                [1] Modificar algún campo
                [2] Para agregar una opción
                [3] Para eliminar alguna opción
                [4] Para eliminar la votación
                Si desea volver escriba [0]
                """.concat("> "));
    }

    private void mostrarMenuEditarCamposDeVotacion(String IDVotacion) {
        List<String> campos = new ArrayList<>();
        for (var campo : CampoDeVotacion.getCamposDeVotacionEditablesPorAdministrador()) {
            campos.add(campo.getTexto());
        }
        while (true) {
            mostrarOpcionesMenuEditarCamposDeVotacion(campos);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(campos.size());
            if (opcionElegida == 0) break;
            editarCampoDeVotacion(IDVotacion, campos.get(opcionElegida - 1));
        }
    }

    private void mostrarOpcionesMenuEditarCamposDeVotacion(List<String> campos) {
        System.out.print("""
                
                Escriba el índice del campo que desea modificar
                """);
        mostrarListaOpciones(campos);
    }

    private void editarCampoDeVotacion(String IDVotacion, String campo) {
        String texto = ValidadorDeDatos.pedirString(campo.concat("> "));
        AccesoADatos.actualizarCampoDeVotacion(IDVotacion, campo, texto);
    }

    private void mostrarMenuEliminarOpcionesDeVotacion(String IDVotacion) {
        List<String> opciones = AccesoADatos.obtenerOpcionesDeVotacion(IDVotacion);
        System.out.println("Escriba la opción que desea eliminar");
        mostrarListaOpciones(opciones);
        int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(opciones.size());
        if (opcionElegida == 0) return;
        AccesoADatos.eliminarOpcionDeVotacion(IDVotacion, opciones.get(opcionElegida - 1));
        System.out.println("Opción eliminada con exito");
    }

    private void agregarOpcionDeVotacion(String IDVotacion) {
        String opcion = ValidadorDeDatos
                .pedirString("Escriba la opción que desea agregar\n> ", 35);
        AccesoADatos.agregarOpcionAVotacion(IDVotacion, opcion);
    }

    private void mostrarDatosDeVotacion(String IDVotacion) {
        JSONObject votacion = AccesoADatos.obtenerVotacionPorID(IDVotacion);
        mostrarCamposDeVotacion(votacion);
        mostrarOpcionesDeVotacion(votacion);
    }

    private void mostrarCamposDeVotacion(JSONObject votacion) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("Titulo ..................... %s", votacion.get(CampoDeVotacion.TITULO.getTexto()));
        map.put("Descripcion ................ %s", votacion.get(CampoDeVotacion.DESCRIPCION.getTexto()));
        map.put("Fecha de inicio ............ %s", votacion.get(CampoDeVotacion.FECHA_INICIO.getTexto()));
        map.put("Hora de inicio ............. %s hrs", votacion.get(CampoDeVotacion.HORA_INICIO.getTexto()));
        map.put("Fecha de término ........... %s", votacion.get(CampoDeVotacion.FECHA_TERMINO.getTexto()));
        map.put("Hora de término ............ %s hrs", votacion.get(CampoDeVotacion.HORA_TERMINO.getTexto()));
        map.put("Estado ..................... %s", votacion.get(CampoDeVotacion.ESTADO.getTexto()));
        for (var key : map.keySet()) {
            System.out.printf(key.concat("%n"), map.get(key));
        }
    }

    private void mostrarOpcionesDeVotacion(JSONObject votacion) {
        var opciones = (JSONObject) votacion.get(CampoDeVotacion.OPCIONES.getTexto());
        List<String> opcionesList =  new ArrayList<>(opciones.keySet());
        System.out.println("\nOpciones");
        for (String opcion : opcionesList) {
            System.out.println(Utilidades.padTexto("", ".", 8).concat(opcion));
        }
    }

    private void mostrarMenuCreacionDeVotacion() {
        String IDVotacion = AccesoADatos.obtenerNuevaIDVotacion();
        HashMap<String, String> mapaConCampos = ValidadorDeDatos.pedirCamposDeVotacion();
        JSONObject votacion = generarNuevaVotacionJSONObject(mapaConCampos, IDVotacion);

        JSONArray jsonArrayVotaciones = AccesoADatos.parsearVotaciones();
        jsonArrayVotaciones.add(votacion);
        AccesoADatos.escribirEnVotaciones(jsonArrayVotaciones.toJSONString());

        System.out.println("¡Votación creada con exito!\n");
        mostrarMenuAgregacionDeOpciones(IDVotacion);
    }

    private HashMap<String, Object> obtenerCamposDeVotacion(String IDVotacion) {
        JSONObject votacion = AccesoADatos.obtenerVotacionPorID(IDVotacion);
        HashMap<String, Object> mapaConCampos = new HashMap<>();
        for (var campo : CampoDeVotacion.getCamposDeVotacion()) {
            mapaConCampos.put(campo.getTexto(), votacion.get(campo.getTexto()));
        }
        return mapaConCampos;
    }

    private JSONObject generarNuevaVotacionJSONObject(HashMap<String, String> mapaConCampos, String IDVotacion) {
        JSONObject votacion = new JSONObject();
        for (var CAMPO : CampoDeVotacion.getCamposDeVotacion()) {
            votacion.put(CAMPO.getTexto(), mapaConCampos.get(CAMPO.getTexto()));
        }
        votacion.put(CampoDeVotacion.ID.getTexto(), IDVotacion);
        votacion.put(CampoDeVotacion.ESTADO.getTexto(), Estado.PENDIENTE.getTexto());
        votacion.put(CampoDeVotacion.VOTOS_PREFERENCIALES.getTexto(), 0);
        votacion.put(CampoDeVotacion.VOTOS_BLANCOS.getTexto(), 0);
        votacion.put(CampoDeVotacion.VOTANTES.getTexto(), new JSONArray());
        votacion.put(CampoDeVotacion.OPCIONES.getTexto(), new JSONObject());
        return votacion;
    }

    private void mostrarMenuAgregacionDeOpciones(String IDVotacion) {
        while (true) {
            mostrarDatosDeVotacion(IDVotacion);
            mostrarMenuMenuAgregacionDeOpciones();
            switch (ValidadorDeDatos.pedirOpcionHasta(1)) {
                case 0 -> {return;}
                case 1 -> agregarOpcionDeVotacion(IDVotacion);
            }
        }
    }

    private void mostrarMenuMenuAgregacionDeOpciones() {
        System.out.print("""
                    
                    Para agregar una opción escriba [1]
                    Para finalizar y volver escriba [0]
                    Elija una opción
                    """.concat("> "));
    }

    public void mostrarResultadosVotosPorOpciones(JSONObject votacion) {
        JSONObject opciones = (JSONObject) votacion.get(CampoDeVotacion.OPCIONES.getTexto());
        List<String> opcionesList = new ArrayList<>(opciones.keySet());
        System.out.println("Votos por opciones");
        for (String opcion : opcionesList) {
            System.out.println(Utilidades.padTexto(opcion, ".", 30)
                    .concat(String.valueOf(opciones.get(opcion))));
        }
    }

    private void mostrarSistemaNoDisponible(String mensaje) {
        System.err.println("El sistema no se encuentra disponible por ahora, disculpe las molestias\n" +
                "Mensaje de error: " + mensaje);
    }
}
