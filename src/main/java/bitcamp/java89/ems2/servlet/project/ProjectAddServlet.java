package bitcamp.java89.ems2.servlet.project;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.MemberDao;
import bitcamp.java89.ems2.dao.StudentDao;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.domain.Project;
import bitcamp.java89.ems2.domain.Student;

@WebServlet("/project/add")
public class ProjectAddServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    try {
      Project project = new Project();
      project.setTitle(request.getParameter("projectName"));
      project.setStartDate(request.getParameter("projectStartDate"));
      project.setEndDate(request.getParameter("projectEndDate"));
      project.setContents(request.getParameter("textContents"));
      
      
      //** 여기하는중
      
      Student student = new Student();
      student.setEmail(request.getParameter("email"));
      student.setPassword(request.getParameter("password"));
      student.setName(request.getParameter("name"));
      student.setTel(request.getParameter("tel"));
      student.setWorking(Boolean.parseBoolean(request.getParameter("working")));
      student.setGrade(request.getParameter("grade"));
      student.setSchoolName(request.getParameter("schoolName"));
      student.setPhotoPath(request.getParameter("photoPath"));
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
  
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("<title>학생관리-등록</title>");
      out.println("</head>");
      out.println("<body>");
      
      // HeaderServlet에게 머리말 HTML 생성을 요청한다.
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>등록 결과</h1>");
    
      StudentDao studentDao = (StudentDao)this.getServletContext().getAttribute("studentDao");
    
      if (studentDao.exist(student.getEmail())) {
        throw new Exception("같은 학생의 이메일이 존재합니다. 등록을 취소합니다.");
      }
      
      MemberDao memberDao = (MemberDao)this.getServletContext().getAttribute("memberDao");
      
      if (!memberDao.exist(student.getEmail())) { // 강사나 매니저로 등록되지 않았다면,
        memberDao.insert(student);
        
      } else { // 강사나 매니저로 이미 등록된 사용자라면 기존의 회원 번호를 사용한다.
        Member member = memberDao.getOne(student.getEmail());
        student.setMemberNo(member.getMemberNo());
      }
      
      studentDao.insert(student);
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







