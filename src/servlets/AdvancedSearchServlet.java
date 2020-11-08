package servlets;

import services.ArticleService;
import services.Helper;
import services.SearchService;
import services.UserProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/advancedSearch")
public class AdvancedSearchServlet extends HttpServlet {
    ArticleService articleService;
    UserProfileService userProfileService;
    Helper helper;
    SearchService searchService;
    @Override
    public void init() throws ServletException {
        articleService = new ArticleService();
        userProfileService = new UserProfileService();
        helper = new Helper();
        searchService = new SearchService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        HashMap<String,Object> root = new HashMap<>();
        response.sendRedirect("http://localhost:8081/myArticle/searchResult?title="+title+"&");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String,Object> root = new HashMap<>();
        helper.render(request,response,"advancedSearch.ftl",root);
    }
}
