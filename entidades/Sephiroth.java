package entidades;

import componentes.Estadisticas;
import java.util.Random;

public class Sephiroth extends Enemigo {

    private int contadorSuperNova;

    /**
     * Crea a Sephiroth con HP 500, Fuerza 40 y contador de SuperNova en 0.
     *
     * @param void
     */
    public Sephiroth() {
        this.nombre = "Sephiroth";
        this.stats = new Estadisticas(500, 0, 40, 0);
        this.xpRecompensa = 0;
        this.chatarraRecompensa = 0;
        this.contadorSuperNova = 0;
    }

    /**
     * Lanza SuperNova, reduciendo el HP de Cloud a 0 de forma instantanea.
     *
     * @param cloud el jugador objetivo
     */
    public void lanzarSuperNova(Jugador cloud) {
        System.out.println("\n*** SEPHIROTH carga energia cosmica... ***");
        System.out.println("*** SUPERNOVA! El destino de la planeta se sella... ***");
        cloud.getStats().recibirDMG(cloud.getStats().getHpActual());
    }

    /**
     * Realiza el turno de Sephiroth: incrementa el contador de SuperNova y ataca con 90% de precision.
     * Si el contador llega a 10, ejecuta SuperNova en lugar del ataque normal.
     *
     * @param cloud el jugador que recibe el ataque
     */
    @Override
    public void atacar(Jugador cloud) {
        contadorSuperNova++;
        if (contadorSuperNova >= 10) {
            lanzarSuperNova(cloud);
            return;
        }

        Random rand = new Random();
        if (rand.nextInt(100) < 90) {
            int dano = (int) (this.stats.getFuerza() * 1.25);
            System.out.println("Sephiroth ataca con Masamune y causa " + dano + " de dano.");
            cloud.recibirDanio(dano);
        } else {
            System.out.println("Sephiroth prepara su proxima jugada...");
        }
        System.out.println("  [Carga SuperNova: " + contadorSuperNova + "/10]");
    }

    /**
     * Reinicia el contador de SuperNova a 0, invocado cuando Cloud ejecuta un ataque Limite.
     *
     * @param void
     */
    public void resetContadorSuperNova() { this.contadorSuperNova = 0; }

    public int getContadorSuperNova() { return contadorSuperNova; }
}
