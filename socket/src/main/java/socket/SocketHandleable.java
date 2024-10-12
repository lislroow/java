package socket;

import java.net.ServerSocket;
import java.net.Socket;

public interface SocketHandleable {
  String getIP();

  int getPort();

  void recvData(byte[] var1);

  void startThread(Socket clientSocket, SocketHandler common, ServerSocket serverSocket);

  void stopNetwork();
}