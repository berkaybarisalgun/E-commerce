package com.shopme.admin.user;

import com.shopme.admin.ShopmeBackEndApplication;
import com.shopme.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//in default it tests with in-memory database so that's why we are specifying it.
@Rollback(false)//end of the test,changes are immutable
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateFirstRole(){
        Role roleAdmin=new Role("Admin","manage everything");

        Role savedRole=repo.save(roleAdmin);
        assertThat(savedRole.getId()).isGreaterThan(0);//The AssertThat statement helps determine whether tests are successful by comparing the expected results with the actual results.

    }

    @Test
    public void testCreateRestRoles(){
        Role roleSalesperson=new Role("Salesperson","manage product price,"+"cusomters,shipping,orders and sales report");
        Role roleEditor=new Role("Editor","manage categories,brands"+"products,articles and menus");
        Role roleShipper=new Role("Shipper","view products,view orders"+"and update order status");
        Role roleAssistant=new Role("Assistant","manage questions and reviews");
        repo.saveAll(List.of(roleSalesperson,roleEditor,roleShipper,roleAssistant));


    }

}
