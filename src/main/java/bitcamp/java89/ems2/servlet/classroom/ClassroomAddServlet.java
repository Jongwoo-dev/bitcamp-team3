package bitcamp.java89.ems2.servlet.classroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.ClassroomDao;
import bitcamp.java89.ems2.domain.Classroom;
import bitcamp.java89.ems2.domain.ClassroomPhoto;
import bitcamp.java89.ems2.util.MultipartUtil;

@WebServlet("/classroom/add")
public class ClassroomAddServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    try {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      
      Map<String,String> dataMap = MultipartUtil.parse(request);
      
      Classroom classroom = new Classroom();
      classroom.setName(dataMap.get("name"));
      
      ArrayList<ClassroomPhoto> photoPathList = new ArrayList<>();
      
      ClassroomPhoto crPhoto;
      
      crPhoto = new ClassroomPhoto();
      crPhoto.setPath(dataMap.get("photoPath1"));
      photoPathList.add(crPhoto);
      
      crPhoto = new ClassroomPhoto();
      crPhoto.setPath(dataMap.get("photoPath2"));
      photoPathList.add(crPhoto);
      
      crPhoto = new ClassroomPhoto();
      crPhoto.setPath(dataMap.get("photoPath3"));
      photoPathList.add(crPhoto);
      
      classroom.setPathList(photoPathList);
      
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("<title>강의실관리-등록</title>");
      out.println("</head>");
      out.println("<body>");
      
      // HeaderServlet에게 머리말 HTML 생성을 요청한다.
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>등록 결과.</h1>");

      ClassroomDao classroomDao = (ClassroomDao)this.getServletContext().getAttribute("classroomDao");
      
      classroomDao.insert(classroom);
      classroom.getPathList().get(0).setClassroomNo(classroom.getClassroomNo());
      classroomDao.insertClassroomPhoto(classroom.getPathList().get(0));
      classroom.getPathList().get(1).setClassroomNo(classroom.getClassroomNo());
      classroomDao.insertClassroomPhoto(classroom.getPathList().get(1));
      classroom.getPathList().get(2).setClassroomNo(classroom.getClassroomNo());
      classroomDao.insertClassroomPhoto(classroom.getPathList().get(2));

      // FooterServlet에게 꼬리말 HTML 생성을 요청한다.
      rd = request.getRequestDispatcher("/footer");
      rd.include(request, response);
      
      out.println("</body>");
      out.println("</html>");
      
    } catch (Exception e) {
      request.setAttribute("error", e);
      
      RequestDispatcher rd = request.getRequestDispatcher("/error");
      rd.forward(request, response);
      return;
    }
    
  }
}
