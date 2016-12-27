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

@WebServlet("/classroom/update")
public class ClassroomUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    try {
      Map<String,String> dataMap = MultipartUtil.parse(request);
      
      Classroom classroom = new Classroom();
      classroom.setClassroomNo(Integer.parseInt(dataMap.get("classroomNo")));
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
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
  
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("<title>강의실-변경</title>");
      out.println("</head>");
      out.println("<body>");
      
      // HeaderServlet에게 머리말 HTML 생성을 요청한다.
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>변경 결과</h1>");
      ClassroomDao classroomDao = (ClassroomDao)this.getServletContext().getAttribute("classroomDao");

      if (!classroomDao.exist(classroom.getClassroomNo())) {
        throw new Exception("강의실을 찾지 못했습니다.");
      }
      
      classroomDao.update(classroom);
      
      for (ClassroomPhoto croomPhoto : classroom.getPathList()) {
        classroomDao.updateClassroomPhoto(croomPhoto);
      }
      
      out.println("<p>변경 하였습니다.</p>");
      
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
