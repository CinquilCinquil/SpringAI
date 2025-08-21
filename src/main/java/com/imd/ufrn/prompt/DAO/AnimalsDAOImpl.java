package com.imd.ufrn.prompt.DAO;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnimalsDAOImpl implements AnimalsDAO {

    @Autowired VectorStore vectorStore;
    public void add(List<String> animals) {
        List<Document> documents = animals.stream()
            .map(Document::new)
            .toList();
        vectorStore.add(documents);
    }

    public List<String> findClosestMatches(String query, int numberOfMatches) {
        SearchRequest request = SearchRequest.builder()
            .query(query)
            .topK(numberOfMatches)
            .build();
        List<Document> results = vectorStore.similaritySearch(request);
        if (results == null) {
            return List.of();
        }
        return results.stream()
            .map((Document doc) -> doc.getText())
            .toList();
    }

    public String findClosestMatch(String query) {
        List<String> matches = findClosestMatches(query, 1)
        if (matches.size() > 0) {
            return matches.get(0);
        }
        else {
            return "EMPTY RESULT";
        }
    }
    
}
