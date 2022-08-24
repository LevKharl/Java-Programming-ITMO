package Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Receiver implements Runnable, Serializable {
    public final ExecutorService executorFork = Executors.newCachedThreadPool();
    public final Socket socket;
    private InputStream is;

    public Receiver(Socket client) {
        this.socket = client;
    }

    @Override
    public void run() {
        try {
                is = socket.getInputStream();
                while (!socket.isClosed()) {
                    byte[] commandBytes = this.readObject();
                    executorFork.execute(new Executor(commandBytes, socket));
                }
                executorFork.shutdown();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Клиент отключился. ");
        }
    }

    private byte[] readObject() throws IOException {
        byte[] commandBytes = new byte[readLenOfObject(is)];
        is.read(commandBytes);
        return commandBytes;
    }

    private int readLenOfObject(InputStream is) throws IOException{
        byte[] arrCnt = new byte[1];
        is.read(arrCnt);
        byte[] len = new byte[arrCnt[0]];
        is.read(len);
        return Integer.parseInt(new String(len));
    }

}
