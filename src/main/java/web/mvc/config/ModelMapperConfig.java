package web.mvc.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapper 라이브러리를 사용하면 객체 간의 변환을 자동으로 처리.
 * 이를 통해 DTO를 엔티티로, 엔티티를 DTO로 변환하는 과정을 간소화
 */
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
