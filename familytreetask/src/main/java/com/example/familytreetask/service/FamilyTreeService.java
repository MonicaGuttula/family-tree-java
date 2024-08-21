package com.example.familytreetask.service;

import com.example.familytreetask.model.FamilyTree;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FamilyTreeService {

    private static final String OPENAI_API_KEY = "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";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    
    @Autowired
    private ObjectMapper objectMapper; 
    
    public FamilyTree generateFamilyTree(String input) {
        RestTemplate restTemplate = new RestTemplate();
        
     // Create request payload
        ArrayNode messagesArray = objectMapper.createArrayNode();
        ObjectNode userMessage = objectMapper.createObjectNode();
        userMessage.put("role", "user");
        //userMessage.put("content", input);
        //userMessage.put("content", "Please provide a structured family tree based on the following description. Format it as a JSON object with a 'name' field and a 'children' array for each person.\\n\\nDescription: " + inputText);
        
        userMessage.put("content", "Please provide a structured family tree based on the following description. Format it as a JSON object with a 'name' field and a 'children' array for each person.\\n\\nDescription: " + input);
         //userMessage.put("content", "Create a family tree based on the description. Treat pairs mentioned together as married couples. Format as JSON with 'name' and 'children'\\n\\nDescription: "+input);
        
        messagesArray.add(userMessage);
        
        ObjectNode requestPayload = objectMapper.createObjectNode();
        requestPayload.put("model", "gpt-3.5-turbo");
        requestPayload.set("messages", messagesArray);
        
        System.out.println("Request Payload: " + requestPayload.toString());
      
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY); 
        
        HttpEntity<String> entity = new HttpEntity<>(requestPayload.toString(), headers);

        // Make request to OpenAI API
        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, entity, String.class);

        // Process response
        JsonNode responseBody = null;
		try {
			responseBody = objectMapper.readTree(response.getBody());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String content = responseBody.path("choices").get(0).path("message").path("content").asText();
        
        FamilyTree familyTree = new FamilyTree();
        familyTree.setDescription(content);
        
        //String responseBody = response.getBody();
        
        // Convert response to FamilyTree model
       // FamilyTree familyTree = new FamilyTree();
        // Populate familyTree object based on the response
        // This is a simplified example. You may need to parse and map the response properly.
        //familyTree.setTree(responseBody);
        return familyTree;
    }
}
