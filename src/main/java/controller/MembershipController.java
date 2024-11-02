package controller;

import dao.MembershipDAO;
import models.Membership;
import models.User;
import javax.servlet.ServletException;
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

public class MembershipController extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	private final MembershipDAO membershipDAO = new MembershipDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            List<Membership> memberships = membershipDAO.displayAll();
            request.setAttribute("listMemberships", memberships);
            request.getRequestDispatcher("views/membership.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            UUID membershipId = UUID.fromString(request.getParameter("membershipId"));
            Membership membership = membershipDAO.selectOne(membershipId);
            request.setAttribute("membership", membership);
            request.getRequestDispatcher("views/editMembership.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Membership membership = new Membership();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        membership.setMembershipId(UUID.fromString(request.getParameter("membershipId")));
        membership.setMembershipCode(request.getParameter("membershipCode"));
        membership.setRegistrationDate(new Date());
        try {
			membership.setExpiringTime(formatter.parse(request.getParameter("expiringTime")));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
        membership.setMembershipStatus(Membership.Status.valueOf(request.getParameter("membershipStatus")));
        
        User reader = new User();
        membership.setReader(reader);
        
        if ("add".equals(action)) {
            membershipDAO.create(membership);
        } else if ("update".equals(action)) {
            membershipDAO.update(membership);
        } else if ("delete".equals(action)) {
            membershipDAO.softDelete(membership.getMembershipId());
        }

        response.sendRedirect("membership?action=list");
    }
}
