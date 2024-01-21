package com.mohit.springfirstproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class WelcomeController {
	
	@RequestMapping("/api/welcome")
	public String WelcomeMessage() {
		String greeting =  "Welcome to the world of Spring Boot.\n";
		String jsondata = "{'name' : 'james', 'age' : '32'}";
		
		ApacheHttpClientPostJsonExample calljson = new ApacheHttpClientPostJsonExample();
		calljson.mainjson();
		
		return greeting + jsondata;
	}
	
	@RequestMapping("/api/jsontest")
	public String PassJsonData() {
		String greeting =  "Welcome to Json Test.\n";
		String jsondata = "{'name' : 'john', 'age' : '30'}";
		
		return greeting + jsondata;
	}

	@PostMapping(value="/api/requeststatus",
			consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity postControllerStatus(
	  @RequestBody LoginForm loginForm) {
		
		System.out.println("Context message at /request");
	    //exampleService.fakeAuthenticate(loginForm);
	    return ResponseEntity.ok(HttpStatus.OK);
	}

	@PostMapping(value="/api/request",
			consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public String postController(
	  @RequestBody LoginForm loginForm) throws SQLException {

		System.out.println("username="+loginForm.getUsername());
		System.out.println("password="+loginForm.getPassword());
		String a="a";
		String b="b";
		
		try{
			if (a.equalsIgnoreCase(b)) {
				throw new SQLException("Failed to connect.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("Context message at /request");
	    //exampleService.fakeAuthenticate(loginForm);
	    return loginForm.getUsername() + " " + loginForm.getPassword();
	}

		
	/***********************************************************
	 * 
	 * @param users
	 * @return
	 * 
	 * Sample json data
	 
		{
		  "users":[
		    {"username":"johny", "password":"password1"},    
		    {"username":"james", "password":"password2"},  
		    {"username":"juddy", "password":"password3"}
		 ]
		}
	 
	 ************************************************************/
	@PostMapping(value="/api/request_multiple",
			consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public String postController(
	  @RequestBody Users users) {

		//loop through users to display all usernames
		for (LoginForm loginForm : users.getLoginForm()) {
			System.out.println("username="+loginForm.getUsername());
		}
		
		System.out.println("Context message at /request_multiple");
	    //exampleService.fakeAuthenticate(loginForm);
	    return "OK";
	}
	
	/***********************************************************
	 * @param json
	 * @return
	 * @throws JsonProcessingException
	 * 
	 * Sample json data
	 
		{
		  "users":[
		    {"username":"johny", "password":"password1"},    
		    {"username":"james", "password":"password2"},  
		    {"username":"juddy", "password":"password3"}
		 ]
		}
	 
	 ***********************************************************/
	@PostMapping("/api/request_nested_json")
	public String postNestedJson(@RequestBody String json) throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode rootNode = objectMapper.readTree(json);
	    
	    // access nested properties
	    //String username = rootNode.get("users").get("username").asText();
	    //String password = rootNode.get("users").get("password").asText();

	    // loop through users to display all usernames and passwords
	    for (JsonNode userNode : rootNode.path("users")) {
	        String username = userNode.get("username").asText();
	        String password = userNode.get("password").asText();
	        System.out.println("Username: " + username + ", Password: " + password);
	    }
	    
	    // do something with the data
	    //System.out.println("username: " + username);
	    //System.out.println("password: " + password);
	    
	    return "OK";
	}
	
	
	
	/***********************************************************
	 * @param json
	 * @return
	 * @throws JsonProcessingException
	 * 
	 * Sample json data	
	  
		{
		  "title": "Example document",
		  "message": "Lorem ipsum solar sit amet.",
		  "file": "encoded",
		  "recipients": [{
		    "first_name": "John",
		    "last_name": "Doe",
		    "email": "example@pleasesign.com.au",
		    "tabs": [{
		      "kind": "signature",
		      "page": 1,
		      "x": 100,
		      "y": 800
		    }]
		  }]
		}
		
	 ***********************************************************/
	@PostMapping("/api/requestall_nested_json")
	public String postAllNestedJson(@RequestBody String json) throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode rootNode = objectMapper.readTree(json);
	    
	    // display all key-value pairs
	    displayAllKeyValues(rootNode, "");
	    
	    return "OK";
	}

	private void displayAllKeyValues(JsonNode node, String currentPath) {
	    if (node.isObject()) {
	        ObjectNode objectNode = (ObjectNode) node;
	        objectNode.fields().forEachRemaining(entry -> {
	            displayAllKeyValues(entry.getValue(), currentPath + "/" + entry.getKey());
	        });
	    } else if (node.isArray()) {
	        ArrayNode arrayNode = (ArrayNode) node;
	        for (int i = 0; i < arrayNode.size(); i++) {
	            displayAllKeyValues(arrayNode.get(i), currentPath + "[" + i + "]");
	        }
	    } else {
	        System.out.println(currentPath + " = " + node.asText());
	    }
	}

	@PostMapping("/api/dbmanipulation")
	public String dbManipulation() {
		

	    Connection con = null;
	    PreparedStatement p = null;
	    ResultSet rs = null;
	 
	    con = Connect.connectDB();

	    try
	    {
	         
	        // Here, the SQL command is used to store String datatype
	        String sql = "select  *  from lms_parameters where param_name like '%ALLOW%' and ROWNUM <= 3";
	        p = con.prepareStatement(sql);
	        rs = p.executeQuery();
	 
	        // Here, print the param_name, param_value1, param_value2 of the customers
	        System.out.println("param_name\t\tparam_value\t\tparam_value2");
	 
	        // Check condition
	        while (rs.next())
	        {
	            String param_name = rs.getString("PARAM_NAME");
	            String param_value = rs.getString("PARAM_VALUE");
	            String param_value2 = rs.getString("PARAM_VALUE2");
	            System.out.println(param_name + "\t\t" + param_value +
	                                    "\t\t" + param_value2);
	        }
			rs.close();
			rs.close();
			con.close();
	    }
	 
	    // Catch block is used for exception
	    catch (SQLException e)
	    {
	        // Print exception pop-up on the screen
	        System.out.println(e);
	    }finally{
			rs = null;
			rs = null;
			con= null;
		}
	    return "";
	}

}
