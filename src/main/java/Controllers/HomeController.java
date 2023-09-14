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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class HomeController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        FoodDAO dao = new FoodDAO();
        ResultSet rs = dao.getAll();
        List<Food> foodList = new ArrayList<>();
        try {
            while (rs.next()) {                
                Food food = new Food(rs.getShort("food_id"),
                      rs.getString("food_name"),
                      rs.getBigDecimal("food_price"),
                      rs.getByte("discount_percent"),
                      rs.getString("food_img_url"),
                      rs.getByte("food_type_id"),
                      dao.getFoodType(rs.getByte("food_type_id")));
              foodList.add(food);         
            }
        } catch (Exception e) {            
        }
        request.setAttribute("foodList", foodList);
        request.getRequestDispatcher("index.jsp").forward(request, response);
        
        FoodTypeDAO dao2 = new FoodTypeDAO();
        ResultSet rs2 = dao2.getAllFoodType();
        List<FoodType> foodTypeList = new ArrayList<>();
        try {
            while (rs.next()) {                
                FoodType foodType = new FoodType(rs2.getByte("foodTypeID"),
                        rs2.getString("foodType"));
              foodTypeList.add(foodType);         
            }
        } catch (Exception e) {            
        }
        request.setAttribute("foodTypeList", foodList);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

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
        FoodDAO dao = new FoodDAO();
        ResultSet rs = dao.getAll();
        List<Food> foodList = new ArrayList<>();
        try {
                while (rs.next()) {
                    Food food = new Food(rs.getShort("food_id"),
                            rs.getString("food_name"),
                            rs.getBigDecimal("food_price"),
                            rs.getByte("discount_percent"),
                            rs.getString("food_img_url"),
                            rs.getByte("food_type_id"),
                            dao.getFoodType(rs.getByte("food_type_id")));
                    foodList.add(food);
                }
            } catch (Exception e) {
            }
        request.setAttribute("foodList", foodList);
        
        List<String> imgURLList = new ArrayList<>();
        imgURLList.add("assets/img/gallery/com_tam.jpg");
        imgURLList.add("assets/img/gallery/noodles.png");
        imgURLList.add("assets/img/gallery/sub-sandwich.png");
        imgURLList.add("assets/img/gallery/junk_food.jpg");
        imgURLList.add("assets/img/gallery/dessert.jpg");
        imgURLList.add("assets/img/gallery/drinks.jpg");
       
        FoodTypeDAO dao1 = new FoodTypeDAO();
        ResultSet rs1 = dao1.getAllFoodType();
        List<FoodType> foodTypeList = new ArrayList<>();
        try {
            while (rs1.next()) {
                FoodType foodType = new FoodType(rs1.getByte("food_type_id"),
                        rs1.getString("food_type"));
                foodTypeList.add(foodType);
            }
        } catch (Exception e) {
        }
        for (int i = 0; i < 6; i++) {
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
