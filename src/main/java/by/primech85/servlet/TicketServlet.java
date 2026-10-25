package by.primech85.servlet;

import by.primech85.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {
    private final TicketService ticketService = TicketService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Long trip_id = Long.valueOf(req.getParameter("trip_id"));
        System.out.println(trip_id);
        try(var writer = resp.getWriter()){
            writer.write("<h1> Купленные билеты: </h1>");
            writer.write("<ul>");
            ticketService.findAllTripId(trip_id).stream().forEach(ticketDto -> {
                writer.write("""
                        <li>
                         %s
                         </li>""".formatted(ticketDto.seat_no()));
            });
            writer.write("</ul>");
        }
    }
}
