package socket;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Main extends JFrame implements SocketHandleable {
  JTextField tf_ip;
  JTextField tf_port;
  ButtonGroup group;
  JRadioButton rb_server;
  JRadioButton rb_client;
  JToggleButton tbtn_start;
  JTextArea ta_send_data;
  JTextArea ta_recv_data;
  JTextArea ta_console;
  JButton btn_send;
  JCheckBox cb_loopback;
  JCheckBox cb_autoReply;
  JCheckBox cb_sendTypeHex;
  boolean startFlag = false;
  TextAreaPrintStream taps;
  Font font = new Font("∏º¿∫ ∞ÌµÒ", 0, 12);
  Thread th;
  Thread ths;
  SocketHandler tns;
  Socket socket;
  static ServerSocket serverSocket;

  public Main() throws HeadlessException {
    this.initialize();
  }

  public Main(GraphicsConfiguration gc) {
    super(gc);
    this.initialize();
  }

  public Main(String title) throws HeadlessException {
    super(title);
    this.initialize();
  }

  public Main(String title, GraphicsConfiguration gc) {
    super(title, gc);
    this.initialize();
  }

  private void initialize() {
    this.setTitle("socket");
    this.setSize(800, 600);
    JPanel p1 = this.createParamPanel();
    JPanel p2 = this.createDataPanel();
    this.getContentPane().add(p1, "North");
    this.getContentPane().add(p2, "Center");
    this.compEnable(true);
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    this.taps = new TextAreaPrintStream(System.out);
    this.taps.setTextArea(this.ta_console);
    System.setOut(this.taps);
    System.setErr(this.taps);
  }

  private JPanel createParamPanel() {
    JPanel p1 = new JPanel();
    p1.setBorder(new EmptyBorder(5, 5, 0, 5));
    p1.setLayout(new BoxLayout(p1, 0));
    JPanel p11 = new JPanel();
    p11.setLayout(new BoxLayout(p11, 0));
    p11.add(new JLabel("IP : "));
    this.tf_ip = new JTextField();
    this.tf_ip.setPreferredSize(new Dimension(120, 18));
    this.tf_ip.setText("127.0.0.1");
    p11.add(this.tf_ip);
    p11.add(Box.createHorizontalStrut(10));
    p11.add(new JLabel("PORT : "));
    this.tf_port = new JTextField();
    this.tf_port.setPreferredSize(new Dimension(50, 18));
    this.tf_port.setText("11000");
    p11.add(this.tf_port);
    JPanel p1r = new JPanel();
    p1r.setBorder(new EmptyBorder(5, 5, 5, 5));
    p1r.setLayout(new BorderLayout());
    p1r.add(p11, "West");
    p1.add(p1r);
    this.group = new ButtonGroup();
    this.rb_server = new JRadioButton("Server");
    this.group.add(this.rb_server);
    this.rb_client = new JRadioButton("Client");
    this.rb_client.setSelected(true);
    this.group.add(this.rb_client);
    this.cb_loopback = new JCheckBox("Loopback");
    this.cb_autoReply = new JCheckBox("Auto-reply");
    //p1.add(this.cb_loopback);
    //p1.add(this.cb_autoReply);
    //p1.add(this.rb_server);
    //p1.add(this.rb_client);
    this.tbtn_start = new JToggleButton("Connect");
    this.tbtn_start.setBackground(SystemColor.control);
    this.tbtn_start.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(), new EmptyBorder(0, 5, 0, 7)));
    this.tbtn_start.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!Main.this.startFlag) {
          Main.this.startNetwork();
        } else {
          Main.this.stopNetwork();
        }

      }
    });
    p1.add(this.tbtn_start);
    return p1;
  }

  private JPanel createDataPanel() {
    JPanel p2 = new JPanel(new BorderLayout());
    p2.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5),
        BorderFactory.createCompoundBorder(new TitledBorder(new EtchedBorder(), "DATA"), new EmptyBorder(5, 5, 5, 5))));
    this.ta_send_data = new JTextArea();
    this.ta_send_data.setFont(this.font);
    this.ta_send_data.setLineWrap(true);
    JScrollPane areaScrollPane_send = new JScrollPane(this.ta_send_data);
    areaScrollPane_send.setVerticalScrollBarPolicy(22);
    areaScrollPane_send.setPreferredSize(new Dimension(250, 100));
    this.ta_recv_data = new JTextArea();
    this.ta_recv_data.setFont(this.font);
    this.ta_recv_data.setEditable(false);
    this.ta_recv_data.setLineWrap(true);
    JScrollPane areaScrollPane_recv = new JScrollPane(this.ta_recv_data);
    areaScrollPane_recv.setVerticalScrollBarPolicy(22);
    areaScrollPane_recv.setPreferredSize(new Dimension(250, 100));
    JPanel p2n1 = new JPanel(new BorderLayout());
    this.btn_send = new JButton("SEND DATA");
    this.btn_send.setBackground(SystemColor.control);
    this.btn_send.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(), new EmptyBorder(0, 5, 0, 7)));
    this.btn_send.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Main.this.sendData();
      }
    });
    p2n1.add(this.btn_send, "West");
    this.cb_sendTypeHex = new JCheckBox("hex");
    this.cb_sendTypeHex.setSelected(false);
    p2n1.add(this.cb_sendTypeHex, "East");
    JPanel p21 = new JPanel(new BorderLayout());
    p21.add(p2n1, "North");
    p21.add(areaScrollPane_send, "Center");
    JPanel p2n2 = new JPanel(new BorderLayout());
    JButton btn_recv = new JButton("RECV DATA");
    btn_recv.setBackground(SystemColor.control);
    btn_recv.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(), new EmptyBorder(0, 5, 0, 7)));
    btn_recv.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Main.this.ta_recv_data.setText("");
        Main.this.ta_recv_data
            .setCaretPosition(Main.this.ta_recv_data.getDocument().getLength());
      }
    });
    p2n2.add(btn_recv, "West");
    JPanel p22 = new JPanel(new BorderLayout());
    p22.add(p2n2, "North");
    p22.add(areaScrollPane_recv, "Center");
    this.ta_console = new JTextArea();
    this.ta_console.setEditable(false);
    this.ta_console.setLineWrap(true);
    this.ta_console.setFont(this.font);
    JScrollPane areaScrollPane_console = new JScrollPane(this.ta_console);
    areaScrollPane_console.setVerticalScrollBarPolicy(22);
    areaScrollPane_console.setPreferredSize(new Dimension(250, 250));
    JPanel p2c = new JPanel();
    p2c.setLayout(new BorderLayout());
    JPanel p2c1 = new JPanel(new BorderLayout());
    JButton btn_console = new JButton("Console");
    btn_console.setBackground(SystemColor.control);
    btn_console.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(), new EmptyBorder(0, 5, 0, 7)));
    btn_console.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Main.this.ta_console.setText("");
        Main.this.ta_console
            .setCaretPosition(Main.this.ta_console.getDocument().getLength());
      }
    });
    p2c1.add(btn_console, "West");
    p2c.add(p2c1, "North");
    p2c.add(areaScrollPane_console, "Center");
    JSplitPane splitPaneN = new JSplitPane(0, p21, p22);
    splitPaneN.setOneTouchExpandable(true);
    splitPaneN.setResizeWeight(0.5);
    splitPaneN.setBorder((Border) null);
    JSplitPane splitPane = new JSplitPane(0, splitPaneN, p2c);
    splitPane.setOneTouchExpandable(true);
    splitPane.setResizeWeight(0.5);
    splitPane.setBorder((Border) null);
    p2.add(splitPane, "Center");
    return p2;
  }

  public void startNetwork() {
    this.compEnable(false);
    this.btn_send.setEnabled(false);
    MakeSocket ms = null;
    if (this.rb_client.isSelected()) {
      System.out.println("Connecting...");
      ms = new MakeSocket(false, this);
    } else {
      System.out.println("Accepting...");
      ms = new MakeSocket(true, this);
    }

    this.ths = new Thread(ms);
    this.ths.start();
    this.startFlag = true;
  }

  public void setSocket(Socket sock) {
    this.socket = sock;
  }

  public void setTns(SocketHandler tns) {
    this.tns = tns;
  }

  public void startThread(Socket sock, SocketHandler tns, ServerSocket ss) {
    this.socket = sock;
    this.tns = tns;
    serverSocket = ss;
    this.th = new Thread(tns);
    this.th.start();
    this.btn_send.setEnabled(true);
  }

  public void stopNetwork() {
    if (this.th != null) {
      this.th.interrupt();
      this.th = null;
    }

    if (this.socket != null) {
      try {
        this.socket.close();
      } catch (IOException var3) {
      }

      this.socket = null;
    }

    if (serverSocket != null) {
      try {
        serverSocket.close();
      } catch (IOException var2) {
      }

      serverSocket = null;
    }

    this.tns = null;
    this.compEnable(true);
    this.startFlag = false;
  }

  private void compEnable(boolean flag) {
    this.tf_ip.setEditable(flag);
    this.tf_port.setEditable(flag);
    this.rb_server.setEnabled(flag);
    this.rb_client.setEnabled(flag);
    this.btn_send.setEnabled(!flag);
    this.tbtn_start.setSelected(!flag);
  }

  public void sendData() {
    try {
      if (this.cb_sendTypeHex.isSelected()) {
        // 2024.09.25 ¿¸√º ±Ê¿Ã 4byte √ﬂ∞°
        String data = this.ta_send_data.getText();
        byte[] buf = data.getBytes();
        String len = String.format("%4s", buf.length).replace(' ', '0');
        this.tns.sendData(SocketHandler.hexToByteArray(len + data));
      } else {
        String data = this.ta_send_data.getText();
        byte[] buf = data.getBytes();
        String len = String.format("%4s", buf.length).replace(' ', '0');
        buf = (len + data).getBytes();
        this.tns.sendData(buf);
      }
    } catch (IOException var2) {
      var2.printStackTrace();
    }

  }

  public void sendData(byte[] buf) {
    try {
      this.tns.sendData(buf);
    } catch (IOException var3) {
      var3.printStackTrace();
    }

  }

  public void recvData(byte[] buf) {
    String str = new String(buf);
    this.ta_recv_data.append(str + '\n');
    this.ta_recv_data.setCaretPosition(this.ta_recv_data.getDocument().getLength());
    if (this.cb_loopback.isSelected()) {
      this.sendData(buf);
    } else if (this.cb_autoReply.isSelected()) {
      this.sendData();
    }

  }

  public String getIP() {
    return this.tf_ip.getText().trim();
  }

  public int getPort() {
    return Integer.parseInt(this.tf_port.getText());
  }

  public static void main(String[] args) {
    Main main = new Main();
    main.setVisible(true);
  }

  private static class MakeSocket implements Runnable {
    private boolean serverMode = false;
    private SocketHandleable nci;

    public MakeSocket(boolean sm, SocketHandleable netCI) {
      this.serverMode = sm;
      this.nci = netCI;
    }

    public void run() {
      Socket socket = null;
      SocketHandler nc = null;
      Main.serverSocket = null;
      if (this.serverMode) {
        try {
          if (Main.serverSocket != null) {
            try {
              Main.serverSocket.close();
            } catch (Throwable var5) {
              var5.printStackTrace();
            }
          }

          Main.serverSocket = new ServerSocket();
          Main.serverSocket.setReuseAddress(true);
          Main.serverSocket.bind(new InetSocketAddress(this.nci.getIP(), this.nci.getPort()));
          System.out.println("Start : " + Main.serverSocket.getLocalSocketAddress());
          socket = Main.serverSocket.accept();
          nc = new SocketHandler(socket, false, true, false, false, this.nci);
        } catch (IOException var6) {
          var6.printStackTrace();
          this.nci.stopNetwork();
          return;
        }
      } else {
        try {
          socket = new Socket(this.nci.getIP(), this.nci.getPort());
          nc = new SocketHandler(socket, false, true, false, false, this.nci);
        } catch (IOException var4) {
          var4.printStackTrace();
          this.nci.stopNetwork();
          return;
        }
      }

      this.nci.startThread(socket, nc, Main.serverSocket);
    }
  }

  private static class TextAreaPrintStream extends PrintStream {
    private JTextArea consoleTextArea;

    public TextAreaPrintStream(OutputStream out) {
      super(out);
    }

    public void setTextArea(JTextArea consoleTextArea) {
      this.consoleTextArea = consoleTextArea;
    }

    public void println(String s) {
      this.print(s);
      this.println();
    }

    public void print(String s) {
      this.consoleTextArea.append(s);
      this.consoleTextArea.setCaretPosition(this.consoleTextArea.getDocument().getLength());
    }

    public void println() {
      this.consoleTextArea.append("\n");
      this.consoleTextArea.setCaretPosition(this.consoleTextArea.getDocument().getLength());
    }

    public void print(char c) {
      this.consoleTextArea.append(Character.toString(c));
      this.consoleTextArea.setCaretPosition(this.consoleTextArea.getDocument().getLength());
    }

    public void println(char c) {
      this.print(c);
      this.println();
    }
  }
}