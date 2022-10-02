package client;
import java.net.*;
import java.io.*;
import org.json.*;

public class client 
{

	public void StartClient(String address,int port) throws IOException
	{
		Socket unSocket 		= null;
        BufferedWriter out 	= null;
        BufferedReader in 		= null;

        try 
		{
            unSocket 	= new Socket(address, port);
            out 		= new BufferedWriter(new OutputStreamWriter(unSocket.getOutputStream(),"UTF-8"));
            in 			= new BufferedReader(new InputStreamReader(unSocket.getInputStream(),"UTF-8"));
        }
        catch (IOException e) 
		{
            e.printStackTrace();
        }

        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;

        while ((fromServer = in.readLine()) != null) {
            System.out.println("Servidor: " + fromServer);
            
        	JSONObject data = new JSONObject();
        	JSONObject jo = new JSONObject();
        	System.out.println("Operación:");
        	int op = Integer.valueOf(stdIn.readLine());
        	jo.put("op", op);
        	
        	switch (op) {
        	case 1:
            	System.out.println("NIS: ");
            	data.put("NIS", stdIn.readLine());
            	System.out.println("estado: ");
            	data.put("estado", stdIn.readLine());
            	System.out.println("mensaje: ");
            	data.put("mensaje", stdIn.readLine());
				System.out.println("tipo_operacion: ");
            	data.put("tipo_operacion", stdIn.readLine());
				System.out.println("conexion: ");
            	data.put("conexion", stdIn.readLine());
            	break;
        	default:
        		System.out.println("Unknown operation");
        	}
        }

        out.close();
        in.close();
        stdIn.close();
        unSocket.close();
    }
    public static void main(String[] args) throws IOException 
	{
		client tms = new client();
		tms.StartClient("localhost", 8088);
	}   
}
