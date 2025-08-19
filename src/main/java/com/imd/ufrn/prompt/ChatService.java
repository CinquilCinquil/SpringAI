package com.imd.ufrn.prompt;

import java.util.List;

public interface ChatService {
    String getAnswer(String question);
    List<Animal> getAnimals(String question);
}