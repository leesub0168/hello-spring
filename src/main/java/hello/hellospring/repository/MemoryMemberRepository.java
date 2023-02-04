package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryMemberRepository implements MemberRepository{
    // 동시성을 고려하면 ConcurrentHashMap 사용해야함
    private static Map<Long, Member> store = new HashMap<>();

    // 동시성을 고려하면 AtomicLong을 사용해야함
    private static long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
