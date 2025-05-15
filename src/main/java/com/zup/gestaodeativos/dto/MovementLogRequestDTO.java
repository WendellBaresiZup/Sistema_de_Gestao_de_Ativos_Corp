package com.zup.gestaodeativos.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementLogRequestDTO {
    private Long activeId;
    private Long userId;
    private String action;

}
