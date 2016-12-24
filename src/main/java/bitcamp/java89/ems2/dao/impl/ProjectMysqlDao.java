package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bitcamp.java89.ems2.dao.ProjectDao;
import bitcamp.java89.ems2.domain.Project;
import bitcamp.java89.ems2.util.DataSource;

public class ProjectMysqlDao implements ProjectDao {
  DataSource ds;
  
  public void setDataSource(DataSource ds) {
    this.ds = ds;
  }
  
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
          "select pjno,titl,sdt,edt,rdt,memb.name" 
          + " from proj left outer join content on proj.pjno=content.cono"
          + " left outer join memb on content.mno=memb.mno");
      ResultSet rs = stmt.executeQuery(); ){
      
      while (rs.next()) {
        Project project = new Project();
        project.setTitle(rs.getString("titl"));
        project.setStartDate(rs.getString("sdt"));
        project.setEndDate(rs.getString("edt"));
        project.setRegisterDate(rs.getString("rdt"));
        project.setName(rs.getString("name"));
        
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
            " select titl, rdt, vw_cnt, sdt, edt, name, conts"
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
        project.setTitle(rs.getString("title"));
        project.setRegisterDate(rs.getString("registerDate"));
        project.setViewCount(Integer.parseInt(rs.getString("viewCount")));
        project.setStartDate(rs.getString("startDate"));
        project.setEndDate(rs.getString("endDate"));
        project.setProjectMemberList(projMembName);
        project.setContents(rs.getString("contents"));
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
  // getone(int projectNo) 에서 사용할 ArrayList
  public ArrayList<String> getProjectMemberByProjectNumber(int projectNo) throws Exception {
    ArrayList<String> projectMember = new ArrayList<>();;
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            " select proj_memb.mno, name" +
            " from proj_memb" +
            " left outer join memb on memb.mno=proj_memb.mno" +
            " where pjno=?");
        ResultSet rs = stmt.executeQuery(); ){
      
      while (rs.next()) {
        projectMember.add(rs.getString("name"));
      }
    } finally {
      ds.returnConnection(con);
    }
    return projectMember;
  }
  
  public void delete(int projectNo) throws Exception {
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement(
          "delete from proj_memb where pjno=?; delete from proj where pjno=?"); )
    {
      stmt.setInt(1, projectNo);
      stmt.setInt(2, projectNo);
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }

}