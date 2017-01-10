package io.aop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Davis on 17/1/10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserModel {
  private String name;

  private int age;
}
