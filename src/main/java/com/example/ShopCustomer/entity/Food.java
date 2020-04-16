package com.example.ShopCustomer.entity;

import lombok.Getter;
import lombok.Setter;

public class Food extends Product {
	
	public Food() {
		
	}
	
	@Getter
	@Setter
	private Integer calories;
	
	@Override
	public String toString() {
		return "\n" + "Food [name=" + getName() + ", amount=" + getAmount() + ", price=" + getPrice() + ", calories=" + getCalories() + "]";
	}


}
