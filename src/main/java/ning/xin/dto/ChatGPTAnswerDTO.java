package ning.xin.dto;

import lombok.Data;

import java.util.List;

/**
 * @author :宁鑫
 * @date : 2023/2/26 08:10
 * chatGpt应答
 */
@Data
public class ChatGPTAnswerDTO {
    private String id;
    private String object;
    private Integer created;
    private String model;
    private List<ChoicesDTO> choices;
    private UsageDTO usage;

    @Data
    public static class UsageDTO {
        private Integer promptTokens;
        private Integer completionTokens;
        private Integer totalTokens;
    }

    @Data
    public static class ChoicesDTO {
        private String text;
        private Integer index;
        private Object logprobs;
        private String finishReason;
    }
}
