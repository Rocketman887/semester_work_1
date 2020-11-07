package servlets;

import models.Article;
import models.User;
import models.UserProfile;
import services.ArticleService;
import services.Helper;
import services.UserProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/article")
public class ArticleServlet extends HttpServlet {
    Helper helper;
    ArticleService articleService;
    UserProfileService userProfileService;
    @Override
    public void init() throws ServletException {
        helper = new Helper();
        articleService = new ArticleService();
        userProfileService = new UserProfileService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String articleID = request.getParameter("id");
        Article article = articleService.showMyArticle((Long.parseLong(articleID)));
        User user = userProfileService.findUser(article.getUserID());
        HashMap <String,Object> root = new HashMap<>();
        root.put("article",article);
        root.put("user",user);
        helper.render(request,response,"article.ftl",root);
    }
}
