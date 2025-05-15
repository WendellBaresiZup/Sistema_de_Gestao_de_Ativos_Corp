package com.zup.gestaodeativos.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementLogResponseDTO {
    private Long id;
    private ActiveResponseDTO activeResponseDTO;
    private UserResponseDTO userResponseDTO;
    private LocalDateTime movementDate;
    private String action;

}
