package com.example.ShopCustomer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.ShopCustomer.entity.Food;
import com.example.ShopCustomer.entity.Nonfood;
import com.example.ShopCustomer.entity.Product;
import com.example.ShopCustomer.entity.ProductList;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@GetMapping("/home")
	public String showHome(Model model, HttpSession session) {
		if(session.getAttribute("cart") == null) {
			List<Product> cart = new ArrayList<>();
			session.setAttribute("cart", cart);
			System.out.println("Cart created");
		}
		List<Product> cartModel = (List<Product>) session.getAttribute("cart");
		System.out.println(cartModel);
		model.addAttribute("productName", new Food());
		model.addAttribute("addedProduct", new Food());
		return "home";
	}
	
	@GetMapping("/productList")
	public String showProducts(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		ProductList list = restTemplate.getForObject("http://localhost:8090/shop/products/", ProductList.class);
		List<Product> theProducts = list.getList();
		model.addAttribute("products", theProducts);
		return "productList";
	}
	
	@PostMapping("/searchProduct")
	public String searchProducts(@ModelAttribute("productName") Food productName, Model model) {
		RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<?> entity = new HttpEntity<Object>(productName,headers);
	    ResponseEntity<ProductList> responseEntity = restTemplate.exchange("http://localhost:8090/shop/search", HttpMethod.POST, entity, ProductList.class);
	    ProductList list = responseEntity.getBody();
	    List<Product> theProducts = list.getList();
		model.addAttribute("foundProducts", theProducts);
		return "productSearch";
	}
	
	@PostMapping("/addProductToCart")
	public String addProductToCart(@ModelAttribute("addedProduct") Food productName, HttpSession session) {
		List<Product> cart = (List<Product>) session.getAttribute("cart");
		RestTemplate restTemplate = new RestTemplate();
		ProductList list = restTemplate.getForObject("http://localhost:8090/shop/products/", ProductList.class);
		List<Product> theProducts = list.getList();
		Optional <Product> theProduct = theProducts.stream().filter(a->a.getName().equals(productName.getName())).findAny();
		if(theProduct.isEmpty()) {
			return "errorProductName";
		}
		Product pr = theProduct.get();
		if(pr.getAmount()<productName.getAmount()) {
			return "errorProductAmount";
		}
		cart.add(productName);
		session.setAttribute("cart", cart);
		return "addedProduct";
	}
	
	@GetMapping("/cartList")
	public String showProductsInCart(Model model, HttpSession session) {
		List<Product> cartModel = (List<Product>) session.getAttribute("cart");
		model.addAttribute("cartProducts", cartModel);
		return "cartList";
	}
	
	@RequestMapping("/deleteProduct")
	public String deleteProductFromCart(@RequestParam("productName") String name, HttpSession session) {
		List<Product> cart = (List<Product>) session.getAttribute("cart");
		cart.removeIf(a->a.getName().equals(name));
		session.setAttribute("cart", cart);
		return "redirect:/customer/cartList";
	}
	
	@PostMapping("/order")
	public String order(HttpSession session) {
		List<Product> cart = (List<Product>) session.getAttribute("cart");
		for(Product tempProduct:cart) {
			
			//get product from DB
			RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    HttpEntity<?> entity = new HttpEntity<Object>(tempProduct,headers);
		    ResponseEntity<Product> responseEntity = restTemplate.exchange("http://localhost:8090/shop/order", HttpMethod.POST, entity, Product.class);
		    Product theProduct = responseEntity.getBody();
			
		    //set new amount of product
			int theProductAmount = theProduct.getAmount();
			theProduct.setAmount(theProductAmount - tempProduct.getAmount());
			
			//write product in DB
			if(theProduct instanceof Food) {
			    entity = new HttpEntity<Object>(theProduct,headers);
			    responseEntity = restTemplate.exchange("http://localhost:8090/shop/products/food", HttpMethod.POST, entity, Product.class);
			}
			if(theProduct instanceof Nonfood) {
			    entity = new HttpEntity<Object>(theProduct,headers);
			    responseEntity = restTemplate.exchange("http://localhost:8090/shop/products/nonfood", HttpMethod.POST, entity, Product.class);
			}
		}
		cart.clear();
		session.setAttribute("cart", cart);
		return "redirect:/customer/home";
	}

}
