package co.com.Garces.microservice.resolveEnigmaApi.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.Garces.microservice.resolveEnigmaApi.model.GetEnigmaRequest;
import co.com.Garces.microservice.resolveEnigmaApi.model.GetEnigmaStepResponse;
import co.com.Garces.microservice.resolveEnigmaApi.model.Header;
import co.com.Garces.microservice.resolveEnigmaApi.model.JsonApiBodyRequest;
import co.com.Garces.microservice.resolveEnigmaApi.model.JsonApiBodyResponseSuccess;
import io.swagger.annotations.ApiParam;

import org.reflections.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GetStepApiController implements GetStepApi {

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    public GetStepApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<JsonApiBodyResponseSuccess>> getStep(@ApiParam(value = "request body get enigma step", required = true) @Valid @RequestBody JsonApiBodyRequest body) {
        List<GetEnigmaRequest> enigmas = body.getData();
        List<JsonApiBodyResponseSuccess> responseList = new ArrayList<>();

        for (GetEnigmaRequest enigma : enigmas) {
        	
            Header header = enigma.getHeader();
            String id = header.getId();
            String type = header.getType();
            String enigmaQuestion = enigma.getEnigma();

            String solution = "BEBA";

            GetEnigmaStepResponse enigmaStepResponse = new GetEnigmaStepResponse();
            enigmaStepResponse.setId(id);
            enigmaStepResponse.setType(type);
            enigmaStepResponse.setSolution(solution);

            JsonApiBodyResponseSuccess responseBody = new JsonApiBodyResponseSuccess();
            responseBody.addDataItem(enigmaStepResponse);
            responseList.add(responseBody);
        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    	
    @GetMapping("/get-enigma")
    public ResponseEntity<String> solveEnigma() {
    	
        RestTemplate restTemplate1=new RestTemplate(); String fooResourceUrl1="http://localhost:8080/v1/getOneEnigma/get-enigma";
      	ResponseEntity<String> response = 
      	restTemplate1.getForEntity(fooResourceUrl1, String.class);
      	
      	RestTemplate restTemplate2=new RestTemplate(); String fooResourceUrl2="http://localhost:8181/v1/getOneEnigma/get-enigma"; 
      	ResponseEntity<String> response2 = 
      	restTemplate2.getForEntity(fooResourceUrl2, String.class);
      	
      	RestTemplate restTemplate3=new RestTemplate(); String fooResourceUrl3="http://localhost:8383/v1/getOneEnigma/get-enigma"; 
      	ResponseEntity<String> response3 = 		
      	restTemplate3.getForEntity(fooResourceUrl3, String.class);
          
          String respuesta1 = restTemplate1.getForEntity(fooResourceUrl1, String.class).getBody();
          String respuesta2 = restTemplate2.getForEntity(fooResourceUrl2, String.class).getBody();
          String respuesta3 = restTemplate3.getForEntity(fooResourceUrl3, String.class).getBody();

          return new ResponseEntity<String>( respuesta1.concat(respuesta2).concat(respuesta3), HttpStatus.OK);
        }
	
}

    
