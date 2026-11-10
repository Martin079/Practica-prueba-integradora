package enemigo;

import utilidades.Utilidades;


public class Enemigo {

    private int vida;

    public Enemigo() {
        this.vida = 100;
    }

    public int getVida() {
        return vida;
    }

    public void recibirDanio(int danio) {
        if (danio > 0) {
            vida = Utilidades.limitarVida(vida - danio);
        }
    }
}
