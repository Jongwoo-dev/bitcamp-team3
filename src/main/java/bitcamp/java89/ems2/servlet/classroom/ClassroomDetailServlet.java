package bitcamp.java89.ems2.servlet.classroom;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.ClassroomDao;
import bitcamp.java89.ems2.domain.Classroom;

@WebServlet("/classroom/detail")
public class ClassroomDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    try {
      int classroomNo = Integer.parseInt(request.getParameter("classroomNo"));
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
  
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>강의실-상세정보</title>");
      out.println("</head>");
      out.println("<body>");
      
      // HeaderServlet에게 머리말 HTML 생성을 요청한다.
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>강의실 정보</h1>");
      out.println("<form action='update' method='POST'>");
      
      ClassroomDao classroomDao = (ClassroomDao)this.getServletContext().getAttribute("classroomDao");
      Classroom classroom = classroomDao.getOne(classroomNo);
      
      if (classroom == null) {
        throw new Exception("해당 강의실이 없습니다.");
      }
      
      out.println("<table border='1'>");
      out.printf("<tr><th>이름</th><td>"
          + "<input name='name' type='text' value='%s'></td></tr>\n", 
          classroom.getName());
      out.printf("<tr><th>사진1</th><td><input name='photoPath1' type='file'></td></tr>");
      out.printf("<tr><th>사진2</th><td><input name='photoPath2' type='file'></td></tr>");
      out.printf("<tr><th>사진3</th><td><input name='photoPath3' type='file'></td></tr>");
      
      out.println("</table>");
      
      out.println("<button type='submit'>변경</button>");
      out.printf(" <a href='delete?classroomNo=%s'>삭제</a>\n", classroom.getClassroomNo());
      out.printf("<input type='hidden' name='memberNo' value='%d'>\n", classroom.getClassroomNo());
      
      out.println(" <a href='list'>목록</a>");
      out.println("</form>");
      
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
