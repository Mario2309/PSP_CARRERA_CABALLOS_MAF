package es.etg.dam.psp.maf;

import java.io.*;
import java.net.Socket;

public class GestionMensajes {

    public void enviar(Socket cliente, String msg) throws IOException {
        OutputStream aux = cliente.getOutputStream();
        DataOutputStream output = new DataOutputStream(aux);
        output.writeUTF(msg);
    }

    public String recibirMSG(Socket cliente) throws IOException {
        InputStream aux = cliente.getInputStream();
        DataInputStream input = new DataInputStream(aux);
        return input.readUTF();
    }
}
