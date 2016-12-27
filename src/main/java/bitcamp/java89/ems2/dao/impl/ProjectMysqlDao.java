package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bitcamp.java89.ems2.dao.ProjectDao;
import bitcamp.java89.ems2.domain.Content;
import bitcamp.java89.ems2.domain.Project;
import bitcamp.java89.ems2.util.DataSource;

@Repository("projectDao")
public class ProjectMysqlDao implements ProjectDao {
  @Autowired DataSource ds;
  
  public boolean exist(int projectNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select count(*) from proj left outer join content on proj.pjno=content.cono where pjno=?"); ) {
      
      stmt.setInt(1, projectNo);
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
  
  public ArrayList<Project> getList() throws Exception {
    ArrayList<Project> list = new ArrayList<>();
    Connection con = ds.getConnection();
    
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select pjno,titl,sdt,edt,rdt,memb.name,path" 
          + " from proj left outer join content on proj.pjno=content.cono"
          + " left outer join memb on content.mno=memb.mno");
      ResultSet rs = stmt.executeQuery(); ){
      
      while (rs.next()) {
        Project project = new Project();
        project.setContentNo(rs.getInt("pjno"));
        project.setProjectNo(rs.getInt("pjno"));
        project.setTitle(rs.getString("titl"));
        project.setStartDate(rs.getString("sdt"));
        project.setEndDate(rs.getString("edt"));
        project.setRegisterDate(rs.getString("rdt"));
        project.setName(rs.getString("name"));
        project.setLogoPath(rs.getString("path"));
        
        list.add(project);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }

  public Project getOne(int projectNo) throws Exception {
    Connection con = ds.getConnection();
    ArrayList<String> projMembName = getProjectMemberByProjectNumber(projectNo);
    try (
        PreparedStatement stmt = con.prepareStatement(
            " select titl, rdt, vw_cnt, sdt, edt, name, conts,path"
                + " from proj"
                + " left outer join content on proj.pjno=content.cono"
                + " left outer join memb on content.mno=memb.mno"
                + " where pjno=?");) {

      stmt.setInt(1, projectNo);
      ResultSet rs = stmt.executeQuery();

      // 프로젝트이름,등록일,조회수,시작일,종료일,프로젝트멤버,(역할),내용,(태그)
      if (rs.next()) { 
        Project project = new Project();
        project.setProjectNo(projectNo);
        project.setTitle(rs.getString("titl"));
        project.setRegisterDate(rs.getString("rdt"));
        project.setViewCount(Integer.parseInt(rs.getString("vw_cnt")));
        project.setStartDate(rs.getString("sdt"));
        project.setEndDate(rs.getString("edt"));
        project.setProjectMemberList(projMembName);
        project.setContents(rs.getString("conts"));
        project.setLogoPath(rs.getString("path"));
        rs.close();
        return project;

      } else {
        rs.close();
        return null;
      }
    } finally {
      ds.returnConnection(con);
    }
  }



  //getOne(int projectNo) 에서 사용할 ArrayList
  public ArrayList<String> getProjectMemberByProjectNumber(int projectNo) throws Exception {
    ArrayList<String> projectMember = new ArrayList<>();;
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            " select proj_memb.mno, name" +
                " from proj_memb" +
                " left outer join memb on memb.mno=proj_memb.mno" +
            " where pjno=?"); ){
        
        stmt.setInt(1, projectNo);
        ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        projectMember.add(rs.getString("name"));
      }
    } finally {
      ds.returnConnection(con);
    }
    return projectMember;
  }

  
  public void insert(Project project) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "insert into proj(pjno,titl,conts,sdt,edt,path) values(?,?,?,?,?,?)"); ) {
      
      stmt.setInt(1, project.getContentNo());
      stmt.setString(2, project.getTitle());
      stmt.setString(3, project.getContents());
      stmt.setString(4, project.getStartDate());
      stmt.setString(5, project.getEndDate());
      stmt.setString(6, project.getLogoPath());
      stmt.executeUpdate();

    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void insertContent(Content content) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "insert into content(mno,rdt,vw_cnt) values(?,now(),0)",
          Statement.RETURN_GENERATED_KEYS); ) {
      
      stmt.setInt(1, content.getMemberNo());
      stmt.executeUpdate();
      
      ResultSet keyRS = stmt.getGeneratedKeys();
      keyRS.next();
      content.setContentNo(keyRS.getInt(1));
      keyRS.close();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  
  public void delete(int projectNo) throws Exception {
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement(
          "delete from proj where pjno=?"); )
    {
      stmt.setInt(1, projectNo);
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void deleteContent(int contentNo) throws Exception {
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement(
          "delete from content where cono=?"); )
    {
      stmt.setInt(1, contentNo);
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void update(Project project) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "update proj set"
          + " titl=?, conts=?, sdt=?, edt=?, path=?"
          + " where pjno=?"); ) {
      
      stmt.setString(1, project.getTitle());
      stmt.setString(2, project.getContents());
      stmt.setString(3, project.getStartDate());
      stmt.setString(4, project.getEndDate());
      stmt.setString(5, project.getLogoPath());
      stmt.setInt(6, project.getContentNo());
      
      stmt.executeUpdate();
      
    } finally {
      ds.returnConnection(con);
    }
  }

}