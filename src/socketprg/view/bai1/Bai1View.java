package socketprg.view.bai1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Bai1View extends JFrame {

	private JPanel contentPane;
	private JTextField aVar;
	private JTextField bVar;
	private JTextField cVar;
	private JTextField dVar;
	
	private Socket sock;
	DataInputStream din;
	DataOutputStream dout;
	ReceiverThread receiver;



	public Bai1View(Socket sock) {
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
	
	private void prepareFrame() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 388);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblV = new JLabel("Viet chuong trinh client-server thuc hien giai phuong trinh bac 3");
		lblV.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblV.setHorizontalAlignment(SwingConstants.CENTER);
		lblV.setBounds(12, 12, 477, 29);
		contentPane.add(lblV);
		
		JLabel lblServerGiaiPhuong = new JLabel("Server giai phuong trinh ax^3 + bx^2 + cx + d = 0 va cho ket qua");
		lblServerGiaiPhuong.setHorizontalAlignment(SwingConstants.CENTER);
		lblServerGiaiPhuong.setBounds(12, 40, 467, 47);
		contentPane.add(lblServerGiaiPhuong);
		
		JLabel lblNhapA = new JLabel("Nhap a: ");
		lblNhapA.setBounds(22, 101, 70, 15);
		contentPane.add(lblNhapA);
		
		JLabel lblNhapB = new JLabel("Nhap b: ");
		lblNhapB.setBounds(22, 128, 70, 15);
		contentPane.add(lblNhapB);
		
		JLabel lblNhapC = new JLabel("Nhap c: ");
		lblNhapC.setBounds(22, 155, 70, 15);
		contentPane.add(lblNhapC);
		
		JLabel lblNhapD = new JLabel("Nhap d: ");
		lblNhapD.setBounds(22, 182, 70, 15);
		contentPane.add(lblNhapD);
		
		aVar = new JTextField();
		aVar.setBounds(110, 99, 114, 19);
		contentPane.add(aVar);
		aVar.setColumns(10);
		
		bVar = new JTextField();
		bVar.setBounds(110, 126, 114, 19);
		contentPane.add(bVar);
		bVar.setColumns(10);
		
		cVar = new JTextField();
		cVar.setBounds(110, 153, 114, 19);
		contentPane.add(cVar);
		cVar.setColumns(10);
		
		dVar = new JTextField();
		dVar.setBounds(110, 180, 114, 19);
		contentPane.add(dVar);
		dVar.setColumns(10);
		
		JButton btnNhap = new JButton("Nhap");
		btnNhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = aVar.getText();
				String b = bVar.getText();
				String c = cVar.getText();
				String d = dVar.getText();
				String info = "1_" + a + "_" + b + "_" + c + "_" + d;
				
				try {
					dout.writeUTF(info);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNhap.setBounds(194, 247, 117, 25);
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
