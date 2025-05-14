package com.zup.gestaodeativos.controlles;

import com.zup.gestaodeativos.dto.ActiveRequestDTO;
import com.zup.gestaodeativos.dto.ActiveResponseDTO;
import com.zup.gestaodeativos.services.ActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actives")
public class ActiveController {

    @Autowired
    private ActiveService activeService;

    @PostMapping
    public ResponseEntity<ActiveResponseDTO> createActive(@RequestBody ActiveRequestDTO activeRequestDTO) {
        ActiveResponseDTO createdActive = activeService.create(activeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActive(@PathVariable Long id) {
        try {
            activeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ActiveResponseDTO>> getAllActives() {
        List<ActiveResponseDTO> actives = activeService.listAll();
        return ResponseEntity.ok(actives);
    }
}