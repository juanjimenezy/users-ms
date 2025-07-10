package co.com.pragma.usersms.config;

import co.com.pragma.usersms.model.users.gateways.ReqresRepository;
import co.com.pragma.usersms.model.users.gateways.UserRedisRepository;
import co.com.pragma.usersms.model.users.gateways.UserRepository;
import co.com.pragma.usersms.usecase.users.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.pragma.usersms.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

        @Bean
        public UserUseCase userUseCase(UserRepository userRepository, ReqresRepository reqresRepository, UserRedisRepository userRedisRepository) {
                return new UserUseCase(userRepository, reqresRepository, userRedisRepository);
        }

}
