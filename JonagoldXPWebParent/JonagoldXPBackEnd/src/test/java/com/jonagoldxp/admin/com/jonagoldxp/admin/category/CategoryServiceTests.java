package com.jonagoldxp.admin.com.jonagoldxp.admin.category;


import com.jonagoldxp.admin.category.CategoryRepository;
import com.jonagoldxp.admin.category.CategoryService;
import com.jonagoldxp.common.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTests {
    @Autowired
    private CategoryService service;

    @MockBean
    private CategoryRepository repo;

    @Test
    public void getAllCategoriesTest(){
        when(repo.findAll()).thenReturn(List.of(new Category(), new Category()));
        assertEquals(2, service.listAll().size());
    }

    @Test
    public void getByIdCategoryTest(){
        Integer id = 3;
        Category category = new Category(id);
        when(repo.findById(id)).thenReturn(Optional.of(category));
        assertEquals(category, repo.findById(id).get());
    }

    @Test
    public void saveProductTest(){
        Category category = new Category();
        when(repo.save(category)).thenReturn(category);
        assertEquals(category, service.save(category));
    }

    @Test
    public void deleteCategoryTest(){
        Integer id = 2;
        long count = 1;
        when(repo.countById(id)).thenReturn(count);
        service.delete(id);
        Mockito.verify(repo, times(1)).deleteById(id);
    }

    @Test
    public void checkUniqueDuplicateNameCategoryTest(){
        String name = "Laptops";
        Integer id = 1;
        Category category = new Category(id, name);
        when(repo.findByName(name)).thenReturn(category);
        assertEquals("DuplicateName", service.checkUnique(id, name, name));
    }

    @Test
    public void checkUniqueDuplicateAliasCategoryTest(){
        String name = "Laptops";
        Integer id = 1;
        Category category = new Category(id, name);
        when(repo.findByName(name)).thenReturn(null);
        when(repo.findByAlias(name)).thenReturn(category);
        assertEquals("DuplicateAlias", service.checkUnique(id, name, name));
    }

    @Test
    public void checkUniqueOkCategoryTest(){
        String name = "Laptops";
        Integer id = 1;
        when(repo.findByName(name)).thenReturn(null);
        when(repo.findByAlias(name)).thenReturn(null);
        assertEquals("OK", service.checkUnique(id, name, name));
    }

    @Test
    public void listHierarchicalCategoriesTest(){
        // listHierarchicalCategories and getCategoriesUsedInForm have almost the same functionality but different arguments
        List<Category> categories = new ArrayList<Category>();
        Category category1 = new Category(1, "Laptops");
        Category category2 = new Category(2, "Phones");
        Category category3 = new Category("Notepads", category1);
        Set<Category> child = new HashSet<>();
        child.add(category3);
        category1.setChildren(child);
        categories.add(category1);
        categories.add(category2);

        when(repo.findAll()).thenReturn(categories);
        List<Category> hierarchy = service.listCategoriesUsedInForm();

        assertEquals(category1.getName(), hierarchy.get(0).getName());
        assertEquals("--" + category3.getName(), hierarchy.get(1).getName());
        assertEquals(category2.getName(), hierarchy.get(2).getName());
    }
}
