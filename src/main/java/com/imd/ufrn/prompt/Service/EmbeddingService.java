package com.imd.ufrn.prompt.Service;

import java.util.List;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    private List<String> animals = List.of(
            "Bee",
            "Dog",
            "Cat",
            "Horse"
    );

    private final EmbeddingModel model;

    public String findAnimal(String query) {

        List<float[]> animalEmbeddings = null;
        float[] queryEmbedding = null;

        System.out.println(model);

        animalEmbeddings = model.embed(animals);
        queryEmbedding = model.embed(query);

        int mostSimilarIndex = -1;
        mostSimilarIndex = findClosestMatch(queryEmbedding, animalEmbeddings);

        if (mostSimilarIndex < 0) {
            return "No animal found with query: " + query;
        }
        else {
            return animals.get(mostSimilarIndex);
        }
    }

    public int findClosestMatch(float[] query, List<float[]> animals) {
        int ind = -1;
        double best_match = 0;

        for (int i = 0;i < animals.size();i ++) {
            double similarity = cossineSimilarity(query, animals.get(i));
            if (similarity >= best_match) {
                best_match = similarity;
                ind = i;
            }
        }

        return ind;
    }

    public double cossineSimilarity(float[] vectorA, float[] vectorB) {
        double dotProduct = 0, normA = 0, normB = 0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }

        double eucledianNormProduct = Math.sqrt(normA) * Math.sqrt(normB);

        return dotProduct / eucledianNormProduct;
    }
    
    public EmbeddingService(EmbeddingModel model) {
        this.model = model;
    }   

}
