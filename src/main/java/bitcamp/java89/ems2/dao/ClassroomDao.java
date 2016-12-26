package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Classroom;
import bitcamp.java89.ems2.domain.ClassroomPhoto;

public interface ClassroomDao {
  boolean exist(int classroomNo) throws Exception;
  ArrayList<Classroom> getList() throws Exception;
  void insert(Classroom classroom) throws Exception;
  void insertClassroomPhoto(ClassroomPhoto classroomPhoto) throws Exception;
  void delete(int classroomNo) throws Exception;
  Classroom getOne(int classroomNo) throws Exception;
  void update(Classroom classroom) throws Exception;
  void updateClassroomPhoto(ClassroomPhoto classroomPhoto) throws Exception;
}
