package lt.academy.javau5.pizza.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lt.academy.javau5.pizza._security.entities.Role;
import lt.academy.javau5.pizza._security.repositories.TokenRepository;
import lt.academy.javau5.pizza._security.services.JwtService;
import lt.academy.javau5.pizza._security.services.UserService;
import lt.academy.javau5.pizza.entities.Product;
import lt.academy.javau5.pizza.repositories.ProductRepository;
import lt.academy.javau5.pizza.services.ProductService;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	@MockBean
	private ProductRepository productRepository;
	
	@MockBean
	private TokenRepository tokenRepository;
	
	@MockBean
	private JwtService jwtService;

	@MockBean
	private UserService userService;
	
	@Test
	@WithMockUser(username = "manageris", roles = {"MANAGER"})
	void testGetsListOfProductsFromEndPoint() throws Exception {
		// Mock the data returned by ProductRepository.findAll()
		// Product(Integer id, String productName, Double productPrice, List<Pizza> pizzas)
		Product p1 = new Product(1, "Tomato", 2.0, null);
		Product p2 = new Product(2, "Pickle", 2.0, null);
		List<Product> productList = Arrays.asList(p1,p2);
		when(productService.findAll()).thenReturn(productList);
		// Perform the GET request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value("Tomato"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productPrice").value(2.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productName").value("Pickle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productPrice").value(2.0));			
	}
	
	@Test
	@WithMockUser(username = "manageris", roles = {"MANAGER"})
	void testGetsProductByIdFromEndPoint() throws Exception {
		// Mock the data returned by ProductRepository.findAll()
		// Product(Integer id, String productName, Double productPrice, List<Pizza> pizzas)
		Product p = new Product(1, "Tomato", 2.0, null);
		when(productService.findById(1)).thenReturn(p);
		// Perform the GET request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/{1}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Tomato"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").value(2.0));		
	}
	
	@Test
	@WithMockUser(username = "manageris", roles = "MANAGER")
    void testAddProduct_Success() throws Exception {
        // Arrange
        Product product = new Product("Tomato", 2.0);
        // Mock the ProductService save method
        when(productService.save(any(Product.class))).thenReturn(product);
//        String productJson = new ObjectMapper().writeValueAsString(product);
        String productJson = "{\"productName\":\""+product.getProductName()+"\",\"productPrice\":"+product.getProductPrice()+"}";
        System.out.println("Created json"+productJson);
        when(productRepository.findProductByProductName(product.getProductName())).thenReturn(null);
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
        		.post("/api/product")
        		.content(productJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))      
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Product added successfully."));
        // Verify that the ProductService save method was called
//        verify(productService).save(any(Product.class));
    }

//    @Test
//    @WithMockUser(username = "manageris", roles = {"MANAGER"})
//    void testAddProduct_Conflict() throws Exception {
//        // Arrange
//        Product product = new Product(1, "Tomato", 2.0, null);
//        // Mock the ProductService save method to throw ProductAlreadyExistException
//        when(productService.save(any(Product.class))).thenThrow(ProductAlreadyExistException.class);
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"id\": 1, \"productName\": \"Tomato\", \"productPrice\": 2.0}"))
//            .andExpect(MockMvcResultMatchers.status().isConflict())
//            .andExpect(MockMvcResultMatchers.content().string("Product already exists."));
//
//        // Verify that the ProductService save method was called
//        verify(productService).save(any(Product.class));
//    }
//
//    @Test
//    @WithMockUser(username = "manageris", roles = {"MANAGER"})
//    void testAddProduct_InternalServerError() throws Exception {
//        // Arrange
//        Product product = new Product(1, "Tomato", 2.0, null);
//        // Mock the ProductService save method to throw an exception
//        when(productService.save(any(Product.class))).thenThrow(RuntimeException.class);
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"id\": 1, \"productName\": \"Tomato\", \"productPrice\": 2.0}"))
//            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
//            .andExpect(MockMvcResultMatchers.content().string("An error occurred while processing the request."));
//
//        // Verify that the ProductService save method was called
//        verify(productService).save(any(Product.class));
//    }
//	
	
	@Test
	@WithMockUser(username = "manageris", roles = "MANAGER")
    void testDeleteProduct() throws Exception {
        // Arrange
		int id = 1;
		Product prod = new Product(id, "Tomato", 2.0, null);
		when(productService.findById(id)).thenReturn(prod);     
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/{id}",1) )               
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Product Deleted Succesfully"));
//         Verify that the ProductService delete method was called
        verify(productService).delete(any());
    }
	@Test
	@WithMockUser(username = "manageris", roles = "MANAGER")
	void testDeleteProduct_Success() throws Exception {
	    // Mock the ProductService delete method
	    when(productService.delete(anyInt())).thenReturn("Product Deleted Successfully");  
	    // Act & Assert
	    mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/{productId}", 1)) // Replace 1 with the actual ID
	        .andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.content().string("Product Deleted Successfully"));
	    // Verify that the ProductService delete method was called
	    verify(productService).delete(anyInt());
	}
	
}
