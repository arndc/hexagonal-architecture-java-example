package me.arnedc.examples.architecture.hexagonal.rest.teams.views;

import java.util.List;
import java.util.stream.Collectors;

import me.arnedc.examples.architecture.hexagonal.core.domain.teams.Member;

public class FullTeamVo {

    private final String id;
    private final String name;
    private final List<MemberVo> members;

    public FullTeamVo(String id, String name, List<Member> members) {
        this.id = id;
        this.name = name;
        this.members = members
                .stream()
                .map(member -> new MemberVo(member.getId().toString(), member.getName()))
                .collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MemberVo> getMembers() {
        return members;
    }

    public static final class MemberVo {

        private final String id;
        private final String name;

        public MemberVo(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

    }

}
