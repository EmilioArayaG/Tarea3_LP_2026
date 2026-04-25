package mapa;

import entidades.Enemigo;
import entidades.Jugador;
import java.util.List;

public abstract class Zona {
    public String nombre;
    protected int nivelRequerido;
    protected List<Enemigo> enemigosDisponibles;

    public abstract void accionZona(Jugador cloud);

    public boolean validarAcceso(Jugador cloud){
        return cloud.getNivel() >= this.nivelRequerido;
    }
    public String getNombre() {return nombre; }
}