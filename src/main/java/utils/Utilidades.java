package utils;

public class Utilidades {

    public static String padDerechaTexto(String texto, int largo) {
        return padDerechaTexto(texto, ".", largo);
    }

    public static String padDerechaTexto(String texto, String patronRelleno, int largo) {
        String relleno = patronRelleno.repeat(largo);
        int largoDeTexto = texto.length();
        String corte = relleno.substring(largoDeTexto);
        return texto.concat(corte);
    }

    public static String padIzquierdaTexto(String texto, String patronRelleno, int largo) {
        String relleno = patronRelleno.repeat(largo);
        int largoDeTexto = texto.length();
        String corte = relleno.substring(largoDeTexto);
        return corte.concat(texto);
    }
}
