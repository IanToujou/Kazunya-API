package net.toujoustudios.kazunyaapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.model.RoleplayMessage;
import net.toujoustudios.kazunyaapi.dto.request.RoleplayMessageRequest;
import net.toujoustudios.kazunyaapi.service.RoleplayMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roleplay-messages")
@RequiredArgsConstructor
public class RoleplayMessageController {

    private final RoleplayMessageService service;

    @GetMapping
    public ResponseEntity<List<RoleplayMessage>> get() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleplayMessage> get(@PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleplayMessage> add(@Valid @RequestBody RoleplayMessageRequest request) {
        RoleplayMessage saved = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleplayMessage> update(@PathVariable int id, @Valid @RequestBody RoleplayMessageRequest request) {
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
