package com.example.appengine.java8.Servlet;

import com.example.appengine.java8.DTO.Voter;
import com.example.appengine.java8.Constants;
import com.example.appengine.java8.Entity.VoteEntity;
import com.example.appengine.java8.Management.VoteManagement;
import com.google.appengine.api.datastore.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "VotersServlet", value = "/admin/voterlist")
public class VotersServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger
            (VotersServlet.class.getName());
    private VoteManagement voterManaging = new VoteManagement();
    private VoteEntity voteEntity = new VoteEntity();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Query query = new Query(voteEntity.getVOTERS());
        System.out.println(query.toString());
        List<Voter> voterList = voterManaging.get(query);
        System.out.println(voterList);
        if(voterList != null){
            req.setAttribute("voterList", voterList);
        }
        req.getRequestDispatcher("/votermanagement.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Voter voter = new Voter();
        String email = req.getParameter("email");
        String name = req.getParameter("name");


        voter.setName(name);
        voter.setEmail(email);
        voter.setEmailSent(false);
        voter.setVoted(false);
        voter.setReminder(false);
        voter.setToken("Empty");
        voterManaging.create(voter);

//        test fetch from saved voters
        Query query = new Query(voteEntity.getVOTERS());
        System.out.println(query.toString());
        List<Voter> voterList = voterManaging.get(query);
        System.out.println("fetching voters"+voterList.get(0).getEmail());
        System.out.println(voterList.get(0).getName());
//        logger.severe("Error fetching voters" + voterManaging.create(voter));
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean res = false;

        Voter voter = new Voter();
//        String token = req.getParameter(Constants.VOTER_TOKEN_PROPERTY);
//        Boolean reminder = Boolean.valueOf(req.getParameter(Constants.VOTER_REMINDER_PROPERTY));
//        Boolean emailSent = Boolean.valueOf(req.getParameter(Constants.VOTER_EMAILSENT_PROPERTY));
        String id = req.getParameter("id");
        String name = req.getParameter(voteEntity.getVOTER_NAME_PROPERTY());
        String email = req.getParameter(voteEntity.getVOTER_EMAIL_PROPERTY());

        Query query = new Query(voteEntity.getVOTERS());
        System.out.println(query.toString());
        List<Voter> voterList = voterManaging.get(query);
//       To DO : update er jnne getOne or getById dorkar
        voter.setName(name);
        voter.setEmail(email);
//        voter.setEmailSent(emailSent);
//        voter.setReminder(reminder);
//        voter.setToken(token);
        if( voterManaging.update(voter) != null){
            res = true;
        }
        resp.getWriter().write(String.valueOf(res));
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("id");
        super.doDelete(req, resp); //Eta kmne kaj korbe ?
    }
}
