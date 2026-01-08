package com.visaRuleEngine.rules;

    //take sannitized input from user
    //build ruleloader object based on input
    //create visadicison object
    //ensure exception handling and logging
     //evaluate rules and return decision object

import com.visaRuleEngine.VisaDecision;
import com.visaRuleEngine.enums.Country;
import com.visaRuleEngine.enums.TravelPurpose;
import com.visaRuleEngine.enums.VisaType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RuleEvaluator {

    private final RuleLoader ruleLoader;

   
    public RuleEvaluator(RuleLoader ruleLoader) throws Exception {
        if (ruleLoader == null) {
            throw new Exception("RuleLoader cannot be null");
        }
        this.ruleLoader = ruleLoader;
    }

    public VisaDecision evaluate(Country passportCountry, Country destinationCountry, 
                                 TravelPurpose travelPurpose, int stayDuration) {
        
        validateInputs(passportCountry, destinationCountry, travelPurpose, stayDuration);

        Rule rule = ruleLoader.getRule(destinationCountry, passportCountry, travelPurpose);

        if (rule == null) {
            return createFallbackDecision("No visa rule found for " + destinationCountry);
        }

        VisaType applicableVisa = matchVisaTypeToPurpose(rule.getVisaTypes(), travelPurpose);
        List<String> warnings = new ArrayList<>(rule.getWarnings());

        if (rule.isVisaRequired() && applicableVisa == null) {
            warnings.add("Visa required but no matching visa type found for purpose: " + travelPurpose);
        }

        return new VisaDecision(
                rule.isVisaRequired(),
                applicableVisa,
                rule.getDocumentsRequired(),
                rule.getEstimatedProcessingTime(),
                Collections.unmodifiableList(warnings)
        );
    }

private void validateInputs(Country passport, Country dest,
            TravelPurpose purpose, int duration) {

        if (passport == null) {
            throw new IllegalArgumentException("Passport Country cannot be null");
        }
        if (dest == null) {
            throw new IllegalArgumentException("Destination Country cannot be null");
        }
        if (purpose == null) {
            throw new IllegalArgumentException("Travel Purpose cannot be null");
        }

        if (passport == dest) {
            throw new IllegalArgumentException(
                    "Passport and Destination countries cannot be the same");
        }

        if (duration < 0) {
            throw new IllegalArgumentException("Stay duration cannot be negative");
        }
    }


    private VisaType matchVisaTypeToPurpose(List<VisaType> availableTypes, TravelPurpose purpose) {
        if (availableTypes == null || availableTypes.isEmpty()) {
            return null;
        }

        // Simple heuristic matching logic
        for (VisaType type : availableTypes) {
            if (isTypeMatchingPurpose(type, purpose)) {
                return type;
            }
        }
        return null;
       // return availableTypes.get(0);
    }

    private boolean isTypeMatchingPurpose(VisaType type, TravelPurpose purpose) {
        switch (purpose) {
            case TOURISM: return type == VisaType.TOURIST_VISA || type == VisaType.E_VISA || type == VisaType.VISA_ON_ARRIVAL;
            case BUSINESS: return type == VisaType.BUSINESS_VISA;
            case EDUCATION: return type == VisaType.STUDENT_VISA;
            default: return false;
        }
    }

    private VisaDecision createFallbackDecision(String warning) {
        return new VisaDecision(
                true, // Fail-safe: assume visa required if unknown
                null,
                Collections.emptyList(),
                0,
                Collections.singletonList(warning)
        );
    }
}