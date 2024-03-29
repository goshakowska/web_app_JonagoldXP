package com.jonagoldxp.admin.product;

import com.jonagoldxp.admin.category.CategoryService;
import com.jonagoldxp.common.entity.Product;
import com.jonagoldxp.admin.paging.PagingAndSortingHelper;
import com.jonagoldxp.admin.paging.PagingAndSortingParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.NoSuchElementException;

@Controller
public class ProductController {
    private final String defaultRedirectURL = "redirect:/products/page/1?sortField=name&sortDir=asc&categoryId=0";
    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;

    @GetMapping("/products")
    public String listFirstPage(Model model) {
        return defaultRedirectURL;
    }

    @GetMapping("/products/page/{pageNum}")
    public String listByPage(
            @PagingAndSortingParam(listName = "listProducts", moduleURL = "/products") PagingAndSortingHelper helper,
            @PathVariable(name = "pageNum") int pageNum) {
        productService.listByPage(pageNum, helper);

        return "products/products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model){

        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);

        model.addAttribute("product", product);
        model.addAttribute("pageTile", "Create New Product");

        return "products/product_form";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, RedirectAttributes ra
    ) {
        productService.save(product);
        ra.addFlashAttribute("message", "The product has been saved successfully.");
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Product product = productService.get(id);

            model.addAttribute("product", product);
            model.addAttribute("pageTitle", "Edit Product (ID: " + id + ")");

            return "products/product_form";

        } catch (NoSuchElementException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return defaultRedirectURL;
        }
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Integer id,
                                Model model, RedirectAttributes ra) {
        try {
            productService.delete(id);
            ra.addFlashAttribute("message", "The product ID " + id + " has been deleted successfully");
        } catch (NoSuchElementException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
        }
        return defaultRedirectURL;
    }

    @GetMapping("/products/{id}/enabled/{status}")
    public String updateProductEnabledStatus(@PathVariable("id") Integer id,
                                             @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
        productService.updateProductEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The Product ID " + id + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);

        return defaultRedirectURL;
    }
}
