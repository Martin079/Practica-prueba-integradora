package utilidades;

public class Utilidades {

	public static int limitarVida(int vida) {
        if (vida < 0) {
            return 0;
        } else if (vida > 100) {
            return 100;
        }
        return vida;
    }
	
}
