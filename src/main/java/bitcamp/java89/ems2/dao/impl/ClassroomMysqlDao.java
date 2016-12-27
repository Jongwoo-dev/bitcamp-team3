package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bitcamp.java89.ems2.dao.ClassroomDao;
import bitcamp.java89.ems2.domain.Classroom;
import bitcamp.java89.ems2.domain.Photo;
import bitcamp.java89.ems2.util.DataSource;

@Repository("classroomDao")
public class ClassroomMysqlDao implements ClassroomDao {
  @Autowired DataSource ds;

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
          "select crmno, name from croom");
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
    ArrayList<Photo> photoList = getClassroomPath(classroomNo);
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select name"
          + " from croom"
          + " where crmno=?");) {

      stmt.setInt(1, classroomNo);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Classroom classroom = new Classroom();
        classroom.setClassroomNo(classroomNo);
        classroom.setName(rs.getString("name"));
        classroom.setPhotoList(photoList);
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
  public ArrayList<Photo> getClassroomPath(int classroomNo) throws Exception {
    ArrayList<Photo> classroomPathList = new ArrayList<>();;
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            " select cpno, path" +
                " from croom_phot" +
                " where crmno=?"); ){
        
        stmt.setInt(1, classroomNo);
        ResultSet rs = stmt.executeQuery();

      while (rs.next()) { 
        Photo croomPhoto = new Photo();
        croomPhoto.setOwnerNo(classroomNo);
        croomPhoto.setNo(rs.getInt("cpno"));
        croomPhoto.setFilePath(rs.getString("path"));
        classroomPathList.add(croomPhoto);
      }
    } finally {
      ds.returnConnection(con);
    }
    return classroomPathList;
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
      
      this.insertPhotoList(classroom);
    } finally {
      ds.returnConnection(con);
    }
  }

  @Override
  public void insertPhotoList(Classroom classroom) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "insert into croom_phot(crmno,path) values(?,?)",
          Statement.RETURN_GENERATED_KEYS); ) {
      
      List<Photo> photoList = classroom.getPhotoList();
      
      for (Photo photo : photoList) {
        if (photo.getFilePath() == null) {
          continue;
        }
        stmt.setInt(1, classroom.getClassroomNo());
        stmt.setString(2, photo.getFilePath());
        stmt.executeUpdate();
        ResultSet keyRS = stmt.getGeneratedKeys();
        keyRS.next();
        photo.setOwnerNo(keyRS.getInt(1));
        keyRS.close();
      }
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

  @Override

  public void deletePhoto(int classroomNo) throws Exception {
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement(
          "delete from croom_phot where crmno=?"); )
    {
      stmt.setInt(1, classroomNo);
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }

  public void update(Classroom classroom) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "update croom set"
          + " name=?"
          + " where crmno=?");) {
      
      stmt.setString(1, classroom.getName());
      stmt.setInt(2, classroom.getClassroomNo());
      stmt.executeUpdate();
      
      deletePhoto(classroom.getClassroomNo());
      insertPhotoList(classroom);
      
      this.updatePhotoList(classroom);
    } finally {
      ds.returnConnection(con);
    }
  }

  @Override
  public void updatePhotoList(Classroom classroom) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "update croom_phot set"
          + " path=?"
          + " where cpno=?");) {
      
      List<Photo> photoList = classroom.getPhotoList();
      
      for (Photo photo : photoList) {
        if (photo.getFilePath() == null) {
          continue;
        }
        stmt.setString(1, photo.getFilePath());
        stmt.setInt(2, (photo.getNo()));
        stmt.executeUpdate();
      }
      
    } finally {
      ds.returnConnection(con);
    }
    
  }
}
