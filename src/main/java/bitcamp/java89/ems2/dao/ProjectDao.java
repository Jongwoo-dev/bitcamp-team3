package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Content;
import bitcamp.java89.ems2.domain.Project;

public interface ProjectDao {
  ArrayList<Project> getList() throws Exception;
  Project getone(int projectNo) throws Exception;
  void insert(Project project) throws Exception;
  
  
  
  //아래부분은 임시로 쓰는거. 나중에 다른Dao들 구현 다 되면 삭제할 메서드들.
  void insertContent(Content content) throws Exception;
}
