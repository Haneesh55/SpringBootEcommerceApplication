package com.cg.ecom.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ecom.dto.AddCartDTO;
import com.cg.ecom.dto.CartDTO;
import com.cg.ecom.exceptions.ItemNotAvailableException;
import com.cg.ecom.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	public CartService cartService;

	@PostMapping("/addCart")
	public ResponseEntity<CartDTO> addToCart(@Valid @RequestBody AddCartDTO addCartDTO){

	    CartDTO cartsave = cartService.addToCart(addCartDTO);
	    return ResponseEntity.ok(cartsave);
	}


	@PutMapping("/updateCart")
	public ResponseEntity<CartDTO> updateCart(@RequestBody CartDTO cartDTO) {
		return new ResponseEntity<CartDTO>(cartService.updateCart(cartDTO), HttpStatus.ACCEPTED);

	}

	@GetMapping("/getCartById/{id}")
	public ResponseEntity<CartDTO> getCartById(@PathVariable int id) {
		CartDTO cartDTO = cartService.getById(id);
		if (cartDTO != null) {
			return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
		}
		throw new ItemNotAvailableException("Cart with id " + id + " doesn't exist");
	}

	
	@DeleteMapping("/deleteCartById/{id}")
	public ResponseEntity<Boolean> deleteCartById(@PathVariable int id) {
		CartDTO cartDTO = cartService.getById(id);
		if (cartDTO != null) {
			cartService.deleteCart(cartDTO);
			return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
		}
		throw new ItemNotAvailableException("Cart with id " + id + "doesnot exists");
	}

	@GetMapping("/fetchAllinCart")
	public ResponseEntity<List<CartDTO>> getAll() {
		List<CartDTO> list = cartService.findAll();
		return ResponseEntity.ok(list);
	}
	

	
}
