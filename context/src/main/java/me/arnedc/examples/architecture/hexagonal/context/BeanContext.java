package me.arnedc.examples.architecture.hexagonal.context;

import javax.persistence.EntityManager;

import me.arnedc.examples.architecture.hexagonal.core.application.DefaultApplicationService;
import me.arnedc.examples.architecture.hexagonal.core.domain.teams.TeamDomainService;
import me.arnedc.examples.architecture.hexagonal.core.domain.users.UserDomainService;
import me.arnedc.examples.architecture.hexagonal.core.ports.driven.TeamProvider;
import me.arnedc.examples.architecture.hexagonal.core.ports.driven.UserProvider;
import me.arnedc.examples.architecture.hexagonal.core.ports.driving.ApplicationService;
import me.arnedc.examples.architecture.hexagonal.persistence.teams.DefaultTeamProvider;
import me.arnedc.examples.architecture.hexagonal.persistence.teams.repositories.TeamRepository;
import me.arnedc.examples.architecture.hexagonal.persistence.users.DefaultUserProvider;
import me.arnedc.examples.architecture.hexagonal.persistence.users.repositories.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanContext {

    @Bean
    public UserProvider userProvider(UserRepository userRepository) {
        return new DefaultUserProvider(userRepository);
    }

    @Bean
    public UserDomainService userDomainService(UserProvider userProvider) {
        return new UserDomainService(userProvider);
    }

    @Bean
    public TeamProvider teamProvider(EntityManager entityManager, TeamRepository teamRepository) {
        return new DefaultTeamProvider(entityManager, teamRepository);
    }

    @Bean
    public TeamDomainService teamDomainService(TeamProvider teamProvider) {
        return new TeamDomainService(teamProvider);
    }

    @Bean
    public ApplicationService applicationService(TeamDomainService teamDomainService,
                                                 UserDomainService userDomainService) {
        return new DefaultApplicationService(teamDomainService, userDomainService);
    }

}
