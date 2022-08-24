package hello.servlet.web.frontcontroller.v3;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**ModelView 도입
 * 서블릿 종속성 제거
 *  ModelView에 Model을 반환(request.setAttribute 제거)
 *
 * 뷰 이름 중복 제거(뷰의 논리 이름을 ModelView에 반환)
 *  /WEB-INF/views/new-form.jsp =>   new-form
 *  /WEB-INF/views/save-result.jsp =>   save-result
 *  /WEB-INF/views/members.jsp =>   members
 */
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    //컨트롤러 맵핑
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //요청 URI 획득
        String requestURI = request.getRequestURI();
        // 컨트롤러 획득
        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //request, response 전달 객체 생성
        Map<String, String> paramMap = createParamMap(request);
        //뷰의 논리이름 및 Data 획득
        ModelView mv = controller.process(paramMap);
        String viewName = mv.getViewName();
        //ViewResolver로 뷰의 논리 이름을 실제이름으로 변환하여 View에 주입
        MyView view = viewResolver(viewName);
        //model을 request.setAttribute에 주입. 랜더링
        view.render(mv.getModel(), request, response);
    }

    /** Iterator?*/
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName,
                        request.getParameter(paramName)));
        return paramMap;
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}