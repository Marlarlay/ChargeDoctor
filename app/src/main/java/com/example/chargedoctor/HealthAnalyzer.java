package com.example.chargedoctor;

public class HealthAnalyzer {

    public static int calculateHealth(
            int currentMa,
            float temp
    ){

        int score = 100;

        if(currentMa < 500){

            score -= 50;

        }else if(currentMa < 1000){

            score -= 30;

        }else if(currentMa < 1500){

            score -= 15;
        }

        if(temp > 45){

            score -= 30;

        }else if(temp > 40){

            score -= 15;
        }

        if(score < 0){

            score = 0;
        }

        return score;
    }

    public static String getStatus(
            int score
    ){

        if(score >= 90){

            return "Aランク";

        }else if(score >= 70){

            return "Bランク";

        }else if(score >= 50){

            return "Cランク";
        }

        return "Dランク";
    }
}