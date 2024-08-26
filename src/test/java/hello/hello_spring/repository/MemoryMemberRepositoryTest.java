package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // TDD : 테스트 주도 개발
    // 어떠한 클래스를 만들기 전에 테스트를 먼저 만들고 그 다음 구현 클래스를 만들어서 실행시켜보는 개발 방식

    @AfterEach
    public void afterEach() {


        // 테스트는 메서드가 실행되는 순서에 상관없이 서로 의존관계 없이 설계되어야 한다.
        // 그러기 위해서는 하나의 테스트가 끝날때마다 공용 저장소를 청소해주어야 한다.
        // AfterEach 는 테스트 메서드가 종료되고 해당 데이터를 클리어 해주는 메서드로써,
        // @AfterEach 를 메서드 위에 적어주게 되면 각 테스트 메서드들이 종료되고 나서 해당 메서드가 실행된다.
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        // optional 에서 값을 꺼낼 떄 .get()을 사용한다. (좋은 방법은 아니지만 테스트라 상관없음)
//        Assertions.assertEquals(member, result);
         assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        // .get()을 사용하면 optional 로 포장된 값을 까서 꺼낼 수 있다.

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
