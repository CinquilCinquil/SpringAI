package com.imd.ufrn.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public class OpenAIChatService implements ChatService {
    private final ChatClient chatClient;

    @Value("classpath:prompt/promptUser.st")
    Resource templateUser;

    @Value("classpath:prompt/promptSystem.st")
    Resource templateSystem;

    private RelevancyEvaluator relevancyEvaluator;
    private FactCheckingEvaluator factCheckingEvaluator;

    public OpenAIChatService(ChatClient.Builder chatClientBuilder) {
        ChatOptions chatOptions = ChatOptions.builder().model("gpt-4o-mini").build();
        this.chatClient = chatClientBuilder.defaultOptions(chatOptions).build();
        this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
        this.factCheckingEvaluator = new FactCheckingEvaluator(chatClientBuilder);
    }

    @Override
    public String getAnswer(String question) {
        String answer = chatClient.prompt()
                                .system(systemSpec -> systemSpec
                                    .text(templateSystem)
                                    .param("FIELD", "Animals"))
                                .user(userSpec -> userSpec
                                    .text(templateUser)
                                    .param("QUESTION", question))
                                .call()
                                .content();

        EvaluationRequest evaluationRequest = new EvaluationRequest(question, answer);
        EvaluationResponse response = relevancyEvaluator.evaluate(evaluationRequest);
        //EvaluationResponse response = factCheckingEvaluator.evaluate(evaluationRequest);
        return answer + "\n"
                        + "Score: " + response.getScore() + "\n"
                        + "Approved: " + response.isPass() + "\n";
    }
}