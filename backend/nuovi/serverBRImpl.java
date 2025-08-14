import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class serverBRImpl extends UnicastRemoteObject implements serverBR {
    
    protected serverBRImpl() throws RemoteException {
        super();
    }
    
    // Implementazione dei metodi definiti nell'interfaccia serverBR

    @Override
    public int registrazione(Utente user) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int login(String email, String password) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}