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

    public int pedirValorEntero() throws InputMismatchException {
        return new Scanner(System.in).nextInt();
    }

    public int pedirOpcion() {
        try {
            return pedirValorEntero();
        } catch (InputMismatchException e) {
            mostrarOpcionInvalida();
            return -1;
        }
    }

    public String pedirString(){
        return new Scanner(System.in).nextLine();
    }

    public void mostrarOpcionInvalida() {
        System.out.println("Por favor, escoja una opción válida");
    }

    public void ingresarComoAdmin() {
        System.out.print("Ingrese la contraseña del administrador \n> ");
        String claveAdmin = pedirString();
        String rutaDatosAdmin = "src/main/datosRegistro/credencialesAdmin.txt";
        if (existeDatoEnArchivo(rutaDatosAdmin, claveAdmin)) {
            mostrarMenuAdministador();
        } else {
            System.out.println("Contraseña incorrecta");
        }
    }

    public void mostrarOpcionesAdmin() {
        System.out.print("""
                [1] Ver Votacion
                [2] Crear Votacion
                [3] Terminar Votacion
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
    }

    public void mostrarMenuAdministador() {
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

    public void mostrarMenuVoto() {
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

    public void mostrarMenuOpciones(String tituloVotacion) {
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

    public boolean realizarVoto(String tituloVotacion, String opcionElegida) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        if (votarOpcionPreferencial(jsonArrayVotaciones, tituloVotacion, opcionElegida)) {
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

    public boolean votarOpcionPreferencial(JSONArray jsonArrayVotaciones, String tituloVotacion, String opcionElegida) {
        for (Object jsonArrayVotacione : jsonArrayVotaciones) {
            JSONObject nextVotacion = (JSONObject) jsonArrayVotacione;
            if (String.valueOf(nextVotacion.get("titulo")).equals(tituloVotacion)) {
                JSONObject opciones = (JSONObject) nextVotacion.get("opciones");
                List<String> opcionesArray = new ArrayList<>(opciones.keySet());
                for (String opcion : opcionesArray) {
                    if (opcion.equals(opcionElegida)) {
                        int votosOpcion = Integer.parseInt(String.valueOf(opciones.get(opcion)));
                        opciones.put(opcion, votosOpcion + 1);
                        int votosPreferenciales = Integer.parseInt(String.valueOf(nextVotacion.get("votos_preferenciales")));
                        nextVotacion.put("votos_preferenciales", votosPreferenciales + 1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void mostrarIndiceVotaciones(List<String> titulosVotaciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < titulosVotaciones.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, titulosVotaciones.get(indice));
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    public void mostrarIndiceOpciones(List<String> opciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < opciones.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, opciones.get(indice));
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    public ArrayList obtenerOpcionesDeVotacion(String tituloVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject nextVotacion = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(nextVotacion.get("titulo")).equals(tituloVotacion)) {
                JSONObject opciones = (JSONObject) nextVotacion.get("opciones");
                return new ArrayList<>(opciones.keySet());
            }
        }
        return  new ArrayList<>();
    }

    public void mostrarResultadosVotacion(String tituloVotacion) {
        JSONArray jsonArrayVotaciones = parsearVotaciones();
        for (Object jsonArrayVotacion : jsonArrayVotaciones) {
            JSONObject votacionSiguiente = (JSONObject) jsonArrayVotacion;
            if (String.valueOf(votacionSiguiente.get("titulo")).equals(tituloVotacion)) {
                mostrarResultadosDatos(votacionSiguiente);
                mostrarResultadosVotosPorOpciones(votacionSiguiente);
                return;
            }
        }
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

    public void verVotacion() {
        if(noHayVotacionesCreadas()){
            System.out.println("No hay votaciones creadas");
            return;
        }
        System.out.println("ELIGE UNA VOTACION");
        String votacion = elegirVotacion();
        String rutaVotacion = "src/main/votaciones/" + votacion;
        leerVotacion(rutaVotacion);
    }

    public void eliminarVotacion() {
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

    public String elegirVotacion() {
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

    public void crearVotacion() {
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

    public int definirCantidad() {
        int cantidadOpciones;
        do {
            cantidadOpciones = pedirOpcion();
        }while(cantidadOpciones<2 || cantidadOpciones>5);
        return cantidadOpciones;
    }

    public void leerVotacion(String ruta) {
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

    public void mostrarListaVotaciones() {
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

    public String[] crearListaVotaciones() {
        String ruta = "src/main/votaciones";
        File f = new File(ruta);
        return f.list();
    }

    public boolean noHayVotacionesCreadas(){
        String[] votaciones = crearListaVotaciones();
        return votaciones.length == 0;
    }

    public boolean existeVotacion(String nombreVotacion){
        String[] listaVotaciones = crearListaVotaciones();

        for (String votacion : listaVotaciones) {
            if (votacion.equals(nombreVotacion)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeDatoEnArchivo(String ruta, String datos) {
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

    public void escribirDatoEnArchivo(String ruta, String datos){
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
}
