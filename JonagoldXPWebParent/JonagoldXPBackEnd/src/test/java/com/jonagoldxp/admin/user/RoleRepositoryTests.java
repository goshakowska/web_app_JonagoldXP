package com.jonagoldxp.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;

import com.jonagoldxp.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // to test a real (and not in-memory) database
@Rollback(false) //will insert data to table
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateFirstRole(){
        Role roleAdmin = new Role("Admin", "manage web application");
        Role savedRole = repo.save(roleAdmin); // returns persistent Role object
        assertThat(savedRole.getId()).isGreaterThan(0); // indicates that Role object has been persisted into the db

    }

    @Test
    public void testCreateNecessaryRoles(){
        Role roleSalesperson = new Role("Salesperson", "manage products' prices & shipment, customers' orders.");

        Role roleEditor = new Role("Editor", "manage categories, brands, products, articles and menus.");

        Role roleShipper = new Role("Shipper", "view orders & products, update orders' statuses.");

        Role roleAssistant = new Role("Assistant", "manage questions and reviews");
        repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));

    }
}
