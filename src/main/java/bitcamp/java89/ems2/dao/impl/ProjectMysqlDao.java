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
  
  public ArrayList<Project> getList() throws Exception {
    ArrayList<Project> list = new ArrayList<>();
    Connection con = ds.getConnection();
    
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select pjno,titl,conts,sdt,edt,memb.name" 
          + " from proj left outer join content on proj.pjno=content.cono"
          + " left outer join memb on content.mno=memb.mno");
      ResultSet rs = stmt.executeQuery(); ){
      
      while (rs.next()) {
        Project project = new Project();
        project.setTitle(rs.getString("titl"));
        project.setStartDate(rs.getString("sdt"));
        project.setEndDate(rs.getString("edt"));
        project.setContents(rs.getString("conts"));
        //project.setProjectNo(Integer.parseInt(rs.getString("pjno"))); //회원이름으로 변경 필요
        
        list.add(project);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }
  
  public Project getone(int projectNo) throws Exception {
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select");) { // 수정해야 함
      
        stmt.setInt(1, projectNo);
        ResultSet rs = stmt.executeQuery();
        
        // 프로젝트이름,등록일,조회수,시작일,종료일,팀원,(역할),내용,태그
        if (rs.next()) { 
          Project project = new Project();
          project.setProjectNo(projectNo);
          project.setRegisterDate(rs.getString("registerDate"));
          project.setViewCount(Integer.parseInt(rs.getString("viewCount")));
          project.setStartDate(rs.getString("startDate"));
          project.setEndDate(rs.getString("endDate"));
          project.setContents(rs.getString("contents"));
          rs.close();
          
          /*
          arraylist<string> projMembsName = getProjectMember(pjno)
          poject.setmembernamelist(projMembsName)
          
          ArrayList<String> members = project.getMemberNameList();
          for(String name : members) {
            System.out.println(name);
          }
          return project;
          */
        } else {
          rs.close();
          return null;
        }
      } finally {
        ds.returnConnection(con);
      }
  }
  
  public void delete(int projectNo) throws Exception {
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement("delete from proj where pjno=?"); )
    {
      stmt.setInt(1, projectNo);
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  
  
}