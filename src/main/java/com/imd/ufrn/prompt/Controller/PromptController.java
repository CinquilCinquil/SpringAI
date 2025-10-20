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

    @GetMapping("/")
    public String openPage() {
        return chatService.getAnswer("Who are you? Present yourself, shortly.");
    }

    @GetMapping("prompt")
    public String getAnswer(@RequestParam String question) {
        return chatService.getAnswer(question);
    }

    /*
     * Model returns answer as a list of animals
     */
    @GetMapping("prompt-object")
    public String getStructuredAnswer(@RequestParam String question) {
        return String.valueOf(chatService.getAnimals(question));
    }
}
