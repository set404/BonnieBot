package ru.set404.bonniebot.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatBotService {
    final ObjectMapper objectMapper = new ObjectMapper();
    String uid = "";

    public Optional<String> receive(String text) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("responseJson", uid);
        map.put("bot", "main");
        map.put("text", text);
        Connection.Response response = Jsoup
                .connect("https://xu.su/api/send")
                .method(Connection.Method.POST)
                .data(map)
                .ignoreContentType(true)
                .execute();
        JsonNode responseJson = objectMapper.readTree(response.body());
        if (!responseJson.get("uid").isNull()) {
            uid = responseJson.get("uid").asText();
        }
        if (responseJson.get("ok").asBoolean()) {
            return Optional.ofNullable(responseJson.get("text").asText());
        }
        return Optional.empty();
    }
}
