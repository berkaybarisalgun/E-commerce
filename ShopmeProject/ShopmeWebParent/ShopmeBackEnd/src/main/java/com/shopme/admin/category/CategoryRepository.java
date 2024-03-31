package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> , CrudRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public List<Category> findRootCategories(Sort sort);

    public Category findByName(String name);

    public Category findByAlias(String alias);
}

