package hello.servlet.web.springmvc.old;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
  1. 핸들러 매핑으로 핸들러 조회
    HandlerMapping을 순서대로 실행해서, 핸들러를찾는다.
    이 경우 빈 이름으로 핸들러를 찾아야 하기 때문에 BeanNameUrlHandlerMapping가 실행에 성공하고 핸들러인 OldController를 반환한다.

 2. 핸들러어댑터조회
    HandlerAdapter의 supports()를 순서대로 호출한다.
    SimpleControllerHandlerAdapter가 Controller 인터페이스를 지원하므로 대상이된다.

 3. 핸들러 어댑터 실행
    디스패처 서블릿이 조회한 SimpleControllerHandlerAdapter를 실행하면서 핸들러 정보도 함께 넘겨준다.
    SimpleControllerHandlerAdapter는핸들러인OldController를내부에서실행하고, 그 결과를 반환한다.

 뷰 리졸버 동작 방식
 1. 핸들러 어댑터 호출
    * 핸들러 어댑터를 통해 new-form이라는 논리뷰 이름을 획득한다.
 2. ViewResolver 호출
    * new-form이라는 뷰 이름으로 viewResolver를 순서대로 호출한다.
    * BeanNameViewResolver는 new-form이라는 이름의 스프링빈으로 등록된 뷰를 찾아야 하는데 없다.
    * InternalResourceViewResolver가 호출된다.
 3. InternalResourceViewResolver
    * 이 뷰 리졸버는 InternalResourceView를 반환한다.
 4. 뷰 - InternalResourceView
    * InternalResourceView는 JSP처럼 포워드 forward()를 호출해서 처리할 수 있는경우에 사용한다.
 5. view.render()
    * view.render()가 호출되고 InternalResourceView는 forward()를 사용해서 JSP를 실행한다.
 */
@Component("/springmvc/old-controller")
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        //return null;
        return new ModelAndView("new-form");
    }
}
