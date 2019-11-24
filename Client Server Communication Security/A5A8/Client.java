import java.io.*;
import java.net.*;
import java.util.*;
public class Client
{
	public static void main(String args[]) throws UnknownHostException, IOException
	{
		char ch;
		int i, len;		
		String RAND, Kc = "", EM = "", DM = "", Enc = "", tosend = "";
		String Ki = "24b47cf5fb534fb1a5a28b415260304d";
		Socket sock = new Socket("127.0.0.1", 9999);
		String msg = "Hello from Client";
		InputStream in = sock.getInputStream();
		OutputStream out = sock.getOutputStream();
		byte rand[] = new byte[1024];
		in.read(rand);
		RAND = new String(rand).trim();
		System.out.println("RAND from Mobile Network: " + RAND);
		RAND = new StringBuilder(RAND).reverse().toString();
		len = RAND.length();
		for(i=0;i<len/2;i++)
			Kc += (char)(((int)(RAND.charAt(i)+Ki.charAt(i)+RAND.charAt(len-i-1)+Ki.charAt(len-i-1)))%26+97);
		System.out.println("Kc: " + Kc);
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
		System.out.println("Decrypted Data from Server: " + DM.replaceAll("\\W", " "));
		System.out.println("Message to send to Server: " + msg);
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
		sock.close();
	}
}
