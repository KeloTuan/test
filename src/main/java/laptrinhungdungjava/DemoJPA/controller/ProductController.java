package laptrinhungdungjava.DemoJPA.controller;

import jakarta.validation.Valid;
import laptrinhungdungjava.DemoJPA.model.Product;
import laptrinhungdungjava.DemoJPA.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/create")
    public String Create(Model model) {
        model.addAttribute("product", new Product());
        return "product/create";
    }
    @PostMapping("/create")
    public String Create(@Valid Product newProduct,
                         BindingResult result,
                         @RequestParam MultipartFile imageProduct,
                         Model model)  {
        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            return "product/create";
        }
        productService.updateImage(newProduct, imageProduct);
        productService.add(newProduct);
        return "redirect:/products";
    }
    @GetMapping()
    public String Index(Model model)
    {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/edit/{id}")
    public String Edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
         if(find == null)
             throw new IllegalStateException("Product not found with ID: " + id);  //error page
         model.addAttribute("product", find);
         return "product/edit";
    }
    @PostMapping("/edit")
    public String Edit(@Valid Product editProduct,
                       BindingResult result,
                       @RequestParam MultipartFile imageProduct,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", editProduct);
            return "product/edit"; // Return to the edit form with error messages
        }
        productService.updateImage(editProduct, imageProduct);
        productService.update(editProduct);
        return "redirect:/products"; // Redirect to the products page after successful update
    }
}
