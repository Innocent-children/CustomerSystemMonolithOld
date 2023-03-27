import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.geekbang.projects.cs.Application;
import org.geekbang.projects.cs.entity.staff.CustomerStaff;
import org.geekbang.projects.cs.mapper.CustomerStaffMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
//@MybatisPlusTest
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerStaffTests {

    @Autowired
    private CustomerStaffMapper customerStaffMapper;

    @Test
    public void testQueryCustomerStaffById() {
        CustomerStaff customerStaff = customerStaffMapper.selectById(1L);

        assertNotNull(customerStaff);
        assertEquals(customerStaff.getNickname(), "tianyalan");
    }
}
