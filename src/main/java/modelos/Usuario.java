package modelos;
/**
 * Crea un usuario que haga uso del programa principal, este puede ser votante o administrador.
 */
public class Usuario {
    /**
     * Indica el identificador numérico único de un usuario.
     */
    private Integer id;
    /**
     * Indica la clave o contraseña de un usuario.
     */
    private String clave;
    /**
     * Indica el rut de un usuario.
     */
    private String rut;

    /**
     * Obtiene el rut asociado a un usuario.
     * @return un String que representa un rut.
     */

    public String getRut() {
        return rut;
    }

    /**
     * Asigna un rut a un usuario.
     * @param rut un Object cuyo valor String representa un rut.
     */

    public void setRut(Object rut) {
        this.rut = rut.toString();
    }

    /**
     * Obtiene el identificador numérico único asociado a un usuario.
     * @return un Integer cuyo valor representa un id.
     */

    public Integer getId() {
        return id;
    }

    /**
     * Asigna un identificador numérico único a un usuario.
     * @param id un Object cuyo valor entero representa un id.
     */

    public void setId(Object id) {
        this.id = Integer.parseInt(id.toString());
    }

    /**
     * Obtiene la clave o contraseña asociada a un usuario.
     * @return un String cuyo valor representa una clave.
     */

    public String getClave() {
        return clave;
    }

    /**
     * Asigna una clave o contraseña a un usuario.
     * @param clave un Object cuyo valor String representa una clave.
     */

    public void setClave(Object clave) {
        this.clave = clave.toString();
    }
}
