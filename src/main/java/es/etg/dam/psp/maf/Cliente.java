package es.etg.dam.psp.maf;

import java.io.IOException;
import java.net.Socket;

public class Cliente implements Constantes, Runnable {

    private final GestionMensajes gestor;

    public Cliente(GestionMensajes gestor){
        this.gestor = gestor;
    }

    @Override
    public void run() {
        try (Socket cliente = new Socket(HOST, PUERTO)) {

            gestor.enviar(cliente, "swq");

            while (!cliente.isClosed()) {
                System.out.println(gestor.recibirMSG(cliente));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
