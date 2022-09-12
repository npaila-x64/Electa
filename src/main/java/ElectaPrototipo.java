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
            case 1 -> System.out.println("INGRESASTE COMO USUARIO");
            case 2 -> System.out.println("INGRESASTE COMO ADMIN");
            case 3 -> System.out.println("REGISTRATE AQUI");
            case 4 -> System.out.println("Hasta pronto");
            default -> {
                System.out.println("Por favor, ingrese una de las opciones");
                elegirIngreso();
            }
        }
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
