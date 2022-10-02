package server;
import java.net.*;
import java.util.ArrayList;
import java.io.*;
import org.json.*;

public class server 
{
	ServerSocket serverSocket   = null;
    Socket communicationSocket  = null;
    BufferedReader in           = null;
    BufferedWriter out          = null;
    ArrayList<JSONObject> data  = new ArrayList<>();
    public void StartServer(int port) throws IOException 
    {
        try
        {
            serverSocket        = new ServerSocket(port);
            System.out.println("Server started. Waiting for client...");
            communicationSocket = serverSocket.accept();
            in                  = new BufferedReader(new InputStreamReader(communicationSocket.getInputStream(),"UTF-8"));
            out                 = new BufferedWriter(new OutputStreamWriter(communicationSocket.getOutputStream(),"UTF-8"));
            String inputLine, outputLine = "";

            while ((inputLine = in.readLine())!= null) 
            {
            	System.out.println(inputLine);
                JSONObject object = new JSONObject(inputLine);
                
                switch (object.getInt("tipo_operacion")) 
                {
                case 1:
                	data.add(object);
                	outputLine = "{\"result\": \"added\"}";
                	break;
                case 2:
                	outputLine = "{\"ping\": \"100ms\"}";
                	break;
                case 3:
                	outputLine = "{\"Orden de desconexion\": \"1\"}";
                	break;
                case 4:
                    for(JSONObject iterableData : data)
                    {
                        if(iterableData.getString("conexion").compareTo("conectado") == 0) 
                        {
                            System.out.println(iterableData.getString("NIS"));
                        }
                    }
                    break;
                case 5:
                for(JSONObject iterableData : data)
                {
                    if(iterableData.getString("conexion").compareTo("desconectado") == 0) 
                    {
                        System.out.println(iterableData.getString("NIS"));
                    }
                }
                break;
                default:
                	outputLine = "{\"Orden de conexion\": \"1\"}";
                }
                out.write(outputLine);
            }
            out.close();
            in.close();
            communicationSocket.close();
            System.out.println("Finalizando Hilo");
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException 
    {
    	server tms = new server();
    	tms.StartServer(8088);
    }
}