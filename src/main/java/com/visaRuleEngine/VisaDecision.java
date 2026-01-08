package com.visaRuleEngine;

    /*
    •	boolean visaRequired
	•	VisaType (enum)
	•	List<DocumentType>
	•	int estimatedProcessingDays
	•	List<String> warnings
    
    */
    
    import java.util.List;

import com.visaRuleEngine.enums.DocumentType;
import com.visaRuleEngine.enums.VisaType;
    public final class VisaDecision {
            private final boolean visaRequired;
            private final VisaType visaType;
            private final List<DocumentType> documentTypes;
            private final int estimatedProcessingDays;
            private final List<String> warnings;

            public VisaDecision(boolean visaRequired, VisaType visaType, List<DocumentType> documentTypes,
                    int estimatedProcessingDays, List<String> warnings) {
                this.visaRequired = visaRequired;
                this.visaType = visaType;
                this.documentTypes = documentTypes;
                this.estimatedProcessingDays = estimatedProcessingDays;
                this.warnings = warnings;
            }
            public boolean isVisaRequired() {
                return visaRequired;
            }
            public VisaType getVisaType() {
                return visaType;
            }
            public List<DocumentType> getDocumentTypes() {
                return documentTypes;
            }
            public int getEstimatedProcessingDays() {
                return estimatedProcessingDays;
            }
            public List<String> getWarnings() {
                return warnings;
            }
            


    
    }