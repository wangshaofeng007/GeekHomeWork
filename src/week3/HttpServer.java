package week3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    public static void main(String[] args) throws IOException {
     ExecutorService executorService =  Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*4);
     ServerSocket serverSocket  =  new ServerSocket(8803);
     while (true){
        Socket socket = serverSocket.accept();
         executorService.execute(()->service(socket));
     }
    }

    private static void service(Socket socket){
        try {
            PrintWriter printWriter =   new PrintWriter( socket.getOutputStream(),true);
            String body = "hello,nio";
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println("Context-Length:"+body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
