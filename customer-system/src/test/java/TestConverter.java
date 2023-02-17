import org.geekbang.projects.cs.controller.vo.req.AddCustomerStaffReqVO;
import org.geekbang.projects.cs.converter.CustomerStaffConverter;
import org.geekbang.projects.cs.entity.staff.CustomerStaff;
import org.geekbang.projects.cs.entity.staff.enums.Gender;
import org.geekbang.projects.cs.service.ICustomerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class TestConverter {
    public static void main(String[] args) {
        AddCustomerStaffReqVO addCustomerStaffReqVO = new AddCustomerStaffReqVO();
        addCustomerStaffReqVO.setAccountId("123456789");
        addCustomerStaffReqVO.setAvatar("avatar");
        addCustomerStaffReqVO.setGender(Gender.FEMALE);
        addCustomerStaffReqVO.setNickname("nickName");
        addCustomerStaffReqVO.setPhone("phone");
        addCustomerStaffReqVO.setRemark("remark");
        addCustomerStaffReqVO.setStaffName("staffName");
        addCustomerStaffReqVO.setStatus("ONLINE");
        addCustomerStaffReqVO.setGoodAt("goodAt");
        addCustomerStaffReqVO.setGroupId(123456L);
        addCustomerStaffReqVO.setWelcomeMessage("welcomeMessage");
        CustomerStaff customerStaff = CustomerStaffConverter.INSTANCE.addCustomerStaffReqVOToCustomerStaff(addCustomerStaffReqVO);
        System.out.println(customerStaff.toString());
    }
}
