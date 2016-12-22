package bitcamp.java89.ems2.servlet.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.ProjectDao;
import bitcamp.java89.ems2.domain.Project;

@WebServlet("/project/detail")
public class ProjectDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    try {
      int projectNo = Integer.parseInt(request.getParameter("projectNo"));
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
  
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>프로젝트관리-상세정보</title>");
      out.println("</head>");
      out.println("<body>");
      
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>프로젝트 정보</h1>");
      out.println("<form action='update' method='POST'>");
      
      ProjectDao projectDao = (ProjectDao)this.getServletContext().getAttribute("projectDao");
      
      //Project project = projectDao.getOne(projectNo);  // 수정해야함
      /*
      if (project == null) {
        throw new Exception("해당 프로젝트가 없습니다.");
      }
      */
      out.println("<table border='1'>");
      // 수정해야 함
      
      
      
      
      out.println(" <a href='list'>목록</a>");
      out.println("</form>");
      
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
