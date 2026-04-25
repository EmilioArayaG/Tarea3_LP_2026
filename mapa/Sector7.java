package mapa;

import entidades.Jugador;
import entidades.EnemigoSimulador;
import componentes.Mejora;
import componentes.TipoStat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sector7 extends Zona {
    private List<Mejora> tiendaLocal;

    public Sector7(){
        this.nombre = "Sector 7";
        this.nivelRequerido = 1;
        this.tiendaLocal = new ArrayList<>();
        this.tiendaLocal.add(new Mejora("Mejora vitalidad", 100, TipoStat.HP_MAX, 20));
    }
}