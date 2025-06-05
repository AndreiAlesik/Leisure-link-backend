package work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties
@EnableCaching
public class LeisureLinkBackend {
  //    private final InitializerBean initializerBean;
  //
  //    public LeisureLinkBackend(InitializerBean initializerBean) {
  //        this.initializerBean = initializerBean;
  //    }

  public static void main(String[] args) {
    SpringApplication.run(LeisureLinkBackend.class, args);
  }

  //    @Bean
  //    public void initCategories(){
  //        initializerBean.createCategoriesIfNotExist();
  //    }
}
