import componentes.Materia;
import entidades.Jugador;
import mapa.Gongaga;
import mapa.NucleoPlaneta;
import mapa.Sector7;
import mapa.Zona;
import java.util.Scanner;

public class Main {

    /**
     * Inicia el juego creando a Cloud, las tres zonas y el bucle principal de navegacion.
     *
     * @param args argumentos de linea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Jugador cloud = new Jugador();

        Sector7       sector7 = new Sector7(scanner);
        Gongaga       gongaga = new Gongaga(scanner);
        NucleoPlaneta nucleo  = new NucleoPlaneta(scanner);

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       FINAL FANTASY VII — RPG        ║");
        System.out.println("╚══════════════════════════════════════╝");

        boolean jugando = true;
        while (jugando) {
            mostrarEstadoJugador(cloud);
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("  1) Ir a Sector 7       (nivel req: 1)");
            System.out.println("  2) Ir a Gongaga        (nivel req: 5)");
            System.out.println("  3) Ir al Nucleo        (nivel req: 20)");
            System.out.println("  4) Gestionar mochila / equipar materias");
            System.out.println("  5) Salir");
            System.out.print("Elige: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": viajarA(cloud, sector7); break;
                case "2": viajarA(cloud, gongaga); break;
                case "3": viajarA(cloud, nucleo);  break;
                case "4": menuInventario(cloud, scanner); break;
                case "5":
                    jugando = false;
                    System.out.println("Hasta la proxima, Cloud.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
        scanner.close();
    }

    /**
     * Valida el nivel requerido y ejecuta la accion de la zona si Cloud cumple el requisito.
     *
     * @param cloud el jugador que viaja
     * @param zona  la zona de destino
     */
    private static void viajarA(Jugador cloud, Zona zona) {
        if (!zona.validarAcceso(cloud)) {
            System.out.println("Necesitas nivel " + zona.getNivelRequerido()
                    + " para ir a " + zona.getNombre()
                    + ". (Nivel actual: " + cloud.getNivel() + ")");
            return;
        }
        zona.accionZona(cloud);
    }

    /**
     * Muestra la mochila y las materias equipadas, y permite equipar una materia de la mochila al arma.
     *
     * @param cloud   el jugador
     * @param scanner Scanner compartido para leer entrada
     */
    private static void menuInventario(Jugador cloud, Scanner scanner) {
        System.out.println("\n=== INVENTARIO ===");
        System.out.println("Chatarra: " + cloud.getChatarra());

        System.out.println("\n-- Materias en mochila --");
        if (cloud.getMochila().isEmpty()) {
            System.out.println("  (vacia)");
        } else {
            for (int i = 0; i < cloud.getMochila().size(); i++) {
                Materia m = cloud.getMochila().get(i);
                System.out.println("  " + (i + 1) + ") " + m.getNombre()
                        + " [" + m.getElemento() + "]");
            }
        }

        System.out.println("\n-- Materias equipadas en Buster Sword ("
                + cloud.getBusterSword().getMateriasEquipadas().size() + "/5) --");
        if (cloud.getBusterSword().getMateriasEquipadas().isEmpty()) {
            System.out.println("  (ninguna)");
        } else {
            for (Materia m : cloud.getBusterSword().getMateriasEquipadas()) {
                System.out.println("  - " + m.getNombre() + " [" + m.getElemento() + "]");
            }
        }

        if (!cloud.getMochila().isEmpty()) {
            System.out.println("\nEquipar materia de mochila? (numero o 0 para volver)");
            System.out.print("Elige: ");
            String input = scanner.nextLine().trim();
            if (!input.equals("0")) {
                try {
                    int idx = Integer.parseInt(input) - 1;
                    if (idx >= 0 && idx < cloud.getMochila().size()) {
                        Materia elegida = cloud.getMochila().get(idx);
                        if (cloud.getBusterSword().equiparMateria(elegida)) {
                            cloud.getMochila().remove(idx);
                            System.out.println("Materia " + elegida.getNombre() + " equipada.");
                        } else {
                            System.out.println("La Buster Sword ya tiene 5 materias equipadas.");
                        }
                    } else {
                        System.out.println("Opcion invalida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Opcion invalida.");
                }
            }
        }
    }

    /**
     * Muestra un resumen del estado actual de Cloud en el menu principal.
     *
     * @param cloud el jugador cuyo estado se muestra
     */
    private static void mostrarEstadoJugador(Jugador cloud) {
        System.out.println("\n--- " + cloud.getNombre()
                + " | Nivel " + cloud.getNivel()
                + " | HP " + cloud.getStats().getHpActual() + "/" + cloud.getStats().getHpMaximo()
                + " | MP " + cloud.getStats().getMpActual() + "/" + cloud.getStats().getMpMaximo()
                + " | Chatarra " + cloud.getChatarra() + " ---");
    }
}
