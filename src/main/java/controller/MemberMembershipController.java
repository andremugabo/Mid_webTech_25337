package controller;

import dao.MembershipDAO;
import dao.MembershipTypeDAO;
import dao.UserDao;
import models.Membership;
import models.Membership.Status;
import models.MembershipType;
import models.User;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@WebServlet("/memberMembership")
public class MemberMembershipController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final MembershipDAO membershipDAO = new MembershipDAO();
	private final MembershipTypeDAO membershipTypeDao = new MembershipTypeDAO();
	private final UserDao userDao = new UserDao();
	MembershipType membershipType = new MembershipType();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		User user = (session != null) ? (User) session.getAttribute("user") : null;

		if (user != null) {
			handleList(request, response, user.getPersonId());
		} else {
			response.sendRedirect("login.html");
		}
	}

	private void handleList(HttpServletRequest request, HttpServletResponse response, UUID userId)
			throws ServletException, IOException {

		Membership myMembership = membershipDAO.selectByUserId(userId);
		List<MembershipType> membershipTypeList = membershipTypeDao.displayAll();

		request.setAttribute("myMembership", myMembership);
		request.setAttribute("memberTypeList", membershipTypeList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("membershipMember.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		User user = (session != null) ? (User) session.getAttribute("user") : null;

		if (user == null) {
			response.sendRedirect("login.html");
			return;
		}

		String action = request.getParameter("action");
		String userId = request.getParameter("userId");
		user = userDao.selectUser(UUID.fromString(userId));
		String membershipTypeId = request.getParameter("membershipTypeId");
		membershipType = membershipTypeDao.selectOne(UUID.fromString(membershipTypeId));
		Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		String expiringTime = request.getParameter("expiringTime");
			
		 try {
			if (formatter.parse(expiringTime).before(currentDate)) {
			     System.out.println("Expiring time is less than current date.");
			     return;
			 }
		} catch (ParseException e) {
				e.printStackTrace();
		}
		
		switch (action != null ? action : "") {
		case "apply":
			Membership newMembership = new Membership();
			newMembership.setReader(user);
			newMembership.setMembershipType(membershipType);
			newMembership.setRegistrationDate(currentDate);
			try {
				newMembership.setExpiringTime(formatter.parse(expiringTime));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			newMembership.setMembershipStatus(Status.PENDING);
			newMembership.setMembershipCode(generateMembershipCode());
			membershipDAO.create(newMembership);
			break;

		case "update":
			Membership existingMembership = membershipDAO.selectByUserId(UUID.fromString(userId));
			if (existingMembership != null) {
				existingMembership.setMembershipType(membershipType);
				try {
					existingMembership.setExpiringTime(formatter.parse(expiringTime));
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				membershipDAO.update(existingMembership);
			}
			break;

		default:
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
			return;
		}

		response.sendRedirect("memberMembership?action=list");
	}

	private String generateMembershipCode() {
		int numMember = (int) (membershipDAO.countMembers() + 1);
		if (numMember < 10)
			return "LM-00" + numMember;
		if (numMember < 100)
			return "LM-0" + numMember;
		return "LM-" + numMember;
	}
}
