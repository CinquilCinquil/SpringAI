package com.imd.ufrn.prompt.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imd.ufrn.prompt.Service.ChatService;

@RestController
public class PromptController {
    private final ChatService chatService;

    public PromptController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("prompt")
    public String getAnswer(@RequestParam String question) {
        return chatService.getAnswer(question);
    }

    @GetMapping("prompt-object")
    public String getStructuredAnswer(@RequestParam String question) {
        return String.valueOf(chatService.getAnimals(question));
    }
}
