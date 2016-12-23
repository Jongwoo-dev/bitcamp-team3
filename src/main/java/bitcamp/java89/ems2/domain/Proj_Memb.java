package bitcamp.java89.ems2.domain;

public class Proj_Memb extends Project {
  private static final long serialVersionUID = 1L;
  
  protected int memberNo;
  protected String rol;
  
  public String getRol() {
    return rol;
  }
  
  public void setRol(String rol) {
    this.rol = rol;
  }

  public int getMemberNo() {
    return memberNo;
  }

  public void setMemberNo(int memberNo) {
    this.memberNo = memberNo;
  }
  
}
