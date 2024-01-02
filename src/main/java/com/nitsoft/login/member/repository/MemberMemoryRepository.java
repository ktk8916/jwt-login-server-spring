package com.nitsoft.login.member.repository;

import com.nitsoft.login.member.domain.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberMemoryRepository {

    private static Long memberIdSequence = 0L;
    private static final Map<Long, Member> memberMap = new ConcurrentHashMap<>();

    public Member save(Member member){
        memberIdSequence += 1;
        member.setSequenceId(memberIdSequence);
        memberMap.put(memberIdSequence, member);
        return member;
    }

    public Optional<Member> findByUsername(String username){
        return memberMap.values()
                .stream()
                .filter(member -> member.getUsername().equals(username))
                .findFirst();
    }

    public Optional<Member> findByUsernameAndPassword(String username, String password){
        return memberMap.values()
                .stream()
                .filter(member -> member.checkLoginInfo(username, password))
                .findFirst();
    }

    public List<Member> findAll(){
        return memberMap.values()
                .stream()
                .toList();
    }

    public void clear(){
        memberIdSequence = 0L;
        memberMap.clear();
    }
}
