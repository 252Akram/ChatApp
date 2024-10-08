import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;
import java.io.*;


public class Client extends JFrame implements ActionListener {

    JTextField t1;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox(); //`Box` is a container that uses a `BoxLayout` object as its layout manager
    static DataOutputStream dout;
    static JFrame f = new JFrame();

    Client(){
        f.setLayout(null);
        JPanel p1= new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,550,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);

        JLabel back =new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel dp =new JLabel(i6);
        dp.setBounds(40,10,50,50);
        p1.add(dp);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video =new JLabel(i9);
        video.setBounds(380,20,30,30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone =new JLabel(i12);
        phone.setBounds(450,20,35,30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert =new JLabel(i15);
        morevert.setBounds(510,20,10,25);
        p1.add(morevert);

        JLabel name =  new JLabel("Raj");
        name.setBounds(110,12,100,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);

        JLabel status =  new JLabel("Active Now");
        status.setBounds(110,35,100,20);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(10,75,530,670);
        f.add(a1);

        t1 = new JTextField();
        t1.setBounds(10,655,400,30);
        t1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(t1);

        JButton b1 = new JButton("Send");
        b1.setBounds(420,655,100,30);
        b1.setBackground(new Color(7,94,84));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        b1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(b1);

        f.setSize(550, 700);  //setting the size of the visible frame
        f.getContentPane().setBackground(Color.white); //used to change the color of the box by using getContentPane
        f.setLocation(800, 50);  //used to change the orientation of the box to a desired location x:from the left y:from the top
        f.setUndecorated(true); //used to remove the title bar of the frame
        f.setVisible(true); //used to set the visibility of the frame as required by setting true or false


    }

    public void actionPerformed(ActionEvent ae){
        try {
            String out = t1.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());//used to set the layout of the panel

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END); //used to set the text to the right side of the panel
            vertical.add(right);//used to add the text to the panel
            vertical.add(Box.createVerticalStrut(13)); //used to create a vertical space between the messages

            a1.add(vertical, BorderLayout.PAGE_START); //used to set the messages to the top of the panel

            dout.writeUTF(out);

            t1.setText("");//used to set the text to empty after sending the message
            f.repaint();//used to update the frame
            f.invalidate();//used to update the frame
            f.validate();//used to update the frame
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //used to set the layout of the panel

        JLabel output = new JLabel("<html><p style = \"width : 150px\">" + out + "</p></html>"); //used to set the text in the label
        output.setFont(new Font("Tahoma", Font.PLAIN, 16)); //used to set the font of the text
        output.setBackground(new Color(13, 201, 66)); //used to set the background color of the text
        output.setOpaque(true); //used to set the text as opaque
        output.setBorder(BorderFactory.createEmptyBorder(15,15,15,50)); //used to set the border of the text

        panel.add(output);

        Calendar cal = Calendar.getInstance(); //used to get the instance of the calendar
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); //used to set the format of the time


        JLabel time = new JLabel();//used to set the time in the label
        time.setText(sdf.format(cal.getTime())); //used to set the time in the label

        panel.add(time); //used to add the time to the panel

        return panel;

    }


    public static void main(String[] args){

        new Client();  //initialization of the server class

        try{
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream()); //used to get the input stream from the socket
            dout = new DataOutputStream(s.getOutputStream());

            while(true){
                a1.setLayout(new BorderLayout());
                String msginput = din.readUTF();
                JPanel panel = formatLabel(msginput);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15)); //used to create a vertical space between the messages

                a1.add(vertical, BorderLayout.PAGE_START); //used to set the messages to the top of the panel


                f.validate();

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
