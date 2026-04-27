package entidades;

import componentes.Elemento;
import componentes.Estadisticas;
import componentes.Vulnerable;
import java.util.Random;

/**
 * Representa a los enemigos salvajes del mundo: Planta Carnivora,
 * Sapo de la Jungla y Robot Centinela. Implementa Vulnerable para
 * que sus debilidades/resistencias/inmunidades sean evaluadas en combate.
 */
public class EnemigoSalvaje extends Enemigo implements Vulnerable {

    /** Tipos posibles de enemigo salvaje. */
    public enum Tipo { PLANTA_CARNIVORA, SAPO_JUNGLA, ROBOT_CENTINELA }

    private Tipo tipo;

    /**
     * Crea un EnemigoSalvaje del tipo indicado, configurando sus stats y recompensas.
     *
     * @param tipo el tipo de enemigo a crear
     */
    public EnemigoSalvaje(Tipo tipo) {
        this.tipo = tipo;
        Random rand = new Random();
        this.xpRecompensa = rand.nextInt(21) + 80;        // 80-100
        this.chatarraRecompensa = rand.nextInt(26) + 50;  // 50-75

        switch (tipo) {
            case PLANTA_CARNIVORA:
                this.nombre = "Planta Carnivora";
                this.stats = new Estadisticas(80, 0, 15, 0);
                break;
            case SAPO_JUNGLA:
                this.nombre = "Sapo de la Jungla";
                this.stats = new Estadisticas(60, 0, 12, 0);
                break;
            case ROBOT_CENTINELA:
                this.nombre = "Robot Centinela";
                this.stats = new Estadisticas(100, 0, 20, 0);
                break;
        }
    }

    /**
     * Evalua el multiplicador de dano magico segun el elemento recibido.
     * Debilidades (x2), resistencias (x0.5) e inmunidades (x0) dependen del tipo.
     *
     * @param elementoMagia el elemento del ataque magico
     * @return multiplicador de dano (2.0, 1.0, 0.5 o 0.0)
     */
    @Override
    public double evaluarDebilidad(Elemento elementoMagia) {
        switch (tipo) {
            case PLANTA_CARNIVORA:
                if (elementoMagia == Elemento.FUEGO) return 2.0;
                if (elementoMagia == Elemento.HIELO) return 2.0;
                if (elementoMagia == Elemento.RAYO)  return 0.0;
                return 1.0;
            case SAPO_JUNGLA:
                if (elementoMagia == Elemento.RAYO)  return 2.0;
                if (elementoMagia == Elemento.HIELO) return 2.0;
                if (elementoMagia == Elemento.FUEGO) return 0.5;
                return 1.0;
            case ROBOT_CENTINELA:
                if (elementoMagia == Elemento.RAYO)  return 2.0;
                if (elementoMagia == Elemento.FISICO) return 0.5;
                if (elementoMagia == Elemento.HIELO) return 0.5;
                return 1.0;
            default:
                return 1.0;
        }
    }

    /**
     * Realiza un ataque fisico al jugador con 85% de precision.
     * El dano es piso(Fuerza * 1.25).
     *
     * @param cloud el jugador que recibe el ataque
     */
    @Override
    public void atacar(Jugador cloud) {
        Random rand = new Random();
        if (rand.nextInt(100) < 85) {
            int dano;
            if (tipo == Tipo.ROBOT_CENTINELA) {
                dano = (int) (this.stats.getFuerza() * 1.25 * 0.5);
            } else {
                dano = (int) (this.stats.getFuerza() * 1.25);
            }
            System.out.println(this.nombre + " ataca y causa " + dano + " de dano fisico.");
            cloud.recibirDanio(dano);
        } else {
            System.out.println(this.nombre + " intento atacar, pero fallo.");
        }
    }

    /**
     * Retorna la cantidad de chatarra que da este enemigo al ser derrotado.
     *
     * @return chatarra de recompensa
     */
    public int getChatarraRecompensa() {
        return chatarraRecompensa;
    }

    /**
     * Retorna el tipo de este enemigo salvaje.
     *
     * @return tipo del enemigo
     */
    public Tipo getTipo() {
        return tipo;
    }
}
