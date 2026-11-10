package torres;

import enemigo.Enemigo;
import utilidades.Aleatorio;

public class Arquera extends Torre {

    public Arquera() {
        super(3, 10);
    }

    @Override
    public int atacar(Enemigo enemigo) {
        int monedasGanadas = 0;
        int danioTotal = getDanioBase(); // Usar getter protegido (encapsulamiento)
        int impactos = 1;

        // 50% de probabilidad de acertar la segunda flecha (generar 1 o 2)
        int probabilidad = Aleatorio.generarEntero(1, 2);

        if (probabilidad == 1) {
            impactos++;
            danioTotal += 10; // Segunda flecha: 10 puntos de daño extra
            monedasGanadas += 2; // Y 2 monedas al jugador
        }

        // Aplicar daño total al enemigo
        enemigo.recibirDanio(danioTotal);

        // Mostrar resultado del ataque
        System.out.println("- Torre Arquera -");
        System.out.println("Impactos: " + impactos);
        System.out.println("Daño realizado: " + danioTotal);
        System.out.println("Monedas conseguidas: " + monedasGanadas);
        System.out.println("");

        return monedasGanadas;
    }
}