package com.example.ShopCustomer.entity;

import lombok.Getter;
import lombok.Setter;

public class Nonfood extends Product {
	
	public Nonfood() {
		
	}
	
	@Getter
	@Setter
	private Integer lifeTime;
	
	@Override
	public String toString() {
		return "\n" + "Nonfood [name=" + getName() + ", amount=" + getAmount() + ", price=" + getPrice() + ", life time=" + getLifeTime() + "]";
	}


}
