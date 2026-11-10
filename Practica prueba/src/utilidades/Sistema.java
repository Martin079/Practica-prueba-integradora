package utilidades;

public class Sistema {

    private Sistema(){}

    public static void esperar(int milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
