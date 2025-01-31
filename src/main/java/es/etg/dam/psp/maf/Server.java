package es.etg.dam.psp.maf;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server implements Constantes {

    public static void main(String[] args) throws IOException, InterruptedException {
        int idCaballo;
        
        Caballo[] caballos = new Caballo[CONEXIONES_MAX];
        Socket[] clientes = new Socket[caballos.length];

        GestionMensajes gestor = new GestionMensajes();

        ServerSocket servidor = new ServerSocket(PUERTO);

        do{        
            if (clientes[CONEXIONES_MAX-1] == null || caballos[CONEXIONES_MAX-1] == null) {
                
                conexiones(caballos, clientes, gestor, servidor);

            } else {
                System.out.println(NO_SE_PUDO_CONECTAR);
            }

            
            do {
                idCaballo = numRandom(0, caballos.length);
                
                repartirPuntos(caballos, idCaballo);
                
                notificarPuntos(caballos, gestor, clientes, idCaballo);


            } while (!comprobarPuntos(caballos, idCaballo));

            notificarGanador(caballos, gestor, clientes);

            for (Socket cliente : clientes) {
                cliente.close();
            }

            servidor.close();

        }while(!servidor.isClosed());

    }

    public static boolean comprobarPuntos(Caballo[] caballos, int idCaballo){
        return caballos[idCaballo].getPuntos() >= 100;
    }

    private static void conexiones(Caballo[] caballos, Socket[] clientes, GestionMensajes gestor, ServerSocket servidor)
            throws IOException {
        for (int i = 0; i < caballos.length; i++) {
            Socket cliente = servidor.accept();

            clientes[i] = cliente;

            caballos[i] = new Caballo(gestor.recibirMSG(cliente));

            gestor.enviar(cliente, MSG_OK);

        }
    }

    private static void notificarGanador(Caballo[] caballos, GestionMensajes gestor, Socket[] clientes) throws IOException {
        for (int i = 0; i < caballos.length; i++) {
            if (caballos[i].getPuntos() >= 100){
                gestor.enviar(clientes[i], MSG_WIN);
            } else {
                gestor.enviar(clientes[i], MSG_LOSE);
            }
        }
    }

    private static void notificarPuntos(Caballo[] caballos, GestionMensajes gestor, Socket[] clientes, int idCaballo) throws IOException {
            gestor.enviar(clientes[idCaballo], EL_CABALLO + caballos[idCaballo].getNombre() +
                    CABALLO_PUNTUO + caballos[idCaballo].getPuntos() + PUNTOS);
    }

    private static void repartirPuntos(Caballo[] caballos, int idCaballo) throws IOException {
        if (caballos[idCaballo].getPuntos() < 100){
            caballos[idCaballo].setPuntos(numRandom(PUNTOS_MIN, PUNTOS_MAX));
        }
    }

    private static int numRandom(int min, int max){
        Random rdm = new Random();
        return rdm.nextInt(min, max);
    }

}