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
import bitcamp.java89.ems2.listener.ContextLoaderListener;

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
      
      out.println("<div id='container' style='width: 800px'>");
      out.println("<div style='margin-top: 30px;float: right;'>");
      //<!-- 프로젝트 생성 버튼 -->
      out.println("<input type='button' value='프로젝트 생성' onclick=\"location.href='form.html'\"");
      out.println("          style='height: 50px; width: 200px; border: 0px; background-color: #bebebe; font-size: 20px; font-weight: bold; color: white; border-radius: 5px;'></input>");
      out.println("</div>");
      out.println("<div style='height: 30px;clear:both;'>");
      out.println("</div>");
      out.println("<hr>");
      
      ProjectDao projectDao = (ProjectDao)ContextLoaderListener.applicationContext.getBean("projectDao");
      ArrayList<Project> list = projectDao.getList();
      
      for (Project project : list) {
        
        out.println("<div style='background-color:#F5F5F5; padding: 20px; width: 700px; margin: 30px'>");
        //<!-- 프로젝트 -->
        //<!-- 프로젝트 로고 -->
        out.println("<div  style='width:60px; float:left;'>");
        out.printf("<img src='%s' height='36px' style='background-color:blue; position:relative; margin-left:10px;'>", "../upload/"+project.getLogoPath());
        out.println("</div>");
        out.println("<div style='float:left; position:relative; padding-left:10px; font-size: 36px; font-weight: bold; width: 620px; height: 80px;'>");
        //<!-- 프로젝트 제목 -->
        out.printf("<a href='detail?projectNo=%d'>%s</a>\n", project.getProjectNo(), project.getTitle());
        out.println("</div>");
        out.println("<div style='float: right; font-size: 16px; font-weight: bold; width: 170px; height: 80px;'>");
        //<!-- 등록일 글쓴이 -->
        out.printf("등록일 [%s]<br>\n", project.getRegisterDate().split(" ")[0]);
        out.printf("글쓴이 [%s]\n", project.getName());
        out.println("    </div>");
        out.println("<div style='margin-left: 40px; font-size: 18px; font-weight: bold;'>");
        //<!-- 시작일 종료일 -->
        out.printf("시작일 [%s]<br>", project.getStartDate());
        out.printf("종료일 [%s]", project.getEndDate());
        out.println("    </div>");
        out.println("<div style='font-size: 14px; font-weight: bold; margin-top: 10px;'>");
        //<!-- 태그 -->
        out.println("#태그 미지원");
        out.println("</div>");
        out.println("</div>");
        out.println("<hr>");
      }
      out.println("</div>");
      
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
