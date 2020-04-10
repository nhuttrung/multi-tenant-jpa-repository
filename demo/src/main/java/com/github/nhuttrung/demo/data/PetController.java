package com.github.nhuttrung.demo.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PetController {
  @Autowired
  private PetRepository petRepository;

  @GetMapping("/pets")
  public String getPets(Model model) {
    model.addAttribute("pets", petRepository.findAll());
    return "pets";
  }
}
