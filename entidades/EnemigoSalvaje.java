package entidades;

import componentes.Elemento;
import componentes.Estadisticas;
import componentes.Vulnerable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EnemigoSalvaje extends Enemigo implements Vulnerable {

    public enum Tipo { PLANTA_CARNIVORA, SAPO_JUNGLA, ROBOT_CENTINELA }

    private Tipo tipo;
    private List<Elemento> debilidades;
    private List<Elemento> resistencias;
    private List<Elemento> inmunidades;

    /**
     * Crea un EnemigoSalvaje del tipo indicado con sus stats, nombre y recompensas aleatorias.
     *
     * @param tipo el tipo de enemigo a crear (PLANTA_CARNIVORA, SAPO_JUNGLA o ROBOT_CENTINELA)
     */
    public EnemigoSalvaje(Tipo tipo) {
        this.tipo = tipo;
        Random rand = new Random();
        this.xpRecompensa = rand.nextInt(21) + 80;
        this.chatarraRecompensa = rand.nextInt(26) + 50;

        switch (tipo) {
            case PLANTA_CARNIVORA:
                this.nombre = "Planta Carnivora";
                this.stats = new Estadisticas(80, 0, 15, 0);
                this.debilidades  = new ArrayList<>(Arrays.asList(Elemento.FUEGO, Elemento.HIELO));
                this.inmunidades  = new ArrayList<>(Arrays.asList(Elemento.RAYO));
                this.resistencias = new ArrayList<>();
                break;
            case SAPO_JUNGLA:
                this.nombre = "Sapo de la Jungla";
                this.stats = new Estadisticas(60, 0, 12, 0);
                this.debilidades  = new ArrayList<>(Arrays.asList(Elemento.RAYO, Elemento.HIELO));
                this.resistencias = new ArrayList<>(Arrays.asList(Elemento.FUEGO));
                this.inmunidades  = new ArrayList<>();
                break;
            case ROBOT_CENTINELA:
                this.nombre = "Robot Centinela";
                this.stats = new Estadisticas(100, 0, 20, 0);
                this.debilidades  = new ArrayList<>(Arrays.asList(Elemento.RAYO));
                this.resistencias = new ArrayList<>(Arrays.asList(Elemento.FISICO, Elemento.HIELO));
                this.inmunidades  = new ArrayList<>();
                break;
        }
    }

    /**
     * Retorna el multiplicador de dano magico segun el elemento recibido y el tipo de enemigo.
     * Debilidad x2, neutro x1, resistencia x0.5, inmunidad x0.
     *
     * @param elementoMagia el elemento del ataque magico
     * @return multiplicador de dano
     */
    @Override
    public double evaluarDebilidad(Elemento elementoMagia) {
        if (debilidades.contains(elementoMagia))  return 2.0;
        if (inmunidades.contains(elementoMagia))  return 0.0;
        if (resistencias.contains(elementoMagia)) return 0.5;
        return 1.0;
    }

    /**
     * Ataca al jugador con 85% de precision. El dano es piso(Fuerza * 1.25).
     *
     * @param cloud el jugador que recibe el ataque
     */
    @Override
    public void atacar(Jugador cloud) {
        Random rand = new Random();
        if (rand.nextInt(100) < 85) {
            int dano = (int) (this.stats.getFuerza() * 1.25);
            System.out.println(this.nombre + " ataca y causa " + dano + " de dano fisico.");
            cloud.recibirDanio(dano);
        } else {
            System.out.println(this.nombre + " intento atacar, pero fallo.");
        }
    }

    /**
     * Retorna la chatarra que suelta este enemigo al ser derrotado.
     *
     * @return cantidad de chatarra de recompensa
     */
    @Override
    public int getChatarraRecompensa() {
        return chatarraRecompensa;
    }

    public Tipo getTipo() { return tipo; }
}
