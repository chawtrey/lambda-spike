package us.hawtrey.lambdaspike.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
  @GetMapping(value = "hello", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getUser() {
    return "Hello! World!";
  }
}
