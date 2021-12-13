package socketprg.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import socketprg.view.bai1.Bai1View;
import socketprg.view.bai2.Bai2View;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.net.Socket;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private Socket sock;
	
	public MainFrame(Socket sock) {
		this.sock = sock;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 369, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblThucHanhBuoi = new JLabel("THUC HANH BUOI 1");
		lblThucHanhBuoi.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblThucHanhBuoi.setHorizontalAlignment(SwingConstants.CENTER);
		lblThucHanhBuoi.setBounds(12, 12, 341, 27);
		contentPane.add(lblThucHanhBuoi);
		
		JLabel lblNewLabel = new JLabel("LAP TRINH SOCKET CLIENT-SERVER BANG GIAO THUC TCP");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 51, 341, 44);
		contentPane.add(lblNewLabel);
		
		JButton btnBai = new JButton("Bai 1");
		btnBai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bai1View bai1 = new Bai1View(sock);
				bai1.setVisible(true);
			}
		});
		btnBai.setBounds(43, 128, 117, 25);
		contentPane.add(btnBai);
		
		JButton btnBai_1 = new JButton("Bai 2");
		btnBai_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bai2View bai2 = new Bai2View(sock);
				bai2.setVisible(true);
			}
		});
		btnBai_1.setBounds(200, 128, 117, 25);
		contentPane.add(btnBai_1);
	}
}
