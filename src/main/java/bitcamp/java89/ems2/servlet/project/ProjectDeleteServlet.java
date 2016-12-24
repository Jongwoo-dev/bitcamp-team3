package bitcamp.java89.ems2.servlet.project;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.ProjectDao;

@WebServlet("/project/delete")
public class ProjectDeleteServlet extends HttpServlet {
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
      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("<title>프로젝트관리-삭제</title>");
      out.println("</head>");
      out.println("<body>");
      
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>삭제 결과</h1>");
      
      ProjectDao projectDao = (ProjectDao)this.getServletContext().getAttribute("projectDao");
      
      if (!projectDao.exist(projectNo)) {
        throw new Exception("프로젝트를 찾지 못했습니다.");
      }
      
//      ContentDao contentDao = (ContentDao)this.getServletContext().getAttribute("contentDao");
//      Proj_MembDao proj_MembDao = (Proj_MembDao)this.getServletContext().getAttribute("proj_MembDao");
//      if (!contentDao.exist(projectNo) && !proj_MembDao.exist(projectNo)) {
//        proj_MembDao.delete(projectNo);
//        contentDao.delete(projectNo);
//      }
      
      out.println("<p>삭제하였습니다.</p>");
      
      rd = request.getRequestDispatcher("/footer");
      rd.include(request, response);
      
      out.println("</body>");
      out.println("</html>");
      
    } catch (Exception e) {
      RequestDispatcher rd = request.getRequestDispatcher("/error");
      rd.forward(request, response);
      return;
    }
    
  }
}
