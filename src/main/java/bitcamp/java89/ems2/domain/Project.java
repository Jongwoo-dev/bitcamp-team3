package bitcamp.java89.ems2.domain;

import java.util.ArrayList;

public class Project extends Content {
  private static final long serialVersionUID = 1L;
  
  protected int projectNo;
  protected String title;
  protected String contents;
  protected String startDate;
  protected String endDate;
  protected String logoPath;
  
  public String getLogoPath() {
    return logoPath;
  }
  public void setLogoPath(String logoPath) {
    this.logoPath = logoPath;
  }
  protected ArrayList<String> projectMemberList;
  
  public int getProjectNo() {
    return projectNo;
  }
  public void setProjectNo(int projectNo) {
    this.projectNo = projectNo;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getContents() {
    return contents;
  }
  public void setContents(String contents) {
    this.contents = contents;
  }
  public String getStartDate() {
    return startDate;
  }
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
  public String getEndDate() {
    return endDate;
  }
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
  public ArrayList<String> getProjectMemberList() {
    return projectMemberList;
  }
  public void setProjectMemberList(ArrayList<String> projectMemberList) {
    this.projectMemberList = projectMemberList;
  }
  
}
