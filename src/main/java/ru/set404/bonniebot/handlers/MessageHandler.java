package ru.set404.bonniebot.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.set404.bonniebot.services.ChatBotService;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {
    ChatBotService chatBotService;

    public BotApiMethod<?> answerMessage(Message message) throws IOException {
        String chatId = message.getChatId().toString();

        String inputText = message.getText();
        if (message.getLeftChatMember() != null) {
            return getOutMessage(chatId, message.getLeftChatMember());
        } else if (!message.isReply() && message.isGroupMessage() && !inputText.toLowerCase().contains("привет бонни")) {
            if ((inputText.toLowerCase().contains("бонни")) || (new Random().nextInt(10) == 4))
                return getMessage(chatId, inputText);
        } else if (inputText == null) {
            throw new IllegalArgumentException();
        } else if (inputText.equals("/start")) {
            return getStartMessage(chatId);
        } else {
            return getMessage(chatId, inputText);
        }
        return null;
    }

    private SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Салем, казахи:)");
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private SendMessage getOutMessage(String chatId, User user) {
        SendMessage sendMessage = new SendMessage(chatId, user.getFirstName() + " ушел...");
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private SendMessage getMessage(String chatId, String message) throws IOException {
        String answer = "";
        if (message.toLowerCase().contains("привет бонни"))
            answer = "Привет всем в чатике ^^";
        else
            answer = bonnieAnswer(message);
        SendMessage sendMessage = new SendMessage(chatId, answer);
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private String bonnieAnswer(String message) throws IOException {
        Optional<String> response = chatBotService.receive(message);
        String answer = "";
        if (response.isPresent())
            answer = response.get();
        if (answer.contains("Xu Su"))
            answer = answer.replace("Xu Su", "Бонни");
        return answer;
    }
}
