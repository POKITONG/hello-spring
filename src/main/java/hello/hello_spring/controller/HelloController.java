package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        // @RequestParam 어노테이션에서 Ctrl + P를 눌러보면 파라미터 정보를 볼 수 있다.
        // 그 중 Required라는 옵션이 디폴트가 true인데, required = false 로 입력을 해주지 않는 이상
        // 무조건 값을 넘겨주어야 하므로 URL창에 hello-mvc?name=??? 이런 식으로
        // hello-template에서 받을 name의 데이터 값을 넘겨주어야 한다.
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    // html의 body를 뜻하는 것이 아니라, http 통신 프로토콜의 바디부에 return되는 데이터를 직접 넣어주겠다는 의미
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;

        // MVC와는 다르게 view를 가치지 않고 해당 문자가 요청한 클라이언트에 바로 내려간다.

    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;

        // ResponseBody를 사용하면 JSON 방식으로 데이터가 반환된다.
        // ResponseBody라는 어노테이션을 사용하게 웹 브라우저에서 톰캣 내장 서버를 통해 스프링 내부로 데이터를 넘길 때,
        // 컨트롤러가 있는지 확인 => 컨트롤러에 hello-api가 있다 라는 것을 확인하고, 해당 컨트롤러에서
        // viewResolver가 아닌 HttpMessageConverter로 넘기게 된다.
        // 뷰어 템플릿으로 넘겨서 처리하지 않고 HTTP의 응답에 그대로 데이터를 넣어서 동작
        // HttpMessageConverter에는 JsonConverter와 StirngConverter가 있는데, 객체가 들어왔을 경우
        // JsonConverter로 넘겨주어 해당 객체 내의 데이터들을 JSON 방식으로 데이터를 만들어서 HTTP 응답에 반환한다.
    }

    static class Hello {
        // static 클래스로 메서드를 만들게 되면 해당 클래스 안에서 만들어진 class 메서드를 그냥 사용할 수 있다.
        // 자바에서 정식으로 지원하는 문법 (HelloController.Hello 이런식으로 사용 가능)
        private String name;
        // name 이라는 필드변수가 private이기 때문에 외부에서 바로 꺼내서 사용하지 못 한다.
        // 그래서 라이브러리나 개발자가 사용할때도 Getter Setter와 같은 메서드를 이용해서 접근하게 되고,
        // 이것을 JavaBean 표준 방식이라고 한다. or 프로퍼티 접근방식

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
