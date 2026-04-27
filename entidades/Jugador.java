package entidades;

import componentes.Estadisticas;
import componentes.Materia;
import componentes.Elemento;
import componentes.Vulnerable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a Cloud Strife, el jugador principal. Gestiona su inventario,
 * estadisticas, barra de limite y progresion de nivel.
 */
public class Jugador {
    private String nombre;
    private int nivel;
    private int xpActual;
    private int chatarra;
    private int limiteActual;
    private final Estadisticas stats;
    private final List<Materia> mochila;
    private final Arma busterSword;

    /** Crea a Cloud con sus valores iniciales de nivel 1. */
    public Jugador() {
        this.nombre = "Cloud";
        this.nivel = 1;
        this.xpActual = 0;
        this.chatarra = 0;
        this.limiteActual = 0;
        this.stats = new Estadisticas(200, 50, 15, 15);
        this.mochila = new ArrayList<>();
        this.busterSword = new Arma();
    }

    /**
     * Suma XP y sube de nivel automaticamente mientras se alcance el umbral.
     *
     * @param xp cantidad de experiencia a agregar
     */
    public void recibirXP(int xp) {
        this.xpActual += xp;
        int xpNecesaria = 10 * this.nivel;
        while (this.xpActual >= xpNecesaria) {
            this.xpActual -= xpNecesaria;
            subirNivel();
            xpNecesaria = 10 * this.nivel;
        }
    }

    /**
     * Incrementa el nivel y mejora las estadisticas base.
     */
    private void subirNivel() {
        this.nivel++;
        this.stats.setHpMaximo(this.stats.getHpMaximo() + 10);
        this.stats.setMpMaximo(this.stats.getMpMaximo() + 5);
        this.stats.setFuerza(this.stats.getFuerza() + 4);
        this.stats.setMagia(this.stats.getMagia() + 6);
        System.out.println(this.nombre + " ha subido al nivel " + this.nivel + "!");
    }

    /**
     * Realiza un ataque fisico sobre un enemigo. El dano es piso(Fuerza*1.25).
     * Carga la barra de limite con piso(dano/5).
     *
     * @param enemigo el enemigo objetivo
     */
    public void atacarFisico(Enemigo enemigo) {
        int dano = busterSword.calcularDanoFisico();
        System.out.println(nombre + " ataca fisicamente a " + enemigo.getNombre()
                + " causando " + dano + " de dano.");
        enemigo.getStats().recibirDMG(dano);
        cargarLimite(dano / 5);
    }

    /**
     * Lanza un hechizo magico sobre un enemigo si Cloud tiene suficiente MP.
     * El dano se multiplica por el factor de debilidad del enemigo si implementa Vulnerable.
     * Carga la barra de limite con piso(danoFinal/5).
     *
     * @param elemento el elemento del hechizo
     * @param enemigo  el enemigo objetivo
     */
    public void lanzarMagia(Elemento elemento, Enemigo enemigo) {
        int costo = busterSword.costoMP(elemento);
        if (stats.getMpActual() < costo) {
            System.out.println("No tienes suficiente MP! (Necesitas " + costo + " MP)");
            return;
        }
        stats.setMpActual(stats.getMpActual() - costo);

        int danoBase = busterSword.calcularDanoMagico(elemento);
        double multiplicador = 1.0;
        if (enemigo instanceof Vulnerable) {
            multiplicador = ((Vulnerable) enemigo).evaluarDebilidad(elemento);
        }
        int danoFinal = (int) (danoBase * multiplicador);

        String efectoTexto = multiplicador > 1.0 ? " (debilidad!)"
                           : multiplicador < 1.0 && multiplicador > 0 ? " (resistido)"
                           : multiplicador == 0.0 ? " (inmune!)" : "";
        System.out.println(nombre + " lanza " + elemento + " sobre " + enemigo.getNombre()
                + " causando " + danoFinal + " de dano magico." + efectoTexto);
        enemigo.getStats().recibirDMG(danoFinal);
        cargarLimite(danoFinal / 5);
    }

    /**
     * Usa un hechizo de CURA sobre si mismo, recuperando HP en funcion de la magia.
     * Requiere que el arma tenga materia de CURA y suficiente MP.
     */
    public void curar() {
        if (!busterSword.tieneElemento(Elemento.CURA)) {
            System.out.println("No tienes materia de CURA equipada.");
            return;
        }
        int costo = busterSword.costoMP(Elemento.CURA);
        if (stats.getMpActual() < costo) {
            System.out.println("No tienes suficiente MP! (Necesitas " + costo + " MP)");
            return;
        }
        stats.setMpActual(stats.getMpActual() - costo);
        int curacion = busterSword.calcularDanoMagico(Elemento.CURA);
        int hpAntes = stats.getHpActual();
        stats.setHpActual(Math.min(stats.getHpActual() + curacion, stats.getHpMaximo()));
        int hpRecuperado = stats.getHpActual() - hpAntes;
        System.out.println(nombre + " se cura " + hpRecuperado + " HP.");
    }

    /**
     * Usa el ataque de limite sobre un enemigo si la barra esta llena (>= 100).
     * Resetea la barra a 0 tras el uso.
     *
     * @param enemigo el enemigo objetivo
     */
    public void usarAtaqueLimite(Enemigo enemigo) {
        if (limiteActual < 100) {
            System.out.println("La barra de limite no esta llena aun! (" + limiteActual + "/100)");
            return;
        }
        int dano = busterSword.calcularDanoLimite();
        System.out.println("*** " + nombre + " desata LIMITE BREAK sobre "
                + enemigo.getNombre() + " causando " + dano + " de dano! ***");
        enemigo.getStats().recibirDMG(dano);
        this.limiteActual = 0;
    }

    /**
     * Descuenta HP a Cloud por el dano recibido y carga la barra de limite con piso(dano/2).
     *
     * @param dano cantidad de dano recibido
     */
    public void recibirDanio(int dano) {
        stats.recibirDMG(dano);
        cargarLimite(dano / 2);
    }

    /**
     * Aplica la penalizacion de muerte: vacia mochila y chatarra, restaura HP y MP,
     * y muestra un mensaje de retorno al Sector 7.
     */
    public void morir() {
        System.out.println("\n" + nombre + " ha caido en batalla...");
        mochila.clear();
        chatarra = 0;
        stats.setHpActual(stats.getHpMaximo());
        stats.setMpActual(stats.getMpMaximo());
        limiteActual = 0;
        System.out.println(nombre + " regresa al Sector 7. (Se perdio la mochila y la chatarra)");
    }

    /**
     * Incrementa la barra de limite sin superar 100.
     *
     * @param cantidad puntos a agregar
     */
    private void cargarLimite(int cantidad) {
        this.limiteActual = Math.min(100, this.limiteActual + cantidad);
    }

    /**
     * Agrega chatarra al inventario del jugador.
     *
     * @param cantidad cantidad de chatarra a agregar
     */
    public void recibirChatarra(int cantidad) {
        this.chatarra += cantidad;
    }

    public String getNombre()                   { return nombre; }
    public int getNivel()                        { return nivel; }
    public int getXpActual()                     { return xpActual; }
    public int getChatarra()                     { return chatarra; }
    public void setChatarra(int chatarra)        { this.chatarra = chatarra; }
    public int getLimiteActual()                 { return limiteActual; }
    public void setLimiteActual(int l)           { this.limiteActual = l; }
    public Estadisticas getStats()               { return stats; }
    public List<Materia> getMochila()            { return mochila; }
    public Arma getBusterSword()                 { return busterSword; }

    /**
     * Clase anidada que representa el arma Buster Sword de Cloud.
     * Gestiona las materias equipadas y calcula el dano de cada tipo de ataque.
     */
    public class Arma {
        private final String nombre;
        private final List<Materia> materiasEquipadas;

        /** Crea la Buster Sword sin materias equipadas. */
        public Arma() {
            this.nombre = "Buster Sword";
            this.materiasEquipadas = new ArrayList<>();
        }

        /**
         * Calcula el dano magico base para un elemento segun la cantidad de materias de ese tipo.
         *
         * @param elemento el elemento del hechizo
         * @return dano magico calculado
         */
        public int calcularDanoMagico(Elemento elemento) {
            int n = contarMateriasElemento(elemento);
            return (int) (Jugador.this.stats.getMagia() * (1.0 + (0.5 * n)));
        }

        /**
         * Calcula el dano de un ataque fisico normal.
         *
         * @return dano fisico = piso(Fuerza * 1.25)
         */
        public int calcularDanoFisico() {
            return (int) (Jugador.this.stats.getFuerza() * 1.25);
        }

        /**
         * Calcula el dano del ataque de limite.
         *
         * @return dano limite = Fuerza * 5
         */
        public int calcularDanoLimite() {
            return Jugador.this.stats.getFuerza() * 5;
        }

        /**
         * Calcula el costo en MP de un hechizo segun cuantas materias de ese elemento hay.
         *
         * @param elemento el elemento del hechizo
         * @return costo en MP
         */
        public int costoMP(Elemento elemento) {
            int n = contarMateriasElemento(elemento);
            return 10 + (5 * n);
        }

        /**
         * Intenta equipar una materia en la espada (maximo 5 ranuras).
         *
         * @param materia la materia a equipar
         * @return true si se equipo exitosamente, false si no hay espacio
         */
        public boolean equiparMateria(Materia materia) {
            if (this.materiasEquipadas.size() < 5) {
                this.materiasEquipadas.add(materia);
                return true;
            }
            return false;
        }

        /**
         * Indica si el arma tiene al menos una materia del elemento dado.
         *
         * @param elemento el elemento a consultar
         * @return true si hay al menos una materia de ese elemento
         */
        public boolean tieneElemento(Elemento elemento) {
            return contarMateriasElemento(elemento) > 0;
        }

        private int contarMateriasElemento(Elemento elemento) {
            int c = 0;
            for (Materia m : this.materiasEquipadas) {
                if (m.getElemento() == elemento) c++;
            }
            return c;
        }

        public String getNombre()                      { return nombre; }
        public List<Materia> getMateriasEquipadas()    { return materiasEquipadas; }
    }
}
