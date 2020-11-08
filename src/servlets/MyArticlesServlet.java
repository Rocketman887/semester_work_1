package servlets;

import models.Article;
import models.User;
import services.ArticleService;
import services.Helper;
import services.UserProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/myArticles")
public class MyArticlesServlet extends HttpServlet {
    Helper helper;
    UserProfileService userProfileService;
    ArticleService articleService;
    @Override
    public void init() throws ServletException {
        helper = new Helper();
        userProfileService = new UserProfileService();
        articleService = new ArticleService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String exit = request.getParameter("exit");
        if(exit.equals("Exit")){
            if(request.getSession(false)!=null){
                if(request.getSession(false).getAttribute("login") !=null){
                    HttpSession httpSession = request.getSession();
                    httpSession.removeAttribute("login");
                }
            }
            Cookie cookie = new Cookie("login","null");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            response.sendRedirect("/signIn");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = (String) request.getSession(false).getAttribute("login");
        User user =  userProfileService.findUser(login);
        ArrayList<Article> articles= articleService.findMyArticles(user.getId());
        HashMap<String,Object> root = new HashMap<>();
        root.put("articles",articles);
        root.put("login",login);
        helper.render(request,response,"myArticles.ftl",root);
    }
}
