package socketprg;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import socketprg.view.MainFrame;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket sock = new Socket("localhost", 9000);
		MainFrame main = new MainFrame(sock);
		main.setVisible(true);

	}

}
