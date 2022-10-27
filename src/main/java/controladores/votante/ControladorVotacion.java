package controladores.votante;

import modelos.Opcion;
import modelos.Votacion;
import modelos.Votante;
import modelos.Voto;
import modelos.dao.UsuarioDao;
import modelos.dao.VotacionDao;
import modelos.dao.VotoDao;
import modelos.enums.TipoDeVoto;
import utils.ValidadorDeDatos;
import vistas.votante.MenuVotacion;

import java.util.ArrayList;
import java.util.List;

public class ControladorVotacion {

    private final MenuVotacion vista;
    private final Integer idVotante;

    public ControladorVotacion(Integer idVotante) {
        this.vista = new MenuVotacion(this);
        this.idVotante = idVotante;
    }

    public void iniciar() {
        this.vista.mostrar();
    }

    public List<Votacion> obtenerVotaciones() {
        return VotacionDao
                .obtenerVotacionesEnElQuePuedeVotarElVotante(idVotante);
    }

    public Votante obtenerVotante() {
        return UsuarioDao.obtenerVotantePorId(idVotante);
    }

    public void mostrarMenuOpcionesParaVotar(Integer opcionElegida) {
        Votacion votacion = VotacionDao
                .obtenerVotacionesEnElQuePuedeVotarElVotante(idVotante)
                .get(opcionElegida - 1);
        List<Opcion> opciones = votacion.getOpciones();
        mostrarOpcionesMenuOpcionesParaVotar(opciones);
        int nuevaOpcion = ValidadorDeDatos.pedirOpcionHasta(opciones.size());
        if (nuevaOpcion == 0) return;
        if (nuevaOpcion == 1) {
            registrarVotoBlanco(votacion, obtenerVotante());
        } else {
            registrarVotoPreferencial(votacion, obtenerVotante(), opciones.get(nuevaOpcion - 1));
        }
        mostrarVotoRealizadoConExito();
    }

    private void mostrarOpcionesMenuOpcionesParaVotar(List<Opcion> opciones) {
        System.out.println("Opciones disponibles");
        mostrarListaOpciones(opciones);
    }

    private void mostrarVotoRealizadoConExito() {
        System.out.println("¡Voto realizado con exito!\n");
    }

    public void mostrarListaOpciones(List<Opcion> opciones) {
        System.out.println("Elija una opción");
        for (int indice = 0; indice < opciones.size(); indice++) {
            int indiceAjustado = indice + 1;
            System.out.printf("[%s] %s%n", indiceAjustado, opciones.get(indice).getNombre());
        }
        System.out.print("Si desea volver escriba [0]\n> ");
    }

    private List<Votacion> obtenerVotacionesEnElQuePuedeVotarElVotante() {
        List<Votacion> votacionesEnCurso = VotacionDao.obtenerVotacionesEnCurso();
        List<Votacion> votacionesEnElQuePuedeVotarElVotante = new ArrayList<>();
        votacionesEnCurso
                .stream()
                .filter(votacionSiguiente -> !VotacionDao.votanteVotoEnEstaVotacion(votacionSiguiente, idVotante))
                .forEach(votacionesEnElQuePuedeVotarElVotante::add);
        return votacionesEnElQuePuedeVotarElVotante;
    }

    public void registrarVoto(Votacion votacion, Opcion opcionElegida) {
        List<Votacion> votaciones = obtenerVotaciones();
        for (var votacionSiguiente : votaciones) {
            if (votacionSiguiente.getId().equals(votacion.getId())) {
                List<Opcion> opciones = votacionSiguiente.getOpciones();
                opciones.stream()
                        .filter(opcion -> opcion.getId().equals(opcionElegida.getId()))
                        .forEach(this::incrementarCantidadDeVotosDeOpcionEnUno);
                votacionSiguiente.setOpciones(opciones);
                VotacionDao.escribirVotaciones(votaciones);
                return;
            }
        }
    }

    private void incrementarCantidadDeVotosDeOpcionEnUno(Opcion opcion) {
        int votosOpcion = opcion.getCantidadDeVotos();
        votosOpcion++;
        opcion.setCantidadDeVotos(votosOpcion);
    }

    public void registrarVotoPreferencial(Votacion votacion, Votante votante, Opcion opcionElegida) {
        registrarVoto(votacion, opcionElegida);
        registrarVotoEnVotos(votacion, votante, opcionElegida);
        registrarVotanteEnVotaciones(votacion, votante);
    }

    public void registrarVotoBlanco(Votacion votacion, Votante votante) {
        Opcion opcionBlanco = new Opcion(TipoDeVoto.VOTO_BLANCO);
        registrarVoto(votacion, opcionBlanco);
        registrarVotoEnVotos(votacion, votante, opcionBlanco);
        registrarVotanteEnVotaciones(votacion, votante);
    }

    public void registrarVotanteEnVotaciones(Votacion votacion, Votante votante) {
        List<Votacion> votaciones = obtenerVotaciones();
        for (var votacionSiguiente : votaciones) {
            if (votacionSiguiente.getId().equals(votacion.getId())) {
                List<Votante> votantes = votacionSiguiente.getVotantes();
                votantes.add(votante);
                votacionSiguiente.setVotantes(votantes);
                VotacionDao.escribirVotaciones(votaciones);
                return;
            }
        }
    }

    public void registrarVotoEnVotos(Votacion votacion, Votante votante, Opcion opcion) {
        List<Voto> votos = VotoDao.obtenerVotos();
        Voto voto = new Voto();
        voto.setId(VotoDao.obtenerNuevaIdVoto());
        voto.setVotacion(votacion);
        voto.setVotante(votante);
        voto.setOpcion(opcion);
        votos.add(voto);
        votacion.setVotos(votos);
        VotoDao.escribirVotos(votos);
    }

    private List<Voto> obtenerVotosDeVotacion(Votacion votacion) {
        List<Voto> votos = VotoDao.obtenerVotos();
        List<Voto> votosDeVotacion = new ArrayList<>();
        for (Voto voto : votos) {
            if (voto.getVotacion().getId().equals(votacion.getId())) {
                votosDeVotacion.add(voto);
            }
        }
        return votosDeVotacion;
    }
}
