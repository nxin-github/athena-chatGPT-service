package ning.xin.core;

import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.NacosNamingService;
import ning.xin.dto.YamlDTO;
import ning.xin.dto.YamlRegistrationCenterDTO;
import ning.xin.util.Utils;

import java.util.Properties;

/**
 * @author :宁鑫
 * @date : 2023/2/25 07:53
 * 链接注册中心
 */
public class LinkRegistrationCenter {

    /**
     * 不允许用户自己改命名空间和服务名，客户端容易找不到，增加用户使用成本
     */
    public static void client() {
        final YamlDTO yaml = Utils.readYaml();
        if (yaml == null) {
            System.out.println("yaml文件读取失败");
            return;
        }
        final YamlRegistrationCenterDTO yamlRegistrationCenter = yaml.getYamlRegistrationCenterDTO();

        Properties properties = new Properties();
        properties.put("serverAddr", yamlRegistrationCenter.getServerAddr());
        try {
            NamingService namingService = new NacosNamingService(properties);
            // 获取公网IP地址
            final String hostAddress = Utils.getHostAddress();
            Instance instance = new Instance();
            instance.setIp(hostAddress);
            instance.setPort(yamlRegistrationCenter.getPort());
            instance.setServiceName("Athena");

            namingService.registerInstance(instance.getServiceName(), instance);
        } catch (Exception e) {
            System.out.println("配置中心链接失败！！！");
            e.printStackTrace();
        }
    }
}
