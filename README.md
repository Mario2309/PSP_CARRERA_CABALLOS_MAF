# Arquitectura del problema
La Arquitectura con la que se realizo este proyecto, es la arquitectura "cliente-servidor"
# Diseño del protocolo y de la solucion
## Diseño del protocolo
```plantuml
Servidor <- Cliente: Nombre caballo
Cliente <--- Servidor: "OK"
Servidor ---> Cliente: Puntos
Servidor <--- Cliente: Ganaste/Perdiste
```
## Diseño de la solucion

```plantuml
@startuml

package es/etg/dam/psp/maf {
    interface Constantes {
        + int PUERTO
        + String HOST
        + String MSG_OK
        + String MSG_WIN
        + String MSG_LOSE
        + int CONEXIONES_MAX
        + int PUNTOS_MIN
        + int PUNTOS_MAX
        + String EL_CABALLO
        + String CABALLO_PUNTUO
        + String PUNTOS
        + String NO_SE_PUDO_CONECTAR
        + String IDENTIFICADOR
    }

    class Caballo {
        - final String nombre
        - int puntos
        + Caballo(String nombre)
        + void setPuntos(int puntos)
        + int getPuntos()
        + String getNombre()
    }

    class Cliente {
        + main(String[] args)
    }

    class GestionMensajes {
        + void enviar(Socket cliente, String msg) throws IOException
        + String recibirMSG(Socket cliente) throws IOException
    }

    class Server {
        + main(String[] args) throws IOException, InterruptedException
        + static boolean comprobarPuntos(Caballo[] caballos, int idCaballo)
        - static void conexiones(Caballo[] caballos, Socket[] clientes, GestionMensajes gestor, ServerSocket servidor) throws IOException
        - static void notificarGanador(Caballo[] caballos, GestionMensajes gestor, Socket[] clientes) throws IOException
        - static void notificarPuntos(Caballo[] caballos, GestionMensajes gestor, Socket[] clientes, int idCaballo) throws IOException
        - static void repartirPuntos(Caballo[] caballos, int idCaballo) throws IOException
        - static int numRandom(int min, int max)
    }

    Constantes <|.. Caballo
    Constantes <|.. Cliente
    Constantes <|.. Server
    Caballo <-down- Server
    GestionMensajes <-down- Cliente
    GestionMensajes <-down- Server
    ServerSocket <-right- Server
    Socket <- GestionMensajes
    Socket <- Cliente
    Socket <- Server

}

@enduml
```