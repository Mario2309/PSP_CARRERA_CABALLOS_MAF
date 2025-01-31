package es.etg.dam.psp.maf;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente implements Constantes {
    public static void main(String[] args) throws UnknownHostException, IOException {
        GestionMensajes gestor = new GestionMensajes();

        Socket cliente = new Socket(HOST, PUERTO);

        gestor.enviar(cliente, "swq");
        
        String msg = "";

        do {
            msg = gestor.recibirMSG(cliente);
            System.out.println(msg);

            if (msg.equals(MSG_WIN) || msg.equals(MSG_LOSE)) {
                cliente.close();
            }
        } while (!cliente.isClosed());

    }

}
