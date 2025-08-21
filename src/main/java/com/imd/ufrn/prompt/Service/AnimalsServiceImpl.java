package com.imd.ufrn.prompt.Service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;

import com.imd.ufrn.prompt.Utils.DocumentReader;

import io.modelcontextprotocol.client.McpSyncClient;

import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imd.ufrn.prompt.DAO.AnimalsDAO;

@Service
public class AnimalsServiceImpl implements AnimalsService {

    private final AnimalsDAO animalsDao;
    private final DocumentReader documentReader;

    @Autowired
    VectorStore vectorStore;

    ChatMemoryRepository chatMemoryRepository = new InMemoryChatMemoryRepository();
    ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(10)
                .build();

    private final ChatClient chatClient;

    public AnimalsServiceImpl(AnimalsDAO animalsDao, DocumentReader documentReader,
        ChatClient.Builder chatClientBuilderOpenAI, List<McpSyncClient> mcpClients) {
        this.animalsDao = animalsDao;
        this.documentReader = documentReader;
        this.chatClient = chatClientBuilderOpenAI
            .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpClients))
            .build();
    }

    @Override
    public void save(List<String> animals) {
        animalsDao.add(animals);
    }

    @Override
    public List<String> findClosestMatches(String query) {
        return animalsDao.findClosestMatches(query, 5);
    }

    @Override
    public String findClosestMatch(String query) {
        return animalsDao.findClosestMatch(query);
    }

    @Override
    public void loadDocument() {
        List<Document> documents = documentReader.loadText();
        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> chunks = splitter.apply(documents);
        animalsDao.addDocs(chunks);
    }
    
}
