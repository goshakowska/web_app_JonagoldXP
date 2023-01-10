package com.jonagoldxp.admin.com.jonagoldxp.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import com.jonagoldxp.admin.category.CategoryRepository;
import com.jonagoldxp.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository repo;

    @Test
    public void testCreateRootCategory(){
        Category category1 = new Category("Computers");
        Category category2 = new Category("Electronics");
        Category savedCategory1 = repo.save(category1);
        Category savedCategory2 = repo.save(category2);

        assertThat(savedCategory1.getId()).isGreaterThan(0);
        assertThat(savedCategory2.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory(){
        Category electronics = new Category(1);
        Category laptops = new Category("Laptops", electronics);
        Category components = new Category("Computer components", electronics);
        repo.saveAll(List.of(laptops, components));
    }

    @Test
    public void testGetCategory(){
        Category category = repo.findById(1).get();
        System.out.println(category.getName());

        Set<Category> children = category.getChildren();

        for (Category subCategory : children){
            System.out.println(subCategory.getName());
        }

        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierarchyOfCategories(){
        Iterable<Category> categories =repo.findAll();

        for (Category category : categories){
            if (category.getParent() == null) {
                System.out.println(category.getName());

                Set<Category> children = category.getChildren();

                for (Category subCategory : children) {
                    System.out.println("-" + subCategory.getName());
                }
            }
        }
    }
}
