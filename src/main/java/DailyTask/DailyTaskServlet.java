/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package DailyTask;

import DAOs.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import Models.Account;
import java.sql.Timestamp;

public class DailyTaskServlet extends HttpServlet {
    private ScheduledExecutorService scheduler;

    @Override
    public void init() throws ServletException {
        super.init();
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Lên lịch thực hiện công việc hàng ngày lúc 3:00 AM
        scheduler.scheduleAtFixedRate(new DailyTask(), calculateInitialDelay(), 1, TimeUnit.DAYS);
    }

    @Override
    public void destroy() {
        scheduler.shutdownNow();
        super.destroy();
    }

    private long calculateInitialDelay() {
        // Lấy ngày hiện tại và thiết lập nó thành 3:00 AM
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 3);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);

        // Nếu đã qua giờ 3:00 AM, lên lịch công việc cho ngày tiếp theo
        if (currentDate.getTimeInMillis() < System.currentTimeMillis()) {
            currentDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Tính thời gian còn lại đến 3:00 AM
        return currentDate.getTimeInMillis() - System.currentTimeMillis();
    }

    private class DailyTask implements Runnable {
        @Override
        public void run() {
           AccountDAO accountDAO = new AccountDAO();
           List<Account> accountList = new ArrayList<>();
           accountList = accountDAO.getAllUser();
           
           Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            for (Account a : accountList) {
                if (a.getLasttime_order().after(currentTime) && a.getLasttime_order() != null) {
                    int result = accountDAO.delete(a.getAccountID());
                    if (result == 1) {
                        log("Delete an acount ID: " + a.getAccountID() + "Successfully!");
                    } else {
                        log("Delete an acount ID: " + a.getAccountID() + "Fail!");
                    }
                } 
            }
        }
    }
}
