package controller;

import dao.MembershipTypeDAO;
import models.MembershipType;

import javax.servlet.RequestDispatcher;
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
    private RequestDispatcher dispatcher;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "edit":
                editMembershipType(request, response);
                break;
            case "list":
            default:
                listMembershipTypes(request, response);
                break;
        }
    }

    private void editMembershipType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID membershipTypeId = UUID.fromString(request.getParameter("membershipTypeId"));
        MembershipType membershipType = membershipTypeDAO.selectOne(membershipTypeId);
        request.setAttribute("membershipType", membershipType);
        dispatcher = request.getRequestDispatcher("membershipType.jsp");
        dispatcher.forward(request, response);
    }

    private void listMembershipTypes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MembershipType> membershipTypes = membershipTypeDAO.displayAll();
        request.setAttribute("listMembershipTypes", membershipTypes);
        dispatcher = request.getRequestDispatcher("membershipTypes.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addMembershipType(request,response);
                break;
            case "update":
                updateMembershipType(request,response);
                break;
            case "delete":
                deleteMembershipType(request,response);
                break;
            default:
                response.sendRedirect("membershipType");
                return;
        }
        response.sendRedirect("membershipType");
    }

    private void addMembershipType(HttpServletRequest request,HttpServletResponse response) throws IOException {
        MembershipType membershipType = new MembershipType();
        membershipType.setMembershipName(request.getParameter("membershipName").toUpperCase());
        membershipType.setMaxBooks(Integer.parseInt(request.getParameter("maxBooks")));
        membershipType.setPrice(Integer.parseInt(request.getParameter("price")));
        membershipTypeDAO.create(membershipType);
        response.sendRedirect("membershipType?action=list");
    }

    private void updateMembershipType(HttpServletRequest request,HttpServletResponse response) {
        UUID membershipTypeId = UUID.fromString(request.getParameter("membershipTypeId"));
        MembershipType membershipType = membershipTypeDAO.selectOne(membershipTypeId);
        if (membershipType != null) {
            membershipType.setMembershipName(request.getParameter("membershipName"));
            membershipType.setMaxBooks(Integer.parseInt(request.getParameter("maxBooks")));
            membershipType.setPrice(Integer.parseInt(request.getParameter("price")));
            membershipTypeDAO.update(membershipType);
        }
    }

    private void deleteMembershipType(HttpServletRequest request, HttpServletResponse response) {
        UUID membershipTypeId = UUID.fromString(request.getParameter("membershipTypeId"));
        membershipTypeDAO.softDelete(membershipTypeId);
    }
}
