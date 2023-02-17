package org.geekbang.projects.cs.controller.webmvc;

import org.geekbang.projects.cs.controller.vo.req.AddCustomerStaffReqVO;
import org.geekbang.projects.cs.controller.vo.req.UpdateCustomerStaffReqVO;
import org.geekbang.projects.cs.controller.vo.resp.CustomerStaffRespVO;
import org.geekbang.projects.cs.converter.CustomerStaffConverter;
import org.geekbang.projects.cs.entity.staff.CustomerStaff;
import org.geekbang.projects.cs.infrastructure.page.PageObject;
import org.geekbang.projects.cs.infrastructure.vo.Result;
import org.geekbang.projects.cs.service.ICustomerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerStaffs")
public class CustomerStaffController {
    @Autowired
    ICustomerStaffService customerStaffService;

    //新增CustomerStaffs
    @PostMapping("/")
    public Result<Long> addCustomerStaff(@RequestBody AddCustomerStaffReqVO addCustomerStaffReqVO) {
        //数据转换，方便存入数据库 VO-->Entity
        CustomerStaff customerStaff = CustomerStaffConverter.INSTANCE.addCustomerStaffReqVOToCustomerStaff(addCustomerStaffReqVO);
        customerStaffService.createCustomerStaff(customerStaff);
        return Result.success(customerStaff.getId());
    }

    //更新CustomerStaff
    @PutMapping("/")
    public Result<Boolean> updateCustomerStaff(@RequestBody UpdateCustomerStaffReqVO updateCustomerStaffReqVO) {
        //数据转换，VO-->Entity
        CustomerStaff customerStaff = CustomerStaffConverter.INSTANCE.updateCustomerStaffReqVOToCustomerStaff(updateCustomerStaffReqVO);
        Boolean update = customerStaffService.updateCustomerStaff(customerStaff);
        return Result.success(update);
    }

    @PutMapping("/status")
    public Result<Boolean> updateCustomerStaffStatus(@RequestBody UpdateCustomerStaffReqVO updateCustomerStaffReqVO) {
        CustomerStaff customerStaff = CustomerStaffConverter.INSTANCE.updateCustomerStaffReqVOToCustomerStaff(updateCustomerStaffReqVO);
        Boolean updateStatus = customerStaffService.updateCustomerStaff(customerStaff);
        return Result.success(updateStatus);
    }

    @GetMapping("/{staffId}")
    public Result<CustomerStaffRespVO> findCustomerStaffById(@PathVariable("staffId") Long staffId) {
        CustomerStaff customerStaff = customerStaffService.findCustomerStaffById(staffId);
        CustomerStaffRespVO customerStaffRespVO = CustomerStaffConverter.INSTANCE.customerStaffToCustomerStaffRespVO(customerStaff);
        return Result.success(customerStaffRespVO);
    }

    //批量获取客服信息
    @GetMapping("/page/{pageSize}/{pageIndex}")
    public Result<PageObject<CustomerStaffRespVO>> findCustomerStaffs(@PathVariable("pageSize") Long pageSize, @PathVariable("pageIndex") Long pageIndex) {
        PageObject<CustomerStaff> pagedCustomerStaff = customerStaffService.findCustomerStaffs(pageSize, pageIndex);
        List<CustomerStaffRespVO> customerStaffRespVOs = CustomerStaffConverter.INSTANCE.customerStaffsToCustomerStaffRespVOs(pagedCustomerStaff.getList());
        PageObject<CustomerStaffRespVO> result = new PageObject<CustomerStaffRespVO>()
                .setList(customerStaffRespVOs)
                .setTotal(pagedCustomerStaff.getTotal())
                .setPageIndex(pagedCustomerStaff.getPageIndex())
                .setPageSize(pagedCustomerStaff.getPageSize());

        return Result.success((result));
    }

    @DeleteMapping("/{staffId}")
    public Result<Boolean> deleteCustomerStaffById(@PathVariable("staffId") Long staffId) {
        Boolean delete = customerStaffService.deleteCustomerStaffById(staffId);
        return Result.success(delete);
    }
}
