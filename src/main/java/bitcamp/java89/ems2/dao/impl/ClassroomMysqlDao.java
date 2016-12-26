package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import bitcamp.java89.ems2.dao.ClassroomDao;
import bitcamp.java89.ems2.domain.Classroom;
import bitcamp.java89.ems2.domain.Student;
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
  
  public Student getOne(int classroomNO) throws Exception {
    Connection con = ds.getConnection(); 
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select croom.crmno, name"
          + " from croom"
          + " left outer join croom_phot on croom.crmno=croom_phot.crmno"
          + " where crmno=?");) {

      stmt.setInt(1, classroomNO);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Classroom classroom = new Classroom();
        classroom.setClassroomNo(rs.getInt(classroomNO));
        classroom.setName(rs.getString("name"));
        classroom.setPathList(pathList);
        rs.close();
        return classroom;
        
      } else {
        rs.close();
        return null;
      }
    } finally {
      ds.returnConnection(con);
    }
  } 
  public ArrayList<String> getClassroomPath(int classroomNo) throws Exception {
    ArrayList<String> classroomPath = new ArrayList<>();;
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            " select proj_memb.mno, name" +
                " from proj_memb" +
                " left outer join memb on memb.mno=proj_memb.mno" +
            " where pjno=?"); ){
        
        stmt.setInt(1, classroomNo);
        ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        classroomPath.add(rs.getString("name"));
      }
    } finally {
      ds.returnConnection(con);
    }
    return classroomPath;
  }
  

  @Override
  public void insert(Classroom classroom) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "insert into croom(name) values(?)",
          Statement.RETURN_GENERATED_KEYS); ) {
      
      stmt.setString(1, classroom.getName());
      stmt.executeUpdate();
      
      ResultSet keyRS = stmt.getGeneratedKeys();
      keyRS.next();
      classroom.setClassroomNo(keyRS.getInt(1));
      keyRS.close();

    } finally {
      ds.returnConnection(con);
    }
  }
}
