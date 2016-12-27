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

@WebServlet("/project/detail")
public class ProjectDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    
    try {
      
      int projectNo = Integer.parseInt(request.getParameter("projectNo"));
      String projectMembers = "";
      
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
            
      ProjectDao projectDao = (ProjectDao)ContextLoaderListener.applicationContext.getBean("projectDao");
      
      Project project = projectDao.getOne(projectNo);
      
      if (project == null) {
        throw new Exception("해당 프로젝트가 없습니다.");
      }
      
      out.println("<div id='container' style='width: 800px'>");
      out.println("<form action='update' method='GET'  enctype='multipart/form-data'>");
      out.println("  <div style='height: 70px'></div>");
      out.println("  <div id='title' style='height: 80px;'>");
      out.println("    <div style='float: left; margin-left: 10px;'>");
      //<!-- 로고 들어갈자리 -->
      out.printf("      <img src='../upload/%s' style='height: 80px;'>\n", project.getLogoPath());
      out.println("    </div>");
      out.println("    <div style='float: right';height: 100px;>");
      //<!-- 작성자 들어갈자리 -->
      out.println("      <span style='margin: 10px; margin-left: 20px; padding: 2px; font-size: 16px; vertical-align: middle; border-radius: 5px; font-weight: bold;'>");
      out.printf("        등록일 [ %s ]</span> <br>\n", project.getRegisterDate().split(" ")[0]);
      out.println("      <span style='margin: 10px; margin-left: 20px; padding: 2px; font-size: 16px; vertical-align: middle; border-radius: 5px; font-weight: bold;'>");
      out.printf("        조회수 %s</span>\n", project.getViewCount());
      out.println("    </div>");
      out.println("    <div style='float: left';height: 80px;>");
      //<!-- 프로젝트명 들어갈자리 -->
      out.println("      <span style='margin: 10px; margin-left: 20px; padding: 2px; font-size: 36px; vertical-align: middle; border-radius: 5px; font-weight: bold;'>");
      out.printf("        %s</span>\n", project.getTitle());
      out.println("    </div>");
      out.println("  </div>");
      out.println("  <div id='predata' style='margin-top: 30px;height: 110px; clear:both;'>");
      //<!-- 시작일 종료일 -->
      out.println("    <div id='startdate' style='margin-left: 10px; height: 30px;'>");
      //<!-- 시작일 -->
      out.println("      <span style='display: table-col; vertical-align: middle; font-size: 18px; font-weight: bold; margin: 10px;'>시작일</span>");
      out.println("      <span style='display: table-col; vertical-align: middle; font-size: 18px; font-weight: bold; margin: 10px;'>");
      out.printf("      [ %s ]</span>\n", project.getStartDate());
      out.println("    </div>");
      out.println("    <div id='enddate' style='margin-left: 10px; height: 50px;'>");
      //<!-- 종료일 -->
      out.println("      <span style='display: table-col; vertical-align: middle; font-size: 18px; font-weight: bold; margin: 10px;'>종료일</span>");
      out.println("      <span style='display: table-col; vertical-align: middle; font-size: 18px; font-weight: bold; margin: 10px;'>");
      out.printf("      [ %s ]</span>\n", project.getEndDate());
      out.println("    </div>");
      out.println("    <div id='writer' style='margin-left: 10px; height: 30px;'>");
      //<!-- 팀원 목록 -->
      out.println("      <span style='display: table-col; vertical-align: middle; font-size: 18px; font-weight: bold; margin: 10px;'>");
      out.print("      프로젝트 멤버 [ ");
      
      ArrayList<String> members = project.getProjectMemberList();
      if (members.size() != 0) {
        projectMembers += members.get(0);
        for (int i = 1; i < members.size(); i++) {
          projectMembers += ", " + members.get(i);
        }
        out.println(projectMembers);
      }
      out.println(" ]</span>");
      
      out.println("    </div>");
      out.println("  </div>");
      out.println("  <div id='content' style='height: 320px;'>");
      //<!-- 내용 -->
      out.println("    <div style='margin: 10px; background-color: #e0e0e0; height: 300px;'>");
      out.println("      <div style='width: 740px; font-size: 16px; padding: 20px;'>");
      out.println(project.getContents());
      out.println("      </div>");
      out.println("    </div>");
      out.println("  </div>");
      out.println("  <div id='postdata' style='height: 80px;'>");
      //<!-- 태그입력 등록버튼 취소버튼 -->
      out.println("    <div id='projectdate' style='float: left; margin: 10px; height: 60px; width: 320px;'>");
      //<!-- 태그입력-->
      out.println("      <span style='display: table-col; vertical-align: middle; font-size: 14px; font-weight: bold; margin: 10px;'>");
      out.println("      #태그기능은 아직 지원하지 않습니다.</span>");
      out.println("    </div>");
      out.println("    <div id='projectdate' style='float: right; margin: 10px; height: 60px; width: 420px;'>");
      //<!-- 등록버튼 취소버튼 -->
      out.printf("      <input type='button' value='삭제' onclick=\"location.href='delete?projectNo=%s'\"\n", project.getProjectNo());
      out.println("        style='height: 40px; width: 120px; border: 0px; background-color: #bebebe; font-size: 20px; font-weight: bold; color: white; margin-right: 20px; border-radius: 5px;'></input>");
      out.println("      <button type='submit' ");
      out.println("        style='height: 40px; width: 120px; border: 0px; background-color: #bebebe; font-size: 20px; font-weight: bold; color: white; margin-right: 20px; border-radius: 5px;'>수정</button>");
      out.println("      <input type='button' value='목록' onclick=\"location.href='list'\"");
      out.println("        style='height: 40px; width: 120px; border: 0px; background-color: #bebebe; font-size: 20px; font-weight: bold; color: white; border-radius: 5px;'></input>");
      out.println("    </div>");
      out.println("  </div>");
      out.printf("<input type='hidden' name='projectNo' value='%d'>\n", project.getProjectNo());
      out.printf("<input type='hidden' name='projectMember' value='%s'>\n", projectMembers);
      out.println("</form>");
      out.println("<hr>");
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