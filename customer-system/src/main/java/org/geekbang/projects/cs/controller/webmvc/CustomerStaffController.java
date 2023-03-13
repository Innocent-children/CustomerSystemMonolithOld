package org.geekbang.projects.cs.controller.webmvc;


import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
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

    @GetMapping("/findAll")
    public Result<List<CustomerStaffRespVO>> findAllCustomerStaff() {
        List<CustomerStaff> customerStaffs = customerStaffService.findCustomerStaffs();
        return Result.success(CustomerStaffConverter.INSTANCE.customerStaffsToCustomerStaffRespVOs(customerStaffs));
    }

    @GetMapping("/{staffId}")
    public Result<CustomerStaffRespVO> findCustomerStaffById(@PathVariable("staffId") Long staffId) {
        CustomerStaff customerStaff = customerStaffService.findCustomerStaffById(staffId);
        CustomerStaffRespVO customerStaffRespVO = CustomerStaffConverter.INSTANCE.customerStaffToCustomerStaffRespVO(customerStaff);
        return Result.success(customerStaffRespVO);
    }

    /**
     * 使用另一个线程进行查找操作，异步进行
     *
     * @param staffId
     * @return
     */
    @GetMapping("/async/{staffId}")
    public WebAsyncTask<CustomerStaffRespVO> asyncFindCustomerStaffById(@PathVariable("staffId") Long staffId) {
        System.out.println("主线程名字：" + Thread.currentThread().getName());
        //启动一个异步Web任务
        WebAsyncTask<CustomerStaffRespVO> task = new WebAsyncTask<CustomerStaffRespVO>(5 * 1000L, new Callable<CustomerStaffRespVO>() {
            @Override
            public CustomerStaffRespVO call() throws Exception {
                System.out.println("当前工作线程名字：" + Thread.currentThread().getName());
                //睡眠6秒，会导致超时（超时时间是5秒）
//                Thread.sleep(6 * 1000);
                CustomerStaff customerStaff = customerStaffService.findCustomerStaffById(staffId);
                CustomerStaffRespVO customerStaffRespVO = CustomerStaffConverter.INSTANCE.customerStaffToCustomerStaffRespVO(customerStaff);
                return customerStaffRespVO;
            }
        });
        //任务超时设置：添加类似熔断的效果，5秒之后超时直接返回一个空对象
        task.onTimeout(new Callable<CustomerStaffRespVO>() {
            @Override
            public CustomerStaffRespVO call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "超时了！！！");
                return new CustomerStaffRespVO();
            }
        });
        //任务完成时执行
        task.onCompletion(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "执行结束");
            }
        });
        task.onError(new Callable<CustomerStaffRespVO>() {
            @Override
            public CustomerStaffRespVO call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "执行时发生异常！！！");
                return new CustomerStaffRespVO();
            }
        });
        System.out.println("主线程依旧在执行：" + Thread.currentThread().getName());
        return task;
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
