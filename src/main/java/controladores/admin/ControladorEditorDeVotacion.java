package controladores.admin;

import controladores.ControladorAplicacion;
import modelos.Opcion;
import modelos.Votacion;
import dao.VotacionDao;
import modelos.enums.EstadoDeVotacion;
import modelos.tablemodel.OpcionesTableModel;
import vistas.admin.PanelEditorVotaciones;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Crea el controlador con el cual se administra la lógica de la vista del editor de votación.
 */
public class ControladorEditorDeVotacion {
    /**
     * Indica la vista del editor de votación.
     */
    private final PanelEditorVotaciones vista;
    /**
     * Indica el controlador principal del programa.
     */
    private final ControladorAplicacion controlador;
    /**
     * Indica el modelo de tabla utilizado por la vista.
     */
    private final OpcionesTableModel modeloDeTabla;
    /**
     * Indica la votación a editar que administra el controlador.
     */
    private Votacion votacion = new Votacion();
    /**
     * Indica si una votación está recién siendo creada.
     */
    private Boolean estaVotacionEnProcesoDeCreacion;

    /**
     * Construye un controlador de tipo ControladorEditorDeVotación. Instancia el controlador principal, el
     * modelo de tabla y la vista. Agrega el panel de la vista mediante el controlador principal y configura
     * el texto de los botones de la tabla.
     * @param controlador el controlador principal de tipo ControladorAplicacion.
     */
    public ControladorEditorDeVotacion(ControladorAplicacion controlador) {
        this.controlador = controlador;
        modeloDeTabla = new OpcionesTableModel();
        modeloDeTabla.setTextoBoton("Eliminar");
        vista = new PanelEditorVotaciones(this);
        this.controlador.agregarPanel(vista, "editor");
    }

    /**
     * Carga la votación y sus opciones en la vista y la tabla respectivamente. Llama al controlador
     * principal para mostrar el panel del editor de votación. Determina si la votación está en modo
     * lectura y si se debe habilitar el botón de poner en marcha.
     *
     * @param votacion la votación a editar.
     * @param esNuevaVotacion un booleano que indica si es una nueva votación.
     * @param modoLectura un booleano que indica si se encuentra en modo lectura.
     */
    public void abrir(Votacion votacion, boolean esNuevaVotacion, boolean modoLectura) {
        this.votacion = votacion;
        this.estaVotacionEnProcesoDeCreacion = esNuevaVotacion;
        cargarVotacion();
        cargarOpciones();
        controlador.mostrarPanel("editor");
        vista.setModoLectura(modoLectura);
        if (esNuevaVotacion || !votacion.getEstadoDeVotacion().equals(EstadoDeVotacion.BORRADOR)) {
            vista.setHabilitarBotonPonerEnMarcha(false);
        }
        vista.moverScrollAlInicio();
    }

    /**
     * Carga la lista de opciones de la votación en la tabla, exceptuando la primera opción de voto blanco.
     */
    private void cargarOpciones() {
        // La sublista es necesaria para ocultar la opción por defecto "Abstenerse"
        modeloDeTabla.setOpciones(votacion.getOpciones().subList(1, votacion.getOpciones().size()));
    }

    /**
     * Carga la votación en la vista del editor de votación.
     */
    private void cargarVotacion() {
        vista.cargarVotacion(votacion);
    }

    /**
     * Llama al controlador para volver al menú de administración si es solicitado.
     */
    public void volverFueSolicitado() {
        controlador.abrirAdministracion();
    }

    /**
     * Obtiene el modelo de tabla.
     * @return una tabla de tipo OpcionesTableModel.
     */
    public OpcionesTableModel getModeloDeTabla() {
        return modeloDeTabla;
    }

    /**
     * Elimina una de las opciones de la votación.
     * @param fila la fila que indica la posición de la opción.
     */
    public void eliminarOpcionFueSolicitado(int fila) {
        // La fila + 1 es necesaria para eliminar el elemento correcto de la
        // lista opciones pues su primer elemento está oculto en la tabla
        votacion.removeOpcion(fila + 1);
        cargarOpciones();
    }

    /**
     * Muestra un JOptionPane con un mensaje de advertencia cuando no se cumplen los requisitos de entrada.
     */
    private void mostrarNoSeCumplenRequisitosDeEntrada() {
        JOptionPane.showMessageDialog(controlador.getMarco(),
                "Faltan datos por completar",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Procesa la votación editada. Si esta fue recién creada se escribe en los datos de las votaciones y
     * habilita el botón para ponerla en marcha. Si se editó una votación ya existente esta es actualizada.
     */
    private void procesarVotacion() {
        if (estaVotacionEnProcesoDeCreacion) {
            VotacionDao.crearVotacion(votacion);
            estaVotacionEnProcesoDeCreacion = false;
            vista.setHabilitarBotonPonerEnMarcha(true);
        } else {
            VotacionDao.actualizarVotacion(votacion);
        }
    }

    /**
     * Muestra un JOptionPane con un mensaje de éxito cuando se completó el proceso sin problemas.
     * @param mensaje especifica el mensaje mostrado en pantalla.
     */
    private void mostrarDialogoDeExito(String mensaje) {
        JOptionPane.showMessageDialog(controlador.getMarco(),
                mensaje,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Obtiene datos temporales y persistentes de la vista actual.
     */
    private void obtenerDatosDeVista() {
        votacion.setTitulo(vista.getTitulo());
        votacion.setDescripcion(vista.getDescripcion());
        obtenerDatosTemporalesDeVista();
    }

    /**
     * Obtiene datos temporales de la vista, como información sobre la fecha actual.
     */
    private void obtenerDatosTemporalesDeVista() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        LocalDateTime fechaInicio = LocalDate.parse(vista.getFechaInicio(), formatter).atStartOfDay()
                .withHour(vista.getHoraInicio()).withMinute(vista.getMinutoInicio());
        votacion.setFechaTiempoInicio(fechaInicio);
        LocalDateTime fechaTermino = LocalDate.parse(vista.getFechaTermino(), formatter).atStartOfDay()
                .withHour(vista.getHoraTermino()).withMinute(vista.getMinutoTermino());
        votacion.setFechaTiempoTermino(fechaTermino);
    }

    /**
     * Comprueba si se cumplen requisitos de entrada al ingresar información en la vista, como
     * comprobar si hay texto en blanco, si no se cumple el mínimo de opciones o si las fechas y horas
     * son contradictorias.
     * @return un booleano que indica si se cumplen los requisitos.
     */
    private boolean seCumplenRequisitosDeEntrada() {
        if (vista.getTitulo().isBlank()) {
            return false;
        }
        if (votacion.getOpciones().size() < 2) {
            return false;
        }
        if (votacion.getFechaTiempoInicio().isAfter(votacion.getFechaTiempoTermino())) {
            return false;
        }
        return true;
    }

    /**
     * Recibe el input del usuario al agregar una nueva opción mediante un JOptionPane.
     * @return un String cuyo valor indica el input del usuario.
     */
    private String pedirNombreDeOpcionPorDialogo() {
        UIManager.put("OptionPane.okButtonText", "OK");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        return JOptionPane.showInputDialog(
                controlador.getMarco(),
                "Escriba el nombre de la opción.",
                "Agregar opción",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Verifica que el nombre ingresado para una nueva opción es válido, ya sea que sea nulo, en blanco o
     * se encuentre repetido.
     * @param nombreDeOpcion un String cuyo valor indica el nombre ingresado.
     * @return un boolean que indica si el nombre es válido.
     */
    private boolean esNombreDeOpcionValido(String nombreDeOpcion) {
        if (nombreDeOpcion == null) return false;
        if (nombreDeOpcion.isBlank()) return false;
        if (esNombreDeOpcionDuplicado(nombreDeOpcion)) return false;
        return true;
    }

    /**
     * Comprueba que un nombre ingresado para una nueva opción ya exista dentro de las opciones
     * de la votación.
     * @param nombreDeOpcion un String cuyo valor indica el nombre ingresado.
     * @return un boolean que indica si el nombre se encuentra repetido.
     */
    private boolean esNombreDeOpcionDuplicado(String nombreDeOpcion) {
        for (Opcion opcion : votacion.getOpciones()) {
            if (opcion.getNombre().equals(nombreDeOpcion)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Crea una nueva opción con la información ingresada por el usuario y actualiza la tabla de opciones.
     * @param nombreDeOpcion un String cuyo valor indica el nombre ingresado para la nueva opción.
     */
    private void crearNuevaOpcion(String nombreDeOpcion) {
        String id = String.valueOf(votacion.getOpciones().size());
        Opcion opcion = new Opcion();
        opcion.setId(id);
        opcion.setNombre(nombreDeOpcion);
        opcion.setVotacion(votacion);
        opcion.setCantidadDeVotos(0);
        votacion.addOpcion(opcion);
        cargarOpciones();
    }

    /**
     * Crea una nueva opción cuando su nombre es válido.
     */
    public void agregarOpcionFueSolicitado() {
        String nombreDeOpcion = pedirNombreDeOpcionPorDialogo();
        if (esNombreDeOpcionValido(nombreDeOpcion)) {
            crearNuevaOpcion(nombreDeOpcion);
        }
    }

    /**
     * Procesa los cambios en la votación cuando se cumplen los requisitos de entrada, de otra forma
     * se muestra un mensaje de advertencia y los cambios no son guardados.
     */
    public void guardarFueSolicitado() {
        obtenerDatosDeVista();
        if (seCumplenRequisitosDeEntrada()) {
            procesarVotacion();
            mostrarDialogoDeExito("¡La votación se guardó en forma exitosa!");
        } else {
            mostrarNoSeCumplenRequisitosDeEntrada();
        }
    }

    /**
     * Pone en marcha una votación cuando el usuario lo solicita y se cumplen los requisitos
     * de entrada, de otra forma se muestra un mensaje de advertencia.
     */
    public void ponerVotacionEnMarchaFueSolicitado() {
        obtenerDatosDeVista();
        if (seCumplenRequisitosDeEntrada()) {
            if (pedirConfirmacionAUsuarioParaPonerEnMarcha()) {
                votacion.setEstadoDeVotacion(EstadoDeVotacion.PENDIENTE);
                procesarVotacion();
                vista.cargarVotacion(votacion);
                vista.setModoLectura(true);
                mostrarDialogoDeExito("¡La votación se puso en marcha en forma exitosa!");
            }
        } else {
            mostrarNoSeCumplenRequisitosDeEntrada();
        }
    }

    /**
     * Recibe el input del usuario al pedir la confirmación para poner en marcha
     * una votación mediante un JOptionPane.
     * @return un boolean cuyo valor referencia la decisión del usuario.
     */
    private boolean pedirConfirmacionAUsuarioParaPonerEnMarcha() {
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        int respuesta = JOptionPane.showConfirmDialog(
                controlador.getMarco(),
                "La siguiente operación pondrá en marcha la votación \n" +
                        "y por tanto esta ya no va a poder ser modificable. \n" +
                        "                      ¿Desea confirmar?",
                "Confirmación",
                JOptionPane.YES_NO_CANCEL_OPTION);
        return respuesta == JOptionPane.YES_OPTION;
    }
}
