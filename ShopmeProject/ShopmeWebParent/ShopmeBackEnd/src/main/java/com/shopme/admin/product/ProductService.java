package com.shopme.admin.product;

import com.shopme.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

	@Autowired private ProductRepository repo;

	public List<Product> listAll() {
		List<Product> list= (List<Product>) repo.findAll();
		return list;
	}
	
}
