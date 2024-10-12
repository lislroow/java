package socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler implements Runnable {
  
  static int totalNum = 0;
  static int maxNum = 0;
  static int bufSize = 1024;
  boolean sync;
  boolean persist;
  boolean depend;
  boolean firstOut;
  String remoteAddr;
  SocketHandleable nci;
  Socket socket;
  BufferedInputStream bis;
  BufferedOutputStream bos;
  DataInputStream dis;
  DataOutputStream dos;

  public SocketHandler() {
    this.sync = true;
    this.persist = false;
    this.depend = true;
    this.firstOut = false;
    this.remoteAddr = "";
    this.socket = null;
    this.bis = null;
    this.bos = null;
    this.dis = null;
    this.dos = null;
  }

  public SocketHandler(Socket sock) throws IOException {
    this.sync = true;
    this.persist = false;
    this.depend = true;
    this.firstOut = false;
    this.remoteAddr = "";
    this.socket = null;
    this.bis = null;
    this.bos = null;
    this.dis = null;
    this.dos = null;
    this.setSocket(sock);
  }

  public SocketHandler(Socket sock, boolean sync, boolean persist, boolean depend, boolean firstOut)
      throws IOException {
    this(sock);
    this.sync = sync;
    this.persist = persist;
    this.depend = depend;
    this.firstOut = firstOut;
  }

  public SocketHandler(Socket sock, boolean sync, boolean persist, boolean depend, boolean firstOut,
      SocketHandleable nci) throws IOException {
    this(sock, sync, persist, depend, firstOut);
    this.nci = nci;
  }

  protected synchronized int handleNum(boolean pm) {
    if (pm) {
      ++totalNum;
      if (totalNum > maxNum) {
        maxNum = totalNum;
      }
    } else {
      --totalNum;
    }

    return totalNum;
  }

  public void run() {
    System.out.println("Start Thread // total thread num = " + this.handleNum(true) + " // max = " + maxNum);
    if (this.depend) {
      this.process();
    } else if (this.nci != null) {
      boolean running = true;

      while (running && !Thread.interrupted()) {
        try {
          this.nci.recvData(this.recvData());
        } catch (IOException var3) {
          var3.printStackTrace();
          this.close();
          running = false;
        }
      }

      this.nci.stopNetwork();
      this.nci = null;
    } else {
      System.out.println("Fail : Independ mode!");
    }

    System.out.println("Stop Thread // total thread num = " + this.handleNum(false) + " // max = " + maxNum);
  }

  public void setSocket(Socket sock) throws IOException {
    this.socket = sock;
    System.out.println("keepalive : " + this.socket.getKeepAlive());
    System.out.println("soTimeout : " + this.socket.getSoTimeout());
    System.out.println("nagle : " + !this.socket.getTcpNoDelay());
    this.remoteAddr = this.socket.getRemoteSocketAddress().toString();
    System.out.println("Connected : " + this.remoteAddr);
    this.bos = new BufferedOutputStream(this.socket.getOutputStream());
    this.dos = new DataOutputStream(this.bos);
    this.bis = new BufferedInputStream(this.socket.getInputStream());
    this.dis = new DataInputStream(this.bis);
  }

  public void process() {
    byte[] outBytes = (byte[]) null;
    byte[] inBytes = (byte[]) null;

    try {
      do {
        if (this.firstOut) {
          System.out.println(":::::::  Send Data first ::::::::::");
          String outString = "002410001111444488882222";
          outBytes = outString.getBytes();
          // 2024.09.25 전체 길이 4byte 추가
          String totLen = String.format("%4s", outBytes.length).replace(' ', '0');
          outBytes = (totLen + outString).getBytes();
          // --
          this.sendData(outBytes, 0, outBytes.length);
        }

        if (!this.firstOut || this.sync) {
          System.out.println(":::::::  stand by (RECV) ::::::::::");
          inBytes = this.recvData();
        }

        if (!this.firstOut && this.sync) {
          this.sendData(inBytes);
        }
      } while (this.persist);

      this.socket.shutdownInput();
      this.socket.shutdownOutput();
    } catch (IOException var8) {
      var8.printStackTrace();
      System.out.println(this.socket.isConnected());
    } finally {
      this.close();
    }

  }

  public void close() {
    if (this.dos != null) {
      try {
        this.dos.close();
      } catch (IOException var6) {
      }
    }

    if (this.dis != null) {
      try {
        this.dis.close();
      } catch (IOException var5) {
      }
    }

    if (this.bos != null) {
      try {
        this.bos.close();
      } catch (IOException var4) {
      }
    }

    if (this.bis != null) {
      try {
        this.bis.close();
      } catch (IOException var3) {
      }
    }

    if (this.socket != null) {
      try {
        this.socket.close();
      } catch (IOException var2) {
      }
    }

    System.out.println("Closed : " + this.remoteAddr);
  }

  public void sendData(byte[] data, int sPos, int len) throws IOException {
    byte[] buf = new byte[len];
    System.arraycopy(data, sPos, buf, 0, len);
    this.sendData(buf);
  }

  public void sendData(byte[] data) throws IOException {
    this.dos.write(data);
    this.dos.flush();
    System.out.println("send body : " + new String(data));
    System.out.println(byteArrayToHex(data));
  }

  public byte[] recvData() throws IOException {
    byte[] inBytes = new byte[bufSize];
    int inNum = this.dis.read(inBytes);
    if (inNum < 0) {
      throw new EOFException();
    } else {
      byte[] data = new byte[inNum];
      System.arraycopy(inBytes, 0, data, 0, inNum);
      System.out.println("recv byte : " + inNum + " bytes");
      System.out.println("recv body : " + new String(data, 0, inNum));
      System.out.println(byteArrayToHex(data));
      return data;
    }
  }

  public static byte[] hexToByteArray(String hex) {
    if (hex != null && hex.length() != 0) {
      String nHex = "";
      String[] hexs = hex.split(" ");

      for (int i = 0; i < hexs.length; ++i) {
        if (hexs[i].length() != 0) {
          while (hexs[i].length() < 2) {
            hexs[i] = "0" + hexs[i];
          }

          nHex = nHex + hexs[i];
        }
      }

      byte[] ba = new byte[nHex.length() / 2];

      for (int i = 0; i < ba.length; ++i) {
        ba[i] = (byte) Integer.parseInt(nHex.substring(2 * i, 2 * i + 2), 16);
      }

      return ba;
    } else {
      return null;
    }
  }

  public static String byteArrayToHex(byte[] ba) {
    if (ba != null && ba.length != 0) {
      StringBuffer sb = new StringBuffer();

      for (int x = 0; x < ba.length; ++x) {
        if (x > 0) {
          sb.append(" ");
        }

        String hexNumber = "0" + Integer.toHexString(255 & ba[x]);
        sb.append(hexNumber.substring(hexNumber.length() - 2));
      }

      return sb.toString();
    } else {
      return null;
    }
  }

  public static void startServer(String serverAddr, int portNum, boolean sync, boolean persist, boolean depend,
      boolean firstOut) {
    ServerSocket serverSocket = null;

    try {
      serverSocket = new ServerSocket(portNum, 100, InetAddress.getByName(serverAddr));
      System.out.println("Start : " + serverSocket.getLocalSocketAddress() + " // sync : " + sync + " // persistence : "
          + persist + " // in-out dependency : " + depend + " // first send : " + firstOut);
    } catch (IOException var10) {
      System.out.println(var10);
      System.exit(1);
    }

    while (true) {
      while (true) {
        try {
          Socket socket = serverSocket.accept();
          SocketHandler tns = new SocketHandler(socket, sync, persist, depend, firstOut);
          Thread th = new Thread(tns);
          th.start();
        } catch (Throwable var11) {
          var11.printStackTrace();
        }
      }
    }
  }

  public static void startServer_single(String serverAddr, int portNum, boolean sync, boolean persist, boolean depend,
      boolean firstOut) {
    ServerSocket serverSocket = null;

    try {
      serverSocket = new ServerSocket(portNum);
      new ServerSocket();
      System.out.println("Start : " + serverSocket.getLocalSocketAddress() + " // sync : " + sync + " // persistence : "
          + persist + " // in-out dependency : " + depend + " // first send : " + firstOut);
    } catch (IOException var9) {
      System.out.println(var9);
      System.exit(1);
    }

    while (true) {
      while (true) {
        try {
          Socket socket = serverSocket.accept();
          SocketHandler tns = new SocketHandler(socket, sync, persist, depend, firstOut);
          tns.process();
        } catch (Throwable var10) {
          var10.printStackTrace();
        }
      }
    }
  }

  public static void startClient(String serverAddr, int portNum, boolean sync, boolean persist, boolean depend,
      boolean firstOut) {
    SocketHandler tns = null;
    Socket socket = null;

    try {
      socket = new Socket(serverAddr, portNum);
      System.out.println("Connected : " + socket.getLocalSocketAddress() + " // sync : " + sync + " // persistence : "
          + persist + " // in-out dependency : " + depend + " // first send : " + firstOut);
      tns = new SocketHandler(socket, sync, persist, depend, firstOut);
      Thread th = new Thread(tns);
      th.start();
    } catch (Throwable var9) {
      var9.printStackTrace();
    }

  }

  public static void main(String[] args) {
    String serverAddr = "127.0.0.1";
    int portNum = 40444;
    boolean sync = true;
    boolean persist = false;
    boolean depend = true;
    boolean firstOut = false;
    System.out.println("java NetworkCommon client [options]");
    System.out.println("or");
    System.out.println("java NetworkCommon server [options]");
    System.out.println();
    System.out.println("Options : [-ip<ip_address>] [-p<port_number>] [-async] [-P] [-ir] [-fo]");
    System.out.println("-ip<ip_address> : ip [default : 127.0.0.1]");
    System.out.println("-p<port_number> : port [default : 40444]");
    System.out.println("-async : ASYNC mode - no reply message [default : sync]");
    System.out.println("-P : persistence mode [default : false]");
    System.out.println("-fo : send first [default : false]");
    System.out.println("-ir : independent RECV / SEND [default : false]");
    System.out.println("-h : help");
    if (args.length < 1) {
      System.exit(1);
    }

    for (int i = 0; i < args.length; ++i) {
      String arg = args[i].trim();
      if (arg.startsWith("-ip")) {
        serverAddr = arg.substring(3).trim();
      } else if (arg.startsWith("-p")) {
        portNum = Integer.parseInt(arg.substring(2));
      } else if (arg.equalsIgnoreCase("-async")) {
        sync = false;
      } else if (arg.equalsIgnoreCase("-P")) {
        persist = true;
      } else if (arg.equalsIgnoreCase("-fo")) {
        firstOut = true;
      } else if (arg.equalsIgnoreCase("-ir")) {
        depend = false;
      } else if (arg.equalsIgnoreCase("-h")) {
        System.exit(0);
      }
    }

    if (args[0].equalsIgnoreCase("server")) {
      startServer(serverAddr, portNum, sync, persist, depend, firstOut);
    } else {
      startClient(serverAddr, portNum, sync, persist, depend, firstOut);
    }

  }
}