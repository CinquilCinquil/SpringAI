package com.imd.ufrn.prompt.Service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.imd.ufrn.prompt.DataTypes.Animal;

import io.modelcontextprotocol.client.McpSyncClient;


@Service
@Primary
public class OpenAIChatService implements ChatService {
    private final ChatClient chatClient;

    @Value("classpath:prompt/promptUser.st")
    Resource templateUser;

    @Value("classpath:prompt/promptSystem.st")
    Resource templateSystem;

    @Autowired VectorStore vectorStore;
    private RelevancyEvaluator relevancyEvaluator;
    private FactCheckingEvaluator factCheckingEvaluator;

    ChatMemoryRepository chatMemoryRepository = new InMemoryChatMemoryRepository();
    ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(10)
                .build();

    public OpenAIChatService(ChatClient.Builder chatClientBuilder, List<McpSyncClient> mcpClients) {
        this.chatClient = chatClientBuilder
            .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpClients))
            .build();
        this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
        this.factCheckingEvaluator = new FactCheckingEvaluator(chatClientBuilder);
    }

    @Override
    public String getAnswer(String question) {
        String user = "default";
        String conversationId = "conversation-" + user;

        String answer = chatClient.prompt(question)
                        .advisors(
                            PromptChatMemoryAdvisor.builder(chatMemory).build(),
                            QuestionAnswerAdvisor.builder((vectorStore)).build()
                        )
                        .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                        .system(systemSpec -> systemSpec
                            .text(templateSystem))
                        .user(question)
                        .call()
                        .content();

        EvaluationRequest evaluationRequest = new EvaluationRequest(question, answer);
        EvaluationResponse relevancy = relevancyEvaluator.evaluate(evaluationRequest);
        EvaluationResponse factcheck = factCheckingEvaluator.evaluate(evaluationRequest);
        
        System.out.println(
            "Relenvacy Score: " + relevancy.getScore() + "\n"
            + "Relenvancy Approved: " + relevancy.isPass() + "\n"
            + "FactCheck Score: " + factcheck.getScore() + "\n"
            + "FactCheck Approved: " + factcheck.isPass() + "\n"
        );

        return answer;
    }

    public List<Animal> getAnimals(String question) {
        return chatClient.prompt()
            .user(question)
            .call()
            .entity(new ParameterizedTypeReference<List<Animal>>() {});
    }
}