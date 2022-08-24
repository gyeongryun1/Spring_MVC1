package hello.servlet.web.frontcontroller.v1;
import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 프론트 컨트롤러 -> 컨트롤러 호출 기능 도입. URI는 프론트 컨트롤러에서 일괄처리
 */
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    /**
     * controller 맵핑
     */
    private Map<String, ControllerV1> controllerMap = new HashMap<>(); public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("FrontControllerServletV1.service");
        String requestURI = request.getRequestURI(); // 요청 URI 획득
        ControllerV1 controller = controllerMap.get(requestURI); // 맵핑된 컨트롤러 획득
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return; }
        controller.process(request, response); //컨트롤러 실행
    }
}
