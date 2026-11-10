package torres;

import enemigo.Enemigo;
import utilidades.Aleatorio;

public class Glacial extends Torre {

    public Glacial() {
        super(4, 15); // Costo 4, Daño base 15
    }

    @Override
    public int atacar(Enemigo enemigo) {
        int monedasGanadas = 0;
        int dañoActual = getDanioBase(); // Usar getter protegido (encapsulamiento)
        int impactos = 1;

        // "Si el enemigo tiene más de 40 puntos de vida ANTES del impacto,
        // el daño se reduce a la mitad"
        if (enemigo.getVida() > 40) {
            dañoActual = getDanioBase() / 2; // 15 / 2 = 7 (división entera)
        }

        // 25% de probabilidad de otorgar 3 monedas (generamos un número entre 1 y 4)
        int probabilidadMonedas = Aleatorio.generarEntero(1, 4);
        if (probabilidadMonedas == 1) {
            monedasGanadas += 3;
        }

        // Aplicar el daño usando el método del enemigo
        enemigo.recibirDanio(dañoActual);

        // Mostrar resultados
        System.out.println("--- Torre Glacial ---");
        System.out.println("Impactos: " + impactos);
        System.out.println("Daño realizado: " + dañoActual);
        System.out.println("Monedas conseguidas: " + monedasGanadas);

        return monedasGanadas;
    }
}