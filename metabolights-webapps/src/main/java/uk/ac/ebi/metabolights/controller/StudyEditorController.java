package uk.ac.ebi.metabolights.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import uk.ac.ebi.metabolights.authenticate.IsaTabAuthenticationProvider;
import uk.ac.ebi.metabolights.model.MetabolightsUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kalai on 20/04/2017.
 */
@Controller
public class StudyEditorController extends AbstractController {

    @RequestMapping(value = {"/editor", "/editor/", "/editor/**"})
    public RedirectView showLabsPage(HttpServletRequest request) {
        RedirectView redirectView = new RedirectView();
        String contextPath = request.getServletContext().getContextPath();
        String path = request.getRequestURI().replace(contextPath, "").replace("/editor", "");

        String url = EntryController.getMetabolightsEditorUrl() + path;
        return addLoginToken(url, redirectView, path);
    }

    private RedirectView addLoginToken(String url, RedirectView redirectView, String path) {
        try {
            MetabolightsUser user = LoginController.getLoggedUser();
            if(user != null && user.getJwtToken() != null){
                String loginOneTimeToken = IsaTabAuthenticationProvider.getLoginOneTimeToken(user);

                if(loginOneTimeToken != null){
                    url += "?loginOneTimeToken=" + loginOneTimeToken;
                    redirectView.setUrl(url);
                    return redirectView;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectView.setUrl(url);
        return redirectView;
    }

    @RequestMapping(value = {"/guides", "/guides/", "/guides/**" })
    public RedirectView showGuidesPage(HttpServletRequest request) {
        RedirectView redirectView = new RedirectView();
        String contextPath = request.getServletContext().getContextPath();
        String path = request.getRequestURI().replace(contextPath, "");
        String url = EntryController.getMetabolightsEditorUrl() + path;

        return addLoginToken(url, redirectView, path);
    }
}
