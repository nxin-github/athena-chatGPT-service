package ning.xin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author :宁鑫
 * @date : 2023/2/26 18:13
 */
@Data
public class YamlRegistrationCenterDTO {
    @JsonProperty("server-addr")
    private String serverAddr;
    @JsonProperty("port")
    private Integer port;
}
