package bitcamp.java89.ems2.domain;

import java.util.ArrayList;

public class Classroom {
  
  protected int classroomNo;
  protected String name;
  protected ArrayList<String> pathList;
  
  
  public int getClassroomNo() {
    return classroomNo;
  }
  public void setClassroomNo(int classroomNo) {
    this.classroomNo = classroomNo;
  }
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
