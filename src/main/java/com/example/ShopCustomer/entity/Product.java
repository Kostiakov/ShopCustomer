package com.example.ShopCustomer.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
include = JsonTypeInfo.As.PROPERTY,
property = "type")
@JsonSubTypes({
@Type(value = Food.class),
@Type(value = Nonfood.class),
})
public abstract class Product {
	
	public Product() {
		
	}
	
	@Getter
	@Setter
	private int id;
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private Integer amount;
	@Getter
	@Setter
	private Integer price;

}
