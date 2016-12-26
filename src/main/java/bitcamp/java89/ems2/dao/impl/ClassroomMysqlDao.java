package bitcamp.java89.ems2.dao.impl;

import java.util.ArrayList;

import bitcamp.java89.ems2.dao.TeacherDao;
import bitcamp.java89.ems2.domain.Teacher;
import bitcamp.java89.ems2.util.DataSource;

public class ClassroomMysqlDao implements TeacherDao {
  DataSource ds;
  
  public void setDataSource(DataSource ds) {
    this.ds = ds;
  }

  @Override
  public boolean exist(int memberNo) throws Exception {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ArrayList<Teacher> getList() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean exist(String email) throws Exception {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void insert(Teacher teacher) throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Teacher getOne(int memberNo) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void update(Teacher teacher) throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void delete(int memberNo) throws Exception {
    // TODO Auto-generated method stub
    
  }

}
