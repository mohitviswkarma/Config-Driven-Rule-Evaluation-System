package com.visaRuleEngine;

import com.visaRuleEngine.enums.Country;
import com.visaRuleEngine.enums.TravelPurpose;
import com.visaRuleEngine.rules.RuleEvaluator;
import com.visaRuleEngine.rules.RuleLoader;
import com.visaRuleEngine.VisaDecision;

import java.util.Scanner;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String rulesFilePath = "src/resources/rules.json";

        try {
            // 1. Initialize System
            System.out.println("--- Initializing Visa Rule Engine ---");
            RuleLoader ruleLoader = new RuleLoader(rulesFilePath);
            RuleEvaluator evaluator = new RuleEvaluator(ruleLoader);
            System.out.println("System Ready.\n");

            // 2. Capture User Input
            System.out.println("--- Enter Travel Details ---");
            
            Country passportCountry = getEnumInput(scanner, Country.class, "Passport Country");
            Country destinationCountry = getEnumInput(scanner, Country.class, "Destination Country");
            TravelPurpose purpose = getEnumInput(scanner, TravelPurpose.class, "Travel Purpose");
            
            System.out.print("Enter Stay Duration (days): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid number. Please enter digits only.");
                System.out.print("Enter Stay Duration (days): ");
                scanner.next();
            }
            int duration = scanner.nextInt();

            // 3. Evaluate
            System.out.println("\n--- Processing Rules ---");
            VisaDecision decision = evaluator.evaluate(passportCountry, destinationCountry, purpose, duration);

            // 4. Output Result
            printDecision(decision);

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: " + e.getMessage());
            e.printStackTrace(); // Useful for debugging file path issues
        } finally {
            scanner.close();
        }
    }

    // Helper to validate Enum inputs safely
    private static <T extends Enum<T>> T getEnumInput(Scanner scanner, Class<T> enumClass, String fieldName) {
        while (true) {
            System.out.printf("Enter %s %s: ", fieldName, Arrays.toString(enumClass.getEnumConstants()));
            String input = scanner.next().toUpperCase().trim();
            try {
                return Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please choose from the list above.");
            }
        }
    }

    private static void printDecision(VisaDecision decision) {
        System.out.println("\n==================================");
        System.out.println("       VISA DECISION RESULT       ");
        System.out.println("==================================");
        System.out.println("Visa Required : " + (decision.isVisaRequired() ? "YES" : "NO"));
        
        if (decision.isVisaRequired()) {
            System.out.println("Visa Type     : " + decision.getVisaType());
            System.out.println("Documents     : " + decision.getDocumentTypes());
            System.out.println("Processing    : " + decision.getEstimatedProcessingDays() + " days");
        }

        if (!decision.getWarnings().isEmpty()) {
            System.out.println("\n[!] Warnings:");
            for (String w : decision.getWarnings()) {
                System.out.println(" - " + w);
            }
        }
        System.out.println("==================================");
    }
}