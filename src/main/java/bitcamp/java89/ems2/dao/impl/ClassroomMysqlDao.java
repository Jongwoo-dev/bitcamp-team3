package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bitcamp.java89.ems2.dao.ClassroomDao;
import bitcamp.java89.ems2.domain.Classroom;
import bitcamp.java89.ems2.util.DataSource;

public class ClassroomMysqlDao implements ClassroomDao {
  DataSource ds;
  
  public void setDataSource(DataSource ds) {
    this.ds = ds;
  }


  @Override
  public ArrayList<Classroom> getList() throws Exception {
    ArrayList<Classroom> list = new ArrayList<>();
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select croom.crmno, name" +
          " from croom" + 
          " left outer join croom_phot on croom.crmno=croom_phot.crmno");
      ResultSet rs = stmt.executeQuery(); ){
      
      while (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Classroom classroom= new Classroom();
        classroom.setClassroomNo((rs.getInt("crmno")));
        classroom.setName(rs.getString("name"));
       
        list.add(classroom);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }



}
