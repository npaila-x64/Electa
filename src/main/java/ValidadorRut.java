import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ValidadorRut {
    public static void main(String[] args) {
        pideRut();
    }

    public static void pideRut() {
        Scanner scanner = new Scanner(System.in);
        List<String> formatoRut = Arrays.asList(
                "%s", "%s", "%s.", "%s", "%s", "%s.", "%s", "%s", "%s", "-%s"
                );
        List<String> entradas = new ArrayList<>();

        while (true) {
            String entrada = scanner.nextLine().split("")[0];
            if (esCaracterValido(entrada.charAt(0))) {
                entradas.add(entrada);
                String rut = "";

                int len = entradas.size();
                for (int i = 0; i < len; i++) {
                    rut = rut.concat(
                            String.format(formatoRut.get(formatoRut.size() - len + i),entradas.get(i))
                    );
                }

                System.out.println("\n\n\n\n\n\n\n\n");
                System.out.print(rut);
                if (esFormatoDeRutCorrecto(rut)) {
                    String no = esRutValido(rut)? "" : "no ";
                    System.out.println(" rut " + no + "es válido");
                }
            }
        }

    }

    public static boolean esCaracterValido(char c) {
        if (String.valueOf(c).isBlank()) {
            return false;
        }
        return Character.isLetterOrDigit(c);
    }

    public static boolean esFormatoDeRutCorrecto(String rut) {
        return rut.matches("^(\\d{1,3}\\.){1,2}\\d{3}?-(\\d|k)");
    }

    public static boolean esRutValido(String rut) {

        String[] componentes = rut.split("-");
        String rutSinDV = String.join("",  componentes[0].split("\\."));
        String DV = componentes[1];

        return DV.equals(calcularDV(rutSinDV));
    }

    public static String calcularDV(String rutSinDV) {

        String rutSinDVInvertido = new StringBuilder(rutSinDV).reverse().toString();
        int DVNumerico = procesarRut(rutSinDVInvertido);
        String DV = convertirDVNumericoADVLiteral(DVNumerico);

        return DV;
    }

    public static int procesarRut(String s) {

        int suma = 0;
        int index = 0;

        while (index < s.length()) {
            String c = s.split("")[index];

            int digito = Integer.parseInt(c);
            int i = (index % 6) + 2;

            suma += i * digito;

            index++;
        }

        return calcularSumaDeDV(suma);
    }

    public static int calcularSumaDeDV(int suma) {

        double a = suma / 11;
        a = Math.floor(a);
        a = a * 11;
        a = Math.abs(suma - a);
        a = 11 - a;

        return (int) a;
    }

    public static String convertirDVNumericoADVLiteral(int s) {
        switch (s) {
            case 10:
                return "k";
            case 11:
                return "0";
            default:
                return String.valueOf(s);
        }
    }
}
