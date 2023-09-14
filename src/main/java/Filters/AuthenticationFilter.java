/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package Filters;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLDecoder;

/**
 *
 * @author CE171454 Hua Tien Thanh
 */
public class AuthenticationFilter implements Filter {

  private static final boolean debug = true;

  // The filter configuration object we are associated with.  If
  // this value is null, this filter instance is not currently
  // configured. 
  private FilterConfig filterConfig = null;

  public AuthenticationFilter() {
  }

  private void doBeforeProcessing(ServletRequest request, ServletResponse response)
          throws IOException, ServletException {
    if (debug) {
      log("AuthenticationFilter:DoBeforeProcessing");
    }

    // Write code here to process the request and/or response before
    // the rest of the filter chain is invoked.
    // For example, a logging filter might log items on the request object,
    // such as the parameters.
    /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
     */
  }

  private void doAfterProcessing(ServletRequest request, ServletResponse response)
          throws IOException, ServletException {
    if (debug) {
      log("AuthenticationFilter:DoAfterProcessing");
    }

    // Write code here to process the request and/or response after
    // the rest of the filter chain is invoked.
    // For example, a logging filter might log the attributes on the
    // request object after the request has been processed. 
    /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
     */
    // For example, a filter might append something to the response.
    /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
     */
  }

  /**
   *
   * @param request The servlet request we are processing
   * @param response The servlet response we are creating
   * @param chain The filter chain we are processing
   *
   * @exception IOException if an input/output error occurs
   * @exception ServletException if a servlet error occurs
   */
  public void doFilter(ServletRequest request, ServletResponse response,
          FilterChain chain)
          throws IOException, ServletException {

    if (debug) {
      log("AuthenticationFilter:doFilter()");
    }

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    doBeforeProcessing(request, response);

    Throwable problem = null;
    try {
      // Authenticates all pages except index.jsp (no need to authenticate there)
      String path = httpRequest.getRequestURI();
      boolean isStaticResource = (path.endsWith("/login")
              || path.isEmpty()
              || path.endsWith(".css")
              || path.endsWith(".map")
              || path.endsWith(".js")
              || path.endsWith(".png")
              || path.endsWith("jpg")
              || path.endsWith(".jpeg")
              || path.endsWith(".gif")
              || path.endsWith(".svg")
              || path.endsWith(".ico")
              || path.endsWith(".json"));
      if (!isStaticResource) {
        // URI does not lead to login controller itself or static resources
        if (path.endsWith("/logout")) {
          // User/Admin clicks the log out button
          logout(httpRequest, httpResponse);
          return;
        } else if (path.startsWith("/admin")) {
          // Destination page is admin page
          if (getAuthStatus(httpRequest) == 2) {
            // Account is of Admin type, proceeds to admin page
            HttpSession session = httpRequest.getSession();
            boolean hasAdminSession = (session.getAttribute("admin") != null
                    && !(((String) session.getAttribute("admin")).isEmpty()));
            if (hasAdminSession) {
              String username = (String) session.getAttribute("admin");
              request.setAttribute("adminName", URLDecoder.decode(username, "UTF-8"));
            } else {
              Cookie[] cookies = httpRequest.getCookies();
              Cookie admin = null;

              for (Cookie cookie : cookies) {
                if (cookie.getName().equals("admin")) {
                  admin = cookie;
                  String adminName = cookie.getValue();
                  request.setAttribute("adminName", URLDecoder.decode(adminName, "UTF-8"));
                  break;
                }
              }
            }
            request.setAttribute("isLoggedIn", true);
          } else {
            // Account is of User type: cannot access Admin page
            // Or if auth fails, also redirects to home page
            httpResponse.sendRedirect("/");
            return;
          }
        } else if (path.startsWith("/user")) {
          // Destination page is user page
          if (getAuthStatus(httpRequest) == 2) {
            // Account is of Admin type, cannot access User pages
            httpResponse.sendRedirect("/admin");
            return;
          } else if (getAuthStatus(httpRequest) == 1) {
            // Account is of User type
            HttpSession session = httpRequest.getSession();
            boolean hasUserSession = (session.getAttribute("user") != null
                    && !(((String) session.getAttribute("user")).isEmpty()));
            if (hasUserSession) {
              String username = (String) session.getAttribute("user");
              request.setAttribute("username", URLDecoder.decode(username, "UTF-8"));
            } else {
              Cookie[] cookies = httpRequest.getCookies();
              Cookie user = null;
              Cookie userID = null;

              for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                  user = cookie;
                  String username = user.getValue();
                  request.setAttribute("username", URLDecoder.decode(username, "UTF-8"));
                }
                if (cookie.getName().equals("userID")) {
                  userID = cookie;
                  session.setAttribute("userID", Integer.parseInt(userID.getValue()));
                }
              }
            }
            request.setAttribute("isLoggedIn", true);
          } else {
            // Return to home page if authentication fails
            httpResponse.sendRedirect("/");
          }
        } else {
          // Destination page is non-admin page nor user page
          if (getAuthStatus(httpRequest) == 1) {
            // Account is of User type
            HttpSession session = httpRequest.getSession();
            boolean hasUserSession = (session.getAttribute("user") != null
                    && !(((String) session.getAttribute("user")).isEmpty()));
            if (hasUserSession) {
              String username = (String) session.getAttribute("user");
              request.setAttribute("username", URLDecoder.decode(username, "UTF-8"));
            } else {
              Cookie[] cookies = httpRequest.getCookies();
              Cookie user = null;
              Cookie userID = null;

              for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                  user = cookie;
                  String username = user.getValue();
                  session.setAttribute("username", URLDecoder.decode(username, "UTF-8"));
                }
                if (cookie.getName().equals("userID")) {
                  userID = cookie;
                  session.setAttribute("userID", Integer.parseInt(userID.getValue()));
                }
              }
            }
            request.setAttribute("isLoggedIn", true);
          } else if (getAuthStatus(httpRequest) == 2) {
            // Account is of Admin type, cannot access user pages
            httpResponse.sendRedirect("/admin");
            return;
          }
          // If auth fails in neither admin nor user pages, continue the filter (nothing happens)
        }
      }
      chain.doFilter(request, response);

    } catch (Throwable t) {
      // If an exception is thrown somewhere down the filter chain,
      // we still want to execute our after processing, and then
      // rethrow the problem after that.
      problem = t;
      t.printStackTrace();
    }

    doAfterProcessing(request, response);

    // If there was a problem, we want to rethrow it if it is
    // a known type, otherwise log it.
    if (problem
            != null) {
      if (problem instanceof ServletException) {
        throw (ServletException) problem;
      }
      if (problem instanceof IOException) {
        throw (IOException) problem;
      }
      sendProcessingError(problem, response);
    }
  }

  /**
   * Return the filter configuration object for this filter.
   */
  public FilterConfig getFilterConfig() {
    return (this.filterConfig);
  }

  /**
   * Set the filter configuration object for this filter.
   *
   * @param filterConfig The filter configuration object
   */
  public void setFilterConfig(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }

  /**
   * Destroy method for this filter
   */
  public void destroy() {
  }

  /**
   * Init method for this filter
   */
  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
    if (filterConfig != null) {
      if (debug) {
        log("AuthenticationFilter:Initializing filter");
      }
    }
  }

  /**
   * Return a String representation of this object.
   */
  @Override
  public String toString() {
    if (filterConfig == null) {
      return ("AuthenticationFilter()");
    }
    StringBuffer sb = new StringBuffer("AuthenticationFilter(");
    sb.append(filterConfig);
    sb.append(")");
    return (sb.toString());
  }

  private void sendProcessingError(Throwable t, ServletResponse response) {
    String stackTrace = getStackTrace(t);

    if (stackTrace != null && !stackTrace.equals("")) {
      try {
        response.setContentType("text/html");
        PrintStream ps = new PrintStream(response.getOutputStream());
        PrintWriter pw = new PrintWriter(ps);
        pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

        // PENDING! Localize this for next official release
        pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
        pw.print(stackTrace);
        pw.print("</pre></body>\n</html>"); //NOI18N
        pw.close();
        ps.close();
        response.getOutputStream().close();
      } catch (Exception ex) {
      }
    } else {
      try {
        PrintStream ps = new PrintStream(response.getOutputStream());
        t.printStackTrace(ps);
        ps.close();
        response.getOutputStream().close();
      } catch (Exception ex) {
      }
    }
  }

  public static String getStackTrace(Throwable t) {
    String stackTrace = null;
    try {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      t.printStackTrace(pw);
      pw.close();
      sw.close();
      stackTrace = sw.getBuffer().toString();
    } catch (Exception ex) {
    }
    return stackTrace;
  }

  public void log(String msg) {
    filterConfig.getServletContext().log(msg);
  }

  /**
   * Returns a status code for authentication. This is used every time a page is
   * loaded.
   *
   * @param request The HttpServletRequest object whose session info and cookies
   * will be extracted from.
   * @return An integer status code depicting the authentication result:
   * <ul>
   * <li>1 if successful user authentication with session stored</li>
   * <li>2 if successful user authentication with cookies stored</li>
   * <li>3 if successful admin authentication with session stored</li>
   * <li>4 if successful admin authentication with cookie stored</li>
   * <li>-1 if unsuccessful authentication</li>
   * </ul>
   */
  public int getAuthStatus(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    int authStatus = -1;

    HttpSession session = request.getSession();
    boolean hasUserSession = (session.getAttribute("user") != null
            && !(((String) session.getAttribute("user")).isEmpty()));
    boolean hasAdminSession = (session.getAttribute("admin") != null
            && !(((String) session.getAttribute("admin")).isEmpty()));
    if (hasUserSession) {
      authStatus = 1;
    } else if (hasAdminSession) {
      authStatus = 2;
    } else if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("user")) {
          authStatus = 1;
          break;
        } else if (cookie.getName().equals("admin")) {
          authStatus = 2;
          break;
        }
      }
    }
    return authStatus;
  }

  public void logout(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        Cookie newCookie = new Cookie(cookie.getName(), null);
        newCookie.setMaxAge(0);
        newCookie.setPath("/");
        response.addCookie(newCookie);
      }
    }
    HttpSession session = request.getSession();
    session.invalidate(); // destroy session
    String contextPath = request.getContextPath().replace("/logout", "");
    response.sendRedirect("/" + contextPath);
  }
}
