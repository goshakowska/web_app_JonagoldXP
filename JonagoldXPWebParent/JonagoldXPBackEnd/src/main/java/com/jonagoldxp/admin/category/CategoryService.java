package com.jonagoldxp.admin.category;

import com.jonagoldxp.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
                categoriesUsedInForm.add(Category.copyIdAndName(category));

                getChildren(categoriesUsedInForm, category, 0);
            }
        }
        return categoriesUsedInForm;
    }

    public Category save(Category category){
        return repo.save(category);
    }

    public Category get(Integer id) throws NoSuchElementException {
        return repo.findById(id).get();
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

    public String checkUnique(Integer id, String name, String alias) {
        boolean isCreatingNew = (id == null || id == 0);

        Category categoryByName = repo.findByName(name);

        if (isCreatingNew) {
            if (categoryByName != null) {
                return "DuplicateName";
            } else {
                Category categoryByAlias = repo.findByAlias(alias);
                if (categoryByAlias != null) {
                    return "DuplicateAlias";
                }
            }
        } else {
            if (categoryByName != null && categoryByName.getId() != id) {
                return "DuplicateName";
            }

            Category categoryByAlias = repo.findByAlias(alias);
            if (categoryByAlias != null && categoryByAlias.getId() != id) {
                return "DuplicateAlias";
            }

        }

        return "OK";
    }
    public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id, enabled);
    }

    public void delete(Integer id) throws NoSuchElementException {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new NoSuchElementException("Could not find any category with ID " + id);
        }

        repo.deleteById(id);
    }
}
