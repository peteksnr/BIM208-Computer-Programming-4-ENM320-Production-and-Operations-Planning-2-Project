package org.example;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Double> zValues = new ArrayList<>();
    private static List<Double> fValues = new ArrayList<>();
    private static List<Double> lValues = new ArrayList<>();

    public static void main(String[] args) {

        try {
            readCSV("src/main/java/org/example/zchart.csv");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter unit cost:");
        double unitCost = scanner.nextDouble();

        System.out.println("Enter ordering cost:");
        double orderingCost = scanner.nextDouble();

        System.out.println("Enter penalty cost:");
        double penaltyCost = scanner.nextDouble();

        System.out.println("Enter interest rate:");
        double interestRate = scanner.nextDouble();

        System.out.println("Enter lead time (months):");
        double leadTime = scanner.nextDouble();

        System.out.println("Enter lead time demand:");
        double leadTimeDemand = scanner.nextDouble();

        System.out.println("Enter lead time standard deviation:");
        double leadTimeStdDev = scanner.nextDouble();


        double holdingCost = unitCost * interestRate;


        double annualDemand = leadTimeDemand * (12 / leadTime);


        double Q = round(Math.sqrt((2 * orderingCost * annualDemand) / holdingCost), 3);
        double R = round(leadTimeDemand, 3);
        double safetyStock, averageAnnualHoldingCost, averageAnnualSetupCost, averageAnnualPenaltyCost, timeBetweenOrders;
        int iterations = 0;

        while (true) {
            iterations++;
            double z = lookupZValue( 1- (Q * holdingCost) / (annualDemand * penaltyCost));
            double newR = round(leadTimeDemand + leadTimeStdDev * z, 3);
            double nR = round(leadTimeStdDev * lookupLossFunctionValue(z), 3);

            double newQ = round(Math.sqrt((2 * annualDemand * (orderingCost + penaltyCost * nR)) / holdingCost), 3);

            if (Math.abs(newQ - Q) < 0.01) {
                Q = newQ;
                R = newR;
                break;
            } else {
                Q = newQ;
                R = newR;
            }
        }

        safetyStock = round(R - leadTimeDemand, 3);
        averageAnnualHoldingCost = round(holdingCost * (Q / 2 + safetyStock), 3);
        averageAnnualSetupCost = round(orderingCost * (annualDemand / Q), 3);
        averageAnnualPenaltyCost = round(penaltyCost * annualDemand * (leadTimeStdDev * lookupLossFunctionValue(lookupZValue (1- (Q * holdingCost) / (annualDemand * penaltyCost)))) / Q, 3);
        timeBetweenOrders = round((Q / annualDemand) * 12, 3);


        System.out.println("Optimal lot size (Q): " + Q);
        System.out.println("Reorder point (R): " + R);
        System.out.println("Number of iterations: " + iterations);
        System.out.println("Safety stock: " + safetyStock);
        System.out.println("Average annual holding cost: " + averageAnnualHoldingCost);
        System.out.println("Average annual setup cost: " + averageAnnualSetupCost);
        System.out.println("Average annual penalty cost: " + averageAnnualPenaltyCost);
        System.out.println("Average time between order placements: " + timeBetweenOrders + " months");
        System.out.println("Proportion of order cycles with no stockout: " + round(lookupCumulativeProbability((R - leadTimeDemand) / leadTimeStdDev) * 100, 3) + "%");
        System.out.println("Proportion of unmet demands: " + round((1 - lookupCumulativeProbability((R - leadTimeDemand) / leadTimeStdDev)), 4));
    }

    private static void readCSV(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        for (String line : lines) {
            String[] values = line.split(",");
            zValues.add(Double.parseDouble(values[0]));
            fValues.add(Double.parseDouble(values[1]));
            lValues.add(Double.parseDouble(values[2]));
        }
    }

    private static double lookupZValue(double value) {
        for (int i = 0; i < fValues.size(); i++) {
            if (value <= fValues.get(i)) {
                return zValues.get(i);
            }
        }
        return zValues.get(zValues.size() - 1);
    }

    private static double lookupLossFunctionValue(double z) {
        for (int i = 0; i < zValues.size(); i++) {
            if (z <= zValues.get(i)) {
                return lValues.get(i);
            }
        }
        return lValues.get(lValues.size() - 1);
    }



    private static double lookupCumulativeProbability(double z) {
        NormalDistribution normalDist = new NormalDistribution();
        return normalDist.cumulativeProbability(z);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
