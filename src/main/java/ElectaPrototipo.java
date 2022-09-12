import java.io.BufferedReader;
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
            case 2 -> System.out.println("INGRESASTE COMO ADMIN");
            case 3 -> System.out.println("REGISTRATE AQUI");
            case 4 -> System.out.println("Hasta pronto");
            default -> {
                System.out.println("Por favor, ingrese una de las opciones");
                elegirIngreso();
            }
        }
    }
    private static void ingresarComoUsuario() {
        System.out.println("INGRESA TU RUT");
        String rutUsuario = pedirString();
        System.out.println("INGRESA TU CONTRASEÑA");
        String contraseñaUsuario = pedirString();

        String datosIngreso = rutUsuario + ";" + contraseñaUsuario;

        if(existeUsuario("src/main/datosRegistro/registroUsuarios.txt", datosIngreso)){
            System.out.println("BIENVENIDO");
        }else{
            System.out.println("RUT o contraseña incorrectos");
        }
    }

    public static boolean existeUsuario(String ruta, String datos) {
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
