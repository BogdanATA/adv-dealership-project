package com.pluralsight.dealership;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ContractDataManager {

    public void saveContract(Contract contract) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("contracts.csv", true))) {
            Vehicle vehicle = contract.getVehicleSold(); // get the vehicle sold from the contract
            String date = contract.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")); // get the date from the contract

            String sharedFields = String.format("%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f", date, contract.getCustomerName(),
                    contract.getCustomerEmail(), vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(),
                    vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());

            if (contract instanceof SalesContract salesContract) {
                bw.write("SALE|" + sharedFields + String.format("|%.2f|%.2f|%.2f|%.2f|%s|%.2f", salesContract.getSalesTaxAmount(),
                        salesContract.getRecordingFee(), salesContract.getProcessingFee(), salesContract.getTotalPrice(),
                        salesContract.isFinanceOption(), salesContract.getMonthlyPayment()));
            } else if (contract instanceof LeaseContract leaseContract) {
                bw.write("LEASE|" + sharedFields + String.format("|%.2f|%.2f|%.2f|%.2f", leaseContract.getExpectedEndingValue(),
                        leaseContract.getLeaseFee(), leaseContract.getTotalPrice(), leaseContract.getMonthlyPayment()));
            }

            bw.newLine();

            System.out.println("Contract saved successfully to contracts.csv.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
