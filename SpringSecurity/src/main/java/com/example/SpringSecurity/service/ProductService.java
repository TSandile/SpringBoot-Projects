package com.example.SpringSecurity.service;

import com.example.SpringSecurity.component.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    List<Product> productList = new ArrayList<>();
    ProductService(){
        productList.add(new Product(1,"HP Laptop",300000.0,20));
        productList.add(new Product(2,"Samsung S21",100000.0,50));
        productList.add(new Product(3,"I Phone 13",200000.0,30));
    }

    public String addProduct(Product product){
        try {
            if(product.getId() == 1){
                throw new IllegalArgumentException("Duplicate product Ids are illegal");
            }else if(product.getPrice() == null){
                throw new NullPointerException("Product price cannot be null");
            }
        }catch(Exception exc){
            throw new ProdctServiceException(exc.getMessage());
        }
        productList.add(product);
        return "product successfully added";
    }

    public List<Product> getAllProducts(){
        return productList;
    }

    //get Product by Id
    public Product getProductById(int id){
        Product existingProduct = findById(id).orElseThrow(
                ()-> new ProductNotFoundException("Product Not Found!")
        );
    }

    private Optional<Product> findById(int id){
        Optional<Product> product = productList.stream().filter((p) -> p.getId() == id).findAny();
        return product;
    }
}
