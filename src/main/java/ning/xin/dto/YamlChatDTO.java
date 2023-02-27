package ning.xin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author :宁鑫
 * @date : 2023/2/26 16:11
 */
@Data
public class YamlChatDTO {
    @JsonProperty("prompt")
    private String prompt;
    @JsonProperty("temperature")
    private Double temperature;
    @JsonProperty("max-tokens")
    private Integer maxTokens;
    @JsonProperty("num")
    private Integer num;
    @JsonProperty("stop")
    private String stop;
    @JsonProperty("api-key")
    private String apiKey;
}
