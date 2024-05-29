package com.shopme.admin.product;

import com.shopme.common.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, CrudRepository<Product,Integer> {

}
