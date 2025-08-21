package br.imd.ufrn;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestMcpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestMcpServerApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider regAnimalTools(AnimalTools mcpServer) {
		return MethodToolCallbackProvider.builder().toolObjects(mcpServer).build();
	}

}
