package com.b07.domain;

import java.io.Serializable;

public interface PrimaryKey extends Serializable {
  // Interface Segregation - and other interfaces that extends

  int getId();

  void setId(int id);
}
