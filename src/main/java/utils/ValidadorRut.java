package utils;

import java.util.Scanner;

public class ValidadorRut {

    public static String pideRut() {
        return new Scanner(System.in).nextLine();
    }

    public static boolean esFormatoDeRutCorrecto(String rut) {
        return rut.matches("^(\\d{1,3}\\.){1,2}\\d{3}?-(\\d|k)");
    }

    public static boolean esRutValido(String rut) {
        if (!esFormatoDeRutCorrecto(rut)) return false;
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
        int indice = 0;

        while (indice < s.length()) {
            String c = s.split("")[indice];
            int digito = Integer.parseInt(c);
            int i = (indice % 6) + 2;
            suma += i * digito;
            indice++;
        }

        return calcularSumaDeDV(suma);
    }

    public static int calcularSumaDeDV(int suma) {

        var variable = suma / 11;
        variable = variable * 11;
        variable = Math.abs(suma - variable);
        variable = 11 - variable;

        return variable;
    }

    public static String convertirDVNumericoADVLiteral(int DVNumerico) {
        return switch (DVNumerico) {
            case 10 -> "k";
            case 11 -> "0";
            default -> String.valueOf(DVNumerico);
        };
    }
}
