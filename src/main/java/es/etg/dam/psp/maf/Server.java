package es.etg.dam.psp.maf;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server implements Constantes {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket[] clientes = new Socket[PARTICIPANTES_MAX];
        Caballo[] caballos = new Caballo[PARTICIPANTES_MAX];
        GestionMensajes gestor = new GestionMensajes();

        ServerSocket server = new ServerSocket(PUERTO);

        establecerConexion(caballos, clientes, gestor, server);

        Thread.sleep(2000);

        while (!comprobarPuntos(caballos)){

            repartirPuntos(caballos);

            notificarPuntos(caballos, gestor, clientes);

        }

        Thread.sleep(2000);

        notificarGanador(caballos, gestor, clientes);

        finalizarConexiones(clientes);


    }

    private static void establecerConexion(Caballo[] caballos, Socket[] clientes, GestionMensajes gestor, ServerSocket server) throws IOException {
        for(int i = 0; i < caballos.length; i++){
            if (caballos[i] == null && clientes[i] == null) {
                Thread clienteThread = new Thread(new Cliente(gestor));
                clienteThread.start();
                clienteThread.setName(IDENTIFICADOR+(i+1));

                Socket cliente = server.accept();

                clientes[i] = cliente;

                caballos[i] = new Caballo(gestor.recibirMSG(clientes[i]));

                gestor.enviar(clientes[i], clienteThread.getName() + MSG_OK);

            } else {
                System.out.println(NO_SE_PUDO_CONECTAR);
            }
        }
    }

    private static void finalizarConexiones(Socket[] clientes) throws IOException {
        for (int i = 0; i < clientes.length; i++) {
            clientes[i].close();
        }
    }

    private static void notificarGanador(Caballo[] caballos, GestionMensajes gestor, Socket[] clientes) throws IOException {
        for (int i = 0; i < caballos.length; i++) {
            if (caballos[i].getPuntos() >= 100){
                gestor.enviar(clientes[i], caballos[i].getNombre() + MSG_WIN);
            } else {
                gestor.enviar(clientes[i], caballos[i].getNombre() + MSG_LOSE);
            }
        }
    }

    private static boolean comprobarPuntos(Caballo[] caballos) throws IOException {
        for (int i = 0; i < caballos.length; i++) {
            if (caballos[i].getPuntos() >= 100){

                return true;
            }
        }
        return false;
    }

    private static void notificarPuntos(Caballo[] caballos, GestionMensajes gestor, Socket[] clientes) throws IOException {
        for (int i = 0; i < caballos.length; i++) {
            gestor.enviar(clientes[i], EL_CABALLO + caballos[i].getNombre() +
                    CABALLO_PUNTUO + caballos[i].getPuntos() + PUNTOS);
        }
    }

    private static void repartirPuntos(Caballo[] caballos) throws IOException {
        for (int i = 0; i < caballos.length; i++) {
            caballos[i].setPuntos(numRandom(PUNTOS_MIN, PUNTOS_MAX));
        }
    }

        private static int numRandom(int min, int max){
            Random rdm = new Random();
            return rdm.nextInt(min, max);

        }

}