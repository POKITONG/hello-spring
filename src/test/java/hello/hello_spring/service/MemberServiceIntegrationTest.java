package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
    // Transaction 에 테스트에 사용하는 데이터를 전부 넣어서 나중에 롤백해주기 때문에
    // 기존 데이터베이스의 변경 없이 테스트를 진행할 수 있다.
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

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

}