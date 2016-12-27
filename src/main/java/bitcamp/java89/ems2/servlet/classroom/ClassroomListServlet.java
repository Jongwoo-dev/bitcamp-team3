package bitcamp.java89.ems2.servlet.classroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.ClassroomDao;
import bitcamp.java89.ems2.domain.Classroom;
import bitcamp.java89.ems2.listener.ContextLoaderListener;

@WebServlet("/classroom/list")
public class ClassroomListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    try {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
  
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>강의실-목록</title>");
      out.println("</head>");
      out.println("<body>");
      
      // HeaderServlet에게 머리말 HTML 생성을 요청한다.
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>강의실 정보</h1>");

      ClassroomDao classroomDao = (ClassroomDao)ContextLoaderListener.applicationContext.getBean("classroomDao");
      ArrayList<Classroom> list = classroomDao.getList();

      out.println("<a href='form.html'>추가</a><br>");
      out.println("<table border='1'>");
      out.println("<tr>");
      out.println("  <th>강의실번호</th>");
      out.println("  <th>강의실이름</th>");
      out.println("</tr>");
      
      for (Classroom classroom : list) {
        out.println("<tr> ");
        out.printf("  <td>%d</td>"
            + "<td><a href='detail?classroomNo=%1$d'>%s</a></td>\n",
          classroom.getClassroomNo(),
          classroom.getName());
        out.println("</tr>");
      }
      
      out.println("</table>");
      
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
