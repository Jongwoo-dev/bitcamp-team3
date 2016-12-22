package bitcamp.java89.ems2.domain;

public class Content extends Member {
  private static final long serialVersionUID = 1L;
  protected int contentsNo;
  protected String registerDate;
  protected int viewCount;
  
  public int getContentsNo() {
    return contentsNo;
  }
  public void setContentsNo(int contentsNo) {
    this.contentsNo = contentsNo;
  }
  public String getRegisterDate() {
    return registerDate;
  }
  public void setRegisterDate(String registerDate) {
    this.registerDate = registerDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

}
