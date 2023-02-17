package org.geekbang.projects.cs.controller.vo.req;

import lombok.Data;
import lombok.experimental.Accessors;
import org.geekbang.projects.cs.entity.staff.enums.Gender;

@Data
@Accessors(chain = true)
public class AddCustomerStaffReqVO {

    private Long groupId;
    private String staffName;
    private String nickname;
    private String accountId;
    private String avatar;
    private String phone;
    private Gender gender;
    private String goodAt;
    private String status;
    private String welcomeMessage;
    private String remark;
}
