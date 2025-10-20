package com.imd.ufrn.prompt.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imd.ufrn.prompt.Service.AnimalsServiceImpl;

@RestController
public class EmbeddingController {
    private final AnimalsServiceImpl embeddingService;

    public EmbeddingController(AnimalsServiceImpl embeddingService) {
        this.embeddingService = embeddingService;
    }

    @GetMapping("embedding")
    public String getAnswer(@RequestParam String query) {
        return embeddingService.findClosestMatch(query);
    }

    /*
     * Test method for saving strings in embeddingService
     */
    @GetMapping("add-animals")
    public void saveAnimals() {
        List<String> animals = List.of(
            "Bee",
            "Dog",
            "Cat",
            "Horse"
        );
        embeddingService.save(animals);
    }

    @GetMapping("animal-rules")
    public void addAnimalRules() {
        embeddingService.loadDocument("data/ANIMAL_BIOLOGY.pdf"); // Downloaded from archive.org
    }
    
}
