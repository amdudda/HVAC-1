import java.util.Date;

/**
 * Created by amdudda on 10/12/2015.
 */
public class WaterHeater extends ServiceCall {

    // attributes
    // inherits address, service date, resolved date, and problem description from ServiceCall.
    // also inherits resolution and fee from ServiceCall
    protected double age; // age of the heater
    protected final int city_fee = 20;  // mandatory county fee is $20

    // constructor
    public WaterHeater(String serviceAddress, String problemDescription, Date date, double hAge) {

        super(serviceAddress, problemDescription, date);
        this.age = hAge;
    }

    // toString overrides inherited toString method
    @Override
    public String toString() {
        // preformatting some of our output
        String resolvedDateString = (resolvedDate == null) ? "Unresolved" : this.resolvedDate.toString();
        String resolutionString = (this.resolution == null) ? "Unresolved" : this.resolution;
        String feeString = (fee == UNRESOLVED) ? "Unresolved" : "$" + Double.toString(fee);
        String totalFeeString = (fee == UNRESOLVED) ? "Unresolved" : "$" + Double.toString(fee + this.city_fee);

        // and our output
        return "Water Heater Service Call:\n" +
                "Service Address: " + serviceAddress + "\n" +
                "Problem Description: " + problemDescription + "\n" +
                String.format("Water Heater Age: %.1f year(s)\n", age) +
                "Reported Date: " + reportedDate + "\n" +
                "Resolved Date: " + resolvedDateString + "\n" +
                "Resolution Description: " + resolutionString + "\n" +
                "Base Fee: " + feeString + "\n" +
                "City Fee: $" + this.city_fee + "\n" +
                "Total Fees: " + totalFeeString + "\n";
    }  // end toString override

    // setter and getters for class variables
    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public int getCity_fee() {
        return city_fee;
    }
    // end setter and getters

} // end WaterHeater class

