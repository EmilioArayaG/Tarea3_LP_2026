package entidades;

import componentes.Estadisticas;
import java.util.Random;

public class EnemigoSimulador extends Enemigo {

    /**
     * Crea un Soldado comun con HP 50, Fuerza 15 y XP aleatoria entre 15 y 20.
     *
     * @param void
     */
    public EnemigoSimulador() {
        this.nombre = "Soldado comun";
        this.stats = new Estadisticas(50, 0, 15, 0);
        this.chatarraRecompensa = 0;
        this.xpRecompensa = new Random().nextInt(6) + 15;
    }

    /**
     * Ataca al jugador con 85% de precision. El dano es piso(Fuerza * 1.25).
     * Nunca reduce el HP de Cloud por debajo de 1.
     *
     * @param cloud el jugador que recibe el ataque
     */
    @Override
    public void atacar(Jugador cloud) {
        Random rand = new Random();
        if (rand.nextInt(100) < 85) {
            int dano = (int) (this.stats.getFuerza() * 1.25);
            if (cloud.getStats().getHpActual() - dano <= 0) {
                dano = cloud.getStats().getHpActual() - 1;
            }
            System.out.println(this.nombre + " ataca y causa " + dano + " de dano.");
            cloud.recibirDanio(dano);
        } else {
            System.out.println(this.nombre + " intento atacar, pero fallo.");
        }
    }
}
