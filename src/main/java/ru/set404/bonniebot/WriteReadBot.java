package ru.set404.bonniebot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import ru.set404.bonniebot.handlers.MessageHandler;

import java.io.IOException;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WriteReadBot extends SpringWebhookBot {
    String botPath;
    String botUsername;
    String botToken;

    MessageHandler messageHandler;

    public WriteReadBot(SetWebhook setWebhook, MessageHandler messageHandler) {
        super(setWebhook);
        this.messageHandler = messageHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (IllegalArgumentException e) {
            return new SendMessage(update.getMessage().getChatId().toString(), "У меня проблема(");
        } catch (Exception e) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Большая проблема((");
        }
    }

    private BotApiMethod<?> handleUpdate(Update update) throws IOException {
        Message message = update.getMessage();
        if (message != null) {
            return messageHandler.answerMessage(update.getMessage());
        }
        return null;
    }
}
