public class Utilidades {

    public static int parsearObjectAInt(Object obj) {
        return Integer.parseInt(String.valueOf(obj));
    }

    public static String padTexto(String texto, int largo) {
        return padTexto(texto, ".", largo);
    }

    public static String padTexto(String texto, String patronRelleno, int largo) {
        String relleno = patronRelleno.repeat(largo);
        int largoDeTexto = texto.length();
        String corte = relleno.substring(largoDeTexto);
        return texto.concat(corte);
    }
}
