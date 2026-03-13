package net.toujoustudios.kazunyaapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.model.RoleplayInteraction;
import net.toujoustudios.kazunyaapi.dto.request.RoleplayInteractionRequest;
import net.toujoustudios.kazunyaapi.service.RoleplayInteractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roleplay-interactions")
@RequiredArgsConstructor
public class RoleplayInteractionController {

    private final RoleplayInteractionService service;

    @GetMapping
    public ResponseEntity<List<RoleplayInteraction>> get(@RequestParam(required = false) String name, @RequestParam(required = false) String type, @RequestParam(required = false) String gender) {
        return ResponseEntity.ok(service.search(name, type, gender));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleplayInteraction> get(@PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleplayInteraction> add(@Valid @RequestBody RoleplayInteractionRequest request) {
        RoleplayInteraction saved = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleplayInteraction> update(@PathVariable int id, @Valid @RequestBody RoleplayInteractionRequest request) {
        return service.findById(id)
                .map(existing -> ResponseEntity.ok(service.update(id, request)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!service.exists(id))
            return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
