package me.arnedc.examples.architecture.hexagonal.rest.teams.views;

public class TeamWithOnlyIdVo {

    private final String id;

    public TeamWithOnlyIdVo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
