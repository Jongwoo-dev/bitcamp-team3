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

@WebServlet("/project/list")
public class ProjectListServlet extends HttpServlet {
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
      out.println("<title>프로젝트관리-목록</title>");
      out.println("</head>");
      out.println("<body>");
      
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<button style='margin-top:5px; float:right;'><a href='form.html'>프로젝트생성</a></button>");
      
      ProjectDao projectDao = (ProjectDao)this.getServletContext().getAttribute("projectDao");
      ArrayList<Project> list = projectDao.getList();
      
      for (Project project : list) {
        out.println("<div style='background-color:#F5F5F5; margin-top:40px;'>");
        out.printf("<h2><a href='detail?projectTitle='>%s</a></h2>", project.getTitle());
        out.printf("<h4 style='float:right;'>등록일 [%s]</h4>\n", project.getRegisterDate());
        out.printf("<h4 style='float:right;'>작성자 [%s]</h4>\n", project.getName());
        out.printf("<h4 style='margin-left:50px;'>시작일 [%s]</h4>\n", project.getStartDate());
        out.printf("<h4 style='margin-left:50px;'>종료일 [%s]</h4>\n", project.getEndDate());
        out.println("태그 내용 출력");
        out.println("</div>");
        out.println("<hr>");
      }
      
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
