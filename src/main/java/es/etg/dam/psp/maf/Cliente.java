package es.etg.dam.psp.maf;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente implements Constantes, Runnable {

    private final GestionMensajes gestor;

    public Cliente(GestionMensajes gestor){
        this.gestor = gestor;
    }

    @Override
    public void run() {
        try (Socket cliente = new Socket(HOST, PUERTO)) {

            gestor.enviar(cliente, "swq");

            while (cliente.isConnected()){
                System.out.println(gestor.recibirMSG(cliente));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
