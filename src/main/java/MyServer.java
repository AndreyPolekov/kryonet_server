import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class MyServer {

    public MyServer() {
        Server server = new Server();
        server.start();
        Kryo kryo = server.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        try {
            server.bind(54556);
        } catch (IOException e) {
            e.printStackTrace();
        }


        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof SomeRequest) {
                    SomeRequest request = (SomeRequest)object;
                    System.out.println(request.text);

                    SomeResponse response = new SomeResponse();
                    response.text = "Response";
                    connection.sendTCP(response);
                }
            }
        });
    }

    public static void main(String[] args) {
        new MyServer();
    }
}
