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
      out.println("<button><a href='form.html'>프로젝트생성</a></button>");
      
      ProjectDao projectDao = (ProjectDao)this.getServletContext().getAttribute("projectDao");
      ArrayList<Project> list = projectDao.getList();
// test용 코딩...
//      ArrayList<Project> list = null;
//      Project p = new Project();
//      p.setProjectNo(1); p.setTitle("t1"); p.setStartDate("2016-11-20"); p.setEndDate("2016-12-31"); p.setContents("c1");
//      list.add(p);
      
      for (Project project : list) {
        out.printf("<h1><a href='detail?projectNo='>%s</a></h1>\n", project.getProjectNo(), project.getTitle());
        //out.printf("<h5>%s</h5>\n", ); //등록일 필요 -> proj_memb 상속 필요
        //out.printf("<h5>%s</h5>\n", ); //팀장이름 필요 -> memb 상속 필요
        out.printf("<h3>시작일 [%s]</h3>\n", project.getStartDate());
        out.printf("<h3>종료일 [%s]</h3>\n", project.getEndDate());
        out.printf("<p style='color:gray;'>%s</p>\n", project.getContents());
        out.println("태그 내용 출력");
      }

      /*
       * 기존 표 출력
      out.println("<table border='1'>");
      out.println("<tr>");
      out.println("  <th>프로젝트명</th>");
      out.println("  <th>시작일</th>");
      out.println("  <th>종료일</th>");
      out.println("  <th>일련번호</th>");
      out.println("</tr>");
      
      for (Project project : list) {
        out.println("<tr> ");
        out.printf("<td><a href='detail?projectNo=%1$s'>%s</a></td><td>%s</td><td>%s</td><td>%s</td>\n",
          project.getTitle(), project.getStartDate(), project.getEndDate(), project.getProjectNo());
        out.println("</tr>");
      }
      
      out.println("</table>");
      */
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
