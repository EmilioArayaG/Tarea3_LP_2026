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

public class Gongaga extends Zona {

    private final Scanner scanner;
    private final List<Materia> poolMaterias;

    /**
     * Crea la zona Gongaga con nivel requerido 5 e inicializa el pool de materias encontrables.
     *
     * @param scanner Scanner compartido para leer la entrada del usuario
     */
    public Gongaga(Scanner scanner) {
        this.nombre = "Gongaga";
        this.nivelRequerido = 5;
        this.scanner = scanner;
        this.poolMaterias = new ArrayList<>();
        this.poolMaterias.add(new Materia("Materia FUEGO", Elemento.FUEGO));
        this.poolMaterias.add(new Materia("Materia HIELO", Elemento.HIELO));
        this.poolMaterias.add(new Materia("Materia RAYO",  Elemento.RAYO));
        this.poolMaterias.add(new Materia("Materia CURA",  Elemento.CURA));
    }

    /**
     * Ejecuta la accion de la zona: 30% de probabilidad de encontrar una Materia,
     * 70% de ser emboscado por enemigos salvajes. Si Cloud muere, pierde mochila y chatarra.
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
     * Selecciona una Materia aleatoria del pool y la agrega a la mochila del jugador.
     *
     * @param cloud el jugador que recoge la materia
     */
    private void encontrarMateria(Jugador cloud) {
        Materia elegida = poolMaterias.get(new Random().nextInt(poolMaterias.size()));
        Materia nueva = new Materia(elegida.getNombre(), elegida.getElemento());
        cloud.getMochila().add(nueva);
        System.out.println("Encontraste una " + nueva.getNombre() + "! Se agrego a tu mochila.");
    }

    /**
     * Genera el grupo de enemigos para la emboscada con probabilidades 60/30/10 para 1/2/3 enemigos.
     * Cada enemigo es un EnemigoSalvaje de tipo aleatorio.
     *
     * @param rand instancia de Random a reutilizar
     * @return lista con los enemigos generados
     */
    public List<Enemigo> generarGrupoEnemigo(Random rand) {
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
