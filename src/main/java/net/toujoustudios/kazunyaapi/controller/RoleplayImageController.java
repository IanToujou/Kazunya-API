package net.toujoustudios.kazunyaapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.model.RoleplayImage;
import net.toujoustudios.kazunyaapi.repository.RoleplayImageRepository;
import net.toujoustudios.kazunyaapi.dto.request.RoleplayImageRequest;
import net.toujoustudios.kazunyaapi.type.InteractionGender;
import net.toujoustudios.kazunyaapi.type.InteractionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roleplay-images")
@RequiredArgsConstructor
public class RoleplayImageController {

    private final RoleplayImageRepository repository;

    @GetMapping
    public ResponseEntity<List<RoleplayImage>> get() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleplayImage> get(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleplayImage> add(@Valid @RequestBody RoleplayImageRequest request) {
        RoleplayImage saved = save(request, new RoleplayImage());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleplayImage> update(@PathVariable int id, @Valid @RequestBody RoleplayImageRequest request) {
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

    private RoleplayImage save(RoleplayImageRequest request, RoleplayImage o) {
        o.setUrl(request.getUrl());
        o.setType(InteractionType.valueOf(request.getType()));
        o.setGenders(request.getGenders().stream()
                .map(InteractionGender::valueOf)
                .toList());
        return repository.save(o);
    }

}
