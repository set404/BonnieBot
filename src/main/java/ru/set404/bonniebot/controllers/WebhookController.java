package ru.set404.bonniebot.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.set404.bonniebot.WriteReadBot;

@RestController
@AllArgsConstructor
public class WebhookController {
    private final WriteReadBot writeReadBot;

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return writeReadBot.onWebhookUpdateReceived(update);
    }
}

