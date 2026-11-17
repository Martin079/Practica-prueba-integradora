import utilidades.Entrada;
import torres.*;

/**
 * Clase encargada de gestionar la compra y construccion de torres.
 * Separa la logica de compra del menu principal (Main).
 */
public class ComprarTorre {

    private Jugador jugador;
    private Entrada entrada;

    public ComprarTorre(Jugador jugador, Entrada entrada) {
        this.jugador = jugador;
        this.entrada = entrada;
    }

    /**
     * Ejecuta el flujo completo de seleccion y construccion de una torre.
     * Utiliza el enumerador TipoTorre unicamente para el proceso de compra.
     */
    public void comprar() {
        System.out.println("\n--- SELECCIONAR TORRE A CONSTRUIR ---");

        // Mostramos los tipos de torre disponibles (Requisito Técnico #3)
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
}