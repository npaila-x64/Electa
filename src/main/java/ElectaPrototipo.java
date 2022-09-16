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

    private void mostrarOpcionesDeIngreso() {
        System.out.print("""
                
                ¡Bienvenido/a al sistema Electa!
                [1] Ingresar como Votante
                [2] Ingresar como Administrador
                Si desea salir escriba [0]
                """.concat("> "));
    }

    private void mostrarMenuDeIngreso() {
        salirBucle:
        while (true) {
            mostrarOpcionesDeIngreso();
            switch (pedirOpcion()) {
                case -1 -> {/*filtra valores no numéricos*/}
                case 0 -> {break salirBucle;}
                case 1 -> ingresarComoUsuario();
                case 2 -> ingresarComoAdmin();
                default -> mostrarOpcionInvalida();
            }
        }
    }

    private int pedirValorEntero() throws InputMismatchException {
        return new Scanner(System.in).nextInt();
    }

    private int pedirOpcion() {
        try {
            return pedirValorEntero();
        } catch (InputMismatchException e) {
            mostrarOpcionInvalida();
            return -1;
        }
    }

    private String pedirString(){
        return new Scanner(System.in).nextLine();
    }

    private void mostrarOpcionInvalida() {
        System.out.println("Por favor, escoja una opción válida");
    }

    private void ingresarComoAdmin() {
        System.out.print("Ingrese la contraseña del administrador \n> ");
        String claveAdmin = pedirString();
        String rutaDatosAdmin = "src/main/datosRegistro/credencialesAdmin.txt";
        if (existeDatoEnArchivo(rutaDatosAdmin, claveAdmin)) {
            mostrarMenuAdministador();
        } else {
            System.out.println("Contraseña incorrecta");
        }
    }

    private void mostrarOpcionesAdmin() {
        System.out.print("""
                [1] Ver Votacion
                [2] Crear Votacion
                [3] Terminar Votacion
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
    }

    private void mostrarMenuAdministador() {
        System.out.println("BIENVENIDO AL MENU DEL ADMINISTRADOR");
        salirBucle:
        while (true) {
            mostrarVotacionesEnCurso();
            mostrarOpcionesAdmin();
            switch (pedirOpcion()) {
                case -1 -> {/*filtra valores no numéricos*/}
                case 0 -> {break salirBucle;}
                case 1 -> verVotacion();
                case 2 -> crearVotacion();
                case 3 -> eliminarVotacion();
                default -> mostrarOpcionInvalida();
            }
        }
    }

    private void ingresarComoUsuario() {
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

    private void mostrarMenuVotacionesVotante(String rut) {
        mostrarVotacionesEnCurso();
        salirBucle:
        while (true) {
            mostrarOpcionesVotante();
            switch (pedirOpcion()) {
                case -1 -> {/*filtra valores no numéricos*/}
                case 0 -> {break salirBucle;}
                case 1 -> mostrarMenuVoto();
                case 2 -> mostrarMenuResultados();
                default -> mostrarOpcionInvalida();
            }
        }
    }

    private static void mostrarOpcionesVotante() {
        System.out.print("""
                OPCIONES
                [1] Votar
                [2] Ver Resultados
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
    }

    private void mostrarVotacionesEnCurso() {
        List<String> titulosVotaciones = obtenerTitulosVotaciones();
        System.out.println("Votaciones En Curso");
        for (String titulo : titulosVotaciones) {
            System.out.println(String.format("\"%s\"", titulo));
        }
    }

    private void mostrarMenuResultados() {
        List<String> titulosVotaciones = obtenerTitulosVotaciones();
        salirBucle:
        while (true) {
            System.out.println("Votaciones disponibles para revisión");
            mostrarIndiceVotaciones(titulosVotaciones);
            int opcionElegida = pedirOpcion();
            switch (opcionElegida) {
                case -1 -> {/*filtra valores no numéricos*/}
                case 0 -> {break salirBucle;}
                default -> {
                    if (opcionElegida > titulosVotaciones.size()) {
                        mostrarOpcionInvalida();
                    } else {
                        mostrarResultadosVotacion(titulosVotaciones.get(opcionElegida - 1));
                    }
                }
            }
        }
    }

    private void mostrarMenuVoto() {
        List<String> titulosVotaciones = obtenerTitulosVotaciones();
        salirBucle:
        while (true) {
            System.out.println("Votaciones disponibles para votación");
            mostrarIndiceVotaciones(titulosVotaciones);
            int opcionElegida = pedirOpcion();
            switch (opcionElegida) {
                case -1 -> {/*filtra valores no numéricos*/}
                case 0 -> {break salirBucle;}
                default -> {
                    if (opcionElegida > titulosVotaciones.size()) {
                        mostrarOpcionInvalida();
                    } else {
                        mostrarMenuOpciones(titulosVotaciones.get(opcionElegida - 1));
                    }
                }
            }
        }
    }

    private void mostrarMenuOpciones(String tituloVotacion) {
        List<String> opciones = obtenerOpcionesDeVotacion(tituloVotacion);
        salirBucle:
        while (true) {
            System.out.println("Opciones disponibles");
            mostrarIndiceOpciones(opciones);
            int opcionElegida = pedirOpcion();
            switch (opcionElegida) {
                case -1 -> {/*filtra valores no numéricos*/}
                case 0 -> {break salirBucle;}
                default -> {
                    if (opcionElegida > opciones.size()) {
                        mostrarOpcionInvalida();
                    }
                    if (realizarVoto(tituloVotacion, opciones.get(opcionElegida - 1))) {
                        System.out.println("¡Voto realizado con exito!");
                        break salirBucle;
                    }
                }
            }
        }
    }

    private boolean realizarVoto(String tituloVotacion, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        if (votarOpcion(jsonArrayVotaciones, tituloVotacion, opcionElegida)) {
            escribirArchivoJSON("src/main/datos/votaciones.json", jsonArrayVotaciones.toJSONString());
            return true;
        } else {
            System.err.println("Error, no se pudo realizar el voto.");
            return false;
        }
    }

    public static void escribirArchivoJSON(String ruta, String contenido) {
        try {
            FileWriter myWriter = new FileWriter(ruta);
            myWriter.write(contenido);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean votarOpcion(JSONArray jsonArrayVotaciones, String tituloVotacion, String opcionElegida) {
        Iterator<?> iterator = jsonArrayVotaciones.iterator();
        while (iterator.hasNext()) {
            JSONObject nextVotacion = (JSONObject) iterator.next();
            if (String.valueOf(nextVotacion.get("titulo")).equals(tituloVotacion)) {
                JSONObject opciones = (JSONObject) nextVotacion.get("opciones");
                List<String> opcionesArray = new ArrayList<>(opciones.keySet());
                for (String opcion : opcionesArray) {
                    if (opcion.equals(opcionElegida)) {
                        int votos = Integer.valueOf(String.valueOf(opciones.get(opcion)));
                        opciones.put(opcion, votos + 1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void mostrarIndiceVotaciones(List<String> titulosVotaciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < titulosVotaciones.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.println(String.format("[%s] %s", indiceAjustado, titulosVotaciones.get(indice)));
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    private void mostrarIndiceOpciones(List<String> opciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < opciones.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.println(String.format("[%s] %s", indiceAjustado, opciones.get(indice)));
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    public static List<String> obtenerOpcionesDeVotacion(String tituloVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        Iterator<?> iterator = jsonArrayVotaciones.iterator();
        while (iterator.hasNext()) {
            JSONObject nextVotacion = (JSONObject) iterator.next();
            if (String.valueOf(nextVotacion.get("titulo")).equals(tituloVotacion)) {
                JSONObject opciones = (JSONObject) nextVotacion.get("opciones");
                return new ArrayList<>(opciones.keySet());
            }
        }
        return  new ArrayList<>();
    }

    public static void mostrarResultadosVotacion(String tituloVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        Iterator<?> iterator = jsonArrayVotaciones.iterator();
        while (iterator.hasNext()) {
            JSONObject nextVotacion = (JSONObject) iterator.next();
            if (String.valueOf(nextVotacion.get("titulo")).equals(tituloVotacion)) {
                String titulo = String.valueOf(nextVotacion.get("titulo"));
                int votoBlancos = Integer.valueOf(String.valueOf(nextVotacion.get("votos_blancos")));
                int votoPreferenciales = Integer.valueOf(String.valueOf(nextVotacion.get("votos_preferenciales")));
                int totalVotos = votoPreferenciales + votoBlancos;
                String fechaInicio = String.valueOf(nextVotacion.get("fecha_inicio"));
                String horaInicio = String.valueOf(nextVotacion.get("hora_inicio"));
                String fechaTermino = String.valueOf(nextVotacion.get("fecha_termino"));
                String horaTermino = String.valueOf(nextVotacion.get("hora_termino"));
                System.out.println(String.format("""
                        Resultados para la votacion "%s"
                        Votos preferenciales   %s
                        Votos blancos          %s
                        Total votos            %s
                        Fecha y hora de inicio   %s %s hrs
                        Fecha y hora de término  %s %s hrs
                        """, titulo, votoBlancos, votoPreferenciales, totalVotos,
                        fechaInicio, horaInicio, fechaTermino, horaTermino));

                JSONObject opciones = (JSONObject) nextVotacion.get("opciones");
                List<String> opcionesList = new ArrayList<>(opciones.keySet());

                System.out.print("""
                        $$$$$$$$$$$$$$$$$$$$
                        $Votos por opciones$
                        $$$$$$$$$$$$$$$$$$$$
                        """);
                for (String opcion : opcionesList) {
                    System.out.println(
                            opcion.concat(" ")
                                    .concat(String.valueOf(opciones.get(opcion))));
                }
                return;
            }
        }
    }

    public static List<String> obtenerTitulosVotaciones() {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        List<String> arrayListVotaciones = new ArrayList<>();
        Iterator<?> iterator = jsonArrayVotaciones.iterator();
        while (iterator.hasNext()) {
            JSONObject nextVotacion = (JSONObject) iterator.next();
            arrayListVotaciones.add(String.valueOf(nextVotacion.get("titulo")));
        }
        return arrayListVotaciones;
    }

    public static JSONArray parsearVotaciones() {
        String jsonVotaciones = leerContenidosJSON("src/main/datos/votaciones.json");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(jsonVotaciones);
            JSONArray arrayVotaciones = (JSONArray) obj;
            return arrayVotaciones;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void verVotacion() {
        if(noHayVotacionesCreadas()){
            System.out.println("No hay votaciones creadas");
            return;
        }
        System.out.println("ELIGE UNA VOTACION");
        String votacion = elegirVotacion();
        String rutaVotacion = "src/main/votaciones/" + votacion;
        leerVotacion(rutaVotacion);
    }

    private void eliminarVotacion() {
        if(noHayVotacionesCreadas()){
            System.out.println("No hay votaciones creadas");
            return;
        }
        mostrarListaVotaciones();
        System.out.print("INGRESA LA VOTACION QUE QUIERAS TERMINAR: ");
        String votacion = elegirVotacion();
        String rutaVotacion = "src/main/votaciones/" + votacion;

        if(existeDatoEnArchivo(rutaVotacion, "TERMINADA")){
            System.out.println("La votación ya se encuentra terminada");
            return;
        }
        escribirDatoEnArchivo(rutaVotacion, "TERMINADA");
    }

    private String elegirVotacion() {
        String[] votaciones = crearListaVotaciones();
        String votacion = "";
        do{
            try{
                int opcion = pedirOpcion() - 1;
                votacion = votaciones[opcion];
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Por favor elija una de las opciones");
            }
        }while(votacion.equals(""));
        return votacion;
    }

    private void crearVotacion() {
        System.out.print("Nombre Votacion (nombre corto y descriptivo):");
        String nombreVotacion = pedirString().toLowerCase();

        if(existeVotacion(nombreVotacion.replace(" ", "_") + ".txt")){
            System.out.println("Ya existe una votacion con dicho nombre");
            crearVotacion();
            return;
        }

        System.out.print("Breve descripcion Votacion:");
        String descripcionVotacion = pedirString().toLowerCase();

        System.out.print("Cantidad de opciones Votacion (Entre 2-5):");
        int cantidadOpciones = definirCantidad();

        String rutaVotacion = "src/main/votaciones/VOTACION_" + nombreVotacion.replace(" ", "_") + ".txt";
        String rutaVotantes = "src/main/votantes/VOTANTES_" + nombreVotacion.replace(" ", "_") + ".txt";
        String rutaVotos = "src/main/votos/VOTOS_" + nombreVotacion.replace(" ", "_") + ".txt";

        escribirDatoEnArchivo(rutaVotacion, "NOMBRE VOTACIÓN: " + '"' + nombreVotacion + '"');
        escribirDatoEnArchivo(rutaVotacion, "DESCRIPCIÓN: " + '"' + descripcionVotacion + '"');

        ingresarOpciones(rutaVotacion, cantidadOpciones);

        escribirDatoEnArchivo(rutaVotantes, "VOTANTES:");
        escribirDatoEnArchivo(rutaVotos, "VOTOS");
    }

    public void ingresarOpciones(String rutaVotacion, int cantidadOpciones) {
        String[] opciones = {"A", "B", "C", "D", "E"};
        for (int index = 0; index < cantidadOpciones; index++) {
            int posicion = index + 1;
            System.out.print("Ingresa la opcion " + posicion + ":");
            escribirDatoEnArchivo(rutaVotacion, "[OPCION " + opciones[index] + "]" + pedirString());
        }
    }

    private int definirCantidad() {
        int cantidadOpciones;
        do {
            cantidadOpciones = pedirOpcion();
        }while(cantidadOpciones<2 || cantidadOpciones>5);
        return cantidadOpciones;
    }
    private void leerVotacion(String ruta) {
        FileReader leerFile;
        BufferedReader leerBuffer;
        String linea;
        try{
            leerFile = new FileReader(ruta);
            leerBuffer = new BufferedReader(leerFile);
            while((linea = leerBuffer.readLine()) != null){
                System.out.println(linea);
            }
        }catch (IOException e){
            System.out.println("El archivo no pudo ser leido");
        }
    }

    private void mostrarListaVotaciones() {
        if(noHayVotacionesCreadas()){
            System.out.println("No hay votaciones creadas");
            return;
        }
        String[] listaVotaciones = crearListaVotaciones();
        String ruta = "src/main/votaciones/";

        System.out.println("VOTACIONES");
        for (int index = 0; index < listaVotaciones.length; index++) {
            String votacion = listaVotaciones[index];
            int posicion = index + 1;

            if (existeDatoEnArchivo(ruta + votacion, "TERMINADA")) {
                System.out.println("[" + posicion + "]" + votacion.split(".txt")[0] + " (TERMINADA)");
            }else{
                System.out.println("[" + posicion + "]" + votacion.split(".txt")[0] + " (EN CURSO)");
            }
        }
    }

    private String[] crearListaVotaciones() {
        String ruta = "src/main/votaciones";
        File f = new File(ruta);
        return f.list();
    }

    private boolean noHayVotacionesCreadas(){
        String[] votaciones = crearListaVotaciones();
        return votaciones.length == 0;
    }

    private boolean existeVotacion(String nombreVotacion){
        String[] listaVotaciones = crearListaVotaciones();

        for (String votacion : listaVotaciones) {
            if (votacion.equals(nombreVotacion)) {
                return true;
            }
        }
        return false;
    }

    private boolean existeDatoEnArchivo(String ruta, String datos) {
        FileReader leerFile;
        BufferedReader leerBuffer;
        String linea;
        try{
            leerFile = new FileReader(ruta);
            leerBuffer = new BufferedReader(leerFile);
            while((linea = leerBuffer.readLine()) != null){
                if(linea.equals(datos)){
                    return true;
                }
            }
        }catch (IOException e){
            System.out.println("El archivo no pudo ser leido");
        }
        return false;
    }

    private void escribirDatoEnArchivo(String ruta, String datos){
        FileWriter escribirFile;
        BufferedWriter escribirBuffer;
        try{
            escribirFile = new FileWriter(ruta, true);
            escribirBuffer = new BufferedWriter(escribirFile);
            escribirBuffer.write(datos);
            escribirBuffer.newLine();
            escribirBuffer.close();
            escribirFile.close();
        }catch (IOException e){
            System.out.println("No se pudo escribir en el archivo");
        }
    }

    public static String leerContenidosJSON(String ruta) {
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

    public static JSONArray parsearVotantes() {
        String jsonVotantes = leerContenidosJSON("src/main/datos/votantes.json");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(jsonVotantes);
            JSONArray arrayVotantes = (JSONArray) obj;
            return arrayVotantes;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean esCredencialVotanteValida(String rut, String clave) {
        JSONArray arrayVotantes = parsearVotantes();
        Iterator<?> iterator = arrayVotantes.iterator();
        while (iterator.hasNext()) {
            JSONObject nextVotante = (JSONObject) iterator.next();
            if (nextVotante.get("rut").equals(rut) && nextVotante.get("clave").equals(clave)) {
                return true;
            }
        }
        return false;
    }


}
