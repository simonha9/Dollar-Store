package com.b07.domain;

public abstract class PrimaryKeyObject implements PrimaryKey {

  /**
   *
   */
  private static final long serialVersionUID = 9123775294451761508L;
  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PrimaryKeyObject other = (PrimaryKeyObject) obj;
    return id == other.id;
  }

}
