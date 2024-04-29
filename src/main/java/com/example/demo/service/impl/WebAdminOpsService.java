package com.example.demo.service.impl;


import com.example.demo.repository.CustomerDetailsRepoService;
import com.example.demo.repository.RoleRepoService;
import com.example.demo.repository.UserRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class WebAdminOpsService {

    private final CustomerDetailsRepoService repoService;
    private final UserRepoService userRepoService;
    private final RoleRepoService roleRepoService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    WebAdminOpsService( CustomerDetailsRepoService repoService,UserRepoService userRepoService,
                        RoleRepoService roleRepoService,PasswordEncoder passwordEncoder
                        ){

        this.repoService = repoService;
        this.userRepoService = userRepoService;
        this.roleRepoService = roleRepoService;
        this.passwordEncoder = passwordEncoder;
    }

}
