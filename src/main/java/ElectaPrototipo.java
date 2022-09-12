import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            case 3 -> System.out.println("REGISTRATE AQUI");
            case 4 -> System.out.println("Hasta pronto");
            default -> {
                System.out.println("Por favor, ingrese una de las opciones");
                elegirIngreso();
            }
        }
    }
    private static void ingresarComoAdmin() {
        System.out.println("INGRESA LA CONTRASEÑA DEL ADMINISTRADOR");
        String contraseñaAdmin = pedirString();
        String ruta = "src/main/datosRegistro/credencialesAdmin.txt";
        if(existeDatoEnArchivo(ruta, contraseñaAdmin)){
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
            case 1 -> mostrarVotacionesEnCurso();
            //case 2 -> crearVotacion();
            //case 3 -> terminarVotacion();
            case 4 -> System.out.println("Hasta pronto");
            default -> {
                System.out.println("Por favor, ingrese una de las opciones");
                elegirIngreso();
            }
        }
    }
    public static void mostrarVotacionesEnCurso() {
        String ruta = "src/main/votaciones";
        File f = new File(ruta);
        String[] archivos = f.list();
        if(archivos.length == 0){
            System.out.println("No hay votaciones en curso");
            return;
        }

        System.out.println("VOTACIONES EN CURSO");
        for (String archivo : archivos) {
            System.out.println("["+archivo+"]");
        }
    }
    private static void ingresarComoUsuario() {
        System.out.println("INGRESA TU RUT");
        String rutUsuario = pedirString();
        System.out.println("INGRESA TU CONTRASEÑA");
        String contraseñaUsuario = pedirString();

        String ruta = "src/main/datosRegistro/registroUsuarios.txt";
        String datosIngreso = rutUsuario + ";" + contraseñaUsuario;

        if(existeDatoEnArchivo(ruta, datosIngreso)){
            System.out.println("BIENVENIDO");
        }else{
            System.out.println("RUT o contraseña incorrectos");
        }
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
