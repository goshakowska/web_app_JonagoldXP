package com.jonagoldxp.admin.category;

import com.jonagoldxp.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    public static final int ROOT_CATEGORIES_PER_PAGE = 4;
    @Autowired CategoryRepository repo;

    public List<Category> listByPage(CategoryPageInfo pageInfo, int pageNum, String sortDir,
                                     String keyword) {
        Sort sort = Sort.by("name");

        if (sortDir.equals("asc")) {
            sort = sort.ascending();
        } else if (sortDir.equals("desc")) {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE, sort);

        Page<Category> pageCategories = null;

        if (keyword != null && !keyword.isEmpty()) {
            pageCategories = repo.search(keyword, pageable);
        } else {
            pageCategories = repo.findRootCategories(pageable);
        }

        List<Category> rootCategories = pageCategories.getContent();

        pageInfo.setTotalElements(pageCategories.getTotalElements());
        pageInfo.setTotalPages(pageCategories.getTotalPages());

        if (keyword != null && !keyword.isEmpty()) {
            List<Category> searchResult = pageCategories.getContent();
            for (Category category : searchResult) {
                category.setHasChildren(category.getChildren().size() > 0);
            }

            return searchResult;

        } else {
            return listHierarchicalCategories(rootCategories, sortDir);
        }
    }

    private List<Category> listHierarchicalCategories(List<Category> rootCategories, String sortDir) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyFull(rootCategory));

            Set<Category> children = sortSubCategories(rootCategory.getChildren(), sortDir);

            for (Category subCategory : children) {
                String name = "--" + subCategory.getName();
                hierarchicalCategories.add(Category.copyFull(subCategory, name));

                getSubCategories(hierarchicalCategories, subCategory, 1, sortDir);
            }
        }

        return hierarchicalCategories;
    }

    private void getSubCategories(List<Category> categories, Category parent, int subLevel, String sortDir){
        int nextSubLevel = subLevel + 1;
        Set<Category> children = sortSubCategories(parent.getChildren(), sortDir);

        for (Category subCategory : children){
            StringBuilder pronoun = new StringBuilder();
            for (int i = 0; i < nextSubLevel; i++){
                pronoun.append("--");
            }
            categories.add(Category.copyFull(subCategory, pronoun + subCategory.getName()));

            getSubCategories(categories, subCategory, nextSubLevel, sortDir);
        }
    }


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

    private SortedSet<Category> sortSubCategories(Set<Category> children) {
        return sortSubCategories(children, "asc");
    }

    private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
        SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {
            @Override
            public int compare(Category cat1, Category cat2) {
                if (sortDir.equals("asc")) {
                    return cat1.getName().compareTo(cat2.getName());
                } else {
                    return cat2.getName().compareTo(cat1.getName());
                }
            }
        });

        sortedChildren.addAll(children);

        return sortedChildren;
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
