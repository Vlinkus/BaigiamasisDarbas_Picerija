package lt.academy.javau5.pizza.controllers;

import lt.academy.javau5.pizza.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/img")
public class PizzaImageController {
    @Autowired
    private PizzaService pizzaService;

//    @GetMapping("/{pizzaId}")
//    public @ResponseBody byte[] getImage(@PathVariable int pizzaId) throws IOException {
//        InputStream in = getClass()
//                .getResourceAsStream("/com/baeldung/produceimage/image.jpg");
//        return IOUtils.toByteArray(in);
//    }
}
