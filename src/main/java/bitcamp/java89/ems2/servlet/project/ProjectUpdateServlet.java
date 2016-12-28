package bitcamp.java89.ems2.servlet.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.ProjectDao;
import bitcamp.java89.ems2.domain.Project;
import bitcamp.java89.ems2.listener.ContextLoaderListener;
import bitcamp.java89.ems2.util.MultipartUtil;

@WebServlet("/project/update")
public class ProjectUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    // doGet 메서드에는 업데이트 입력 폼 생성
    try {
      
      int projectNo = Integer.parseInt(request.getParameter("projectNo"));
      String projectMembers = request.getParameter("projectMember");
      
      ProjectDao projectDao = (ProjectDao)ContextLoaderListener.applicationContext.getBean("projectDao");
      Project project = projectDao.getOne(projectNo);

      String title = project.getTitle();
      String startDate = project.getStartDate();
      String endDate = project.getEndDate();
      String contents = project.getContents();
      String logoPath = project.getLogoPath();
      
      // 어디서 넘어왔는지.
      String referer = request.getHeader("Referer");
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
  
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>프로젝트관리-변경</title>");
      out.println("</head>");
      out.println("<body>");
      
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      // * 여기서부터 html 코드?
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>프로젝트관리-입력폼</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("  <div id='container' style='width: 800px'>");
      out.println("    <form action='update' method='POST' enctype='multipart/form-data'>");
      out.println("      <div style='height: 70px'></div>");
      out.println("    <div style='float: left; margin-left: 10px;'>");
      //<!-- 로고 들어갈자리 -->
      out.printf("      <img src='../upload/%s' style='height: 80px;'>\n", logoPath);
      out.println("    </div>");
      out.println("      <div id='title' style='float: left; height: 80px;'>");
      // <!-- 프로젝트명 들어갈자리 -->
      out.printf("        <input name='projectName' type='text' placeholder='프로젝트 이름' value=%s\n", title);
      out.println("          style='width:350px; margin: 10px; margin-left: 20px; padding: 2px; font-size: 36px; vertical-align: middle; border-radius: 5px;'>");
      out.println("      </div>");
      
      out.println("<div style='float: right; padding-top: 10px'>");
      //<!-- 로고등록버튼 들어갈자리 -->
      out.println("  <input name='logoPath' type='file' "); 
      out.println("  style='margin: 10px; height: 40px; width: 180px;'>");
      out.println("</div>");
      out.println("      <div id='predata' style='clear:both; height: 130px;'>");
      // <!-- 시작일 종료일 팀원선택버튼 -->
      out.println("        <div id='projectdate'");
      out.println("          style='float: left; height: 100px; width: 600px;'>");
      
      out.println("          <div id='startdate' style='margin: 10px; height: 30px;'>");
      // <!-- 시작일 -->
      out.println("            <span");
      out.println("              style='display: table-col; vertical-align: middle; font-size: 18px; font-weight: bold; margin: 10px;'>시작일</span>");
      out.println("            <input name='projectStartDate' type='text'");
      out.printf("              placeholder='2016-12-01' value=%s \n", startDate);
      out.println("              style='margin: 10px; padding: 2px; font-size: 18px; vertical-align: middle; border-radius: 5px;'>");
      out.println("          </div>");
      out.println("          <div id='enddate' style='margin: 10px; height: 40px;'>");
      // <!-- 종료일 -->
      out.println("            <span");
      out.println("              style='display: table-col; vertical-align: middle; font-size: 18px; font-weight: bold; margin: 10px;'>종료일</span>");
      out.printf("            <input name='projectEndDate' type='text' placeholder='2017-02-01' value=%s \n", endDate);
      out.println("              style='margin: 10px; padding: 2px; font-size: 18px; vertical-align: middle; border-radius: 5px;'>");
      out.println("          </div>");
      out.println("        </div>");
      // <!-- 팀원선택버튼 -->
      out.println("        <div id='teamselect'");
      out.println("          style='float: right; height: 100px; width: 200px'>");
      out.println("          <input type='button' value='팀원선택'");
      out.println("            onclick=\"location.href='../projectMember/list'\"");
      out.println("            style='margin: 10px; height: 80px; width: 180px; border: 0px; background-color: #bebebe; font-size: 20px; font-weight: bold; color: white; border-radius: 5px;'>");
      out.println("        </div>");
      out.println("        <div id='projectMemberListDiv' style='margin: 10px; height: 30px;'>");
      // <!-- 팀원 목록 -->
      out.println("          <span style='display: table-col; vertical-align: middle; font-size: 18px; font-weight: bold; margin: 10px;'>");
      out.printf("프로젝트 멤버 [ %s ]</span>", projectMembers);
      out.println("        </div>");
      out.println("      </div>");
      out.println("      <div id='content' style='height: 320px;'>");
      // <!-- 내용 -->
      out.println("        <div style='margin: 10px; background-color: #e0e0e0; height: 300px;'>");
      out.println("          <textarea rows='17' cols='102' name='textContents'");
      out.println("            placeholder='프로젝트의 내용을 입력해 주세요'");
      out.println("            style='margin: 20px; resize: none; border-radius: 5px;'>");
      out.println(contents);
      out.println("</textarea>");
      out.println("        </div>");
      out.println("      </div>");
      out.println("      <div id='postdata' style='height: 80px;'>");
      // <!-- 태그입력 등록버튼 취소버튼 -->
      out.println("        <div id='projectdate'");
      out.println("          style='float: left; margin: 10px; height: 60px; width: 480px;'>");
      // <!-- 태그입력-->
      out.println("          <span");
      out.println("            style='display: table-col; vertical-align: middle; font-size: 18px; font-weight: bold; margin: 10px;'>태그</span>");
      out.println("          <input name='tagData' type='text' placeholder='#자바, #웹, #스프링'");
      out.println("            style='margin: 10px; padding: 2px; font-size: 18px; vertical-align: middle; border-radius: 5px; width: 350px'>");
      out.println("        </div>");
      out.println("        <div id='projectdate'");
      out.println("          style='float: right; margin: 10px; height: 60px; width: 280px;'>");
      // <!-- 등록버튼 취소버튼 -->
      out.println("          <div style='float: left; margin: 10px; height: 40px; width: 120px;'>");
      out.println("            <button type='submit'");
      out.println("              style='height: 40px; width: 120px; border: 0px; background-color: #bebebe; font-size: 20px; font-weight: bold; color: white; border-radius: 5px;'>확인</button>");
      out.println("          </div>");
      out.println("          <div");
      out.println("            style='float: right; margin: 10px; height: 40px; width: 120px;'>");
      out.printf("            <input type='button' value='취소' onclick=\"location.href='%s'\"\n", referer);
      out.println("              style='height: 40px; width: 120px; border: 0px; background-color: #bebebe; font-size: 20px; font-weight: bold; color: white; border-radius: 5px;'>");
      out.println("          </div>");
      out.println("        </div>");
      out.println("      </div>");
      out.printf("    <input type='hidden' name='projectNo' value='%d'>\n", projectNo);
      out.println("    </form>");
      out.println("    <hr>");
      out.println("  </div>");

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
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // doPost 메서드에는 dao로 업데이트명령을 호출.
    
    try {
      Map<String,String> dataMap = MultipartUtil.parse(request);
      
      Project project = new Project();
      project.setProjectNo(Integer.parseInt(dataMap.get("projectNo")));
      project.setContentNo(Integer.parseInt(dataMap.get("projectNo")));
      project.setTitle(dataMap.get("projectName"));
      project.setStartDate(dataMap.get("projectStartDate"));
      project.setEndDate(dataMap.get("projectEndDate"));
      project.setContents(dataMap.get("textContents"));
      project.setLogoPath(dataMap.get("logoPath"));
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("<title>프로젝트관리-변경</title>");
      out.println("</head>");
      out.println("<body>");

      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);

      out.println("<h1>변경 결과</h1>");

      ProjectDao projectDao = (ProjectDao)ContextLoaderListener.applicationContext.getBean("projectDao");
      
      if (!projectDao.exist(project.getProjectNo())) {
        throw new Exception("해당 프로젝트가 없습니다.");
      }
      
      projectDao.update(project);

      out.println("<p>변경 하였습니다.</p>");

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
