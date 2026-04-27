package mapa;

import componentes.Combate;
import componentes.Elemento;
import componentes.Materia;
import entidades.Enemigo;
import entidades.EnemigoSalvaje;
import entidades.Jugador;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Zona de jungla con nivel requerido 5. Al ingresar hay 30% de encontrar
 * una Materia de elemento aleatorio y 70% de ser emboscado por enemigos salvajes.
 */
public class Gongaga extends Zona {

    private final Scanner scanner;

    /**
     * Crea la zona Gongaga con nivel requerido 5.
     *
     * @param scanner Scanner compartido para leer la entrada del usuario
     */
    public Gongaga(Scanner scanner) {
        this.nombre = "Gongaga";
        this.nivelRequerido = 5;
        this.scanner = scanner;
    }

    /**
     * Ejecuta la accion de la zona: 30% probabilidad de encontrar una Materia,
     * 70% de ser emboscado. Si Cloud muere, pierde mochila y chatarra.
     *
     * @param cloud el jugador que explora la zona
     */
    @Override
    public void accionZona(Jugador cloud) {
        System.out.println("\n=== GONGAGA — JUNGLA SALVAJE ===");
        Random rand = new Random();

        if (rand.nextInt(100) < 30) {
            encontrarMateria(cloud);
        } else {
            System.out.println("Emboscada! Los enemigos te atacan!");
            List<Enemigo> grupo = generarGrupoEnemigo(rand);
            Combate combate = new Combate(cloud, grupo, true, scanner);
            boolean sobrevivio = combate.iniciar();
            if (!sobrevivio) {
                cloud.morir();
            }
        }
    }

    /**
     * Genera una Materia con elemento aleatorio (FUEGO, HIELO, RAYO o CURA)
     * y la agrega a la mochila del jugador.
     *
     * @param cloud el jugador que recoge la materia
     */
    private void encontrarMateria(Jugador cloud) {
        Elemento[] elementos = { Elemento.FUEGO, Elemento.HIELO, Elemento.RAYO, Elemento.CURA };
        Elemento elegido = elementos[new Random().nextInt(elementos.length)];
        Materia nueva = new Materia("Materia " + elegido, elegido);
        cloud.getMochila().add(nueva);
        System.out.println("Encontraste una Materia de " + elegido + "! Se agrego a tu mochila.");
    }

    /**
     * Genera el grupo de enemigos para la emboscada.
     * 60% de probabilidad de 1 enemigo, 30% de 2, 10% de 3.
     * Cada enemigo es un EnemigoSalvaje de tipo aleatorio.
     *
     * @param rand instancia de Random a reutilizar
     * @return lista con los enemigos generados
     */
    private List<Enemigo> generarGrupoEnemigo(Random rand) {
        int roll = rand.nextInt(100);
        int cantidad = (roll < 60) ? 1 : (roll < 90) ? 2 : 3;

        EnemigoSalvaje.Tipo[] tipos = EnemigoSalvaje.Tipo.values();
        List<Enemigo> grupo = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            grupo.add(new EnemigoSalvaje(tipos[rand.nextInt(tipos.length)]));
        }

        System.out.println("Aparecen " + cantidad + " enemigo(s):");
        for (Enemigo e : grupo) {
            System.out.println("  - " + e.getNombre());
        }
        return grupo;
    }
}
