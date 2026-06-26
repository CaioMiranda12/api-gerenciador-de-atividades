package com.caiocesar.gerenciador_de_atividades.dto.activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReorderActivityDTO {
    private Long id;
    private Long groupId;
    private Integer position;
}
