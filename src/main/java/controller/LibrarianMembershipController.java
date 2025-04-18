package controller;

import dao.MembershipDAO;
import dao.MembershipTypeDAO;
import models.Membership;
import models.MembershipType;
import models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@WebServlet("/librarianMembership")
public class LibrarianMembershipController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final MembershipDAO membershipDAO = new MembershipDAO();
	private final MembershipTypeDAO membershipTypeDao = new MembershipTypeDAO();
	private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		try {
			switch (action) {
			case "list":
				handleList(request, response);
				break;
			case "edit":
				handleApproved(request, response);
				break;
			case "delete":
				handleRejected(request, response);
				break;
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		try {

			switch (action) {
			case "add":
				createMembership(request, response);
				break;
			case "update":
				updateMembership(request, response);
				break;
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
		}

		response.sendRedirect("librarianMembership?action=list");
	}

	private void handleList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Membership> membershipsList = membershipDAO.displayAll();
		List<MembershipType> membershipTypeList = membershipTypeDao.displayAll();

		request.setAttribute("membershipsList", membershipsList);
		request.setAttribute("memberTypeList", membershipTypeList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("membershipLibrarian.jsp");
		dispatcher.forward(request, response);
	}

	private void handleApproved(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UUID membershipId = UUID.fromString(request.getParameter("membershipId"));
		membershipDAO.updateApprovedStatus(membershipId);
		// Send email notification for approval
		sendStatusUpdateEmail(membershipId, "APPROVED");
		RequestDispatcher dispatcher = request.getRequestDispatcher("librarianMembership?action=list");
		dispatcher.forward(request, response);
	}

	private void handleRejected(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UUID membershipId = UUID.fromString(request.getParameter("membershipId"));
		membershipDAO.updateRejectedStatus(membershipId);
		// Send email notification for rejection
		sendStatusUpdateEmail(membershipId, "REJECTED");
		RequestDispatcher dispatcher = request.getRequestDispatcher("librarianMembership?action=list");
		dispatcher.forward(request, response);
	}

	private void createMembership(HttpServletRequest request, HttpServletResponse response) {
		Membership membership = new Membership();
		membership.setMembershipId(UUID.randomUUID());
		membership.setMembershipCode(generateMembershipCode());
		membership.setRegistrationDate(new Date());

		try {
			membership.setExpiringTime(formatter.parse(request.getParameter("expiringTime")));
			membership.setMembershipStatus(Membership.Status.valueOf(request.getParameter("membershipStatus")));
			membership.setReader(new User());
			membershipDAO.create(membership);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private void updateMembership(HttpServletRequest request, HttpServletResponse response) {
		Membership membership = new Membership();
		membership.setMembershipId(UUID.fromString(request.getParameter("membershipId")));
		membership.setMembershipCode(request.getParameter("membershipCode"));
		membership.setRegistrationDate(new Date());

		try {
			membership.setExpiringTime(formatter.parse(request.getParameter("expiringTime")));
			membership.setMembershipStatus(Membership.Status.valueOf(request.getParameter("membershipStatus")));
			membership.setReader(new User());
			membershipDAO.update(membership);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private String generateMembershipCode() {
		int numMember = (int) (membershipDAO.countMembers() + 1);
		if (numMember < 10)
			return "LM-00" + numMember;
		if (numMember < 100)
			return "LM-0" + numMember;
		return "LM-" + numMember;
	}

	private void sendStatusUpdateEmail(UUID membershipId, String status) {

		Membership membership = membershipDAO.selectOne(membershipId);
		String readerEmail = membership.getReader().getUserName();

		// Set email details
		String subject = "Library Membership Status Update";
		String body = String.format("Library Membership Status Update\n\n" + "Dear %s,\n\n"
				+ "We wanted to inform you that your library membership status has been updated to: %s.\n\n"
				+ "If you have any questions or need further assistance, please feel free to reach out to us.\n\n"
				+ "Thank you for being a valued member of our library community.\n\n"
				+ "Best regards,\nThe Library Team", membership.getReader().getFirstName(), status);

		// Send email using EmailServlet
		try {
			EmailServlet emailServlet = new EmailServlet();
			emailServlet.sendEmail(readerEmail, subject, body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
