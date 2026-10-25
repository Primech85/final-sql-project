package by.primech85.servlet;

import by.primech85.service.TripService;
import by.primech85.utils.JSPHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/Trips")
public class TripServlet extends HttpServlet {
    private final TripService tripService = TripService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        req.getRequestDispatcher(JSPHelper.getPath("header")).include(req,resp);
        try(var writer = resp.getWriter()){

            writer.write("<h1> Список рейсов: </h1>");
            writer.write("<ul>");
            tripService.tripALL().stream().forEach(tripDto -> {
                writer.write("""
                        <li>
                         <a href ='tickets?trip_id=%d'>%s</a>
                        </li>""".formatted(tripDto.id(),tripDto.discription()));
            });
            writer.write("</ul>");
        }

    }
}
