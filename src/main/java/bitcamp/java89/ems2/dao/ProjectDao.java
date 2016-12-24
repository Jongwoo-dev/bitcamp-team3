package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Project;

public interface ProjectDao {
  boolean exist(int projectNo) throws Exception;
  ArrayList<Project> getList() throws Exception;
}
