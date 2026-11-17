import utilidades.Entrada;
import enemigo.Enemigo;

public class Main {
    public static void main(String[] args) {
        Jugador jugador = new Jugador();
        Enemigo jefe = new Enemigo(); // Arranca con 100 de vida
        Entrada entrada = new Entrada();

        // Las classes ComprarTorre y AtacarJefe centralizan la lógica de compra y ataque
        ComprarTorre comprarTorre = new ComprarTorre(jugador, entrada);
        AtacarJefe atacarJefe = new AtacarJefe(jugador, jefe);

        int accion = 0;

        // El menú se repite mientras no elija salir (3) y el jefe siga vivo (> 0)
        while (accion != 3 && jefe.getVida() > 0) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("Monedas disponibles: " + jugador.getMonedas());
            System.out.println("Vida del Jefe Final: " + jefe.getVida());
            System.out.println("1. Construir torre");
            System.out.println("2. Atacar al jefe final");
            System.out.println("3. Salir");

            // Usamos la clase Entrada que te dieron para leer el número de forma segura
            accion = entrada.ingresarEntero(1, 3);

            switch (accion) {
                case 1:
                    comprarTorre.comprar();
                    break;
                case 2:
                    atacarJefe.atacar();
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
}