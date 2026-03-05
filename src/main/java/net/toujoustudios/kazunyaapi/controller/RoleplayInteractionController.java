package net.toujoustudios.kazunyaapi.controller;

import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.model.RoleplayInteraction;
import net.toujoustudios.kazunyaapi.model.RoleplayInteractionImage;
import net.toujoustudios.kazunyaapi.repository.RoleplayInteractionImageRepository;
import net.toujoustudios.kazunyaapi.repository.RoleplayInteractionRepository;
import net.toujoustudios.kazunyaapi.request.RoleplayInteractionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roleplay-interactions")
@RequiredArgsConstructor
public class RoleplayInteractionController {

    private final RoleplayInteractionRepository repository;
    private final RoleplayInteractionImageRepository imageRepository;

    @GetMapping
    public ResponseEntity<List<RoleplayInteraction>> get() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleplayInteraction> get(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleplayInteraction> add(@RequestBody RoleplayInteractionRequest request) {
        RoleplayInteraction saved = save(request, new RoleplayInteraction());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleplayInteraction> update(@PathVariable int id, @RequestBody RoleplayInteractionRequest request) {
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

    private RoleplayInteraction save(RoleplayInteractionRequest request, RoleplayInteraction o) {
        if (request.name() == null || request.name().isBlank())
            throw new IllegalArgumentException("Name is required");
        List<RoleplayInteractionImage> images = imageRepository.findAllById(request.images());
        if (images.size() != request.images().size())
            throw new IllegalArgumentException("One or more image IDs do not exist");
        o.setName(request.name());
        o.setImages(images);
        return repository.save(o);
    }

}
