/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.FoodDAO;
import DAOs.FoodTypeDAO;
import Models.Food;
import Models.FoodType;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.io.PrintWriter;

public class HomeController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                  
        // Set the JSON string in the session
        HttpSession session = request.getSession();
        Gson gson = new Gson();
        FoodDAO foodDAO = new FoodDAO();
        List<Food> foodList = new ArrayList<>();
        
        foodList = foodDAO.getAllList();
        String jsonFoodList = gson.toJson(foodList);
        // Use triple slash (\\\") to escape ", because JSON.parse() doesn't work nicely with \"
        jsonFoodList = jsonFoodList.replace("\"", "\\\"");
        session.setAttribute("jsonFoodList", jsonFoodList);
        
        session.setAttribute("foodList", foodList);

        List<String> imgURLList = new ArrayList<>();
        String baseURL = "assets/img/gallery/";
        int numOfImages = 10;
        for (int i = 1; i <= numOfImages; i++) {
          imgURLList.add(baseURL + i + ".jpg");
        }

        FoodTypeDAO foodTypeDAO = new FoodTypeDAO();
        List<FoodType> foodTypeList = new ArrayList<>();
        foodTypeList = foodTypeDAO.getAllFoodType();
        for (int i = 0; i < 10; i++) {
            foodTypeList.get(i).setImgURL(imgURLList.get(i));
        }
        request.setAttribute("foodTypeList", foodTypeList);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
