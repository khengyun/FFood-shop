package Controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Assert;

import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LoginController loginController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    
    @Test
    public void testLoginCase1()  throws Exception {
        // Mocking request parameters
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("anhnq1130@gmail.com");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883e");
        when(request.getParameter("chkRememberMe")).thenReturn("remember");

        // Mocking getSession() method
        when(request.getSession()).thenReturn(session);

        // Calling the doPost method
        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(true));
        verify(response, times(1)).sendRedirect(eq("/"));
        
        // Verifying that cookies are set correctly
        verify(response, times(1)).addCookie(argThat(cookie -> {
            if (cookie.getName().equals("user")) {
                Assert.assertEquals("user", "quocanh123", cookie.getValue());
                Assert.assertEquals("Expected path", "/", cookie.getPath());
                return true;
            }
            return false;
        }));
        
        verify(response, times(1)).addCookie(argThat(cookie -> {
            if (cookie.getName().equals("userID")) {
                Assert.assertEquals("Expected user ID string", String.valueOf(201), cookie.getValue());
                Assert.assertEquals("Expected path", "/", cookie.getPath());
                return true;
            }
            return false;
        }));
    }

    @Test
    public void testLoginCase2() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("anhnq1130@gmail.com");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883e");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(true));
        // Verify appropriate methods are called based on your business logic
        verify(session, times(1)).setAttribute(eq("user"), eq("quocanh123"));
        verify(session, times(1)).setAttribute(eq("userID"), eq(201));
        verify(response, times(1)).sendRedirect(eq("/"));
    }
    
    @Test
    public void testLoginCase3() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("anhnq1130@gmail.com");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883x");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(false));
        verify(response, times(1)).sendRedirect(eq("/home#failure_login_info"));
    }
    
    @Test
    public void testLoginCase4() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("");
        when(request.getAttribute("txtPassword")).thenReturn("");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(false));
        verify(response, times(1)).sendRedirect(eq("/home#failure_login_info"));
    }
    
    @Test
    public void testLoginCase5() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("anhnq1130@gmail.com");
        when(request.getAttribute("txtPassword")).thenReturn("");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(false));
        verify(response, times(1)).sendRedirect(eq("/home#failure_login_info"));
    }
    
    @Test
    public void testLoginCase6() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883e");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(false));
        verify(response, times(1)).sendRedirect(eq("/home#failure_login_info"));
    }
    
    @Test
    public void testLoginCase7()  throws Exception {
        // Mocking request parameters
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("thanhhtce171454@fpt.edu.vn");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883e");
        when(request.getParameter("chkRememberMe")).thenReturn("remember");

        // Mocking getSession() method
        when(request.getSession()).thenReturn(session);

        // Calling the doPost method
        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(true));
        verify(response, times(1)).sendRedirect(eq("/admin"));
        
        // Verifying that cookies are set correctly
        verify(response, times(1)).addCookie(argThat(cookie -> {
            if (cookie.getName().equals("admin")) {
                Assert.assertEquals("admin", "tienthanh123", cookie.getValue());
                Assert.assertEquals("Expected path", "/", cookie.getPath());
                return true;
            }
            return false;
        }));
        
        verify(response, times(1)).addCookie(argThat(cookie -> {
            if (cookie.getName().equals("adminID")) {
                Assert.assertEquals("Expected admin ID string", String.valueOf(4), cookie.getValue());
                Assert.assertEquals("Expected path", "/", cookie.getPath());
                return true;
            }
            return false;
        }));
    }
    
    @Test
    public void testLoginCase8() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("thanhhtce171454@fpt.edu.vn");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883e");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(true));
        // Verify appropriate methods are called based on your business logic
        verify(session, times(1)).setAttribute(eq("admin"), eq("tienthanh123"));
        verify(session, times(1)).setAttribute(eq("adminID"), eq(Byte.parseByte(String.valueOf(4))));
        verify(response, times(1)).sendRedirect(eq("/admin"));
    }
    
    @Test
    public void testLoginCase9()  throws Exception {
        // Mocking request parameters
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("teststaff1@fpt.edu.vn");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883e");
        when(request.getParameter("chkRememberMe")).thenReturn("remember");

        // Mocking getSession() method
        when(request.getSession()).thenReturn(session);

        // Calling the doPost method
        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(true));
        verify(response, times(1)).sendRedirect(eq("/staff"));
        
        // Verifying that cookies are set correctly
        verify(response, times(1)).addCookie(argThat(cookie -> {
            if (cookie.getName().equals("staff")) {
                Assert.assertEquals("staff", "testStaff1", cookie.getValue());
                Assert.assertEquals("Expected path", "/", cookie.getPath());
                return true;
            }
            return false;
        }));
        byte sID = 1;
        verify(response, times(1)).addCookie(argThat(cookie -> {
            if (cookie.getName().equals("staffID")) {
                Assert.assertEquals("Expected staff ID string", Byte.toString(sID), cookie.getValue());
                Assert.assertEquals("Expected path", "/", cookie.getPath());
                return true;
            }
            return false;
        }));
    }
    
    @Test
    public void testLoginCase10() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("teststaff1@fpt.edu.vn");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883e");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        
        loginController.doPost(request, response);
        
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(true));
        // Verify appropriate methods are called based on your business logic
        verify(session, times(1)).setAttribute(eq("staff"), eq("testStaff1"));
        verify(session, times(1)).setAttribute(eq("staffID"), eq(Byte.parseByte(String.valueOf(1))));
        verify(response, times(1)).sendRedirect(eq("/staff"));
    }
    
    @Test
    public void testLoginCase11()  throws Exception {
        // Mocking request parameters
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("testPromotion1@fpt.edu.vn");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883e");
        when(request.getParameter("chkRememberMe")).thenReturn("remember");

        // Mocking getSession() method
        when(request.getSession()).thenReturn(session);

        // Calling the doPost method
        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(true));
        verify(response, times(1)).sendRedirect(eq("/promotionManager"));
        
        // Verifying that cookies are set correctly
        verify(response, times(1)).addCookie(argThat(cookie -> {
            if (cookie.getName().equals("promotionManager")) {
                Assert.assertEquals("promotionManager", "testPromotion1", cookie.getValue());
                Assert.assertEquals("Expected path", "/", cookie.getPath());
                return true;
            }
            return false;
        }));
    }
    
    @Test
    public void testLoginCase12() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("testPromotion1@fpt.edu.vn");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883e");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(true));
        // Verify appropriate methods are called based on your business logic
        verify(session, times(1)).setAttribute(eq("promotionManager"), eq("testPromotion1"));
        verify(response, times(1)).sendRedirect(eq("/promotionManager"));
    }
    
    @Test
    public void testLoginCase13() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("abcxyz@fpt.edu.vn");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883x");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(false));
        verify(response, times(1)).sendRedirect(eq("/home#failure_login_info"));
    }
    
    @Test
    public void testLoginCase14() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("test1fpt.edu.vn");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883x");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(false));
        verify(response, times(1)).sendRedirect(eq("/home#failure_login_info"));
    }
    
    @Test
    public void testLoginCase15() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("abccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc@fpt.edu.vn");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883x");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(false));
        verify(response, times(1)).sendRedirect(eq("/home#failure_login_info"));
    }
    
    @Test
    public void testLoginCase16() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("abccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc@fpt.edu.vn");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883x");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(false));
        verify(response, times(1)).sendRedirect(eq("/home#failure_login_info"));
    }
    
    @Test
    public void testLoginCase17() throws Exception {
        when(request.getParameter("btnSubmit")).thenReturn("Submit");
        when(request.getParameter("txtEmail")).thenReturn("test1$%@fpt@edu.vn");
        when(request.getAttribute("txtPassword")).thenReturn("e10adc3949ba59abbe56e057f20f883x");
        when(request.getParameter("chkRememberMe")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        loginController.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("isSuccessful"), eq(false));
        verify(response, times(1)).sendRedirect(eq("/home#failure_login_info"));
    }
}
