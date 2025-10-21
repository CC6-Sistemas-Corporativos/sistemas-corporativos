package com.equipe_sc.sistemas_corporativos.config;

import com.equipe_sc.sistemas_corporativos.role.Role;
import com.equipe_sc.sistemas_corporativos.role.RoleName;
import com.equipe_sc.sistemas_corporativos.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Order(1)
@Component
public class InitializerConfig implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) throws Exception {
        this.logger.info("[InitializerConfig] - Verifying roles...");
        this.verifyRoles();
    }

    @Transactional
    protected void verifyRoles(){
        Set<Role> existingRoles = new HashSet<>(this.roleService.findAll());

        if(existingRoles.isEmpty()){
            this.logger.info("[StartupInitializer] No roles found. Creating default roles...");
            for (RoleName roleName : RoleName.values()) {
                this.roleService.create(roleName);
                this.logger.info("[StartupInitializer] Created role: {}", roleName);
            }
        } else {
            this.logger.info("[StartupInitializer] Roles already exist.");
        }
    }

}
