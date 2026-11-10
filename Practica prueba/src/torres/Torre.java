package torres;


/**
 * Clase abstracta base para todas las torres.
 * Contiene características comunes: costo y daño base.
 * Cumple Requisito Técnico #1: las torres derivan de esta clase abstracta.
 * Encapsulamiento mejorado: campos private con getters protegidos.
 */
public abstract class Torre implements Atacante {

    private int costo;
    private int danioBase;

    public Torre(int costo, int danioBase) {
        this.costo = costo;
        this.danioBase = danioBase;
    }

    // Getters protegidos para que las subclases accedan sin romper encapsulamiento
    protected int getCosto() {
        return costo;
    }

    protected int getDanioBase() {
        return danioBase;
    }

    // Getters públicos para consultas externas si se necesitan
    public int getCostoPublico() {
        return costo;
    }

    public int getDanioBasePublico() {
        return danioBase;
    }
}