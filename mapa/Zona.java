package mapa;

import entidades.Enemigo;
import entidades.Jugador;
import java.util.List;

/**
 * Clase base abstracta para todas las zonas del mundo.
 * Cada zona define su nivel requerido y la accion que ocurre al visitarla.
 */
public abstract class Zona {
    /** Nombre descriptivo de la zona, mostrado en menus. */
    public String nombre;
    /** Nivel minimo que debe tener Cloud para acceder. */
    protected int nivelRequerido;
    /** Lista de enemigos disponibles en la zona (puede no usarse en todas). */
    protected List<Enemigo> enemigosDisponibles;

    /**
     * Ejecuta la logica principal de la zona cuando Cloud la visita.
     *
     * @param cloud el jugador que visita la zona
     */
    public abstract void accionZona(Jugador cloud);

    /**
     * Verifica si Cloud cumple el nivel minimo requerido para acceder.
     *
     * @param cloud el jugador a validar
     * @return true si el nivel de Cloud es mayor o igual al nivel requerido
     */
    public boolean validarAcceso(Jugador cloud) {
        return cloud.getNivel() >= this.nivelRequerido;
    }

    public String getNombre()        { return nombre; }
    public int getNivelRequerido()   { return nivelRequerido; }
}
