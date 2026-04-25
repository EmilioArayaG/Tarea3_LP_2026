package entidades;

import componentes.Estadisticas;
import java.util.Random;

public class EnemigoSimulador extends Enemigo {

    public EnemigoSimulador(){
        this.nombre = "Soldado comun";
        this.stats = new Estadisticas(50, 0, 15, 0);
        this.chatarraRecompensa = 0;
        Random rand = new Random();
        this.xpRecompensa = rand.nextInt(6) + 15;
    }
    @Override
    public void atacar(Jugador cloud){
        Random rand = new Random();
        if (rand.nextInt(100) < 85){
            int danoFisico = (int) (this.stats.getFuerza() * 1.25);

            if(checkDanoSeguro(cloud)){
                danoFisico = cloud.getStats().getHpActual() - 1;
            }
            System.out.println(this.nombre + " ataca y causa "+ danoFisico + " de daño.");
            cloud.getStats().recibirDMG(danoFisico);

        } else {
            System.out.println(this.nombre + " intento atacar, pero fracaso.");
        }
    }
    public boolean checkDanoSeguro(Jugador cloud){
        int danoProyectado = (int) (this.stats.getFuerza() * 1.25);
        return (cloud.getStats().getHpActual() - danoProyectado) <= 0;
    }
}