import java.time.format.DateTimeFormatter;
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
                    .pedirEntrada("Ingrese la contraseña del administrador \n> ");
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
            String rutVotante = ValidadorDeDatos.pedirEntrada("Ingrese su rut\n> ");
            String claveVotante = ValidadorDeDatos.pedirEntrada("Ingrese su clave\n> ");
            if (ValidadorDeDatos.esCredencialVotanteValida(rutVotante, claveVotante)) {
                mostrarMenuVotacionesVotante(AccesoADatos.obtenerVotantePorRut(rutVotante));
            } else {
                System.out.println("RUT o contraseña incorrectos");
            }
        } catch (AccesoADatosInterrumpidoException e) {
            mostrarSistemaNoDisponible(e.getMessage());
        }
    }

    private void mostrarMenuVotacionesVotante(Votante votante) {
        while (true) {
            mostrarVotacionesEnCurso();
            mostrarOpcionesMenuVotacionesVotante();
            switch (ValidadorDeDatos.pedirOpcionHasta(2)) {
                case 0 -> {return;}
                case 1 -> mostrarMenuParaVotar(votante);
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
                .obtenerVotacionesConEstado(Estado.EN_CURSO));
    }

    private void mostrarVotacionesFinalizadas() {
        System.out.println("Votaciones Finalizadas");
        mostrarTitulosVotaciones(AccesoADatos
                .obtenerVotacionesConEstado(Estado.FINALIZADO));
    }

    private void mostrarVotacionesBorrador() {
        System.out.println("Votaciones Borrador");
        mostrarTitulosVotaciones(AccesoADatos
                .obtenerVotacionesConEstado(Estado.BORRADOR));
    }

    private void mostrarVotacionesPendientes() {
        System.out.println("Votaciones Pendientes");
        mostrarTitulosVotaciones(AccesoADatos
                .obtenerVotacionesConEstado(Estado.PENDIENTE));
    }

    private void mostrarTitulosVotaciones(List<Votacion> votaciones) {
        for (Votacion votacion : votaciones) {
            System.out.printf("\"%s\"%n", votacion.getTitulo());
        }
    }

    private void mostrarMenuResultados() {
        while (true) {
            List<Votacion> votacionesFinalizadas = AccesoADatos
                    .obtenerVotacionesConEstado(Estado.FINALIZADO);
            mostrarOpcionesMenuResultados(votacionesFinalizadas);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(votacionesFinalizadas.size());
            if (opcionElegida == 0) break;
            mostrarResultadosVotacion(votacionesFinalizadas.get(opcionElegida - 1));
        }
    }

    private void mostrarOpcionesMenuResultados(List<Votacion> votacionesFinalizadas) {
        System.out.println("Votaciones finalizadas disponibles para su revisión");
        List<String> titulos = new ArrayList<>();
        for (var votacionSiguiente : votacionesFinalizadas) {
            titulos.add(votacionSiguiente.getTitulo());
        }
        mostrarListaDeCampos(titulos);
    }

    private void mostrarMenuParaVotar(Votante votante) {
        while (true) {
            List<Votacion> votacionesDisponibles = AccesoADatos
                    .obtenerVotacionesEnElQuePuedeVotarElVotante(votante);
            mostrarVotacionesDisponiblesParaVotacion(votacionesDisponibles);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(votacionesDisponibles.size());
            if (opcionElegida == 0) break;
            mostrarMenuOpcionesParaVotar(
                    votacionesDisponibles.get(opcionElegida - 1), votante);
        }
    }

    private void mostrarVotacionesDisponiblesParaVotacion(List<Votacion> votaciones) {
        System.out.println("Votaciones disponibles para votación");
        List<String> titulos = new ArrayList<>();
        for (var votacionSiguiente : votaciones) {
            titulos.add(votacionSiguiente.getTitulo());
        }
        mostrarListaDeCampos(titulos);
    }

    private void mostrarMenuOpcionesParaVotar(Votacion votacion, Votante votante) {
        List<Opcion> opciones = votacion.getOpciones();
        agregarVotoBlanco(opciones);
        mostrarOpcionesMenuOpcionesParaVotar(opciones);
        int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(opciones.size());
        if (opcionElegida == 0) return;
        if (opcionElegida == 1) {
            AccesoADatos.realizarVotoBlanco(votacion, votante);
        } else {
            AccesoADatos.realizarVotoPreferencial(votacion, votante, opciones.get(opcionElegida - 1));
        }
        mostrarVotoRealizadoConExito();
    }

    private void agregarVotoBlanco(List<Opcion> opciones) {
        Opcion votoBlanco = new Opcion(TipoDeVoto.VOTO_BLANCO);
        opciones.add(0, votoBlanco);
    }

    private void mostrarOpcionesMenuOpcionesParaVotar(List<Opcion> opciones) {
        System.out.println("Opciones disponibles");
        mostrarListaOpciones(opciones);
    }

    private void mostrarVotoRealizadoConExito() {
        System.out.println("¡Voto realizado con exito!\n");
    }

    public void mostrarListaOpciones(List<Opcion> opciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < opciones.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, opciones.get(indice).getNombre());
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    private void mostrarListaDeCampos(List<String> campos) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < campos.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, campos.get(indice));
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    private void mostrarResultadosVotacion(Votacion votacion) {
        mostrarResultadosDatos(votacion);
        mostrarResultadosVotosPorOpciones(votacion);
    }

    public void mostrarResultadosDatos(Votacion votacion) {
        var titulo = votacion.getTitulo();
        var votosBlancos = votacion.getVotosBlancos();
        var votosPreferenciales = votacion.getVotosPreferenciales();
        int totalVotos = votosPreferenciales + votosBlancos;
        var fechaTiempoInicio = votacion.getFechaTiempoInicio()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm"));
        var fechaTiempoTermino = votacion.getFechaTiempoTermino()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm"));
        System.out.printf("""
                        Resultados para la votacion "%s"
                        Votos preferenciales %s %s
                        Votos blancos %s %s
                        Total votos %s %s
                        Fecha y hora de inicio %s %s hrs
                        Fecha y hora de término %s %s hrs
                        %n""", titulo,
                Utilidades.padTexto("", 30 - 20), votosPreferenciales,
                Utilidades.padTexto("", 30 - 13), votosBlancos,
                Utilidades.padTexto("", 30 - 11), totalVotos,
                Utilidades.padTexto("", 30 - 22), fechaTiempoInicio,
                Utilidades.padTexto("", 30 - 23), fechaTiempoTermino);
    }

    private void mostrarPanelDeControlDeVotaciones() {
        while (true) {
            List<Votacion> votaciones = AccesoADatos.obtenerVotaciones();
            mostrarOpcionesPanelDeControlDeVotaciones(votaciones);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(votaciones.size());
            if (opcionElegida == 0) break;
            mostrarEditorDeVotacion(votaciones.get(opcionElegida - 1));
        }
    }

    private void mostrarOpcionesPanelDeControlDeVotaciones(List<Votacion> votaciones) {
        System.out.print("""
                Para modificar o eliminar una votación
                escriba el número correspondiente a su índice
                """);
        mostrarListaVotacionesDetallada(votaciones);
    }

    private void mostrarListaVotacionesDetallada(List<Votacion> votaciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < votaciones.size(); indice++) {
            mostrarDetalleVotacion(votaciones.get(indice), indice + 1);
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    private void mostrarDetalleVotacion(Votacion votacion, int indiceAjustado) {
        String titulo = votacion.getTitulo();
        Estado estado = votacion.getEstado();
        System.out.printf("[%s] %s %s ", indiceAjustado,
                Utilidades.padTexto(titulo, ".", 60), estado);
        Map<Estado, Runnable> estadosImpresion = new HashMap<>();
        estadosImpresion.put(Estado.EN_CURSO,  () -> System.out.printf("%s Terminará el %s a las %s hrs%n",
                Utilidades.padTexto("", 15 - estado.length()),
                votacion.getFechaTermino(), votacion.getTiempoTermino()));
        estadosImpresion.put(Estado.FINALIZADO,() -> System.out.printf("%s Finalizó el %s a las %s hrs%n",
                Utilidades.padTexto("", 15 - estado.length()),
                votacion.getFechaTermino(), votacion.getTiempoTermino()));
        estadosImpresion.put(Estado.PENDIENTE, () -> System.out.printf("%s Iniciará el %s a las %s hrs%n",
                Utilidades.padTexto("", 15 - estado.length()),
                votacion.getFechaInicio(), votacion.getTiempoInicio()));
        estadosImpresion.put(Estado.BORRADOR,  () -> System.out.printf("%n"));
        estadosImpresion.get(estado).run();
    }

    private void mostrarEditorDeVotacion(Votacion votacion) {
//      FIXME A futuro se podría usar el siguiente formato para las opciones / menús
//        LinkedHashMap<String, Runnable> opciones = new LinkedHashMap<>();
//        opciones.put("Modificar algún campo",       () -> mostrarMenuEditarCamposDeVotacion(IDVotacion));
//        opciones.put("Para agregar una opción",     () -> agregarOpcionDeVotacion(IDVotacion));
//        opciones.put("Para eliminar alguna opción", () -> mostrarMenuEliminarOpcionesDeVotacion(IDVotacion));
//        opciones.put("Para eliminar la votación",   () -> AccesoADatos.eliminarVotacion(IDVotacion));
//        LinkedHashSet<String> set = new LinkedHashSet(opciones.values());
//        int opcion = ValidadorDeDatos.pedirOpcionHasta(set.size());
        while (true) {
            mostrarOpcionesEditorDeVotacion(votacion);
            switch (ValidadorDeDatos.pedirOpcionHasta(4)) {
                case 0 -> {return;}
                case 1 -> mostrarMenuEditarCamposDeVotacion(votacion);
                case 2 -> agregarOpcionDeVotacion(votacion);
                case 3 -> mostrarMenuEliminarOpcionesDeVotacion(votacion);
                case 4 -> {AccesoADatos.eliminarVotacion(votacion); return;}
            }
        }
    }

    private void mostrarOpcionesEditorDeVotacion(Votacion votacion) {
        mostrarDatosDeVotacion(votacion);
        System.out.print("""
                
                [1] Modificar algún campo
                [2] Para agregar una opción
                [3] Para eliminar alguna opción
                [4] Para eliminar la votación
                Si desea volver escriba [0]
                """.concat("> "));
    }

    private void mostrarMenuEditarCamposDeVotacion(Votacion votacion) {
        List<CampoDeVotacion> campos =
                new ArrayList<>(CampoDeVotacion.getCamposDeVotacionEditablesPorAdministrador());
        while (true) {
            mostrarOpcionesMenuEditarCamposDeVotacion(campos);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(campos.size());
            if (opcionElegida == 0) break;
            editarCampoDeVotacion(votacion, campos.get(opcionElegida - 1));
        }
    }

    private void mostrarOpcionesMenuEditarCamposDeVotacion(List<CampoDeVotacion> campos) {
        System.out.print("""
                
                Escriba el índice del campo que desea modificar
                """);
        List<String> camposTexto = new ArrayList<>();
        for (CampoDeVotacion c : campos) camposTexto.add(c.getTexto());
        mostrarListaDeCampos(camposTexto);
    }

    private void editarCampoDeVotacion(Votacion votacion, CampoDeVotacion campo) {
        Object texto = ValidadorDeDatos.pedirEntrada(campo.getTexto().concat("> "));
        AccesoADatos.actualizarCampoDeVotacion(votacion, campo, texto);
    }

    private void mostrarMenuEliminarOpcionesDeVotacion(Votacion votacion) {
        List<Opcion> opciones = votacion.getOpciones();
        System.out.println("Escriba la opción que desea eliminar");
        mostrarListaOpciones(opciones);
        int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(opciones.size());
        if (opcionElegida == 0) return;
        AccesoADatos.eliminarOpcionDeVotacion(votacion, opciones.get(opcionElegida - 1));
        System.out.println("Opción eliminada con exito");
    }

    private void agregarOpcionDeVotacion(Votacion votacion) {
        String opcion = ValidadorDeDatos
                .pedirEntrada("Escriba la opción que desea agregar\n> ", 35);
        AccesoADatos.agregarOpcionAVotacion(votacion, opcion);
    }

    private void mostrarDatosDeVotacion(Votacion votacion) {
        mostrarCamposDeVotacion(votacion);
        mostrarOpcionesDeVotacion(votacion);
    }

    private void mostrarCamposDeVotacion(Votacion votacion) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("Titulo ..................... %s", votacion.getTitulo());
        map.put("Descripcion ................ %s", votacion.getDescripcion());
        map.put("Fecha de inicio ............ %s", votacion.getFechaInicio());
        map.put("Hora de inicio ............. %s hrs", votacion.getTiempoInicio());
        map.put("Fecha de término ........... %s", votacion.getFechaTermino());
        map.put("Hora de término ............ %s hrs", votacion.getTiempoTermino());
        map.put("Estado ..................... %s", votacion.getEstado().getTexto());
        for (var key : map.keySet()) {
            System.out.printf(key.concat("%n"), map.get(key));
        }
    }

    private void mostrarOpcionesDeVotacion(Votacion votacion) {
        var opciones = votacion.getOpciones();
        System.out.println("\nOpciones");
        for (var opcion : opciones) {
            System.out.println(Utilidades.padTexto("", 8).concat(opcion.getNombre()));
        }
    }

    private void mostrarMenuCreacionDeVotacion() {
        Votacion votacion = ValidadorDeDatos.pedirCamposDeVotacion();
        AccesoADatos.crearVotacion(votacion);
        System.out.println("¡Votación creada con exito!\n");
        mostrarMenuAgregacionDeOpciones(votacion);
    }

    private void mostrarMenuAgregacionDeOpciones(Votacion votacion) {
        while (true) {
            mostrarDatosDeVotacion(votacion);
            mostrarMenuMenuAgregacionDeOpciones();
            switch (ValidadorDeDatos.pedirOpcionHasta(1)) {
                case 0 -> {return;}
                case 1 -> agregarOpcionDeVotacion(votacion);
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

    public void mostrarResultadosVotosPorOpciones(Votacion votacion) {
        List<Opcion> opciones = votacion.getOpciones();
        System.out.println("Votos por opciones");
        for (var opcion : opciones) {
            System.out.println(Utilidades.padTexto(opcion.getNombre(), ".", 30)
                    .concat(opcion.getCantidadDeVotos().toString()));
        }
    }

    private void mostrarSistemaNoDisponible(String mensaje) {
        System.err.println("El sistema no se encuentra disponible por ahora, disculpe las molestias\n" +
                "Mensaje de error: " + mensaje);
    }
}
