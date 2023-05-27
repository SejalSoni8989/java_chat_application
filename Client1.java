import java.net.Socket;
import java.io.*;

class Client{
    Socket socket;
    //for reading
    BufferedReader br;
    //for writing the data
    PrintWriter out;
    public Client(){
        try{
            System.out.println("sending request to server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("connection done.");

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void startReading(){
        //tread-read the data
        Runnable r1=()->{
            System.out.println("reader started");
            try{
            while(true){
              
               String msg=br.readLine();
               if(msg.equals("exit")){
                System.out.println("server terminated the chat");
                socket.close();
                break;
               }
               System.out.println("server :"+msg);
        }
    }catch(Exception e){
        e.printStackTrace();
    }
        };
        new Thread(r1).start();
    }
    public void startWriting(){
        //thread-recived the data from the user and sand the data client

        Runnable r2=()->{
            System.out.println("writer started..");
            try{
            while(true){
                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
                    out.print(content);
                    out.flush();//if data is not going
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                
            }
        }catch(Exception e){
            e.printStackTrace();

        }

        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("This is client");
        new Client();
    }

    
}
