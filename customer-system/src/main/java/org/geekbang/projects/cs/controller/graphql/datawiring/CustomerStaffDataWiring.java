package org.geekbang.projects.cs.controller.graphql.datawiring;

import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.TypeRuntimeWiring;
import org.geekbang.projects.cs.controller.graphql.datafetcher.AllCustomerStaffsDataFetcher;
import org.geekbang.projects.cs.controller.graphql.datafetcher.CustomerGroupDataFetcher;
import org.geekbang.projects.cs.controller.graphql.datafetcher.CustomerStaffDataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.stereotype.Component;

import java.util.function.UnaryOperator;

@Component
public class CustomerStaffDataWiring implements RuntimeWiringBuilderCustomizer {

    @Autowired
    private AllCustomerStaffsDataFetcher allCustomerStaffsDataFetcher;

    @Autowired
    private CustomerStaffDataFetcher customerStaffDataFetcher;

    @Autowired
    private CustomerGroupDataFetcher customerGroupDataFetcher;

    @Override
    public void customize(RuntimeWiring.Builder builder) {

//        builder.type("Query", typeWiring -> typeWiring
//                    .dataFetcher("staffs", allCustomerStaffsDataFetcher)
//                    .dataFetcher("staff", customerStaffDataFetcher))
//                .type("CustomerStaff", typeWiring -> typeWiring
//                    .dataFetcher("group", customerGroupDataFetcher));
        builder.type("Query", new UnaryOperator<TypeRuntimeWiring.Builder>() {
            @Override
            public TypeRuntimeWiring.Builder apply(TypeRuntimeWiring.Builder builder) {
                return builder.dataFetcher("staffs", allCustomerStaffsDataFetcher);
            }
        }).type("Query", new UnaryOperator<TypeRuntimeWiring.Builder>() {
            @Override
            public TypeRuntimeWiring.Builder apply(TypeRuntimeWiring.Builder builder) {
                return builder.dataFetcher("staff", customerStaffDataFetcher);
            }
        }).type("CustomerStaff", new UnaryOperator<TypeRuntimeWiring.Builder>() {
            @Override
            public TypeRuntimeWiring.Builder apply(TypeRuntimeWiring.Builder builder) {
                return builder.dataFetcher("group", customerGroupDataFetcher);
            }
        });
    }
}
