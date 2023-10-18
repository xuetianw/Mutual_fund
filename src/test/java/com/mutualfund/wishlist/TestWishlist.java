package com.mutualfund.wishlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mutualfund.wishlist.entity.Customer;
import com.mutualfund.wishlist.entity.CustomerWishlist;
import com.mutualfund.wishlist.repository.CustomerRepository;
import com.mutualfund.wishlist.repository.CustomerWishlistRepository;
import com.mutualfund.wishlist.service.CustomerServiceImpl;
import com.mutualfund.wishlist.service.CustomerWishlistServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestWishlist {
	
	@InjectMocks
	CustomerWishlistServiceImpl customerWishlistService;
	@InjectMocks
	CustomerServiceImpl customerService;
	
	@Mock
	CustomerWishlistRepository customerWishlistRepository;
	@Mock
	CustomerRepository customerRepository;
	
	List<CustomerWishlist> customerWishlists = new ArrayList<CustomerWishlist>();
	Customer customer1;
	
	//Runs before every test	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);		
		customer1 = new Customer();
		customer1.setCustomerId((long) 1);
		customer1.setFname("firstname");
		customer1.setLname("lastname");
		customer1.setEmail("email@gmail.com");
		customer1.setDate_of_birth(LocalDate.of(1997, 8, 23));

		CustomerWishlist customerWishlist1 = new CustomerWishlist(1,customer1,"LIC","LIC",1);
		CustomerWishlist customerWishlist2 = new CustomerWishlist(2,customer1,"CMF","CMF",2);
		customerWishlists.add(customerWishlist1);
		customerWishlists.add(customerWishlist2);
	}
	
	@Test
	public void getCustomerWishlistByIdTest() {
		when(customerWishlistRepository.findByWishlistId(1)).thenReturn(customerWishlists.get(0));
		try {
			CustomerWishlist customerWishlist1 = customerWishlistService.getCustomerWishlist(1);
			assertEquals("LIC",customerWishlist1.getMfName());
			assertEquals("LIC",customerWishlist1.getMfFundHouse());
			assertEquals(1,customerWishlist1.getMfSchemaId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getCustomerWishlistByNonExistingIdTest() {
		when(customerWishlistRepository.findByWishlistId(3)).thenReturn(null);
		try {
			CustomerWishlist customerWishlist1 = customerWishlistService.getCustomerWishlist(3);
			assertEquals(customerWishlist1,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getCustomerWishlistByIdTestInvalidWishlistIdTest() {
		Exception ex=Assertions.assertThrows(Exception.class, ()->{
			 customerWishlistService.getCustomerWishlist(-1);
		});
		assertEquals("Wishlist Id cannot be less than 0", ex.getMessage());
	}
	
	@Test
	public void getCustomerWishlistListByCustomerIdTest() {
		when(customerWishlistRepository.findByCustomerCustomerId((long) 1)).thenReturn(customerWishlists);
		try {
			List<CustomerWishlist> customerWishlist1 = customerWishlistService.getCustomerWishlistByCustomerId((long) 1);
			assertEquals("LIC",customerWishlist1.get(0).getMfName());
			assertEquals("LIC",customerWishlist1.get(0).getMfFundHouse());
			assertEquals(1,customerWishlist1.get(0).getMfSchemaId());
			assertEquals("CMF",customerWishlist1.get(1).getMfName());
			assertEquals("CMF",customerWishlist1.get(1).getMfFundHouse());
			assertEquals(2,customerWishlist1.get(1).getMfSchemaId());			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getCustomerWishlistByNonExistingCustomerIdTest() {
		try {
			List<CustomerWishlist> customerWishlist1 = customerWishlistService.getCustomerWishlistByCustomerId(3L);
			assertEquals(customerWishlist1,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getCustomerWishlistByInvalidCustomerIdTest() {
		Exception ex=Assertions.assertThrows(Exception.class, ()->{
			 customerWishlistService.getCustomerWishlistByCustomerId((long) -1);
		});
		assertEquals("Customer Id cannot be less than 0", ex.getMessage());
	}
	
	@Test
	public void getCustomerByIdTest() {
		when(customerRepository.findByCustomerId((long) 1)).thenReturn(customer1);
		try {
			Customer customer = customerService.getCustomer((long) 1);
			assertEquals("firstname", customer.getFname());
			assertEquals("lastname", customer.getLname());
			assertEquals("email@gmail.com", customer.getEmail());
			assertEquals(LocalDate.of(1997, 8, 23), customer.getDate_of_birth());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getCustomerByNonExistingIdTest() {
		Exception ex=Assertions.assertThrows(Exception.class, ()->{
			customerService.getCustomer((long) 2);
		});
		assertEquals("Customer not found", ex.getMessage());
	}
	
	@Test
	public void getCustomerByInvalidIdTest() {
		Exception ex=Assertions.assertThrows(Exception.class, ()->{
			customerService.getCustomer((long) -2);
		});
		assertEquals("Customer Id cannot be less than 0", ex.getMessage());
	}
	
	@Test
	public void getWishlistByCustomerIdAndWishlistIdTest() {
		Optional<CustomerWishlist> customerWishlistOpt = Optional.of(customerWishlists.get(0));
		when(customerWishlistRepository.findByCustomerCustomerIdAndMfSchemaId((long) 1,1)).thenReturn(customerWishlistOpt);
		try {
			CustomerWishlist customerWishlist = customerWishlistService.getWishlistByCustomerIdAndWishlistId((long) 1, 1);
			assertEquals("LIC",customerWishlist.getMfName());
			assertEquals("LIC",customerWishlist.getMfFundHouse());
			assertEquals(1,customerWishlist.getMfSchemaId());
			assertEquals("firstname",customerWishlist.getCustomer().getFname());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getWishlistByCustomerIdAndWishlistIdWithInvalidCustomerIdTest() {
		Exception ex=Assertions.assertThrows(Exception.class, ()->{
			customerWishlistService.getWishlistByCustomerIdAndWishlistId((long) -1, 1);
		});
		assertEquals("Customer Id and MfSchema Id cannot be less than 0", ex.getMessage());
	}
	
	@Test
	public void getWishlistByCustomerIdAndWishlistIdWithInvalidMfSchemaIdTest() {
		Exception ex=Assertions.assertThrows(Exception.class, ()->{
			customerWishlistService.getWishlistByCustomerIdAndWishlistId((long) 1, -1);
		});
		assertEquals("Customer Id and MfSchema Id cannot be less than 0", ex.getMessage());
	}
	
	@Test
	public void getWishlistByCustomerIdAndWishlistIdNotExistingTest() {
		Exception ex=Assertions.assertThrows(Exception.class, ()->{
			customerWishlistService.getWishlistByCustomerIdAndWishlistId((long) 3, 3);
		});
		assertEquals("Unable to find mutualfund in wishlist", ex.getMessage());
	}
	
	@Test
	public void addMutualFundToWishlistTest() {
		CustomerWishlist customerWishlist = new CustomerWishlist(0,null,"LIC","LIC",1);
		when(customerWishlistRepository.save(customerWishlist)).thenReturn(customerWishlist);
		
		try {
			CustomerWishlist customerWishlist1 = customerWishlistService.addMutualFundToWishlist((long) 1, 1, "LIC", "LIC");
			assertEquals(customerWishlist1.getMfName(), "LIC");
			assertEquals(customerWishlist1.getMfFundHouse(), "LIC");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Test
	public void addMutualFundToWishlistInvalidSchemaIdTest() {
		Exception ex=Assertions.assertThrows(Exception.class, ()->{
			customerWishlistService.addMutualFundToWishlist((long) 0, -1, "LIC", "LIC");
		});
		assertEquals("Customer Id and MfSchema Id cannot be less than 0", ex.getMessage());
	}
	
	@Test
	public void addMutualFundToWishlistInvalidMFNameTest() {
		Exception ex=Assertions.assertThrows(Exception.class, ()->{
			customerWishlistService.addMutualFundToWishlist((long) 0, 1, "LICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLIC", "LIC");
		});
		assertEquals("mfName cannot have more than 64 charecters", ex.getMessage());
	}
	
	@Test
	public void addMutualFundToWishlistInvalidMFFundHouseTest() {
		Exception ex=Assertions.assertThrows(Exception.class, ()->{
			customerWishlistService.addMutualFundToWishlist((long) 0, 1, "LIC", "LICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLICLIC");
		});
		assertEquals("mfFundHouse cannot have more than 64 charecters", ex.getMessage());
	}	
	
}
