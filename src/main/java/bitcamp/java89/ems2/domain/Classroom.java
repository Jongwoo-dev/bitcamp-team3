package bitcamp.java89.ems2.domain;

import java.util.List;

public class Classroom {
  
  protected int classroomNo;
  protected String name;
  protected List<Photo> photoList;
  
  
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
  public List<Photo> getPhotoList() {
    return photoList;
  }
  public void setPhotoList(List<Photo> photoList) {
    this.photoList = photoList;
  }
}
