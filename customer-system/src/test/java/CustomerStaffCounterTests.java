import org.geekbang.projects.cs.metrics.CustomerStaffCounter;
import org.junit.jupiter.api.Test;

public class CustomerStaffCounterTests {

    @Test
    public void testCountCustomerStaffPhoneNumber() {

        CustomerStaffCounter.countPhoneNumber("13355667788");
        CustomerStaffCounter.countPhoneNumber("13355667788");

        CustomerStaffCounter.countPhoneNumber("13355667789");

        assertThat(CustomerStaffCounter.getPhoneNumberCount("13355667788")).isEqualTo(2);
    }

}
