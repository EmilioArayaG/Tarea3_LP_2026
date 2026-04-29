package componentes;

public interface Vulnerable {

    /**
     * Retorna el multiplicador de dano magico segun el elemento recibido.
     * 2.0 = debilidad, 1.0 = neutro, 0.5 = resistencia, 0.0 = inmunidad.
     *
     * @param elementoMagia el elemento del ataque magico
     * @return multiplicador de dano
     */
    double evaluarDebilidad(Elemento elementoMagia);
}
