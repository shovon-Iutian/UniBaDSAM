package com.example.appengine.java8.Servlet;

import com.example.appengine.java8.DTO.Candidates;
import com.example.appengine.java8.DTO.VoteTime;
import com.example.appengine.java8.DTO.Voter;
import com.example.appengine.java8.Entity.CandidatesEntity;
import com.example.appengine.java8.Entity.VoteEntity;
import com.example.appengine.java8.Entity.VoteTimeEntity;
import com.example.appengine.java8.Management.CandidatesManagement;
import com.example.appengine.java8.Management.VoteManagement;
import com.example.appengine.java8.Management.VoteTimeManagement;
import com.google.appengine.api.datastore.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "VoteResult", value = "/votingresult")
public class VoteResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Integer> votestats = new HashMap<>();

        VoteTimeEntity voteTimeEntity = new VoteTimeEntity();
        VoteTimeManagement voteTimeManagement = new VoteTimeManagement();
        Query query = new Query(voteTimeEntity.getVoteTimeKind());
        List<VoteTime> voteTimes = voteTimeManagement.get(query);

        CandidatesManagement candidatesManagement = new CandidatesManagement();
        CandidatesEntity candidatesEntity = new CandidatesEntity();
        Query query1 = new Query(candidatesEntity.getCandidateKind());
        List<Candidates> candidatesList = new ArrayList<>();
        candidatesList = candidatesManagement.get(query1);

        VoteManagement voteManagement = new VoteManagement();
        List<Voter> voterList = new ArrayList<>();
        VoteEntity voteEntity = new VoteEntity();
        Query query2 = new Query(voteEntity.getVOTERS());
        voterList= voteManagement.get(query2);

        Date date = null;
        //Date enddate= null;
        VoteTime voteTime = new VoteTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,1);
        date = calendar.getTime();
        for (VoteTime votetime : voteTimes) {
            Date enddate = votetime.getEnddate();
            System.out.println(date);
            System.out.println(enddate);
            if(date.after(enddate)){
                Integer votecasted = voteManagement.getCastedVoterCount();
                Integer pendingvote= voteManagement.getPendingVoterCount();
                Integer votercount= voterList.size();
                votestats.put("votecasted",votecasted);
                votestats.put("pendingvote",pendingvote);
                votestats.put("votercount",votercount);
                float percentagevoter =  ((float) votecasted / (float) votercount) * 100;
                req.setAttribute("percentagevoter", percentagevoter);
                req.setAttribute("candidatesList",candidatesList);
                req.setAttribute("votestats",votestats);
                req.getRequestDispatcher("/votingresults.jsp").forward(req, resp);
            }
            else {
                req.getRequestDispatcher("/votingresultpublish.jsp").forward(req, resp);
            }
        }


    }
}
