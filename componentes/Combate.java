package componentes;

import entidades.Enemigo;
import entidades.Jugador;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Combate {

    private final Jugador cloud;
    private final List<Enemigo> enemigos;
    private final boolean puedeHuir;
    private final Scanner scanner;

    /**
     * Crea una instancia de combate entre Cloud y un grupo de enemigos.
     *
     * @param cloud     el jugador
     * @param enemigos  lista de enemigos que participan en el combate
     * @param puedeHuir true si se permite huir, false en el combate final
     * @param scanner   Scanner compartido para leer entrada del usuario
     */
    public Combate(Jugador cloud, List<Enemigo> enemigos, boolean puedeHuir, Scanner scanner) {
        this.cloud = cloud;
        this.enemigos = enemigos;
        this.puedeHuir = puedeHuir;
        this.scanner = scanner;
    }

    /**
     * Ejecuta el bucle de combate completo alternando turno del jugador y turno de enemigos.
     *
     * @return true si Cloud sobrevivio (gano o huyo), false si fue derrotado
     */
    public boolean iniciar() {
        System.out.println("\n=== COMBATE INICIADO ===");
        for (Enemigo e : enemigos) {
            System.out.println("  - " + e.getNombre()
                    + " (HP: " + e.getStats().getHpActual() + ")");
        }

        while (hayEnemigosVivos() && cloud.getStats().getHpActual() > 0) {
            mostrarEstado();
            boolean huyo = turnoJugador();
            if (huyo) return true;

            if (!hayEnemigosVivos()) break;

            turnoEnemigos();

            if (cloud.getStats().getHpActual() <= 0) {
                System.out.println("\nCloud ha sido derrotado...");
                return false;
            }
        }

        if (hayEnemigosVivos()) return false;

        System.out.println("\n=== VICTORIA! ===");
        darRecompensas();
        return true;
    }

    /**
     * Muestra el estado actual de Cloud (HP, MP, Limite) y de todos los enemigos vivos.
     */
    private void mostrarEstado() {
        System.out.println("\n--- Estado de " + cloud.getNombre() + " ---");
        System.out.println("  HP: " + cloud.getStats().getHpActual()
                + "/" + cloud.getStats().getHpMaximo());
        System.out.println("  MP: " + cloud.getStats().getMpActual()
                + "/" + cloud.getStats().getMpMaximo());
        System.out.println("  Limite: " + cloud.getLimiteActual() + "/100");
        System.out.println("--- Enemigos ---");
        for (Enemigo e : enemigos) {
            if (e.getStats().getHpActual() > 0) {
                System.out.println("  " + e.getNombre()
                        + " HP: " + e.getStats().getHpActual());
            }
        }
    }

    /**
     * Gestiona el turno del jugador mostrando el menu de acciones y ejecutando la elegida.
     *
     * @return true si el jugador huyo exitosamente del combate
     */
    private boolean turnoJugador() {
        Enemigo objetivo = primerEnemigoVivo();
        boolean tieneMagia = !cloud.getBusterSword().getMateriasEquipadas().isEmpty();

        System.out.println("\nAcciones:");
        System.out.println("  1) Ataque fisico");
        if (tieneMagia)                     System.out.println("  2) Magia");
        if (cloud.getLimiteActual() >= 100)  System.out.println("  3) Ataque Limite!");
        if (puedeHuir)                       System.out.println("  4) Huir");
        System.out.print("Elige: ");

        String input = scanner.nextLine().trim();

        switch (input) {
            case "1":
                cloud.atacarFisico(objetivo);
                return false;
            case "2":
                if (!tieneMagia) {
                    System.out.println("Opcion invalida.");
                    return turnoJugador();
                }
                return menuMagia(objetivo);
            case "3":
                if (cloud.getLimiteActual() < 100) {
                    System.out.println("Opcion invalida.");
                    return turnoJugador();
                }
                cloud.usarAtaqueLimite(objetivo);
                return false;
            case "4":
                if (!puedeHuir) {
                    System.out.println("No puedes huir de este combate!");
                    return turnoJugador();
                }
                return intentarHuir();
            default:
                System.out.println("Opcion invalida.");
                return turnoJugador();
        }
    }

    /**
     * Muestra el submenu de materias equipadas y ejecuta el hechizo o curacion elegida.
     *
     * @param objetivo el enemigo sobre el que se lanza la magia
     * @return false siempre, la magia no termina el combate
     */
    private boolean menuMagia(Enemigo objetivo) {
        List<Materia> materias = cloud.getBusterSword().getMateriasEquipadas();
        System.out.println("Que magia usas?");
        for (int i = 0; i < materias.size(); i++) {
            Materia m = materias.get(i);
            int costo = cloud.getBusterSword().costoMP(m.getElemento());
            System.out.println("  " + (i + 1) + ") " + m.getNombre()
                    + " [" + m.getElemento() + "] (Costo: " + costo + " MP)");
        }
        System.out.println("  0) Volver");
        System.out.print("Elige: ");
        String input = scanner.nextLine().trim();

        if (input.equals("0")) return turnoJugador();

        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx < 0 || idx >= materias.size()) {
                System.out.println("Opcion invalida.");
                return menuMagia(objetivo);
            }
            Materia elegida = materias.get(idx);
            if (elegida.getElemento() == Elemento.CURA) {
                cloud.curar();
            } else {
                cloud.lanzarMagia(elegida.getElemento(), objetivo);
            }
        } catch (NumberFormatException e) {
            System.out.println("Opcion invalida.");
            return menuMagia(objetivo);
        }
        return false;
    }

    /**
     * Intenta huir del combate con 50% de probabilidad de exito.
     *
     * @return true si Cloud huyo, false si el intento fallo
     */
    private boolean intentarHuir() {
        if (new Random().nextBoolean()) {
            System.out.println("Cloud huye del combate!");
            return true;
        }
        System.out.println("No pudiste escapar!");
        return false;
    }

    /**
     * Ejecuta el ataque de los enemigos vivos sobre Cloud.
     * Con 1 enemigo siempre ataca; con 2 hay 50% de ataque conjunto; con 3 hay 33%.
     * Si no atacan juntos, solo ataca uno aleatorio.
     */
    private void turnoEnemigos() {
        List<Enemigo> vivos = new ArrayList<>();
        for (Enemigo e : enemigos) {
            if (e.getStats().getHpActual() > 0) vivos.add(e);
        }
        if (vivos.isEmpty()) return;

        int n = vivos.size();
        boolean ataquenJuntos;
        if (n == 1) {
            ataquenJuntos = true;
        } else if (n == 2) {
            ataquenJuntos = new Random().nextInt(100) < 50;
        } else {
            ataquenJuntos = new Random().nextInt(100) < 33;
        }

        if (ataquenJuntos) {
            for (Enemigo e : vivos) {
                if (cloud.getStats().getHpActual() > 0) e.atacar(cloud);
            }
        } else {
            Enemigo atacante = vivos.get(new Random().nextInt(vivos.size()));
            atacante.atacar(cloud);
        }
    }

    /**
     * Entrega XP y chatarra de cada enemigo derrotado al jugador.
     */
    private void darRecompensas() {
        for (Enemigo e : enemigos) {
            int xp = e.getXpRecompensa();
            int chatarra = e.getChatarraRecompensa();
            if (xp > 0) {
                System.out.println("Obtienes " + xp + " XP de " + e.getNombre() + ".");
                cloud.recibirXP(xp);
            }
            if (chatarra > 0) {
                System.out.println("Obtienes " + chatarra + " chatarra de " + e.getNombre() + ".");
                cloud.recibirChatarra(chatarra);
            }
        }
    }

    /**
     * Indica si al menos un enemigo de la lista sigue con HP mayor que cero.
     *
     * @return true si hay al menos un enemigo vivo
     */
    private boolean hayEnemigosVivos() {
        for (Enemigo e : enemigos) {
            if (e.getStats().getHpActual() > 0) return true;
        }
        return false;
    }

    /**
     * Retorna el primer enemigo de la lista que aun tiene HP mayor que cero.
     *
     * @return el primer Enemigo vivo
     */
    private Enemigo primerEnemigoVivo() {
        for (Enemigo e : enemigos) {
            if (e.getStats().getHpActual() > 0) return e;
        }
        return enemigos.get(0);
    }
}
