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

@WebServlet("/report")
public class ReportController extends HttpServlet {

   
	private static final long serialVersionUID = 1L;
	private MembershipTypeDAO membershipTypeDAO = new MembershipTypeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MembershipType> membershipTypes = membershipTypeDAO.displayAll();
        request.setAttribute("membershipTypes", membershipTypes);
        request.getRequestDispatcher("report.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("generatePDF".equals(action)) {
            // Logic to generate PDF report goes here
            generatePDFReport(response);
        } else {
            // Handle other actions if needed
        }
    }

    private void generatePDFReport(HttpServletResponse response) throws IOException {
        // Use a PDF library (like iText or Apache PDFBox) to create a PDF report.
        // This is a placeholder for the PDF generation logic.
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"membership_report.pdf\"");
        
        // Sample content - replace with actual PDF generation logic
        response.getWriter().write("This is a sample PDF report for memberships."); 
        // In a real implementation, write the generated PDF to the response's output stream
    }
}
