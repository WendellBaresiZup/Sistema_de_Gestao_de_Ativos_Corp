package com.zup.gestaodeativos.mapper;

import com.zup.gestaodeativos.dto.AssetAssignmentDTO;
import com.zup.gestaodeativos.models.Active;
import com.zup.gestaodeativos.models.AssetAssignment;
import com.zup.gestaodeativos.models.User;

public class AssetAssignmentMapper {

    public static AssetAssignmentDTO toDto(AssetAssignment assignment) {
        if (assignment == null) {
            return null;
        }
        AssetAssignmentDTO dto = new AssetAssignmentDTO();
        dto.setId(assignment.getId());

        Active active = assignment.getActive();
        if (active != null) {
            dto.setActiveId(active.getId());
            dto.setActiveSerialNumber(active.getSerialNumber());
            dto.setActiveType(active.getType());
        }

        User user = assignment.getUser();
        if (user != null) {
            dto.setUserId(user.getId());
            dto.setUserName(user.getNome());
            dto.setUserEmail(user.getEmail());
        }

        dto.setAssignedAt(assignment.getAssignedAt());
        dto.setUnassignedAt(assignment.getUnassignedAt());
        return dto;
    }
}
