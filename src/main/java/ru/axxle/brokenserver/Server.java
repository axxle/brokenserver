package ru.axxle.brokenserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
	Шаблон сервера для различных дебагов. В одном из случаев, необходимо нужно было не отвечать на http-запрос и при этом чтобы браузер ждать ответа от сервера.
	Для этого с помощью nginx был перенаправлен нужный запрос на этот сервер (фрагмент конфига nginx-а будет также приложен)
*/
public class Server {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
	private static int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws InterruptedException  {
        try {
            try  {
				int port = DEFAULT_PORT;
				if (args.length > 0) {
					port = Integer.parseInt(args[0]);
				}
                server = new ServerSocket(port);
                System.out.println("Server started on port " + port);
                clientSocket = server.accept(); // accept() будет ждать пока
                //кто-нибудь не захочет подключиться
                try { // установив связь и воссоздав сокет для общения с клиентом можно перейти
                    // к созданию потоков ввода/вывода.
                    // теперь мы можем принимать сообщения
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    // и отправлять
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));


					for (int i = 0; i<10000000; i++) {
						int x = in.read(); // ждём пока клиент что-нибудь нам напишет
						System.out.print((char)x);
						if (x == -1) {
							Thread.sleep(1000);
						}
						// не долго думая отвечает клиенту
						//out.write("Privet, vi prislali: " + word + "\n");
						//out.flush(); // выталкиваем все из буфера
					}

                } finally { // в любом случае сокет будет закрыт
                    System.out.println("dfjkhgkdf");
                    clientSocket.close();
                    // потоки тоже хорошо бы закрыть
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Сервер закрыт!");
                    server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}