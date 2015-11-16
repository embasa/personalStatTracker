package irc;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * cs166 projecto for classo babyo
 * Created by bruno on 11/12/15.
 */
public class ChatServer {
  private final int LISTENING_PORT = 2002;
  public ChatServer(){

  }
  public static void main(String[] args) throws IOException{

  }

  public void startServer(){
    try (ServerSocket serverSocket = new ServerSocket(LISTENING_PORT)) {

    }catch (IOException e){
      e.printStackTrace();
    }

  }
}
