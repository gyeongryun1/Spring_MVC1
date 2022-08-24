package hello.servlet.web.frontcontroller.v3.controller;
import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import java.util.Map;
public class MemberSaveControllerV3 implements ControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        // paraMap에서 request Data 획득
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        //비지니스 로직
        Member member = new Member(username, age);
        memberRepository.save(member);

        //뷰의 논리 경로 주입
        ModelView mv = new ModelView("save-result");
        //model에 Data 주입
        mv.getModel().put("member", member);
        return mv;
    }
}
