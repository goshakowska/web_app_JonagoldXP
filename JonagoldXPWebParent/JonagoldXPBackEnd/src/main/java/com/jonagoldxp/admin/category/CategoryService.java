package com.jonagoldxp.admin.category;

import com.jonagoldxp.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    @Autowired CategoryRepository repo;

    public List<Category> listAll(){
        return repo.findAll();
    }

    public List<Category> listCategoriesUsedInForm(){
        List<Category> categoriesUsedInForm = new ArrayList<>();
        Iterable<Category> categoriesInDB = repo.findAll();

        for (Category category : categoriesInDB){
            if (category.getParent() == null){
                categoriesUsedInForm.add(new Category(category.getName()));

                getChildren(categoriesUsedInForm, category, 0);
            }
        }
        return categoriesUsedInForm;
    }

    private void getChildren(List<Category> categories, Category parent, int subLevel){
        int nextSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children){
            StringBuilder pronoun = new StringBuilder();
            for (int i = 0; i < nextSubLevel; i++){
                pronoun.append("--");
            }
            categories.add(new Category(pronoun + subCategory.getName()));

            getChildren(categories, subCategory, nextSubLevel);
        }
    }
}