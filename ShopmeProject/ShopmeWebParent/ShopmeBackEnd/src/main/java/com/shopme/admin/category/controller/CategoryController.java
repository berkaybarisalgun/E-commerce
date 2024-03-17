package com.shopme.admin.category.controller;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.category.CategoryService;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/categories")
    public String listAll(Model model){
        List<Category> listCategories=service.listAll();
        model.addAttribute("listCategories",listCategories);
        return "categories/categories";

    }


    @GetMapping("/categories/new")
    public String newCategory(Model model){

        List<Category> listCategories = service.listCategoriesUsedInForm();
        model.addAttribute("listCategories",listCategories);
        model.addAttribute("category",new Category());
        model.addAttribute("pageTitle","Create new Category");

        return "categories/category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(Category category,
                               @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        category.setImage(fileName);

        Category savedCategory = service.save(category);
        String uploadDir="../category-images/"+savedCategory.getId();
        FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);


        ra.addFlashAttribute("message","The Category has been saved successfully");
        return "redirect:/categories";
    }




}
