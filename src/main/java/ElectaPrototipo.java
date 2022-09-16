import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

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
            mostrarListaVotaciones();
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
            mostrarMenuVotaciones(rutVotante);
        } else {
            System.out.println("RUT o contraseña incorrectos");
        }
    }

    private void mostrarMenuVotaciones(String rut) {
        if (noHayVotacionesCreadas()) return;
        salirBucle:
        while (true) {
            mostrarListaVotaciones();
            mostrarOpcionesVotante();
            switch (pedirOpcion()) {
                case -1 -> {/*filtra valores no numéricos*/}
                case 0 -> {break salirBucle;}
                case 1 -> votar(rut);
                case 2 -> verResultados();
                default -> mostrarOpcionInvalida();
            }
        }
    }

    private static void mostrarOpcionesVotante() {
        System.out.print("""
                OPCIONES
                [1].Votar
                [2].Ver Resultados
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
    }

    private void verResultados(){
        System.out.println("ELIGE UNA VOTACION");
        String votacion = elegirVotacion();
        String rutaVotacion = "src/main/votaciones/"+votacion;

        if(!existeDatoEnArchivo(rutaVotacion, "TERMINADA")){
            System.out.println("La votacion todavia sigue en curso");
            return;
        }
        System.out.println("RESULTADOS");

        String rutaVotos = "src/main/votos/VOTOS_"+votacion.substring(9);
        String[] posiblesOpciones = {"A", "B", "C", "D", "E"};
        int numeroOpciones = cantidadOcurrencias(rutaVotacion, "[OPCION");
        int[] cantidadVotosPorOpcion = new int[numeroOpciones];
        for (int i = 0; i < numeroOpciones; i++) {
            cantidadVotosPorOpcion[i] = cantidadOcurrencias(rutaVotos, posiblesOpciones[i]);
            System.out.println("[OPCION "+posiblesOpciones[i] + "]" + " = " + cantidadVotosPorOpcion[i]);
        }

    }

    private int cantidadOcurrencias(String ruta, String opcion) {
        FileReader leerFile;
        BufferedReader leerBuffer;
        String linea;
        int cont = 0;
        try{
            leerFile = new FileReader(ruta);
            leerBuffer = new BufferedReader(leerFile);
            while((linea = leerBuffer.readLine()) != null){
                if(linea.contains(opcion)){
                    cont++;
                }
            }
        }catch (IOException e){
            System.out.println("El archivo no pudo ser leido");
        }
        return cont;
    }

    private void votar(String rut){
        System.out.println("ELIGE UNA VOTACION");
        String votacion = elegirVotacion();
        String rutaVotacion = "src/main/votaciones/" + votacion;
        String rutaVotantes = "src/main/votantes/VOTANTES" + votacion.substring(8);
        String rutaVotos = "src/main/votos/VOTOS" + votacion.substring(8);

        if(existeDatoEnArchivo(rutaVotacion, "TERMINADA") || existeDatoEnArchivo(rutaVotantes, rut)){
            System.out.println("No puedes votar ya que la votacion ya termino o ya votaste");
            return;
        }
        leerVotacion(rutaVotacion);
        System.out.println("Elige una de las OPCIONES (escriba la letra de la opcion)");
        String voto = pedirString().toUpperCase();
        escribirDatoEnArchivo(rutaVotos, voto);
        escribirDatoEnArchivo(rutaVotantes, rut);
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
