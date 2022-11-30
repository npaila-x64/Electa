package vistas.votante;

import controladores.ControladorResultados;
import modelos.Opcion;
import modelos.Votacion;
import utils.Utilidades;
import utils.ValidadorDeDatos;

import java.util.ArrayList;
import java.util.List;

public class MenuResultados {

    private final ControladorResultados controlador;

    public MenuResultados(ControladorResultados controlador) {
        this.controlador = controlador;
    }

    public void mostrarResultadosVotosPorOpciones(Votacion votacion) {
        List<Opcion> opciones = votacion.getOpciones();
        System.out.println("Votos por opciones");
        for (var opcion : opciones) {
            System.out.println(Utilidades.padDerechaTexto(opcion.getNombre(), 30)
                    .concat(opcion.getCantidadDeVotos().toString()));
        }
    }

    public void mostrarResultadosDatos(Votacion votacion) {
        // TODO Mmmmmmh se ve innecesario...
        var datos = this.controlador.obtenerDatosDeVotacion(votacion);
        System.out.printf("Resultados para la votacion \"%s\"\n" +
                          "Votos preferenciales %s %s\n" +
                          "Votos blancos %s %s\n" +
                          "Total votos %s %s\n" +
                          "Fecha y hora de inicio %s %s hrs\n" +
                          "Fecha y hora de término %s %s hrs\n" +
                          "%n", datos.get("titulo"),
                Utilidades.padDerechaTexto("", 30 - 20), datos.get("votosPreferenciales"),
                Utilidades.padDerechaTexto("", 30 - 13), datos.get("votosBlancos"),
                Utilidades.padDerechaTexto("", 30 - 11), datos.get("totalVotos"),
                Utilidades.padDerechaTexto("", 30 - 22), datos.get("fechaTiempoInicio"),
                Utilidades.padDerechaTexto("", 30 - 23), datos.get("fechaTiempoTermino"));
    }

    private void mostrarOpcionesMenuResultados(List<Votacion> votacionesFinalizadas) {
        System.out.println("Votaciones finalizadas disponibles para su revisión");
        List<String> titulos = new ArrayList<>();
        for (var votacionSiguiente : votacionesFinalizadas) {
            titulos.add(votacionSiguiente.getTitulo());
        }
        mostrarListaDeCampos(titulos);
    }

    private void mostrarListaDeCampos(List<String> campos) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < campos.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, campos.get(indice));
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }
}
