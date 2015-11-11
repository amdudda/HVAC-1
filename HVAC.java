

import java.util.LinkedList;
import java.util.Date;
import java.util.Scanner;

public class HVAC {


    /** Program to manage service calls to furnaces and AC units
     */

    public static LinkedList<ServiceCall> todayServiceCalls;
    public static LinkedList<ServiceCall> resolvedServiceCalls;

    private static Scanner scanner;   //Global scanner used for all input


    public static void main(String[] args) {

        HVACgui hvaCgui = new HVACgui();

        //Use todayServiceCalls as a Queue
        //So, add new calls to the end with add()
        //Remove a resolved call from the front of the queue with remove()
        //Check on the current call - the one at the head of the queue - with peek()

        //This will enable us to deal with calls in the order in which they were received

        todayServiceCalls = new LinkedList<ServiceCall>();

        // This will be used to store a list of resolved service calls.

        resolvedServiceCalls = new LinkedList<ServiceCall>();

        scanner = new Scanner(System.in);

        boolean quit = false;

        while (!quit) {

            System.out.println("1. Add service call to queue");
            System.out.println("2. Resolve current call");
            System.out.println("3. Print current call");
            System.out.println("4. Print all outstanding calls");
            System.out.println("5. Print all resolved calls ");
            System.out.println("6. Quit");

            int choice = getPositiveIntInput();

            switch (choice) {

                case 1: {
                    addServiceCall();
                    break;
                }
                case 2: {
                    //Resolve service call

                    //Remove from head of the queue

                    if (todayServiceCalls.isEmpty()) {
                        System.out.println("No service calls today");
                        break;
                    }

                    ServiceCall resolvedCall = todayServiceCalls.remove();

                    System.out.println("Enter resolution for " + resolvedCall);

                    String resolution = getStringInput();
                    System.out.println("Enter fee charged to customer");
                    double fee = getPositiveDoubleInput();

                    resolvedCall.setResolution(resolution);
                    resolvedCall.setFee(fee);
                    resolvedCall.setResolvedDate(new Date());  //default resolved date is now

                    //Add this call to the list of resolved calls
                    resolvedServiceCalls.add(resolvedCall);
                    break;

                }
                case 3: {
                    //Print next service call - it is the one at the top of the queue
                    if (todayServiceCalls.isEmpty()) {
                        System.out.println("No service calls today");
                        break;
                    } else {
                        System.out.println(todayServiceCalls.peek());
                    }
                    break;
                }
                //Print all service calls
                case 4: {

                    System.out.println("Today's service calls are: ");

                    if (todayServiceCalls.isEmpty()) {
                        System.out.println("No service calls today");
                        break;
                    }

                    for (int call = 0; call < todayServiceCalls.size() ; call++) {
                        System.out.println("Service Call " + call + "\n" + todayServiceCalls.get(call) + "\n");
                    }
                    break;
                }

                case 5: {
                    System.out.println("List of resolved calls: ");

                    if (resolvedServiceCalls.isEmpty()) {
                        System.out.println("No resolved calls");
                        break;
                    }

                    for (ServiceCall c : resolvedServiceCalls) {
                        System.out.println(c + "\n");

                    }
                    break;
                }

                case 6: {
                    quit = true;
                    break;

                }

                default: {
                    System.out.println("Enter a number from the menu choices");
                }

            }


        }

        System.out.println("Thanks, bye!");
        //Tidy up... close the scanner
        scanner.close();
    }


    private static void addServiceCall() {

        //What type of thing needs servicing?

        System.out.println("1. Add service call for furnace");
        System.out.println("2. Add service call for AC unit");
        System.out.println("3. Add service call for water heater");
        System.out.println("4. Quit");

        int choice = getPositiveIntInput();

        switch (choice) {

            case 1: {
                getUserInput("furnace");
                break;
            }
            case 2: {
                getUserInput("AC Unit");
                break;

            }
            case 3:{
                // collect our necessary data
                getUserInput("water heater");
                break;
            }
            case 4: {
                return;

            }
            default: {
                System.out.println("Enter a number from the menu choices");
            }

        }

    }

    private static void getUserInput(String device_type) {
        // this unifies multiple lines of code that gather the same kind of info
        // into a separate method.
        System.out.println("Enter address of " + device_type);
        String address = getStringInput();
        System.out.println("Enter description of problem");
        String problem = getStringInput();
        ServiceCall dev;

        if (device_type.equals("water heater")) {
            System.out.println("Enter age of water heater");
            Double age = getPositiveDoubleInput();
            dev = new WaterHeater(address,problem,new Date(),age);
        } else if (device_type.equals("AC Unit")){
            System.out.println("Enter model of AC unit");
            String model = getStringInput();
            dev = new CentralAC(address, problem, new Date(), model);
        } else { // device is a furnace
            int type = 0;
            while (type < 1 || type > 3) {
                System.out.println("Type of furnace?\n" +
                        Furnace.FurnaceTypeManager.furnaceTypeUserChoices());
                //We can only choose from types defined in FurnaceTypeManager
                type = getPositiveIntInput();
            }

            dev = new Furnace(address, problem, new Date(), type);
        }  // end if-else block

        todayServiceCalls.add(dev);
        System.out.println("Added the following " + device_type + " to list of calls:\n" + dev);
    }

    //Validation methods

    private static int getPositiveIntInput() {

        while (true) {
            try {
                String stringInput = scanner.nextLine();
                int intInput = Integer.parseInt(stringInput);
                if (intInput >= 0) {
                    return intInput;
                } else {
                    System.out.println("Please enter a positive number");
                    continue;
                }
            } catch (NumberFormatException ime) {
                System.out.println("Please type a positive number");
            }
        }

    }

    private static double getPositiveDoubleInput() {

        while (true) {
            try {
                String stringInput = scanner.nextLine();
                double doubleInput = Double.parseDouble(stringInput);
                if (doubleInput >= 0) {
                    return doubleInput;
                } else {
                    System.out.println("Please enter a positive number");
                    continue;
                }
            } catch (NumberFormatException ime) {
                System.out.println("Please type a positive number");
            }
        }

    }

    private static String getStringInput() {

        String entry = scanner.nextLine();
        return entry;

    }
}

