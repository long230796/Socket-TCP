package socketprg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import socketprg.bai1calculate.Bai1Calculate;



class ReceiverThread extends Thread {
	private Socket sock;
	DataInputStream din;
	DataOutputStream dout;
	
	public ReceiverThread(Socket sock) {
		this.sock = sock;
	}
	
	private void sender(String result) {
		try {
	        dout.writeUTF(result);
		} catch (Exception e) {
			try {
				dout.writeUTF("Vui long nhap so nguyen duong");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	private static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
		if (!destinationDirectory.exists()) {
			destinationDirectory.mkdir();
		}
		for (String f : sourceDirectory.list()) {
			copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
		}
	}
	
	public static void copyDirectoryCompatibityMode(File source, File destination) throws IOException {
	    if (source.isDirectory()) {
	        copyDirectory(source, destination);
	    } else {
	        copyFile(source, destination);
	    }
	}
	
	
	private static void copyFile(File sourceFile, File destinationFile) 
			  throws IOException {
			    try (InputStream in = new FileInputStream(sourceFile); 
			      OutputStream out = new FileOutputStream(destinationFile)) {
			        byte[] buf = new byte[1024];
			        int length;
			        while ((length = in.read(buf)) > 0) {
			            out.write(buf, 0, length);
			        }
			    }
			}
	
	
	private void routing(String data) {
		String[] arrValue = data.split("_");
		String bai = arrValue[0];
		
		if (bai.contains("1")) {
			Double a = Double.valueOf(arrValue[1]);
			Double b = Double.parseDouble(arrValue[2]);
			Double c = Double.parseDouble(arrValue[3]);
			Double d = Double.parseDouble(arrValue[4]);
//			Integer.parseInt(new String(packeta.getData(), 0, packeta.getLength()).trim());
			System.out.println(a);
			System.out.println(b);
			System.out.println(c);
			System.out.println(d);
			
			Bai1Calculate cubic = new Bai1Calculate();
			cubic.solve(a, b, c, d);
	        System.out.println("x1 = " + cubic.x1);
	        sender("x1 = " + cubic.x1);
	        if (cubic.nRoots == 3) {
	        	sender("x2 = " + cubic.x2);
	        	sender("x3 = " + cubic.x3);
	            System.out.println("x2 = " + cubic.x2);
	            System.out.println("x3 = " + cubic.x3);
	        }
		} else {
			String src = arrValue[1];
			String dst = arrValue[2];
			
			System.out.println(src);
			System.out.println(dst);
			File sourceDirectory = new File(src);
			File destinationDirectory = new File(dst);
			
			try {
				copyDirectory(sourceDirectory, destinationDirectory);
			} catch (IOException e) {
				sender("khong the copy directory ");
			}
			
			sender("Copy thu muc thanh cong");
		}
		
		
	}
	
	public void run() {
		while(! interrupted()) {
			try {
				din = new DataInputStream(sock.getInputStream());
				dout = new DataOutputStream(sock.getOutputStream());
				String data = din.readUTF();
				
				if ("quit".equalsIgnoreCase(data) ) {
					try {
						System.out.println("Received from client - " + data);
						sock.close();
					} catch (Exception e) {}
					this.interrupt();
				} else {
					System.out.println("Received from client - " + data);
					routing(data);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket ser = new ServerSocket(9000);
		
		System.out.println("Waiting for the client...");
		
		Socket sock = ser.accept();
		
		System.out.println("Client connected!");

		ReceiverThread receiver = new ReceiverThread(sock);
		receiver.start();

	}

}
