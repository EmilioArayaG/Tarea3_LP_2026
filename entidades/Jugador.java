package entidades;

import componentes.Estadisticas;
import componentes.Materia;
import componentes.Elemento;
import java.util.ArrayList;
import java.util.List;

public class Jugador {
    public String nombre = "Cloud";
    private int nivel;
    private int xpActual;
    private int chatarra;
    private int limiteActual;
    private Estadisticas stats;
    private List<Materia> mochila;
    private Arma busterSword;

    public Jugador() {
        this.nivel = 1;
        this.xpActual = 0;
        this.chatarra = 0;
        this.limiteActual = 0;
        this.stats = new Estadisticas(200, 50, 15, 15);
        this.mochila = new ArrayList<>();
        this.busterSword = new Arma();
    }

    public void recibirXP (int xp){
        this.xpActual += xp;
        int xpNecesaria = 10 * this.nivel;
        
        while (this.xpActual >= xpNecesaria){
            this.xpActual -= xpNecesaria;
            subirNivel();
            xpNecesaria = 10 * this.nivel;
        }
    }

    private void subirNivel(){
        this.nivel++;
        this.stats.setHpMaximo(this.stats.getHpMaximo() + 10);
        this.stats.setMpMaximo(this.stats.getMpMaximo() + 5);
        this.stats.setFuerza(this.stats.getFuerza() + 4);
        this.stats.setMagia(this.stats.getMagia() + 6);
        System.out.println(this.nombre + " ha subido al nivel " + this.nivel + ".");
    }

    public String getNombre() { return nombre; }
    public int getNivel() { return nivel; }
    public int getXpActual() { return xpActual; }
    public int getChatarra() { return chatarra; }
    public void setChatarra(int chatarra) { this.chatarra = chatarra; }
    public int getLimiteActual() { return limiteActual; }
    public void setLimiteActual(int limiteActual) { this.limiteActual = limiteActual; }
    public Estadisticas getStats() { return stats; }
    public List<Materia> getMochila() { return mochila; }
    public Arma getBusterSword() { return busterSword; }

    public class Arma {
        public String nombre = "Buster Sword";
        private List<Materia> materiasEquipadas;

        public Arma(){
            this.materiasEquipadas = new ArrayList<>();
        }
        public int calcularDanoMagico(Elemento elemento) {
            int n = contarMateriasElemento(elemento);
            return (int) (Jugador.this.stats.getMagia() * (1.0 + (0.5 * n)));
        }
        public int calcularDanoFisico() {
            return (int) (Jugador.this.stats.getFuerza() * 1.25);
        }
        public int calcularDanoLimite() {
            return Jugador.this.stats.getFuerza() * 5;
        }
        public int costoMP(Elemento elemento) {
            int n = contarMateriasElemento(elemento);
            return 10 + (5 * n);
        }
        public boolean equiparMateria(Materia materia) {
            if (this.materiasEquipadas.size() < 5) {
                this.materiasEquipadas.add(materia);
                return true;
            }
            return false;
        }
        public boolean tieneElemento(Elemento elemento) {
            return contarMateriasElemento(elemento) > 0;
        }
        private int contarMateriasElemento(Elemento elemento) {
            int c = 0;
            for (Materia m : this.materiasEquipadas) {
                if (m.getElemento() == elemento) {
                    c++;
                }
            }
            return c;
        }
        public List<Materia> getMateriasEquipadas() { return materiasEquipadas; }
    }
}