import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ElectaPrototipo {
    public static void main(String[] args) {
        menuInicio();
    }
    private static void menuInicio() {
        mostrarOpcionesDeIngreso();
        elegirIngreso();
    }
    private static void mostrarOpcionesDeIngreso() {
        System.out.println("[1].Ingresar como Usuario");
        System.out.println("[2].Ingresar como Admin");
        System.out.println("[3].Salir");
    }
    private static void elegirIngreso() {
        int opcion = pedirOpcion();
        switch (opcion) {
            case 1 -> ingresarComoUsuario();
            case 2 -> ingresarComoAdmin();
            case 3 -> System.out.println("Hasta pronto");
            default -> {
                System.out.println("Por favor, ingrese una de las opciones");
                elegirIngreso();
            }
        }
    }
    private static void ingresarComoAdmin() {
        System.out.println("INGRESA LA CLAVE DEL ADMINISTRADOR");
        String claveAdmin = pedirString();
        String rutaDatosAdmin = "src/main/datosRegistro/credencialesAdmin.txt";
        if(existeDatoEnArchivo(rutaDatosAdmin, claveAdmin)){
            menuAdmin();
        }else {
            System.out.println("Contraseña incorrecta");
        }
    }
    private static void menuAdmin() {
        System.out.println("BIENVENIDO AL MENU DEL ADMINISTRADOR");
        mostrarListaVotaciones();
        mostrarOpcionesAdmin();
        elegirOpcionAdmin();
    }
    private static void mostrarOpcionesAdmin() {
        System.out.print("\n[1].Ver Votacion ");
        System.out.print("[2].Crear Votacion ");
        System.out.print("[3].Terminar Votacion ");
        System.out.print("[4].Cerrar Sesion\n");
    }
    private static void elegirOpcionAdmin() {
        int opcion = pedirOpcion();
        switch (opcion) {
            case 1 -> verVotacion();
            case 2 -> crearVotacion();
            case 3 -> terminarVotacion();
            case 4 -> System.out.println("Hasta pronto");
            default -> {
                System.out.println("Por favor, ingrese una de las opciones");
                elegirOpcionAdmin();
            }
        }
    }
    private static void ingresarComoUsuario() {
        System.out.println("INGRESA TU RUT");
        String rutUsuario = pedirString();
        System.out.println("INGRESA TU CLAVE");
        String claveUsuario = pedirString();

        String rutaRegistro = "src/main/datosRegistro/registroUsuarios.txt";
        String datosIngreso = rutUsuario + ";" + claveUsuario;

        if(existeDatoEnArchivo(rutaRegistro, datosIngreso)){
            menuVotaciones(rutUsuario);
        }else{
            System.out.println("RUT o contraseña incorrectos");
        }
    }
    private static void menuVotaciones(String rut) {
        if(noHayVotacionesCreadas()){
            mostrarListaVotaciones();
            return;
        }
        mostrarListaVotaciones();
        mostrarOpcionesUsuario();
        elegirOpcionUsuario(rut);
    }
    private static void mostrarOpcionesUsuario() {
        System.out.println("\nOPCIONES");
        System.out.print("\n[1].Votar ");
        System.out.print("[2].Ver Resultados\n");
    }
    private static void elegirOpcionUsuario(String rut) {
        int opcion = pedirOpcion();
        switch (opcion) {
            case 1 -> votar(rut);
            case 2 -> verResultados();
            default -> {
                System.out.println("Por favor, ingrese una de las opciones");
                elegirOpcionAdmin();
            }
        }
    }
    public static void verResultados(){
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
    private static int cantidadOcurrencias(String ruta, String opcion) {
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
    public static void votar(String rut){
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
    private static void verVotacion() {
        if(noHayVotacionesCreadas()){
            System.out.println("No hay votaciones creadas");
            return;
        }
        System.out.println("ELIGE UNA VOTACION");
        String votacion = elegirVotacion();
        String rutaVotacion = "src/main/votaciones/" + votacion;
        leerVotacion(rutaVotacion);
    }
    private static void terminarVotacion() {
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
    private static String elegirVotacion() {
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
    private static void crearVotacion() {
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
    public static void ingresarOpciones(String rutaVotacion, int cantidadOpciones) {
        String[] opciones = {"A", "B", "C", "D", "E"};
        for (int index = 0; index < cantidadOpciones; index++) {
            int posicion = index + 1;
            System.out.print("Ingresa la opcion " + posicion + ":");
            escribirDatoEnArchivo(rutaVotacion, "[OPCION " + opciones[index] + "]" + pedirString());
        }
    }
    private static int definirCantidad() {
        int cantidadOpciones;
        do {
            cantidadOpciones = pedirOpcion();
        }while(cantidadOpciones<2 || cantidadOpciones>5);
        return cantidadOpciones;
    }
    private static void leerVotacion(String ruta) {
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
    public static void mostrarListaVotaciones() {
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
    private static String[] crearListaVotaciones() {
        String ruta = "src/main/votaciones";
        File f = new File(ruta);
        return f.list();
    }
    public static boolean noHayVotacionesCreadas(){
        String[] votaciones = crearListaVotaciones();
        return votaciones.length == 0;
    }
    public static boolean existeVotacion(String nombreVotacion){
        String[] listaVotaciones = crearListaVotaciones();

        for (String votacion : listaVotaciones) {
            if (votacion.equals(nombreVotacion)) {
                return true;
            }
        }
        return false;
    }
    public static boolean existeDatoEnArchivo(String ruta, String datos) {
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
    public static void escribirDatoEnArchivo(String ruta, String datos){
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
    private static String pedirString(){
        return new Scanner(System.in).nextLine();
    }
    private static int pedirOpcion() {
        try{
            return pedirValor();
        }catch (InputMismatchException e){
            System.out.println("Error, ingresa un valor válido");
            return pedirOpcion();
        }
    }
    private static int pedirValor() throws InputMismatchException{
        return new Scanner(System.in).nextInt();
    }
}
