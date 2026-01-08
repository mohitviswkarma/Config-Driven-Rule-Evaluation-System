package com.visaRuleEngine.rules;

//RuleLoader (reads config)

    //laod json file
    //create object of Rule

    //read from json file and store the objects into map 

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import com.visaRuleEngine.enums.Country;
import com.visaRuleEngine.enums.DocumentType;
import com.visaRuleEngine.enums.TravelPurpose;
import com.visaRuleEngine.enums.VisaType;
import com.visaRuleEngine.rules.Rule;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class RuleLoader {
    
    private static RuleLoader instance;
    private final Map<String, Rule> rulesCache;
    
   
    private RuleLoader(String jsonFilePath) throws IOException {
        this.rulesCache = new HashMap<>();
        loadAndIndexRules(jsonFilePath);
    }
    
    
  //load json into memory, only once during init 
private void loadAndIndexRules(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            
           
            for (JsonElement element : jsonArray) {
                JsonObject json = element.getAsJsonObject();
                
           
                String passportCountry = json.get("passportCountry").getAsString();
                String destinationCountry = json.get("destinationCountry").getAsString();
                
               
                Rule ruleData = parseRuleData(json);
                
                String key = createCacheKey(passportCountry, destinationCountry);
                rulesCache.put(key, ruleData);
            }
            
            System.out.println("Loaded " + rulesCache.size() + " rules into cache.");
        }
    }
    
    //Parse JSON object into lightweight RuleData structure
private Rule parseRuleData(JsonObject json) {
    Rule data = new Rule();
    data.ruleId = json.get("ruleId").getAsInt();
    data.passportCountry = Country.valueOf(json.get("passportCountry").getAsString().toUpperCase());
    data.destinationCountry = Country.valueOf(json.get("destinationCountry").getAsString().toUpperCase());
    data.visaRequired = json.get("VisaRequired").getAsString().equalsIgnoreCase("true");
    data.estimatedProcessingTime = Integer.parseInt(json.get("estimatedProcessingTime").getAsString());

    // Parse visa types
    data.visaTypes = new ArrayList<>();
    JsonArray visaTypeArray = json.getAsJsonArray("visaType");
    for (JsonElement ve : visaTypeArray) {
        data.visaTypes.add(VisaType.valueOf(ve.getAsString().toUpperCase())); // Convert each element to VisaType enum
    }

    // Parse document types
    data.documentsRequired = new ArrayList<>();
    JsonArray docArray = json.getAsJsonArray("documentRequired");
    for (JsonElement de : docArray) {
        data.documentsRequired.add(DocumentType.valueOf(de.getAsString().toUpperCase())); // Convert each element to DocumentType enum
    }

    return data;
}
    
    
     // Create cache key for HashMap lookup
private String createCacheKey(String passportCountry, String destinationCountry) {
        return passportCountry.toUpperCase() + "_" + destinationCountry.toUpperCase();
    }
    
public Rule getRule(Country destinationCountry, Country passportCountry, TravelPurpose travelPurpose) {
        String key = createCacheKey(passportCountry.name(), destinationCountry.name());
        Rule data = rulesCache.get(key);
        if (data == null) {
            // No rule found for this combination
            return null;
        }
        return buildRule(data, destinationCountry, passportCountry, travelPurpose);
    }
    
    private Rule buildRule(Rule data, Country destinationCountry, 
                          Country passportCountry, TravelPurpose travelPurpose) {
        
        // Convert string visa types to enum
        List<VisaType> visaTypes = new ArrayList<>();
        for (VisaType vt : data.visaTypes) {
            try {
                String enumName = vt.toString().toUpperCase().replace(" ", "_");
                visaTypes.add(VisaType.valueOf(enumName));
            } catch (IllegalArgumentException e) {
                System.err.println("Warning: Unknown visa type '" + vt + "'");
            }
        }
        
        // Convert string document types to enum
        List<DocumentType> documentTypes = new ArrayList<>();
        for (DocumentType dt : data.documentsRequired) {
            try {
                String enumName = dt.toString().toUpperCase().replace("STATEMENT", "_STATEMENT");
                documentTypes.add(DocumentType.valueOf(enumName));
            } catch (IllegalArgumentException e) {
                System.err.println("Warning: Unknown document type '" + dt + "'");
            }
        }
       
        return new Rule(
            data.ruleId, //int
            passportCountry,
            destinationCountry,
            data.visaRequired,
            visaTypes,
            documentTypes,
            data.estimatedProcessingTime,
            data.warnings
        );
    }
}

