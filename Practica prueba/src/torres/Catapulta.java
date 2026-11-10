package torres;

import enemigo.Enemigo;

public class Catapulta extends Torre {

    public Catapulta() {
        super(5, 20);
    }

    @Override
    public int atacar(Enemigo enemigo) {
        int monedasGanadas = 0;
        int dañoTotal = getDanioBase(); // Usar getter protegido (encapsulamiento)
        int impactos = 1;

        // 1. PRIMERO: Aplicar el daño base (20 puntos) - "lanza una bola de fuego que provoca 20 puntos de daño"
        enemigo.recibirDanio(dañoTotal);

        // 2. DESPUÉS DEL IMPACTO: Si el enemigo tiene menos de 30 puntos de vida,
        // provoca 10 puntos de daño ADICIONALES
        // Requisito: "Si DESPUÉS DEL IMPACTO el enemigo tiene menos de 30 puntos de vida"
        if (enemigo.getVida() < 30) {
            dañoTotal += 10; // 10 puntos de daño extra
            enemigo.recibirDanio(10); // Aplicar el daño adicional
        }

        // Mostrar resultados
        System.out.println("--- Torre Catapulta ---");
        System.out.println("Impactos: " + impactos);
        System.out.println("Daño realizado: " + dañoTotal);
        System.out.println("Monedas conseguidas: " + monedasGanadas);

        return monedasGanadas;
    }
}