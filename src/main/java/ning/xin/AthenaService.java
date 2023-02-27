package ning.xin;

import ning.xin.core.LinkRegistrationCenter;
import ning.xin.core.SocketService;

/**
 * @author :宁鑫
 * @date : 2023/2/26 11:21
 */
public class AthenaService {
    public static void main(String[] args) {
        //1 注册到注册中心
        LinkRegistrationCenter.client();
        //2 启动Socket
        SocketService.executor();
    }
}
