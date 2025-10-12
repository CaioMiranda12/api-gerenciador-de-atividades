package com.caiocesar.gerenciador_de_atividades.infrastructure.repository;

import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
