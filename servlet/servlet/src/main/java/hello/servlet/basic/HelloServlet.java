package hello.servlet.basic;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    /** HttpServletRequest의 역할
     * 1. HTTP 요청메시지를 파싱
     * 2. 임시 저장소 기능.
     *  - 저장: request.setAttribute(name,value)
     *  - 조회: request.getAttribute(name)
     * 3. 세션 관리 기능
     *  - request.getSesstion(create:true)
     */


    //cntl+O
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      log.info("request={}",request);
      log.info("response={}",response);

      String username = request.getParameter("username");
      log.info("uesername={}",username);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello"+username);
    }
}
