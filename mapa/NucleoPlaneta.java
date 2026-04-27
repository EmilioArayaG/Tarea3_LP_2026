package mapa;

import componentes.Combate;
import entidades.Enemigo;
import entidades.Jugador;
import entidades.Sephiroth;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Zona final del juego. Requiere nivel 20 y al menos 2 materias equipadas.
 * El combate contra Sephiroth es obligatorio: no se puede huir.
 * La victoria termina el programa.
 */
public class NucleoPlaneta extends Zona {

    private final Scanner scanner;

    /**
     * Crea la zona Nucleo del Planeta con nivel requerido 20.
     *
     * @param scanner Scanner compartido para leer la entrada del usuario
     */
    public NucleoPlaneta(Scanner scanner) {
        this.nombre = "Nucleo del Planeta";
        this.nivelRequerido = 20;
        this.scanner = scanner;
    }

    /**
     * Valida que Cloud cumpla los requisitos (nivel 20 y al menos 2 materias equipadas)
     * e inicia el combate final contra Sephiroth sin posibilidad de huir.
     * Si Cloud gana, imprime el mensaje final y termina el programa.
     * Si Cloud muere, pierde mochila y chatarra.
     *
     * @param cloud el jugador que enfrenta el combate final
     */
    @Override
    public void accionZona(Jugador cloud) {
        System.out.println("\n=== NUCLEO DEL PLANETA ===");

        if (cloud.getNivel() < 20) {
            System.out.println("Necesitas nivel 20 para acceder. (Nivel actual: "
                    + cloud.getNivel() + ")");
            return;
        }

        int materiasEquipadas = cloud.getBusterSword().getMateriasEquipadas().size();
        if (materiasEquipadas < 2) {
            System.out.println("Necesitas al menos 2 materias equipadas. (Equipadas: "
                    + materiasEquipadas + ")");
            return;
        }

        System.out.println("Una presencia oscura llena el aire...");
        System.out.println("Sephiroth aparece ante ti. No hay vuelta atras.");
        System.out.print("Presiona ENTER para comenzar el combate final...");
        scanner.nextLine();

        Sephiroth sephiroth = new Sephiroth();
        List<entidades.Enemigo> grupo = new ArrayList<>();
        grupo.add(sephiroth);

        Combate combate = new Combate(cloud, grupo, false, scanner);
        boolean sobrevivio = combate.iniciar();

        if (sobrevivio) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║         FINAL FANTASY VII            ║");
            System.out.println("║                                      ║");
            System.out.println("║  Cloud derroto a Sephiroth.          ║");
            System.out.println("║  El Planeta esta a salvo.            ║");
            System.out.println("║                                      ║");
            System.out.println("║          FIN DEL JUEGO               ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.exit(0);
        } else {
            cloud.morir();
        }
    }
}
