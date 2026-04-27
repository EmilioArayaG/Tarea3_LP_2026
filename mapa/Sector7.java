package mapa;

import componentes.Combate;
import componentes.Mejora;
import componentes.TipoStat;
import entidades.Enemigo;
import entidades.EnemigoSimulador;
import entidades.Jugador;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Zona inicial del juego. Permite al jugador entrenarse en el simulador
 * o comprar mejoras en la tienda a cambio de chatarra.
 */
public class Sector7 extends Zona {

    private final List<Mejora> tiendaLocal;
    private final Scanner scanner;

    /**
     * Crea la zona Sector 7 con las tres mejoras disponibles en tienda.
     *
     * @param scanner Scanner compartido para leer la entrada del usuario
     */
    public Sector7(Scanner scanner) {
        this.nombre = "Sector 7";
        this.nivelRequerido = 1;
        this.scanner = scanner;
        this.tiendaLocal = new ArrayList<>();
        this.tiendaLocal.add(new Mejora("Mejora de Vitalidad",  100, TipoStat.HP_MAX,  20));
        this.tiendaLocal.add(new Mejora("Mejora de Concentracion", 80, TipoStat.MP_MAX, 15));
        this.tiendaLocal.add(new Mejora("Mejora de Fuerza",     120, TipoStat.FUERZA,   5));
    }

    /**
     * Muestra el menu principal del Sector 7: simulador de combate o tienda.
     *
     * @param cloud el jugador que interactua con la zona
     */
    @Override
    public void accionZona(Jugador cloud) {
        System.out.println("\n=== SECTOR 7 ===");
        System.out.println("  1) Simulador de combate");
        System.out.println("  2) Tienda de mejoras");
        System.out.println("  3) Volver");
        System.out.print("Elige: ");
        String input = scanner.nextLine().trim();

        switch (input) {
            case "1": iniciarSimulador(cloud); break;
            case "2": abrirTienda(cloud);      break;
            case "3": break;
            default:
                System.out.println("Opcion invalida.");
                accionZona(cloud);
        }
    }

    /**
     * Genera 1 o 2 EnemigoSimulador aleatoriamente e inicia el combate de entrenamiento.
     *
     * @param cloud el jugador que entra al simulador
     */
    private void iniciarSimulador(Jugador cloud) {
        Random rand = new Random();
        int cantidad = rand.nextBoolean() ? 1 : 2;
        List<Enemigo> grupo = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            grupo.add(new EnemigoSimulador());
        }
        System.out.println("\nEntrando al simulador con " + cantidad + " enemigo(s)...");
        Combate combate = new Combate(cloud, grupo, true, scanner);
        boolean sobrevivio = combate.iniciar();
        if (!sobrevivio) {
            cloud.morir();
        }
    }

    /**
     * Muestra las mejoras disponibles, descuenta chatarra y aplica el bonus al jugador.
     *
     * @param cloud el jugador que usa la tienda
     */
    private void abrirTienda(Jugador cloud) {
        System.out.println("\n=== TIENDA DE MEJORAS === (Chatarra: " + cloud.getChatarra() + ")");
        for (int i = 0; i < tiendaLocal.size(); i++) {
            Mejora m = tiendaLocal.get(i);
            System.out.println("  " + (i + 1) + ") " + m.getNombre()
                    + " | +" + m.getValorBono() + " " + m.getStatAfectado()
                    + " | Costo: " + m.getCostoChatarra() + " chatarra");
        }
        System.out.println("  0) Volver");
        System.out.print("Elige: ");
        String input = scanner.nextLine().trim();

        if (input.equals("0")) return;

        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx < 0 || idx >= tiendaLocal.size()) {
                System.out.println("Opcion invalida.");
                abrirTienda(cloud);
                return;
            }
            Mejora elegida = tiendaLocal.get(idx);
            if (cloud.getChatarra() < elegida.getCostoChatarra()) {
                System.out.println("No tienes suficiente chatarra! (Necesitas "
                        + elegida.getCostoChatarra() + ")");
            } else {
                cloud.setChatarra(cloud.getChatarra() - elegida.getCostoChatarra());
                aplicarMejora(cloud, elegida);
                System.out.println("Mejora aplicada: " + elegida.getNombre());
            }
        } catch (NumberFormatException e) {
            System.out.println("Opcion invalida.");
        }
        abrirTienda(cloud);
    }

    /**
     * Aplica el bonus de una mejora a las estadisticas del jugador segun el stat afectado.
     *
     * @param cloud   el jugador que recibe la mejora
     * @param mejora  la mejora a aplicar
     */
    private void aplicarMejora(Jugador cloud, Mejora mejora) {
        switch (mejora.getStatAfectado()) {
            case HP_MAX:
                cloud.getStats().setHpMaximo(cloud.getStats().getHpMaximo() + mejora.getValorBono());
                cloud.getStats().setHpActual(cloud.getStats().getHpActual() + mejora.getValorBono());
                break;
            case MP_MAX:
                cloud.getStats().setMpMaximo(cloud.getStats().getMpMaximo() + mejora.getValorBono());
                cloud.getStats().setMpActual(cloud.getStats().getMpActual() + mejora.getValorBono());
                break;
            case FUERZA:
                cloud.getStats().setFuerza(cloud.getStats().getFuerza() + mejora.getValorBono());
                break;
        }
    }
}
