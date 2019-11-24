import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class Server
{
	public static void main(String args[]) throws IOException
	{
		int i;
		String RAND, SRES_ = "", SRES_sub;
		String Ki = "24b47cf5fb534fb1a5a28b415260304d";
		ServerSocket ss = new ServerSocket(9999);
		Socket sock = ss.accept();
		InputStream in = sock.getInputStream();
		OutputStream out = sock.getOutputStream();
		UUID uuid = UUID.randomUUID();
		RAND = uuid.toString().replace("-","");
		System.out.println("RAND Generated: " + RAND);
		out.write(RAND.getBytes());
		for(i=0;i<RAND.length();i++)
		{
			if(i < Ki.length())
				SRES_ += (char)(((RAND.charAt(i)^Ki.charAt(i)))%26+97);
			else
				SRES_ += RAND.charAt(i);
		}
		System.out.println("SRES* :" + SRES_);
		byte SRES[] = new byte[1024];
		in.read(SRES);
		if(SRES_.equals(new String(SRES).trim()))
			System.out.println("SRES matched!! \n Subscriber Authenticated...");
		out.write("true".getBytes());
		sock.close();
		ss.close();
	}
}
