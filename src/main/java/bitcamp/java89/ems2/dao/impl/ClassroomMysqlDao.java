package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import bitcamp.java89.ems2.dao.ClassroomDao;
import bitcamp.java89.ems2.domain.Classroom;
import bitcamp.java89.ems2.domain.ClassroomPhoto;
import bitcamp.java89.ems2.domain.Student;
import bitcamp.java89.ems2.util.DataSource;

public class ClassroomMysqlDao implements ClassroomDao {
  DataSource ds;
  
  public void setDataSource(DataSource ds) {
    this.ds = ds;
  }

  public boolean exist(int classroomNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select count(*)"
          + " from croom left outer join croom_phot on croom.crmno=croom_phot.crmno"
          + " where croom.crmno=?"); ) {
      
      stmt.setInt(1, classroomNo);
      ResultSet rs = stmt.executeQuery();
      
      rs.next();
      int count = rs.getInt(1);
      rs.close();
      
      if (count > 0) {
        return true;
      } else {
        return false;
      }
      
    } finally {
      ds.returnConnection(con);
    }
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
  
  public Classroom getOne(int classroomNo) throws Exception {
    Connection con = ds.getConnection(); 
    ArrayList<ClassroomPhoto> croomPath = getClassroomPath(classroomNo);
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select croom.crmno, name,path"
          + " from croom"
          + " left outer join croom_phot on croom.crmno=croom_phot.crmno"
          + " where croom.crmno=?");) {

      stmt.setInt(1, classroomNo);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Classroom classroom = new Classroom();
        classroom.setClassroomNo(rs.getInt(classroomNo));
        classroom.setName(rs.getString("name"));
        classroom.setPathList(croomPath);
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
  public ArrayList<ClassroomPhoto> getClassroomPath(int classroomNo) throws Exception {
    ArrayList<ClassroomPhoto> classroomPath = new ArrayList<>();;
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            " select cpno, path" +
                " from croom_phot" +
                " left outer join croom on croom.crmno=croom_phot.crmno" +
            " where croom.crmno=?"); ){
        
        stmt.setInt(1, classroomNo);
        ResultSet rs = stmt.executeQuery();

      while (rs.next()) { // 잘 모르겠는 부분
        classroomPath.add((ClassroomPhoto) rs.getObject("classroomPhoto"));
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

  @Override
  public void insertClassroomPhoto(ClassroomPhoto classroomPhoto) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "insert into croom_phot(crmno,path) values(?,?)",
          Statement.RETURN_GENERATED_KEYS); ) {
      
      stmt.setInt(1, classroomPhoto.getClassroomNo());
      stmt.setString(2, classroomPhoto.getPath());
      stmt.executeUpdate();
      
      ResultSet keyRS = stmt.getGeneratedKeys();
      keyRS.next();
      classroomPhoto.setClassroomPhotoNo(keyRS.getInt(1));
      keyRS.close();
    } finally {
      ds.returnConnection(con);
    }
  }

  
  public void delete(int classroomNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
      PreparedStatement stmt = con.prepareStatement(
          "delete from croom where crmno=?"); ) {
      
      stmt.setInt(1, classroomNo);
      
      stmt.executeUpdate();
      
    } finally {
      ds.returnConnection(con);
    }
  }
}
