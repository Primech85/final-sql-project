package by.primech85.servlet;

import by.primech85.service.TripService;
import by.primech85.utils.JSPHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/content")
public class ContentServlet extends HttpServlet {
    private final TripService tripService = TripService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var trips = tripService.tripALL();
        req.setAttribute("trips",trips);

        req.getRequestDispatcher(JSPHelper.getPath("content")).forward(req,resp);
    }
}
