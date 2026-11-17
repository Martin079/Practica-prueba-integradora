import java.util.ArrayList;
import enemigo.Enemigo;
import torres.Torre;

/**
 * Clase encargada de gestionar el ataque al jefe final.
 * Separa la logica de ataque del menu principal (Main).
 */
public class AtacarJefe {

    private Jugador jugador;
    private Enemigo jefe;

    public AtacarJefe(Jugador jugador, Enemigo jefe) {
        this.jugador = jugador;
        this.jefe = jefe;
    }

    /**
     * Ejecuta el ataque del jefe: cada torre ataca en el orden de construccion.
     * Cada torre ejecuta su propio ataque (polimorfismo) y las monedas ganadas
     * se suman al jugador.
     */
    public void atacar() {
        ArrayList<Torre> torres = jugador.getTorres();

        // Validar si hay torres construidas
        if (torres.isEmpty()) {
            System.out.println("No hay torres construidas. ¡Construye al menos una antes de atacar!");
            return;
        }

        System.out.println("\n--- ¡COMIENZA EL ATAQUE AL JEFE! ---");

        // Iteramos las torres en el orden en que fueron construidas
        // Polimorfismo: cada torre ejecuta su propio ataque sin condicionales por tipo
        for (Torre torre : torres) {
            // Si el jefe ya murió antes de que le toque atacar a esta torre, cortamos el ciclo
            if (jefe.getVida() <= 0) {
                System.out.println("El jefe ya fue derrotado. Las torres restantes no atacan.");
                break;
            }

            // Cada torre ejecuta su ataque, realiza daño al jefe y devuelve las monedas ganadas
            int monedasGanadas = torre.atacar(jefe);

            // Si la torre ganó monedas, se las sumamos al jugador
            if (monedasGanadas > 0) {
                jugador.agregarMonedas(monedasGanadas);
            }

            System.out.println("-----------------------------------");
        }

        // Al finalizar el turno de ataques, mostramos el estado final
        System.out.println("\n RESUMEN DEL ATAQUE:");
        System.out.println("Vida restante del jefe: " + jefe.getVida());
        System.out.println("Monedas disponibles del jugador: " + jugador.getMonedas());
    }
}