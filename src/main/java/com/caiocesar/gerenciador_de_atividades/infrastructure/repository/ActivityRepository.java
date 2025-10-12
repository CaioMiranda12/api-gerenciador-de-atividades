package com.caiocesar.gerenciador_de_atividades.infrastructure.repository;

import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
