package com.caiocesar.gerenciador_de_atividades.dto.activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateActivityDTO {
    private String description;
    private LocalDate dueDate;
    private Long groupId;
}
