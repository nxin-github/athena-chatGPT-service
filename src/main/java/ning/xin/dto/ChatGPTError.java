package ning.xin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :宁鑫
 * @date : 2023/2/26 18:00
 */
@NoArgsConstructor
@Data
public class ChatGPTError {

    @JsonProperty("error")
    private ErrorDTO error;

    @NoArgsConstructor
    @Data
    public static class ErrorDTO {
        @JsonProperty("message")
        private String message;
        @JsonProperty("type")
        private String type;
        @JsonProperty("param")
        private Object param;
        @JsonProperty("code")
        private Object code;
    }
}
