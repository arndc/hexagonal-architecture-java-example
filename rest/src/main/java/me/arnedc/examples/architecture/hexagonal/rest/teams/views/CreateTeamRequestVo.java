package me.arnedc.examples.architecture.hexagonal.rest.teams.views;

public class CreateTeamRequestVo {

    private NewTeamVo team;

    public NewTeamVo getTeam() {
        return team;
    }

    public static final class NewTeamVo {

        private String name;

        public String getName() {
            return name;
        }

    }

}
