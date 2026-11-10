package torres;

/**
 * Enumerador que representa los tipos de torre disponibles.
 * Cumple con el Requisito Técnico #3:
 * - Cada tipo tiene su precio correspondiente
 * - Incluye método puedeComprar(int monedas)
 * - Se utiliza únicamente para el proceso de compra
 * - Factory method para crear la instancia concreta de la torre
 */
public enum TipoTorre {
    ARQUERA(3),
    CATAPULTA(5),
    GLACIAL(4);

    private final int precio;

    TipoTorre(int precio) {
        this.precio = precio;
    }

    public int getPrecio() {
        return precio;
    }

    /**
     * Verifica si el jugador tiene monedas suficientes para comprar este tipo de torre.
     * @param monedas Monedas disponibles del jugador
     * @return true si puede comprar, false en caso contrario
     */
    public boolean puedeComprar(int monedas) {
        return monedas >= precio;
    }

    /**
     * Factory method: crea una nueva instancia de la torre correspondiente.
     * Las torres construidas se representan mediante objetos de las clases concretas
     * (Arquera, Catapulta, Glacial) usando la jerarquía de herencia.
     * @return Nueva instancia de la torre
     */
    public Torre crearTorre() {
        switch (this) {
            case ARQUERA:
                return new Arquera();
            case CATAPULTA:
                return new Catapulta();
            case GLACIAL:
                return new Glacial();
            default:
                return null; // Por seguridad, aunque el enum solo tiene estos tres
        }
    }

    @Override
    public String toString() {
        // Para mostrar en el menú: "ARQUERA (3 monedas)"
        return name() + " (" + precio + " monedas)";
    }
}