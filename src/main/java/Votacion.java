import java.time.LocalDateTime;
import java.util.List;

public class Votacion {

    private Integer id;
    private String titulo;
    private String descripcion;
    private Estado estado;
    private LocalDateTime fechaTiempoInicio;
    private LocalDateTime fechaTiempoTermino;
    private List<Opcion> opciones;
    private List<Votante> votantes;
    private Integer votosPreferenciales;
    private Integer votosBlancos;



}
