package com.example.ShopCustomer.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ProductList {
	
	@Getter
	@Setter
	List<Product> list;
	
	public ProductList() {
		list = new ArrayList<>();
	}

}
