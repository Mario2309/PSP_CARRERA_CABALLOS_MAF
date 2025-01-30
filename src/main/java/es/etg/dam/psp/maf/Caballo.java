package es.etg.dam.psp.maf;

public class Caballo implements Constantes{

    private final String nombre;
    private int puntos;

    public Caballo(String nombre){
        this.nombre = nombre;
    }

    public void setPuntos(int puntos) {
        this.puntos += puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getNombre() {
        return nombre;
    }
}
