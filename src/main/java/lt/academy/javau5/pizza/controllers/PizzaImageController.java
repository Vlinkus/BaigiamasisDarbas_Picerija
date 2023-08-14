package lt.academy.javau5.pizza.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/api/pizza/image")
public class PizzaImageController {

//    @GetMapping(value = "/{name:.+}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
//    public ResponseEntity<byte[]> getImage(@PathVariable String name) throws IOException {
//        // Load the image from the classpath
//        Resource resource = new ClassPathResource("images/" + name);
//
//        if (!resource.exists()) {
//            // Handle image not found case
//            return ResponseEntity.notFound().build();
//        }
//
//        // Read the image bytes
//        try (InputStream inputStream = resource.getInputStream()) {
//            byte[] imageBytes = inputStream.readAllBytes();
//            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
//        }
//    }

}
