package basic.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void clear() {
        memberRepository.clearAll();
    }

    @Test
    void save() {
        //given
        Member member = new Member();
        member.setUsername("kim");
        member.setAge(26);
        //when
        Member savedMember = memberRepository.save(member);
        //then
        Assertions.assertThat(savedMember).isEqualTo(member);
    }

    @Test
    void findById() {
        //given
        Member member = new Member();
        member.setUsername("kim");
        member.setAge(26);
        //when
        Member savedMember = memberRepository.save(member);
        Long savedMemberId = savedMember.getId();
        //then
        Assertions.assertThat(savedMemberId).isEqualTo(memberRepository.findById(savedMemberId).getId());
    }

    @Test
    void findByAll() {
        //given
        Member member1 = new Member();
        member1.setUsername("kim");
        member1.setAge(26);
        Member member2 = new Member();
        member2.setUsername("lee");
        member2.setAge(25);
        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        //then
        Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

}