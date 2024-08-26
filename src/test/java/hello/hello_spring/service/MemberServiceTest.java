package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        // 각각의 테스트는 독립적으로 실행되어야 하기 때문에 메서드 실행 전에 항상 생성해주어야 한다.
        // 외부에서 MemberService에 MemoryMemberRepository를 넣어주기 때문에 이를 의존성 주입 (DI) 라고 한다.
    }

    @AfterEach
    public void afterEach() {

        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given 뭔가 주어졌는데
        Member member = new Member();
        member.setName("hello");

        //when 이걸 실행했을 때
        long saveId = memberService.join(member);

        //then 이런 결과가 나와야 된다
        Member findeMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findeMember.getName());

    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // IllegalStateException이라는 클래스가 발생해야 하고 그 다음으로 람다가 넘어가야 함
        // 해당 로직을 태울 때 오른쪽으로 들어가야 함
        // memberService.join(member2)를 넣으면 예외가 터져야한다.
        // 뒤의 memberService.join(member2) 라는 로직을 태울건데 해당 로직을 태우면 앞의
        // IllegalStateException.class 라는 예외가 터져야 한다는 의미

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

     /*   try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        //then

    }

    @Test
    void findMember() {
    }

    @Test
    void findOne() {
    }
}