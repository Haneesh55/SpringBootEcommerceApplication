package com.cg.ecom.controller;

import java.util.List;

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

import com.cg.ecom.dto.AddProductItemsDTO;
import com.cg.ecom.dto.ProductItemsDTO;
import com.cg.ecom.exceptions.ItemNotAvailableException;
import com.cg.ecom.service.ProductItemsService;

@RestController
@RequestMapping("/api/product")
public class ProductItemsController {

	@Autowired
	public ProductItemsService productItemsService;
	
	@PostMapping("/addproductitems")
	public ResponseEntity<ProductItemsDTO> addProductItems(@RequestBody AddProductItemsDTO addProductItemsDTO){
		
			ProductItemsDTO productitems = productItemsService.addProductItems(addProductItemsDTO);
			return ResponseEntity.ok(productitems);
		
	}

	@GetMapping("/productItemsById/{id}")
	public ResponseEntity<ProductItemsDTO> getProductItemsById(@PathVariable int id)
	{
		if(productItemsService.getById(id)!=null) {
		ProductItemsDTO productItemsDTO=productItemsService.getById(id);
		return new ResponseEntity<ProductItemsDTO>(productItemsDTO, HttpStatus.FOUND);
		}
		throw new ItemNotAvailableException();
	}
	
	@PutMapping("/updateProductItems")
	public ResponseEntity<ProductItemsDTO> updateProductItems(@RequestBody ProductItemsDTO productItemsDTO){
		return new ResponseEntity<ProductItemsDTO>(productItemsService.updateProductItems(productItemsDTO), HttpStatus.ACCEPTED);
		
	}
	
	@DeleteMapping("/deleteProductItems/{id}")
	public ResponseEntity<Boolean> deleteProductItemsById(@PathVariable int id)
	{
		ProductItemsDTO productItemsDTO=productItemsService.getById(id);
		if(productItemsDTO!=null) {
			productItemsService.deleteProductItems(productItemsDTO);
			return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
		}
		throw new ItemNotAvailableException("Items with id " +id+ "doesnot exists");
	}
	
	@GetMapping("/fetchAllproductItems")
	public ResponseEntity<List<ProductItemsDTO>> getAllProductItems(){
		List<ProductItemsDTO> list=productItemsService.findAll();
		return ResponseEntity.ok(list);
	}
}
