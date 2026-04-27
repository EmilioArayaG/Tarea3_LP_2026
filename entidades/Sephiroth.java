package entidades;

import componentes.Estadisticas;
import java.util.Random;

/**
 * El jefe final del juego. No implementa Vulnerable: todos los multiplicadores
 * magicos son 1.0. Posee un contador de SuperNova que, al llegar a 10,
 * ejecuta un ataque instantaneo que derrota a Cloud.
 */
public class Sephiroth extends Enemigo {

    private int contadorSuperNova;

    /** Crea a Sephiroth con sus stats fijas (HP 500, Fuerza 40). */
    public Sephiroth() {
        this.nombre = "Sephiroth";
        this.stats = new Estadisticas(500, 0, 40, 0);
        this.xpRecompensa = 0;
        this.chatarraRecompensa = 0;
        this.contadorSuperNova = 0;
    }

    /**
     * Lanza SuperNova, dejando a Cloud con 0 HP inmediatamente.
     *
     * @param cloud el jugador objetivo
     */
    public void lanzarSuperNova(Jugador cloud) {
        System.out.println("\n*** SEPHIROTH carga energia cosmica... ***");
        System.out.println("*** SUPERNOVA! El destino de la planeta se sella... ***");
        cloud.getStats().recibirDMG(cloud.getStats().getHpActual());
    }

    /**
     * Realiza el turno de Sephiroth: ataque fisico con 90% de precision.
     * Cada turno incrementa el contadorSuperNova; al llegar a 10 lanza SuperNova.
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
     * Retorna el valor actual del contador de SuperNova.
     *
     * @return contadorSuperNova (0-10)
     */
    public int getContadorSuperNova() {
        return contadorSuperNova;
    }

    public void resetContadorSuperNova() { this.contadorSuperNova = 0; }
}
