package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(long id);
    // 해당 값이 null 일 떄 null을 그대로 반환하는 대신 Optional 로 감싸서 반환하는 방식을 선호.

    Optional<Member> findByName(String name);

    List<Member> findAll();
}
