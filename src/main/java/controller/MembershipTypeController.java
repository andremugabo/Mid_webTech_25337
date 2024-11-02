package controller;

import dao.MembershipTypeDAO;
import models.MembershipType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/membershipType")
public class MembershipTypeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private MembershipTypeDAO membershipTypeDAO = new MembershipTypeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            List<MembershipType> membershipTypes = membershipTypeDAO.displayAll();
            request.setAttribute("listMembershipTypes", membershipTypes);
            request.getRequestDispatcher("membershipType.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            UUID membershipTypeId = UUID.fromString(request.getParameter("membershipTypeId"));
            MembershipType membershipType = membershipTypeDAO.selectOne(membershipTypeId);
            request.setAttribute("membershipType", membershipType);
            request.getRequestDispatcher("membershipType.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("add")) {
            MembershipType membershipType = new MembershipType();
            membershipType.setMembershipName(request.getParameter("membershipName"));
            membershipType.setMaxBooks(Integer.parseInt(request.getParameter("maxBooks")));
            membershipType.setPrice(Integer.parseInt(request.getParameter("price")));
            membershipTypeDAO.create(membershipType);
        } else if (action.equals("update")) {
            UUID membershipTypeId = UUID.fromString(request.getParameter("membershipTypeId"));
            MembershipType membershipType = membershipTypeDAO.selectOne(membershipTypeId);
            if (membershipType != null) {
                membershipType.setMembershipName(request.getParameter("membershipName"));
                membershipType.setMaxBooks(Integer.parseInt(request.getParameter("maxBooks")));
                membershipType.setPrice(Integer.parseInt(request.getParameter("price")));
                membershipTypeDAO.update(membershipType);
            }
        } else if (action.equals("delete")) {
            UUID membershipTypeId = UUID.fromString(request.getParameter("membershipTypeId"));
            membershipTypeDAO.softDelete(membershipTypeId);
        }
        response.sendRedirect("membershipType");
    }
}
