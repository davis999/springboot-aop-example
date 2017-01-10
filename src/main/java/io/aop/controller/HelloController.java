package io.aop.controller;

import io.aop.aspect.CurrencyMonitor;
import io.aop.model.UserModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Davis on 17/1/10.
 */
@RestController
public class HelloController {

  @ApiOperation("say hello")
//  @CurrencyMonitor(fields = {"name"})
  @PostMapping("/")
  public String sayHello(@RequestBody
                         @ApiParam(value = "name", required = true)
                           @CurrencyMonitor(fields = {"name"})
                             UserModel user) {
    return "hello " + user.getName() + ", your are " + user.getAge() + " years old.";
  }
}
