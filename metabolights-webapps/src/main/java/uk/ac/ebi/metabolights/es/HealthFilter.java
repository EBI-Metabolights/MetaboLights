package uk.ac.ebi.metabolights.es;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*
 * Class for EBI external services, are we alive?
 * Configured in web.xml
 */

public class HealthFilter implements Filter
{

    public HealthFilter()
    {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        request.setAttribute("health", Boolean.valueOf(true));
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException
    {
    }

    public void destroy()
    {
    }

}