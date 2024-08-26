package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    // 실무에서는 동시성 문제 때문에 공유되는 변수일 경우에는 concurrent 를 사용해야 하지만,
    // 단순 예제이기 때문에 HashMap 사용.
    private static long sequence = 0L;
    // sequence 는 0, 1, 2 이렇게 키값을 생성해주는 애
    // 실무에서는 마찬가지로 동시성 문제 때문에 Atomic, Long 등등을 사용해야 한다.

    @Override

    public Member save(Member member) {
        member.setId(++sequence);
        // 시스템에서 아이디 먼저 세팅
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(long id) {
        return Optional.ofNullable(store.get(id));
        // store에서 해당 id인 Map을 불러서 리턴해 주는데, 만약 null 값일 확률이 있을 경우 Optional.ofNullabe
        // 로 감싸줄 수 있다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        // 루프를 돌면서 해당 이름으로 값을 찾는다. 그래서 찾게 되면 반환을 하고,
        // 끝까지 루프를 돌렸는데도 찾지 못하면 optional에 null이 포함돼서 반환된다.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        // store에 있는 values(Member)가 쭉 반환된다.
    }

    public void clearStore() {
        store.clear();
    }
}
