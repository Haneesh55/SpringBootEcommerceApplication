package com.cg.ecom.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ecom.dto.AddOrdersDTO;
import com.cg.ecom.dto.OrdersDTO;
import com.cg.ecom.entity.Cart;
import com.cg.ecom.entity.Customers;
import com.cg.ecom.entity.Orders;
import com.cg.ecom.entity.ProductSupplier;
import com.cg.ecom.exceptions.CartIdAlreadyExistsException;
import com.cg.ecom.exceptions.ItemNotAvailableException;
import com.cg.ecom.exceptions.OrderNotFoundException;
import com.cg.ecom.repository.OrdersRepository;
import com.cg.ecom.repository.ProductItemsRepository;
import com.cg.ecom.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private ProductItemsRepository productItemsRepository;

	@Override
	public OrdersDTO addOrders(AddOrdersDTO addOrdersDTO)  throws CartIdAlreadyExistsException
	{
		
//		// Check if the cart ID already exists
//	    boolean existingOrder = ordersRepository.existsByCartId(ordersDTO.getCartId());
//	    
//	    if (existingOrder) 
//	    {
//	       
//	    	 throw new CartIdAlreadyExistsException();
//	    }
//	    else {
	    	
		Orders orders = new Orders();
		int productId = addOrdersDTO.getProductId();
		
//		ProductItems productItem = productItemsRepository.findById(productId).orElse(null);
//		if(addOrdersDTO.getQuantity() <= productItem.getQuantity()) 
//		{
		
		Customers cust = new Customers();
		Cart cart = new Cart();
		cart.setCartId(addOrdersDTO.getCartId());
		orders.setCartId(cart);
		
		cust.setCustomerId(addOrdersDTO.getCustomerId());
		orders.setCustomerId(cust);
//		orders.setDate(ordersDTO.getDate());
		orders.setDeliveryAddress(addOrdersDTO.getDeliveryAddress());
		orders.setStatus(addOrdersDTO.getStatus());
//		orders.setCartId(ordersDTO.getCartId());

		ProductSupplier rest = new ProductSupplier();
		rest.setProductSupplierId(addOrdersDTO.getProductSupplierId());
		orders.setProductSupplierId(rest);
//		orders.setDate(ordersDTO.getDate());
		orders.setDeliveryAddress(addOrdersDTO.getDeliveryAddress());
	//	orders.setStatus(ordersDTO.getStatus());
//		orders.setCartId(ordersDTO.getCartId());

		Orders ordersave = ordersRepository.save(orders);
		
		OrdersDTO ordersDTO = new OrdersDTO();
		ordersDTO.setCartId(ordersave.getCartId().getCartId());
		ordersDTO.setCustomerId(ordersave.getCustomerId().getCustomerId());
		ordersDTO.setDeliveryAddress(ordersave.getDeliveryAddress());
		ordersDTO.setOrderId(ordersave.getOrderId());
//		ordersDTO.setProductId(addOrdersDTO.getProductId());
		ordersDTO.setProductSupplierId(ordersave.getProductSupplierId().getProductSupplierId());
//		ordersDTO.setQuantity(addOrdersDTO.getQuantity());
		ordersDTO.setStatus(addOrdersDTO.getStatus());
		
		return ordersDTO;

//		Customers customer = customersRepository.findById(ordersDTO.getCustomerId()).get();
//		}
		//throw new ProductOutStockException();
	    }
//	}
	

	@Override
	public OrdersDTO updateOrders(OrdersDTO ordersDTO) {

		Optional<Orders> optionalorder = ordersRepository.findById(ordersDTO.getOrderId());
	    if (optionalorder.isPresent()) 
	    {
		Orders orders = new Orders();
		ProductSupplier rest = new ProductSupplier();
		Customers cust = new Customers();
		rest.setProductSupplierId(ordersDTO.getProductSupplierId());
		cust.setCustomerId(ordersDTO.getCustomerId());
		orders.setProductSupplierId(rest);
		orders.setCustomerId(cust);
		//orders.setDate(ordersDTO.getDate());
		orders.setDeliveryAddress(ordersDTO.getDeliveryAddress());
	    orders.setStatus(ordersDTO.getStatus());
		orders.setOrderId(ordersDTO.getOrderId());
//		orders.setCartId(ordersDTO.getCartId());

		ordersRepository.save(orders);
		return ordersDTO;
	}
	    throw new OrderNotFoundException();
	}

	@Override
	public boolean deleteOrders(OrdersDTO ordersDTO) {
		Orders orders = new Orders();
		orders.setOrderId(ordersDTO.getOrderId());
		ordersRepository.delete(orders);
		return true;
	}

	@Override
	public OrdersDTO getById(int id) {
		Optional<Orders> orders = ordersRepository.findById(id);
		if (orders.isPresent()) {
			OrdersDTO dto = new OrdersDTO();
			BeanUtils.copyProperties(orders.get(), dto);
			dto.setCustomerId(orders.get().getCustomerId().getCustomerId());
			dto.setProductSupplierId(orders.get().getProductSupplierId().getProductSupplierId());
			dto.setCartId(orders.get().getCartId().getCartId());
//			dto.setProductId(orders.get().getProductSupplierId().getProduct().getId());
			return dto;
		}
		throw new ItemNotAvailableException("Item not available at this time");
	}



	@Override
	public List<OrdersDTO> findAll() {
		List<Orders> orders = ordersRepository.findAll();
		List<OrdersDTO> dtos = new ArrayList<>();
		for (Orders order : orders) {
			OrdersDTO dto = new OrdersDTO();
			BeanUtils.copyProperties(order, dto);
			dto.setCustomerId(order.getCustomerId().getCustomerId());
			dto.setProductSupplierId(order.getProductSupplierId().getProductSupplierId());
			dto.setCartId(order.getCartId().getCartId());
			dtos.add(dto);
		}
		return dtos;
	}


}
