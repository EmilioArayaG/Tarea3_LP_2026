package entidades;

import componentes.Estadisticas;

public abstract class Enemigo {
    protected String nombre;
    protected int xpRecompensa;
    protected int chatarraRecompensa;
    protected Estadisticas stats;

    /**
     * Realiza un ataque sobre el jugador.
     *
     * @param cloud el jugador que recibe el ataque
     */
    public abstract void atacar(Jugador cloud);

    /**
     * Entrega la recompensa de XP del enemigo al jugador.
     *
     * @param cloud el jugador que recibe la XP
     */
    public void giveXpRecompensa(Jugador cloud) {
        cloud.recibirXP(this.xpRecompensa);
    }

    public String getNombre()              { return nombre; }
    public Estadisticas getStats()         { return stats; }
    public int getXpRecompensa()           { return xpRecompensa; }
    public int getChatarraRecompensa()     { return chatarraRecompensa; }
}
