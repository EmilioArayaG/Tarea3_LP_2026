package entidades;

import componentes.Estadisticas;

public abstract class Enemigo {
    protected String nombre;
    protected int xpRecompensa;
    protected int chatarraRecompensa;
    protected Estadisticas stats;

    public abstract void atacar(Jugador cloud);

    public void giveXpRecompensa(Jugador cloud){
        cloud.recibirXp(this.xpRecompensa);
    }
    public String getNombre() { return nombre; }
    public Estadisticas getStats() { return stats; }
}