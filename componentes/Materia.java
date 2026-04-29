package componentes;

public class Materia {
    private final String nombre;
    private final Elemento elemento;

    /**
     * Crea una Materia con el nombre y elemento indicados.
     *
     * @param nombre   nombre descriptivo de la materia
     * @param elemento elemento magico que representa
     */
    public Materia(String nombre, Elemento elemento) {
        this.nombre = nombre;
        this.elemento = elemento;
    }

    public String getNombre()     { return nombre; }
    public Elemento getElemento() { return elemento; }
}
