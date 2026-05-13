package mapa;

import componentes.Combate;
import entidades.Enemigo;
import entidades.Jugador;
import entidades.Sephiroth;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NucleoPlaneta extends Zona {

    private final Scanner scanner;
    private final int materiasMinimasRequeridas;

    /**
     * Crea la zona Nucleo del Planeta con nivel requerido 20 y minimo 2 materias equipadas.
     *
     * @param scanner Scanner compartido para leer la entrada del usuario
     */
    public NucleoPlaneta(Scanner scanner) {
        this.nombre = "Nucleo del Planeta";
        this.nivelRequerido = 20;
        this.materiasMinimasRequeridas = 2;
        this.scanner = scanner;
    }

    /**
     * Valida los requisitos de acceso (nivel 20 y al menos 2 materias equipadas) e inicia
     * el combate final contra Sephiroth sin posibilidad de huir.
     * Si Cloud gana imprime el mensaje de victoria y termina el programa.
     * Si Cloud muere aplica la penalizacion de muerte.
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
        if (materiasEquipadas < materiasMinimasRequeridas) {
            System.out.println("Necesitas al menos " + materiasMinimasRequeridas + " materias equipadas. (Equipadas: " + materiasEquipadas + ")");
            return;
        }

        System.out.println("Una presencia oscura llena el aire...");
        System.out.println("Sephiroth aparece ante ti. No hay vuelta atras.");
        System.out.print("Presiona ENTER para comenzar el combate final...");
        scanner.nextLine();

        this.iniciarCombate(cloud);
    }

    /**
     * Instancia a Sephiroth, crea el combate final sin posibilidad de huir y lo ejecuta.
     * Si Cloud gana termina el programa con System.exit(0).
     * Si Cloud pierde aplica la penalizacion de muerte llamando a cloud.morir().
     *
     * @param cloud el jugador que enfrenta el combate final contra Sephiroth
     */
    public void iniciarCombate(Jugador cloud) {
        Sephiroth sephiroth = new Sephiroth();
        List<Enemigo> grupo = new ArrayList<>();
        grupo.add(sephiroth);

        Combate combate = new Combate(cloud, grupo, false, scanner, false);
        boolean sobrevivio = combate.iniciar();

        if (sobrevivio) {
            System.out.println("\n============ Fin Del Juego! ============");
            System.exit(0);
        } else {
            cloud.morir();
        }
    }
}
