package ning.xin.core;

import com.alibaba.fastjson.JSONObject;
import ning.xin.dto.ChatGPTAnswerDTO;
import ning.xin.dto.ChatGPTError;
import ning.xin.dto.YamlChatDTO;
import ning.xin.dto.YamlDTO;
import ning.xin.util.Utils;
import okhttp3.*;

import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author :宁鑫
 * @date : 2023/2/25 21:48
 * 连接ChatGPT
 */
public class ChatClient {

    public static List<String> client(String prompt) {
        try {
            final YamlDTO yaml = Utils.readYaml();
            if (yaml == null) {
                return Collections.singletonList("未读取到yaml配置文件");
            }
            final YamlChatDTO yamlChat = yaml.getYamlChatDTO();
            final JSONObject chatRequest = new JSONObject();
            chatRequest.put("temperature", yamlChat.getTemperature());
            chatRequest.put("max_tokens", yamlChat.getMaxTokens());
            chatRequest.put("n", yamlChat.getNum());
            chatRequest.put("stop", yamlChat.getStop());
            chatRequest.put("prompt", prompt);

            MediaType mediaType = MediaType.parse("application/json");
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            RequestBody body = RequestBody.create(mediaType, JSONObject.toJSONString(chatRequest));
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/engines/text-davinci-003/completions")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer ".concat(yamlChat.getApiKey()))
                    .build();

            final Call call = client.newCall(request);
            call.timeout().timeout(60 * 4, TimeUnit.SECONDS);

            final String responseData = call.execute().body().string();
            final ChatGPTAnswerDTO parse = JSONObject.parseObject(responseData, ChatGPTAnswerDTO.class);
            if (parse.getChoices() != null && !parse.getChoices().isEmpty()) {
                return parse.getChoices().stream().map(ChatGPTAnswerDTO.ChoicesDTO::getText).collect(Collectors.toList());
            } else {
                //如果没有的话可能是异常情况，尝试转换为异常返回结果
                final ChatGPTError chatError = JSONObject.parseObject(responseData, ChatGPTError.class);
                return Collections.singletonList(chatError.getError().getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SocketTimeoutException) {
                return Collections.singletonList("超时，请重试！！！");
            } else {
                return Collections.singletonList(e.getMessage());
            }
        }
    }
}

