package bitcamp.java89.ems2.domain;

import java.util.ArrayList;

public class Classroom {
  
  protected String name;
  protected ArrayList<String> pathList;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public ArrayList<String> getPathList() {
    return pathList;
  }
  public void setPathList(ArrayList<String> pathList) {
    this.pathList = pathList;
  }
}
