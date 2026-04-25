package entidades;

import componentes.Estadisticas;

public abstract class Enemigo {
    protected String nombre;
    protected int xpRecompensa;
    protected int chatarraRecompensa;
    protected Estadisticas stats;

    public abstract void atacar(Jugador cloud);

    public void giveXpRecompensa(Jugador cloud){
        cloud.recibirXP(this.xpRecompensa);
    }
    public String getNombre() { return nombre; }
    public Estadisticas getStats() { return stats; }
}