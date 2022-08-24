package Main;

import ObjectToSend.ObjectToClient;

import java.io.*;

public class BytesHandler implements Serializable {
    public static byte[] serialize(Object o) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(o);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public static Object deserialize(byte[] array) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(array);
        ObjectInput in = new ObjectInputStream(bis);
        ObjectToClient test = (ObjectToClient) in.readObject();
        return test;
    }
}
