package net.toujoustudios.kazunyaapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.model.RoleplayImage;
import net.toujoustudios.kazunyaapi.dto.request.RoleplayImageRequest;
import net.toujoustudios.kazunyaapi.service.RoleplayImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roleplay-images")
@RequiredArgsConstructor
public class RoleplayImageController {

    private final RoleplayImageService service;

    @GetMapping
    public ResponseEntity<List<RoleplayImage>> get() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleplayImage> get(@PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleplayImage> add(@Valid @RequestBody RoleplayImageRequest request) {
        RoleplayImage saved = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleplayImage> update(@PathVariable int id, @Valid @RequestBody RoleplayImageRequest request) {
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
