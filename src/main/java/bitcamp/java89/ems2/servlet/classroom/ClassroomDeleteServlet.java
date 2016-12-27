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
import bitcamp.java89.ems2.listener.ContextLoaderListener;

@WebServlet("/classroom/delete")
public class ClassroomDeleteServlet extends HttpServlet {
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
      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("<title>강의실-삭제</title>");
      out.println("</head>");
      out.println("<body>");
      
      // HeaderServlet에게 머리말 HTML 생성을 요청한다.
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>삭제 결과</h1>");

      ClassroomDao classroomDao = (ClassroomDao)ContextLoaderListener.applicationContext.getBean("classroomDao");

      if (!classroomDao.exist(classroomNo)) {
        throw new Exception("강의실을 찾지 못했습니다.");
      }
      //임시사용
      classroomDao.deletePhoto(classroomNo);
      
      classroomDao.delete(classroomNo);
      
//      ClassroomPhotoDao classroomPhotoDao = (ClassroomPhotoDao)this.getServletContext().getAttribute("classroomPhotoDao");
//      while (classroomPhotoDao.exist(classroomNo)) {
//        classroomPhotoDao.delete(classroomNo);
//      }
      
      out.println("<p>삭제하였습니다.</p>");
      
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
