package org.geekbang.projects.cs.integration;

import org.geekbang.projects.cs.entity.staff.CustomerStaff;
import org.geekbang.projects.cs.entity.tenant.OutsourcingSystem;
import org.geekbang.projects.cs.infrastructure.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OutsourcingSystemClient {

    @Autowired
    RestTemplate restTemplate;

    public List<CustomerStaff> getCustomerStaffs(OutsourcingSystem outsourcingSystem) {

        ResponseEntity<Result> result = null;
        try {
            //通过RestTemplate发起远程调用
            result = restTemplate.exchange(
                    outsourcingSystem.getSystemUrl(),
                    HttpMethod.GET,
                    null,
                    Result.class
            );
        } catch (HttpStatusCodeException e) {
            //捕捉HTTP异常
            e.getResponseBodyAsString();

        } catch (Exception e) {
            e.printStackTrace();
        }


        List<CustomerStaff> customerStaffs = (List<CustomerStaff>) result.getBody().getData();

        return customerStaffs;
    }
}
