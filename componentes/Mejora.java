package componentes;

public class Mejora {
    private final String nombre;
    private final int costoChatarra;
    private final TipoStat statAfectado;
    private final int valorBono;

    /**
     * Crea una mejora con su nombre, costo en chatarra, estadistica objetivo y valor de bono.
     *
     * @param nombre        nombre descriptivo de la mejora
     * @param costoChatarra costo en chatarra para comprarla
     * @param statAfectado  estadistica que incrementa (HP_MAX, MP_MAX o FUERZA)
     * @param valorBono     cantidad que se suma a la estadistica
     */
    public Mejora(String nombre, int costoChatarra, TipoStat statAfectado, int valorBono) {
        this.nombre = nombre;
        this.costoChatarra = costoChatarra;
        this.statAfectado = statAfectado;
        this.valorBono = valorBono;
    }

    public String getNombre()            { return nombre; }
    public int getCostoChatarra()        { return costoChatarra; }
    public TipoStat getStatAfectado()    { return statAfectado; }
    public int getValorBono()            { return valorBono; }
}
