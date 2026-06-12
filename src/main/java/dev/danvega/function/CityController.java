package dev.danvega.function;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    private final ChatClient chatClient;

    public CityController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/cities")
    public String cities(
            @RequestParam(value = "message", defaultValue = "What is the weather in Yangon?") String message) {
        
    
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                .withFunction("currentWeatherFunction") 
                .build();

     
        return this.chatClient.prompt()
                .system("You are a helpful AI assistant answering questions about cities around the world.")
                .user(message)
                .options(openAiChatOptions) 
                .call()
                .content(); 
    }
}