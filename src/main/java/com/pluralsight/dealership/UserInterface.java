package com.pluralsight.dealership;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private Scanner scanner;

    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public void display() {
        init();
        boolean quit = false;
        while (!quit) {
            System.out.println("\n--- " + dealership.getName() + " ---");
            System.out.println("1. Get vehicles by price");
            System.out.println("2. Get vehicles by make and model");
            System.out.println("3. Get vehicles by year");
            System.out.println("4. Get vehicles by color");
            System.out.println("5. Get vehicles by mileage");
            System.out.println("6. Get vehicles by type");
            System.out.println("7. Get all vehicles");
            System.out.println("8. Add vehicle");
            System.out.println("9. Remove vehicle");
            System.out.println("S. Sell a Vehicle");
            System.out.println("L. Lease a Vehicle");
            System.out.println("99. Quit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> processGetByPriceRequest();
                case "2" -> processGetByMakeModelRequest();
                case "3" -> processGetByYearRequest();
                case "4" -> processGetByColorRequest();
                case "5" -> processGetByMileageRequest();
                case "6" -> processGetByVehicleTypeRequest();
                case "7" -> processGetAllVehiclesRequest();
                case "8" -> processAddVehicleRequest();
                case "9" -> processRemoveVehicleRequest();
                case "S" -> System.out.println("sell vehicle");
                case "L" -> System.out.println("lease vehicle");
                case "99" -> quit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void processGetByPriceRequest() {
        double minPrice = readDouble("Please Enter a Min Price: ");
        double maxPrice = readDouble("Please Enter a Max Price: ");

        List<Vehicle> vehicles = dealership.getVehiclesByPrice(minPrice, maxPrice);
        displayVehicles(vehicles);
    }

    public void processGetByMakeModelRequest() {
        String make = readString("Please Enter a Make: ");
        String model = readString("Please Enter a Model: ");

        List<Vehicle> vehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    public void processGetByYearRequest() {
        int minYear = readInt("Please Enter a Min Year: ");
        int maxYear = readInt("Please Enter a Max Year: ");

        List<Vehicle> vehicles = dealership.getVehiclesByYear(minYear, maxYear);
        displayVehicles(vehicles);
    }

    public void processGetByColorRequest() {
        String color = readString("Please Enter a Color: ");

        List<Vehicle> vehicles = dealership.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    public void processGetByMileageRequest() {
        int minMileage = readInt("Please Enter a Min Mileage: ");
        int maxMileage = readInt("Please Enter a Max Mileage: ");

        List<Vehicle> vehicles = dealership.getVehiclesByMileage(minMileage, maxMileage);
        displayVehicles(vehicles);
    }

    public void processGetByVehicleTypeRequest() {
        String vehicleType = readString("Please Enter a Vehicle Type: ");

        List<Vehicle> vehicles = dealership.getVehiclesByType(vehicleType);
        displayVehicles(vehicles);
    }

    public void processGetAllVehiclesRequest() {
        List<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);
    }

    public void processAddVehicleRequest() {
        System.out.println("\n--- Adding Vehicle ---");
        Vehicle vehicle = getVehicleInfo(scanner);
        dealership.addVehicle(vehicle);
        saveAndConfirm("Vehicle added successfully.");
    }

    public void processRemoveVehicleRequest() {
        System.out.println("\n--- Removing Vehicle ---");
        int vin = readInt("Enter the VIN of the vehicle you wish to remove: ");

        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (vehicle.getVin() == vin) {
                dealership.removeVehicle(vehicle);
                saveAndConfirm("Vehicle removed successfully.");
                return;
            }
        }

        System.out.println("Vehicle not found. Please try again.");
    }

    private void init() {
        DealershipFileManager manager = new DealershipFileManager();
        dealership = manager.getDealership();
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles != null) {
            printHeader();
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    }

    private void saveAndConfirm(String message) {
        DealershipFileManager manager = new DealershipFileManager();
        manager.saveDealership(dealership);
        System.out.println(message);
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number");
            }
        }
    }

    public Vehicle getVehicleInfo(Scanner scanner) {
        int vin = readInt("VIN: ");
        int year = readInt("Year: ");
        String make = readString("Make: ");
        String model = readString("Model: ");
        String vehicleType = readString("Vehicle Type: ");
        String color = readString("Color: ");
        int odometer = readInt("Mileage: ");
        double price = readDouble("Price: ");

        return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
    }

    private void printHeader() {
        System.out.printf("%-10s %-6s %-12s %-12s %-12s %-10s %-12s %s\n",
                "VIN", "Year", "Make", "Model", "Type", "Color", "Mileage", "Price");
        System.out.println("-".repeat(94));
    }

}