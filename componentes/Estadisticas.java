package componentes;

public class Estadisticas {
    private int hpActual;
    private int hpMaximo;
    private int mpActual;
    private int mpMaximo;
    private int fuerza;
    private int magia;

    /**
     * Crea un conjunto de estadisticas con los valores maximos dados e inicializa los actuales al maximo.
     *
     * @param hpMaximo HP maximo inicial
     * @param mpMaximo MP maximo inicial
     * @param fuerza   valor de fuerza
     * @param magia    valor de magia
     */
    public Estadisticas(int hpMaximo, int mpMaximo, int fuerza, int magia) {
        this.hpMaximo = hpMaximo;
        this.hpActual = hpMaximo;
        this.mpMaximo = mpMaximo;
        this.mpActual = mpMaximo;
        this.fuerza = fuerza;
        this.magia = magia;
    }

    /**
     * Reduce el HP actual por el valor indicado, sin bajar de 0.
     *
     * @param valor cantidad de dano recibido
     */
    public void recibirDMG(int valor) {
        this.hpActual -= valor;
        if (this.hpActual < 0) {
            this.hpActual = 0;
        }
    }

    public int getHpActual()              { return hpActual; }
    public void setHpActual(int hpActual) { this.hpActual = hpActual; }
    public int getHpMaximo()              { return hpMaximo; }
    public void setHpMaximo(int hpMaximo) { this.hpMaximo = hpMaximo; }
    public int getMpActual()              { return mpActual; }
    public void setMpActual(int mpActual) { this.mpActual = mpActual; }
    public int getMpMaximo()              { return mpMaximo; }
    public void setMpMaximo(int mpMaximo) { this.mpMaximo = mpMaximo; }
    public int getFuerza()                { return fuerza; }
    public void setFuerza(int fuerza)     { this.fuerza = fuerza; }
    public int getMagia()                 { return magia; }
    public void setMagia(int magia)       { this.magia = magia; }
}
