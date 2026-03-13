package net.toujoustudios.kazunyaapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.model.RoleplayMessage;
import net.toujoustudios.kazunyaapi.repository.RoleplayMessageRepository;
import net.toujoustudios.kazunyaapi.dto.request.RoleplayMessageRequest;
import net.toujoustudios.kazunyaapi.type.InteractionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roleplay-messages")
@RequiredArgsConstructor
public class RoleplayMessageController {

    private final RoleplayMessageRepository repository;

    @GetMapping
    public ResponseEntity<List<RoleplayMessage>> get() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleplayMessage> get(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleplayMessage> add(@Valid @RequestBody RoleplayMessageRequest request) {
        RoleplayMessage saved = save(request, new RoleplayMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleplayMessage> update(@PathVariable int id, @Valid @RequestBody RoleplayMessageRequest request) {
        return repository.findById(id)
                .map(existing -> ResponseEntity.ok(save(request, existing)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!repository.existsById(id))
            return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private RoleplayMessage save(RoleplayMessageRequest request, RoleplayMessage o) {
        o.setMessage(request.getMessage());
        o.setType(InteractionType.valueOf(request.getType()));
        return repository.save(o);
    }

}
