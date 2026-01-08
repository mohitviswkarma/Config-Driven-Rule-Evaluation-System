package com.visaRuleEngine.rules;
import java.util.ArrayList;
import java.util.List;

import com.visaRuleEngine.enums.Country;
import com.visaRuleEngine.enums.DocumentType;
import com.visaRuleEngine.enums.VisaType;

public class Rule {
    /* 
    public final int ruleId;
    public final Country passportCountry;
    public final Country destinationCountry;
    public final boolean visaRequired;
    public final List<VisaType> visaTypes;
    public final List<DocumentType> documentsRequired;
    public final int estimatedProcessingTime;
    public final List<String> warnings;

    */

    public  int ruleId;
    public  Country passportCountry;
    public  Country destinationCountry;
    public  boolean visaRequired;
    public  List<VisaType> visaTypes;
    public  List<DocumentType> documentsRequired;
    public  int estimatedProcessingTime;
    public  List<String> warnings;

    public Rule() {
        this.ruleId = 0;
        this.passportCountry = null; // or a default value
        this.destinationCountry = null; // or a default value
        this.visaRequired = false;
        this.visaTypes = new ArrayList<>();
        this.documentsRequired = new ArrayList<>();
        this.estimatedProcessingTime = 0;
        this.warnings = new ArrayList<>();
    }
    
    public Rule(int ruleId, Country passportCountry, Country destinationCountry,
               boolean visaRequired, List<VisaType> visaTypes, 
               List<DocumentType> documentsRequired, int estimatedProcessingTime,
               List<String> warnings) {
        this.ruleId = ruleId;
        this.passportCountry = passportCountry;
        this.destinationCountry = destinationCountry;
        this.visaRequired = visaRequired;
        this.visaTypes = visaTypes;
        this.documentsRequired = documentsRequired;
        this.estimatedProcessingTime = estimatedProcessingTime;
        this.warnings = warnings;
    }
    
    public int getRuleId() { return ruleId; }
    public Country getPassportCountry() { return passportCountry; }
    public Country getDestinationCountry() { return destinationCountry; }
    public boolean isVisaRequired() { return visaRequired; }
    public List<VisaType> getVisaTypes() { return new ArrayList<>(visaTypes); }
    public List<DocumentType> getDocumentsRequired() { return new ArrayList<>(documentsRequired); }
    public int getEstimatedProcessingTime() { return estimatedProcessingTime; }
    public List<String> getWarnings() { return new ArrayList<>(warnings); }
}

