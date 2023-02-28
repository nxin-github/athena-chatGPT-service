package ning.xin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ning.xin.AthenaService;
import ning.xin.dto.YamlDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.*;

/**
 * @author :宁鑫
 * @date : 2023/2/26 11:29
 */
public class Utils {
    public static YamlDTO readYaml() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try {
            //这么读取yaml有点蠢，但是没想到什么好办法
            String jarPath = new File(AthenaService.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getAbsolutePath();
            int lastIndex = jarPath.lastIndexOf("/");
//            String path = jarPath.substring(0, lastIndex).concat("/athena-service.yaml");
            String path = "/Users/xinning/Desktop/service/example/athena-service.yaml";
            final File file = new File(path);
            final InputStream resourceAsStream = new FileInputStream(file);
            return mapper.readValue(resourceAsStream, YamlDTO.class);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Executor getExecutor() {
        //获取当前系统cpu数
        int processors = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(
                processors * 2 + 1,
                processors * 2 + 5,
                5L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    /**
     * 防止因为使用vpn导致客户端找不到
     */
    public static String getHostAddress() throws UnknownHostException {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLinkLocalAddress() && !addr.isLoopbackAddress() && addr instanceof java.net.Inet4Address) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return InetAddress.getLocalHost().getHostAddress();
    }
}
