package servlets;

import models.Article;
import models.User;
import services.AddArticleService;
import services.Helper;
import services.UserProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/addArticle")
public class AddArticleServlet extends HttpServlet {
    private Helper helper;
    private UserProfileService userProfileService;
    private AddArticleService addArticleService;
    @Override
    public void init() throws ServletException {
        helper = new Helper();
        userProfileService = new UserProfileService();
        addArticleService = new AddArticleService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userProfileService.findUser((String) request.getSession(false).getAttribute("login"));
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        HashMap<String,Object> root = new HashMap<>();
        Article article = new Article(title, text, user.getId());


                if(addArticleService.checkTitleSeqLength(article)) {
                    if (addArticleService.checkTextSeqLength(article)) {
                        addArticleService.addArticle(article);
                        root.put("message","Your article was successfully published!");
                        helper.render(request,response,"addArticle.ftl",root);
                    }
                    else {
                        root.put("message","The text cannot be so shot. Minimal length: 30 characters!");
                        helper.render(request,response,"addArticle.ftl",root);
                    }
                }
                else {
                    root.put("message","The title cannot be so short. Minimal length: 4 characters!");
                    helper.render(request,response,"addArticle.ftl",root);
                }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        helper.render(request,response,"addArticle.ftl",new HashMap<String,Object>());
    }
}
