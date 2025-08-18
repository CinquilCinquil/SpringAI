package com.imd.ufrn.prompt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromptController {
    private final ChatService chatService;

    public PromptController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("prompt")
    public String getMethodName(@RequestParam String question) {
        return chatService.getAnswer(question);
    }
}
