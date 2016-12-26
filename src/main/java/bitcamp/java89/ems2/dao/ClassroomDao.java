package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Classroom;
import bitcamp.java89.ems2.domain.Teacher;

public interface ClassroomDao {
  boolean exist(int classroomNo) throws Exception;
  ArrayList<Classroom> getList() throws Exception;
  void insert(Classroom classroom) throws Exception;
  Teacher getOne(int classroomNo) throws Exception;
  void update(Classroom classroom) throws Exception;
  void delete(int classroomNo) throws Exception;
}
