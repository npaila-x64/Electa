package vistas.admin;

import controladores.admin.ControladorAdministracionDeVotaciones;
import modelos.Votacion;
import modelos.dao.VotacionDao;
import modelos.enums.Estado;
import utils.Utilidades;
import utils.ValidadorDeDatos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuAdministracionDeVotaciones {

    private final ControladorAdministracionDeVotaciones controlador;

    public MenuAdministracionDeVotaciones(ControladorAdministracionDeVotaciones controlador) {
        this.controlador = controlador;
    }

    public void mostrar() {
        while (true) {
            var votaciones = VotacionDao.obtenerVotaciones();
            mostrarOpcionesPanelDeControlDeVotaciones(votaciones);
            int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(votaciones.size());
            if (opcionElegida == 0) break;
            controlador.mostrarEditorDeVotacion(opcionElegida);
        }
    }

    private void mostrarOpcionesPanelDeControlDeVotaciones(List<Votacion> votaciones) {
        System.out.print("Para modificar o eliminar una votación\n" +
                         "escriba el número correspondiente a su índice\n");
        mostrarListaVotacionesDetallada(votaciones);
    }

    private void mostrarListaVotacionesDetallada(List<Votacion> votaciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < votaciones.size(); indice++) {
            mostrarDetalleVotacion(votaciones.get(indice), indice + 1);
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    private void mostrarDetalleVotacion(Votacion votacion, int indiceAjustado) {
        String titulo = votacion.getTitulo();
        Estado estado = votacion.getEstado();
        System.out.printf("[%s] %s %s ", indiceAjustado,
                Utilidades.padTexto(titulo, ".", 60), estado);
        Map<Estado, Runnable> estadosImpresion = new HashMap<>();
        estadosImpresion.put(Estado.EN_CURSO,  () -> System.out.printf("%s Terminará el %s a las %s hrs%n",
                Utilidades.padTexto("", 15 - estado.length()),
                votacion.getFechaTermino(), votacion.getTiempoTermino()));
        estadosImpresion.put(Estado.FINALIZADO,() -> System.out.printf("%s Finalizó el %s a las %s hrs%n",
                Utilidades.padTexto("", 15 - estado.length()),
                votacion.getFechaTermino(), votacion.getTiempoTermino()));
        estadosImpresion.put(Estado.PENDIENTE, () -> System.out.printf("%s Iniciará el %s a las %s hrs%n",
                Utilidades.padTexto("", 15 - estado.length()),
                votacion.getFechaInicio(), votacion.getTiempoInicio()));
        estadosImpresion.put(Estado.BORRADOR,  () -> System.out.printf("%n"));
        estadosImpresion.get(estado).run();
    }
}
