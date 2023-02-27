package ning.xin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author :宁鑫
 * @date : 2023/2/26 16:01
 */
@Data
public class YamlDTO {
    @JsonProperty("chatgpt")
    private YamlChatDTO yamlChatDTO;

    @JsonProperty("registration-center")
    private YamlRegistrationCenterDTO yamlRegistrationCenterDTO;
}
