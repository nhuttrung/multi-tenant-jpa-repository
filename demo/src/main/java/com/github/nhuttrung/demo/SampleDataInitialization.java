package com.github.nhuttrung.demo;

import com.github.nhuttrung.demo.data.Pet;
import com.github.nhuttrung.demo.data.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleDataInitialization implements CommandLineRunner {
  @Autowired
  private PetRepository petRepository;

  @Override
  public void run(String... args) {
    // Init sample Pets
    for (int i=0; i<20; i++) {
      petRepository.save(new Pet("Cat " + (i+1), (i % 2) + 1));
    }
  }
}
