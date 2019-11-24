import java.io.*;
import java.net.*;
import java.util.*;
public class Server
{
	public static void main(String args[]) throws IOException
	{
		char ch;
		int i, len;
		String RAND, Kc = "", EM = "", DM= "", Enc = "", tosend = "";
		String Ki = "24b47cf5fb534fb1a5a28b415260304d";
		String msg = "Hello from Server";
		ServerSocket ss = new ServerSocket(9999);
		Socket sock = ss.accept();
		InputStream in = sock.getInputStream();
		OutputStream out = sock.getOutputStream();
		UUID uuid = UUID.randomUUID();
		RAND = uuid.toString().replace("-","");
		System.out.println("RAND Generated: " + RAND);
		out.write(RAND.getBytes());
		RAND = new StringBuilder(RAND).reverse().toString();
		len = RAND.length();
		for(i=0;i<len/2;i++)
			Kc += (char)(((int)(RAND.charAt(i)+Ki.charAt(i)+RAND.charAt(len-i-1)+Ki.charAt(len-i-1)))%26+97);
		System.out.println("Kc: " + Kc);
		System.out.println("Message to send to Client: " + msg);
		for(i=0;i<msg.length();i++)
		{
			if(i < Kc.length())
			{
				EM += (char)((msg.charAt(i)+Kc.charAt(i)));
				tosend += (char)(((msg.charAt(i)+Kc.charAt(i))%26)+97);
			}
			else
			{
				EM += msg.charAt(i);
				tosend += msg.charAt(i);
			}
		}
		System.out.println("Encrypted Message to send: " + tosend);
		out.write(EM.getBytes());
		byte data[] = new byte[1024];
		in.read(data);
		Enc = new String(data).trim();
		for(i=0;i<Enc.length();i++)
		{
			if(i < Kc.length())
				DM += (char)((Enc.charAt(i)-Kc.charAt(i)));
			else
				DM += Enc.charAt(i);
		}
		System.out.println("Decrypted Data from Client: " + DM.replaceAll("\\W", " "));
		sock.close();
		ss.close();
	}
}
