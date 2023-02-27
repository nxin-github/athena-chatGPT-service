package ning.xin.core;

import com.alibaba.fastjson.JSON;
import ning.xin.dto.YamlDTO;
import ning.xin.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SocketService {
    public static void executor() {
        try {
            // 创建 ServerSocket，并绑定端口号
            final YamlDTO yaml = Utils.readYaml();
            if (yaml == null) {
                System.out.println("yaml文件读取失败！！！");
                return;
            }
            ServerSocket serverSocket = new ServerSocket(24264);

            // 等待客户端连接
            System.out.println("等待客户端链接...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户端已连接: " + socket.getInetAddress());

                // 创建一个新线程处理客户端的消息
                Utils.getExecutor().execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端消息处理线程
     */
    static class ClientHandler implements Runnable {

        private final Socket SOCKET;

        public ClientHandler(Socket socket) {
            this.SOCKET = socket;
        }

        @Override
        public void run() {
            try {
                // 获取输入输出流
                BufferedReader in = new BufferedReader(new InputStreamReader(SOCKET.getInputStream()));
                PrintWriter out = new PrintWriter(SOCKET.getOutputStream(), true);

                // 循环读取客户端发送的消息并广播给所有客户端
                while (true) {
                    String msg = in.readLine();
                    if (msg == null) {
                        break;
                    }
                    final List<String> answerList = ChatClient.client(msg);
                    final String answerString = JSON.toJSONString(answerList);
                    out.println(answerString);
                }
                System.out.println(SOCKET.getInetAddress()+"，退出了");
                in.close();
                out.close();
                SOCKET.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
