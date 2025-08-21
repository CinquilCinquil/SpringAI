package com.imd.ufrn.prompt.Tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class AnimalTools {

    @Tool(description = "Returns true if the animal is a fish, false otherwise." +
     "Must be used when asked to verify if a certain species if a fish species.")
    String isFish(@ToolParam(description = "Animal species") String species) {
        System.out.print("Verifying if " + species + " is a fish");
        return String.valueOf(!species.equals("Not Fishius"));
    }

    @Tool(name= "Animal Register",
     description = "Registers an animal. Must be used when asked to register an animal.")
    String registerAnimal(@ToolParam(description="Animal name to be registered") String name) {
        System.out.print("Hurray " + name + "!!!!!");
        return "Animal " + name + "has been registered!";
    }
}
