package mapa;

import entidades.Enemigo;
import entidades.Jugador;
import java.util.List;

public abstract class Zona {
    public String nombre;
    protected int nivelRequerido;
    protected List<Enemigo> enemigosDisponibles;

    /**
     * Ejecuta la logica principal de la zona cuando Cloud la visita.
     *
     * @param cloud el jugador que visita la zona
     */
    public abstract void accionZona(Jugador cloud);

    /**
     * Verifica si Cloud cumple el nivel minimo requerido para acceder a la zona.
     *
     * @param cloud el jugador a validar
     * @return true si el nivel de Cloud es mayor o igual al nivel requerido
     */
    public boolean validarAcceso(Jugador cloud) {
        return cloud.getNivel() >= this.nivelRequerido;
    }

    public String getNombre()       { return nombre; }
    public int getNivelRequerido()  { return nivelRequerido; }
}
