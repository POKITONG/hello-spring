package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    // 서비스 클래스에서는 비즈니스에 관련된 용어들을 사용해야 한다. (메서드명)
    // 리포지토리에서는 좀 더 기계적인 용어를 사용

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입

    public long join(Member member) {

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

        //같은 이름이 있는 중복 회원X
        // Optional<Member> result = memberRepository.findByName(member.getName());
        // Optional 을 바로 반환하는 것은 비권장, 어차피 Optional<Member>을 사용하지 않아도
        // Optional 로 반환되기 때문에 .ifPresent 를 바로 사용할 수 있다.
        // result.orElseGet() 값이 있으면 꺼내고, 값이 없으면 () 안의 메서드를 실행 혹은 default 값을 넣어서 꺼냄
        // validateDuplicateMember(member);
        // 중복 회원 검증

        // findByName 처럼 로직이 쭉 나올 경우에 메서드로 뽑아주는 것이 좋다.
        /*memberRepository.save(member);
        return member.getId();*/
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                    // .ifPresent() 는 앞의 value (result)가 null이 아니고 해당하는 값이 있을 떄 동작하는 함수
                    // optional 로 감쌀 경우 Optional 안에 멤버 객체가 존재하는 것.
                    // 그렇게 감싸서 반환해주기 때문에 Optional 의 여러 메서드를 사용할 수 있다.
                });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
