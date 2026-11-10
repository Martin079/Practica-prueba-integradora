import utilidades.Entrada;
import torres.*;
import java.util.ArrayList;
import enemigo.Enemigo;

public class Main {
    public static void main(String[] args) {
        Jugador jugador = new Jugador();
        Enemigo jefe = new Enemigo(); // Arranca con 100 de vida
        Entrada entrada = new Entrada();

        int opcion = 0;

        // El menú se repite mientras no elija salir (3) y el jefe siga vivo (> 0)
        while (opcion != 3 && jefe.getVida() > 0) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("Monedas disponibles: " + jugador.getMonedas());
            System.out.println("Vida del Jefe Final: " + jefe.getVida());
            System.out.println("1. Construir torre");
            System.out.println("2. Atacar al jefe final");
            System.out.println("3. Salir");

            // Usamos la clase Entrada que te dieron para leer el número de forma segura
            opcion = entrada.ingresarEntero(1, 3);

            switch (opcion) {
                case 1:
                    construirTorre(jugador, entrada);
                    break;
                case 2:
                    atacarJefe(jugador, jefe);
                    break;
                case 3:
                    System.out.println("¡Gracias por jugar!");
                    break;
            }

            // Verificamos si el jefe murió después de un ataque para avisar que ganó
            if (jefe.getVida() <= 0) {
                System.out.println("\n ¡VICTORIA! El jefe final ha sido derrotado. ¡Ganaste el juego!");
            }
        }

        entrada.cerrarScanner();
    }

    public static void construirTorre(Jugador jugador, Entrada entrada) {
        System.out.println("\n--- SELECCIONAR TORRE A CONSTRUIR ---");

        // Usamos el enumerador TipoTorre (Requisito Técnico #3)
        // El enumerador se utiliza únicamente para el proceso de compra
        TipoTorre[] tipos = TipoTorre.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]); // toString() muestra "NOMBRE (precio monedas)"
        }
        System.out.println((tipos.length + 1) + ". Volver al menú principal");

        int tipoSeleccionado = entrada.ingresarEntero(1, tipos.length + 1);

        // Opción volver al menú
        if (tipoSeleccionado == tipos.length + 1) {
            return;
        }

        // Obtener el tipo de torre seleccionado (índice 0-based)
        TipoTorre tipoTorre = tipos[tipoSeleccionado - 1];

        // Verificar si puede comprar usando el método del enumerador
        if (tipoTorre.puedeComprar(jugador.getMonedas())) {
            // Descontar monedas y crear la torre usando el factory method del enum
            jugador.restarMonedas(tipoTorre.getPrecio());
            Torre nuevaTorre = tipoTorre.crearTorre();
            jugador.agregarTorre(nuevaTorre);
            System.out.println(" ¡Torre construida con éxito! Te quedan " + jugador.getMonedas() + " monedas.");
        } else {
            System.out.println("No tienes suficientes monedas para construir esta torre.");
        }
    }

    public static void atacarJefe(Jugador jugador, Enemigo jefe) {
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