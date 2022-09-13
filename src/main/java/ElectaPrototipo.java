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
        System.out.println("[3].Registrar Usuario");
        System.out.println("[4].Salir");
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
        System.out.println("INGRESA LA CONTRASEÑA DEL ADMINISTRADOR");
        String contraseñaAdmin = pedirString();
        String rutaDatosAdmin = "src/main/datosRegistro/credencialesAdmin.txt";
        if(existeDatoEnArchivo(rutaDatosAdmin, contraseñaAdmin)){
            menuAdmin();
        }else {
            System.out.println("Contraseña incorrecta");
        }
    }
    private static void menuAdmin() {
        System.out.println("BIENVENIDO AL MENU DEL ADMINISTRADOR");
        mostrarOpcionesAdmin();
        elegirOpcionAdmin();
    }
    private static void mostrarOpcionesAdmin() {
        System.out.println("[1].Ver Votaciones en Curso");
        System.out.println("[2].Crear Votacion");
        System.out.println("[3].Terminar Votacion");
        System.out.println("[4].Cerrar Sesion");
    }
    private static void elegirOpcionAdmin() {
        int opcion = pedirOpcion();
        switch (opcion) {
            case 1 -> mostrarVotaciones();
            case 2 -> crearVotacion();
            case 3 -> terminarVotacion();
            case 4 -> System.out.println("Hasta pronto");
            default -> {
                System.out.println("Por favor, ingrese una de las opciones");
                elegirOpcionAdmin();
            }
        }
    }
    private static void terminarVotacion() {
        mostrarVotaciones();
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

        System.out.print("Cantidad de opciones Votacion:");
        int cantidadOpciones;
        do {
            cantidadOpciones = pedirOpcion();
        }while(cantidadOpciones<=0);

        String ruta = "src/main/votaciones/" + nombreVotacion.replace(" ", "_") + ".txt";
        escribirDatoEnArchivo(ruta, "NOMBRE VOTACION: " + nombreVotacion);
        escribirDatoEnArchivo(ruta, "DESCRIPCION: " + descripcionVotacion);
        escribirDatoEnArchivo(ruta, "OPCIONES:");
        for (int i = 0; i < cantidadOpciones; i++) {
            int posicion = i + 1;
            System.out.print("Ingresa la opcion " + posicion + ":");
            escribirDatoEnArchivo(ruta, "-" + pedirString());
        }
        escribirDatoEnArchivo(ruta, "VOTANTES:");
    }
    public static void mostrarVotaciones() {
        String[] listaVotaciones = crearListaVotaciones();
        String ruta = "src/main/votaciones";
        if(listaVotaciones.length == 0){
            System.out.println("No hay votaciones creadas");
            return;
        }

        System.out.println("VOTACIONES EN CURSO");
        for (int i = 0; i < listaVotaciones.length; i++) {
            String votacion = listaVotaciones[i];
            int posicion = i + 1;
            if (existeDatoEnArchivo(ruta + "/" + votacion, "TERMINADA")) {
                System.out.println("[" + posicion + "]" + votacion.split(".txt")[0] + " (TERMINADA)");
            }else{
                System.out.println("[" + posicion + "]" + votacion.split(".txt")[0] + " (EN CURSO)");
            }
        }
    }
    private static String[] crearListaVotaciones() {
        String ruta = "src/main/votaciones";
        File f = new File(ruta);
        String[] archivos = f.list();
        return archivos;
    }
    private static void ingresarComoUsuario() {
        System.out.println("INGRESA TU RUT");
        String rutUsuario = pedirString();
        System.out.println("INGRESA TU CONTRASEÑA");
        String contraseñaUsuario = pedirString();

        String rutaRegistro = "src/main/datosRegistro/registroUsuarios.txt";
        String datosIngreso = rutUsuario + ";" + contraseñaUsuario;

        if(existeDatoEnArchivo(rutaRegistro, datosIngreso)){
            System.out.println("BIENVENIDO");
        }else{
            System.out.println("RUT o contraseña incorrectos");
        }
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
