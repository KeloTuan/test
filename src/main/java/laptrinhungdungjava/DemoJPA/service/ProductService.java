package laptrinhungdungjava.DemoJPA.service;

import laptrinhungdungjava.DemoJPA.model.Product;
import laptrinhungdungjava.DemoJPA.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getAll() {
        return productRepository.findAll();
    }
    public Product get(int id) {

        return productRepository.findById(id).orElse(null);
    }
    public void add(Product newProduct) {
        productRepository.save(newProduct);
    }
    public void updateImage(Product newProduct, MultipartFile imageProduct)
    {
        if (!imageProduct.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/images");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setImage(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    public void update(Product editProduct)
    {
         Product find = get(editProduct.getId());
         if(find!= null) {
           find.setImage(editProduct.getImage());
           find.setPrice(editProduct.getPrice());
           find.setName(editProduct.getName());
           productRepository.saveAndFlush(find);
        }
    }
}
