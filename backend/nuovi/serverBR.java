import java.rmi.*;

public interface serverBR extends Remote {
    /* elenco dei metodi che il server deve implementare */
    public int registrazione(Utente user) throws RemoteException;
    public int login(String email, String password) throws RemoteException;
}