package com.wensheng.zcc.amc.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
//  @Bean
//  public Docket apiDocket() {
//    return new Docket(DocumentationType.SWAGGER_2)
//        .select()
//        .apis(RequestHandlerSelectors.any())
//        .paths(PathSelectors.any())
//        .build();
//  }

  @Value("${spring.security.oauth2.client.registration.amc-client.client-id}")
  private String amcAdminClientId;


  @Value("${spring.security.oauth2.client.registration.amc-admin.secret}")
  private String amcAdminSecret;


  @Value("${spring.security.oauth2.client.registration.amc-admin.scopes}")
  private String amcAdminScopes;

  @Value("${spring.security.oauth2.client.registration.amc-admin.authorizedGrantTypes}")
  private String amcAdminAuthorizedGrantTypes;

  @Value("${spring.security.oauth2.client.registration.amc-admin.redirectUris}")
  private String amcAdminRedirectUris;



//  @Value("${info.build.name}")
  private String infoBuildName = "internal.test.0.0.1";

  @Value("${env.oauth2.authLink}")
  private String authLink ;

  @Bean
  public Docket api() {

    List<ResponseMessage> list = new ArrayList<>();
    list.add(new ResponseMessageBuilder().code(500).message("500 message")
        .responseModel(new ModelRef("Result")).build());
    list.add(new ResponseMessageBuilder().code(401).message("Unauthorized")
        .responseModel(new ModelRef("Result")).build());
    list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable")
        .responseModel(new ModelRef("Result")).build());

    return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any()).build().securitySchemes(Collections.singletonList(securitySchema()))
        .securityContexts(Collections.singletonList(securityContext())).pathMapping("/")
        .useDefaultResponseMessages(false).apiInfo(apiInfo()).globalResponseMessage(RequestMethod.GET, list)
        .globalResponseMessage(RequestMethod.POST, list);



  }

  private OAuth securitySchema() {

    List<AuthorizationScope> authorizationScopeList = new ArrayList();
    authorizationScopeList.add(new AuthorizationScope("read", "read all"));
    authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
    authorizationScopeList.add(new AuthorizationScope("write", "access all"));

    List<GrantType> grantTypes = new ArrayList();
    GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(authLink+"/oauth/token");

    grantTypes.add(creGrant);

    return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/**"))
        .build();
  }

  private List<SecurityReference> defaultAuth() {

    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
    authorizationScopes[0] = new AuthorizationScope("read", "read all");
    authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
    authorizationScopes[2] = new AuthorizationScope("write", "write all");

    return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
  }

  @Bean
  public SecurityConfiguration securityInfo() {
    return new SecurityConfiguration(amcAdminClientId, amcAdminSecret, "", "", "", ApiKeyVehicle.HEADER, "", " ");
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("My API title").description("")
        .termsOfServiceUrl("https://www.wenshengamc.com/api")
        .contact(new Contact("Chenwei", "http://www.example.com", "chenwei@wenshengamc.com"))
        .license("Open Source").licenseUrl("https://www.wenshengamc.com").version("2.0").build();
  }




}