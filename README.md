

# Config-Driven Visa Rule Engine

A lightweight, configuration-driven Java application that evaluates visa requirements for travelers based on their passport, destination, and travel purpose. The system uses an external JSON configuration to determine rules, avoiding hardcoded business logic.

## 📂 Project Structure

```text
src/
├── main/
│   ├── java/com/visaRuleEngine/
│   │   ├── enums/            # Country, VisaType, TravelPurpose, DocumentType
│   │   ├── rules/            # RuleLoader, RuleEvaluator, Rule (DTO)
│   │   ├── VisaDecision.java # Output DTO
│   │   └── Main.java         # Entry Point
│   └── resources/
│       └── rules.json        # Configuration file containing visa rules
├── pom.xml                   # Maven dependencies and build config
└── README.md

```

## 🛠 Prerequisites

* **Java:** JDK 8 or higher
* **Build Tool:** Maven 3.x

## 📦 Dependencies

* **Gson (2.10.1):** Used for parsing the `rules.json` configuration file.

## 🚀 Setup & Execution

1. **Compile the Project:**
```bash
mvn clean compile

```


2. **Run the Application:**
Execute the main class directly using Maven:
```bash
mvn exec:java -Dexec.mainClass="com.visaRuleEngine.Main"

```



## ⚙️ Configuration (`rules.json`)

Visa rules are defined in `src/resources/rules.json`. To add a new rule, append a JSON object with the following structure:

```json
{
  "ruleId": 17,
  "passportCountry": "INDIA",
  "destinationCountry": "GERMANY",
  "VisaRequired": "true",
  "visaType": ["Schengen Tourist Visa"],
  "documentRequired": ["Passport", "TravelInsurance"],
  "estimatedProcessingTime": "15",
  "warnings": ["Check validity"]
}

```

## 🧪 Key Features

* **Config-Driven:** Rules are loaded dynamically at runtime; no code changes needed for new countries.
* **Defensive Coding:** Validates all inputs (Enums, null checks) before processing.
* **Fail-Fast:** Application halts immediately if configuration is missing or invalid.
* **Zero Frameworks:** Pure Java implementation without Spring or Databases.
