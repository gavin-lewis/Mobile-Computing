import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.net.Socket;
import java.util.Arrays;
public class Client
{
	public static void main(String args[]) throws UnknownHostException, IOException
	{
		int i;		
		String RAND, SRES = "";
		String Ki = "24b47cf5fb534fb1a5a28b415260304d";
		Socket sock = new Socket("127.0.0.1", 9999);
		String rep = new String();
		InputStream in = sock.getInputStream();
		OutputStream out = sock.getOutputStream();
		byte rand[] = new byte[1024];
		in.read(rand);
		RAND = new String(rand).trim();
		System.out.println("RAND from Mobile Network: " + RAND);
		for(i=0;i<RAND.length();i++)
		{
			if(i < Ki.length())
				SRES += (char)(((RAND.charAt(i)^Ki.charAt(i)))%26+97);
			else
				SRES += RAND.charAt(i);
		}
		System.out.println("SRES :" + SRES);
		out.write(SRES.getBytes());
		Arrays.fill(rand,(byte)0);
		in.read(rand);
		if("true".equals(new String(rand).trim()))
			System.out.println("Access Granted");
		else
			System.out.println("Access Denied");
		sock.close();
	}
}
