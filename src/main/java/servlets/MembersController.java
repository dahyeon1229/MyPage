package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MembersDAO;
import dto.MembersDTO;

@WebServlet("*.members")
public class MembersController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf8");

		String cmd = request.getRequestURI();
		System.out.println("요청 uri : " + cmd);

		MembersDAO dao = MembersDAO.getInstance();

		try {
			if (cmd.equals("/mypage.members")) {
				//HttpSession session = request.getSession();
				//String id = (String) session.getAttribute("loginId");
		        String id = "dkdldb1324";
				MembersDTO dto = dao.mypage(id);
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("/myPage.jsp").forward(request,response);
			} else if (cmd.equals("/update.members")) {
				response.sendRedirect("/updateMyPage.jsp");
			} else if (cmd.equals("/updateComplete.members")) {
				//HttpSession session = request.getSession();
				//String id = (String) session.getAttribute("loginId");
				String id = "dkdldb1324";
				String name = request.getParameter("name");
				String phone1 = request.getParameter("phone1");
				String phone2 = request.getParameter("phone2");
				String phone = "010"+phone1 + phone2;
				String email1 = request.getParameter("email1");
				String email2 = request.getParameter("email2");
				String email = email1 + "@" + email2;
				String zipcode = request.getParameter("zipcode");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				String address = address1 + " " +address2;
				String address3 = request.getParameter("address3");

				dao.update(id, name, phone, email, zipcode, address, address3);
				MembersDTO dto = dao.mypage(id);
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("/myPage.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/error.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
