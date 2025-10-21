package com.equipe_sc.sistemas_corporativos.role;

import com.equipe_sc.sistemas_corporativos.role.dtos.RoleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAll(){
        return ResponseEntity.ok(this.service.getAll());
    }

}
