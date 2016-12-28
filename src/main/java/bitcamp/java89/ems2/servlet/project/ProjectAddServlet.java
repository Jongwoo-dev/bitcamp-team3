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
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.domain.Project;
import bitcamp.java89.ems2.listener.ContextLoaderListener;
import bitcamp.java89.ems2.util.MultipartUtil;

@WebServlet("/project/add")
public class ProjectAddServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    try {
      Map<String,String> dataMap = MultipartUtil.parse(request);
      
      Project project = new Project();
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
      out.println("<title>프로젝트관리-등록</title>");
      out.println("</head>");
      out.println("<body>");
      
      // HeaderServlet에게 머리말 HTML 생성을 요청한다.
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>등록 결과</h1>");
    
      ProjectDao projectDao = (ProjectDao)ContextLoaderListener.applicationContext.getBean("projectDao");
      
      // 원랜 ContentDao 를 읽어와서 그쪽 인서트 메서드로 입력 구현을 해야하나 그쪽이 완료가 안된 관계로
      // 일단은 ProjectDao에서 구현함. 추후 ContentDao로 구현이 되면 content insert 부분 교체 
      //ContentDao contentDao = (ContentDao)this.getServletContext().getAttribute("contentDao");
      
      // 해당 유저가 존재하는지 체크
      Member writer = (Member)request.getSession().getAttribute("member");
      if (writer == null) {
        throw new Exception("로그인이 필요합니다.");
      }
      
      // 작성자가 존재하면 해당 이메일의 멤버번호로 프로젝트 등록자를 설정 
      project.setMemberNo(writer.getMemberNo());
      projectDao.insertContent(project);
      
      projectDao.insert(project);
      out.println("<p>등록하였습니다.</p>");
      
      // FooterServlet에게 꼬리말 HTML 생성을 요청한다.
      rd = request.getRequestDispatcher("/footer");
      rd.include(request, response);
      
      out.println("</body>");
      out.println("</html>");

    } catch (Exception e) {
      // 오류 정보를 ServletRequest에 담는다.
      request.setAttribute("error", e);
      
      RequestDispatcher rd = request.getRequestDispatcher("/error");
      rd.forward(request, response);
      return;
    }
  }
}








