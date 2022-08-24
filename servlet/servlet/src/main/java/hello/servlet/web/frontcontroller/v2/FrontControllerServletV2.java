package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * dispatcher.forward()를 직접 생성해서 호출하지 않고 MyView객체를 생성하여 뷰 이름을 넣고 반환한다.
 */
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {
    /**
     * 컨트롤러 맵핑
     */
    private Map<String, ControllerV2> controllerMap = new HashMap<>(); public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI(); //요청 URI 획득
        ControllerV2 controller = controllerMap.get(requestURI); // 맵핑된 컨트롤러 획득
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return; }

        MyView view = controller.process(request, response); // View path 및 Data 획득
        view.render(request, response); //랜더링
    }
}