package org.geekbang.projects.cs.controller.hateoas;


import lombok.extern.slf4j.Slf4j;
import org.geekbang.projects.cs.controller.hateoas.assembler.HypermediaCustomerStaffAssembler;
import org.geekbang.projects.cs.controller.vo.req.AddCustomerStaffReqVO;
import org.geekbang.projects.cs.controller.vo.req.UpdateCustomerStaffReqVO;
import org.geekbang.projects.cs.controller.vo.resp.CustomerStaffRespVO;
import org.geekbang.projects.cs.converter.CustomerStaffConverter;
import org.geekbang.projects.cs.entity.staff.CustomerStaff;
import org.geekbang.projects.cs.infrastructure.page.PageObject;
import org.geekbang.projects.cs.infrastructure.vo.Result;
import org.geekbang.projects.cs.service.ICustomerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.List;
import java.util.concurrent.Callable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/hypermedia/customerStaffs")
public class HypermediaCustomerStaffController {
    @Autowired
    ICustomerStaffService customerStaffService;

    @Autowired
    HypermediaCustomerStaffAssembler hypermediaCustomerStaffAssembler;

    @GetMapping("/{staffId}")
    public EntityModel<CustomerStaff> findOneCustomerStaffById(@PathVariable("staffId") Long staffId) {
        CustomerStaff customerStaff = customerStaffService.findCustomerStaffById(staffId);
        //为当前请求创建一个link
        Link selfLink = linkTo(methodOn(HypermediaCustomerStaffController.class).findOneCustomerStaffById(staffId)).withSelfRel();
        //为CustomerStaff根请求地址创建link
        Link rootLink = linkTo(methodOn(HypermediaCustomerStaffController.class).findAllCustomerStaffs()).withRel("customerStaffs");
        return EntityModel.of(customerStaff, selfLink, rootLink);
    }

    //批量获取客服信息
    @GetMapping("/")
    public CollectionModel<EntityModel<CustomerStaff>> findAllCustomerStaffs() {
        List<CustomerStaff> customerStaffList = customerStaffService.findCustomerStaffs();
        return hypermediaCustomerStaffAssembler.toCollectionModel(customerStaffList);
    }
}
