package socketprg.view.bai2;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Bai2View extends JFrame {

	private JPanel contentPane;
	private JTextField source;
	private JTextField destination;

	private Socket sock;
	DataInputStream din;
	DataOutputStream dout;
	ReceiverThread receiver;

	
	public Bai2View(Socket sock) {
		this.sock = sock;
		setupSocket();
		prepareFrame();
	}
	
	private void setupSocket() {
		try {
			din = new DataInputStream(sock.getInputStream());
			dout = new DataOutputStream(sock.getOutputStream());
		} catch (Exception e) {}
		
		receiver = new ReceiverThread();
		receiver.start();
	}
	
	public void prepareFrame() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCopyThuMuc = new JLabel("Copy thu muc nguon vao dich");
		lblCopyThuMuc.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopyThuMuc.setBounds(12, 0, 426, 36);
		contentPane.add(lblCopyThuMuc);
		
		JLabel lblNguon = new JLabel("Nguon: ");
		lblNguon.setBounds(22, 48, 70, 15);
		contentPane.add(lblNguon);
		
		JLabel lblDich = new JLabel("Dich: ");
		lblDich.setBounds(22, 83, 70, 15);
		contentPane.add(lblDich);
		
		source = new JTextField();
		source.setBounds(110, 46, 114, 19);
		contentPane.add(source);
		source.setColumns(10);
		
		destination = new JTextField();
		destination.setBounds(110, 81, 114, 19);
		contentPane.add(destination);
		destination.setColumns(10);
		
		JButton btnNhap = new JButton("Nhap");
		btnNhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String src = source.getText();
				String dst = destination.getText();
				String info = "2_" + src + "_" + dst;
				
				try {
					dout.writeUTF(info);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNhap.setBounds(168, 162, 117, 25);
		contentPane.add(btnNhap);
	}
	
	class WindowHandler extends WindowAdapter {    // handle close button
		public void windowClosing(WindowEvent e) {
			System.out.println("client sent quit");
			try {
				System.out.println("client sent quit");
				dout.writeUTF("quit");
				sock.close();
			}catch(Exception exp) {}
			dispose();
			receiver.interrupt();
		}
	}
	
	class ReceiverThread extends Thread {
		public void run() {
			while (!interrupted()) {
				try {
					String data = din.readUTF();
					JOptionPane.showMessageDialog(null, data);
				} catch (Exception e) {}
			}
		}
	}

}
