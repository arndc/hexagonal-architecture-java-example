package me.arnedc.examples.architecture.hexagonal.rest.teams;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import me.arnedc.examples.architecture.hexagonal.core.domain.teams.Member;
import me.arnedc.examples.architecture.hexagonal.core.domain.teams.Team;
import me.arnedc.examples.architecture.hexagonal.core.ports.driving.ApplicationService;
import me.arnedc.examples.architecture.hexagonal.rest.teams.views.CreateTeamRequestVo;
import me.arnedc.examples.architecture.hexagonal.rest.teams.views.FullTeamVo;
import me.arnedc.examples.architecture.hexagonal.rest.teams.views.TeamWithOnlyIdVo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequestMapping("/teams")
@RestController
public class TeamRestController {

    private final ApplicationService applicationService;

    public TeamRestController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<TeamWithOnlyIdVo> createTeam(@RequestHeader("USER-TOKEN") String userToken,
                                                       @RequestBody CreateTeamRequestVo createTeamRequestVo) {
        final Team team = applicationService.createTeam(createTeamRequestVo.getTeam().getName(), userToken);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(team.getId()).toUri();

        return ResponseEntity.created(location).body(new TeamWithOnlyIdVo(team.getId().toString()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullTeamVo> getTeam(@PathVariable("id") String teamId) {
        final Optional<Team> optionalTeam = applicationService.retrieveTeamWithMembers(teamId);

        if (optionalTeam.isEmpty()) return ResponseEntity.notFound().build();

        final Team team = optionalTeam.get();
        final Optional<List<Member>> optionalMembers = team.getMembers();
        if (optionalMembers.isEmpty()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        final FullTeamVo teamVo = new FullTeamVo(team.getId().toString(), team.getName(), optionalMembers.get());

        return ResponseEntity.ok(teamVo);
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<TeamWithOnlyIdVo> createTeam(@RequestHeader("USER-TOKEN") String userToken,
                                                       @PathVariable("id") String teamId) {
        final UUID teamUuid = applicationService.joinTeam(teamId, userToken);

        return ResponseEntity.ok(new TeamWithOnlyIdVo(teamUuid.toString()));
    }

}
