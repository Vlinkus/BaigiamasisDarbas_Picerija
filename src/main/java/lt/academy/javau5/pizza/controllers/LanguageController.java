package lt.academy.javau5.pizza.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageController {

	@GetMapping("/api/languages")
    public ResponseEntity<List<String>> getSupportedLanguages() {
        List<String> languages = Arrays.asList("en", "lt"); 
        return ResponseEntity.ok(languages);
    }
	
	@PostMapping("/change-language")
	public ResponseEntity<String> changeLanguage(@RequestParam String language) {
	       return ResponseEntity.ok("Kalba pakeista į " + language);
	}
}
