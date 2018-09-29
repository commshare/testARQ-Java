package media.commshare.arqlib;



import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

class Sender extends JFrame {
    private static JTextArea showArea;
    private JTextField inputField;
    private static String IPadress;
    public static String[] datas = new String[1000];
    public static Timer timer = new Timer();
    public static int m = 0;

    public static void sentData(String Message) {
        byte[] dataarr = new byte[100010];
        dataarr = Message.getBytes();

        try {
            Thread.currentThread().sleep(500);//毫秒
        } catch (Exception e) {
        }


        try {
            InetAddress sentIP = InetAddress.getByName(IPadress);
            DatagramSocket dsset = new DatagramSocket(62000);
            DatagramPacket dprec = new DatagramPacket(dataarr, dataarr.length, sentIP, 64650);
            System.out.println(sentIP);
            // 从己方62000 端口发送到客户端64650端口
            dsset.send(dprec);
            dsset.close();
            timer1(1);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recieveData() {
        byte[] dataarr = new byte[100010];
        try {
            DatagramSocket dsset = new DatagramSocket(63300);
            // 监听己方63300端口收到的消息
            DatagramPacket dprec = new DatagramPacket(dataarr, dataarr.length);
            dsset.receive(dprec);
            dsset.close();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            showArea.append(format.format(new Date()) + "\n" + "新消息： " + new String(dataarr).trim() + "\n");

            String content2 = new String(dataarr).trim().split("\\(")[1].split("\\)")[0];
            System.out.println(content2);
            switch (content2) {
                case "成功接受":
                    timer1(0);
                    m++;
                    String type = "";
                    type = ImitateTransfer.transfer_send();
                    switch (type) {
                        case "100":
                            type = "成功发送";
                            break;

                        case "101":
                            type = "做校验时出错";
                            break;

                        case "102":
                            type = "传送时丢失";
                            break;
                    }
                    sentData("A发送:  数据为:" + datas[m] + "类型为:" + type + ";  (正常发送)");
                    showArea.append("A发送:  数据为:" + datas[m] + "类型为:" + type + ";  (正常发送)" + "\n");
                    break;

                case "确认丢失":

                    break;


                case "确认超时":

                    break;

            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startChat() {

        for (int i = 0; i < datas.length; i++) {
            datas[i] = String.valueOf(i);
        }

        JLabel displyjLabel = new JLabel("客户端A");
        displyjLabel.setBounds(150, 10, 100, 15);
        JPanel myJPanel = new JPanel();
        myJPanel.setLayout(null);
        this.setContentPane(myJPanel);
        myJPanel.add(displyjLabel);
        showArea = new JTextArea();
        showArea.setLineWrap(true);
        JScrollPane scrollpane = new JScrollPane(showArea);
        scrollpane.setBounds(20, 30, 350, 350);
        scrollpane.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.CYAN, Color.BLUE, null, null));
        scrollpane.setVisible(true);
        inputField = new JTextField();
        inputField.setBounds(20, 410, 280, 25);
        inputField.setVisible(true);
        myJPanel.add(scrollpane);
        JButton mybutton = new JButton("发送");
        mybutton.setBounds(310, 410, 60, 25);
        myJPanel.add(mybutton);
        myJPanel.add(inputField);
        myJPanel.setVisible(true);
        JButton quitjButton = new JButton("退出");
        quitjButton.setBounds(250, 450, 100, 30);
        myJPanel.add(quitjButton);
        quitjButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        });
        JButton returnjButton = new JButton("返回主界面");
        returnjButton.setBounds(20, 450, 100, 30);
        myJPanel.add(returnjButton);
        returnjButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                initWindow();
            }
        });
        this.setVisible(true);
        inputField.requestFocus();
        inputField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                if (e.getKeyChar() == '\n') {

                    String type = "";
                    type = ImitateTransfer.transfer_send();
                    switch (type) {
                        case "100":
                            type = "成功发送";
                            break;

                        case "101":
                            type = "做校验时出错";
                            break;

                        case "102":
                            type = "传送时丢失";
                            break;


                    }

                    //A发送：  数据为：1  类型为：成功发送;  (开始发送)
                    sentData("A发送:  数据为:" + datas[m] + "类型为:" + type + ";  (开始发送)");
                    inputField.setText("");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    showArea.append("A发送:  数据为:" + datas[m] + "类型为:" + type + ";  (开始发送)" + "\n");
                    //sendMessage("A（发送）-" + datas[m] + "-" + 实验二.ImitateTransfer.transfer_send() + "---开始发送");
                }
            }
        });
        mybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                // 当发送键被按下
                String type = "";
                type = ImitateTransfer.transfer_send();
                switch (type) {
                    case "100":
                        type = "成功发送";
                        break;

                    case "101":
                        type = "做校验时出错";
                        break;

                    case "102":
                        type = "传送时丢失";
                        break;


                }

                //A发送：  数据为：1  类型为：成功发送;  (开始发送)
                sentData("A发送:  数据为:" + datas[m] + "类型为:" + type + ";  (开始发送)");
                inputField.setText("");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                showArea.append("A发送:  数据为:" + datas[m] + "类型为:" + type + ";  (开始发送)" + "\n");
                //sendMessage("A（发送）-" + datas[m] + "-" + 实验二.ImitateTransfer.transfer_send() + "---开始发送");

            }
        });
    }

    public void initWindow() {
        JPanel myjPanel = new JPanel();
        myjPanel.setLayout(null);
        this.setContentPane(myjPanel);
        JLabel myjLabel = new JLabel("欢迎使用本聊天程序");
        myjLabel.setBounds(50, 100, 300, 40);
        myjLabel.setForeground(Color.cyan);
        myjLabel.setFont(new Font("HirakakuProN-W6", Font.BOLD, 30));
        JLabel tishiJLabel = new JLabel("请输入对方的IP地址:");
        tishiJLabel.setBounds(15, 300, 150, 20);
        final JTextField ipJTextField = new JTextField("127.128.0.1");
        ipJTextField.setBounds(150, 300, 115, 20);
        JButton okJButton = new JButton("确定");
        okJButton.setBounds(280, 300, 70, 20);
        ipJTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                if (e.getKeyChar() == '\n') {
                    startChat();
                }
            }
        });
        okJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                IPadress = ipJTextField.getText();
                startChat();
            }
        });
        myjPanel.add(tishiJLabel);
        myjPanel.add(myjLabel);
        myjPanel.add(ipJTextField);
        myjPanel.add(okJButton);
        JButton quitjButton = new JButton("退出");
        quitjButton.setBounds(150, 350, 100, 30);
        myjPanel.add(quitjButton);
        quitjButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        });
        this.setVisible(true);

    }

    public Sender() {
        this.setBounds(420, 100, 400, 550);
        this.setLayout(null);
        this.setTitle("客户端A");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initWindow();
        while (true) {
            recieveData();
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new Sender();
    }

    public static void timer1(int q) {

        if (q == 1) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    String type = "";
                    type = ImitateTransfer.transfer_send();
                    switch (type) {
                        case "100":
                            type = "成功发送";
                            break;

                        case "101":
                            type = "做校验时出错";
                            break;

                        case "102":
                            type = "传送时丢失";
                            break;
                    }
                    sentData("A发送:  数据为:" + datas[m] + "类型为:" + type + ";  (超时重发)");
                    showArea.append("A发送:  数据为:" + datas[m] + "类型为:" + type + ";  (超时重发)" + "\n");

                }
            }, 2000);
        }

        if (q == 0) {
            timer.cancel();
        }
    }


}

/*
作者：关玮琳linSir
        链接：https://www.jianshu.com/p/9f8ec5667fa6
        來源：简书
        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。*/
