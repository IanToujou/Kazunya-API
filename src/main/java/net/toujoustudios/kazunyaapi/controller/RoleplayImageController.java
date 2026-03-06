package net.toujoustudios.kazunyaapi.controller;

import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.model.RoleplayImage;
import net.toujoustudios.kazunyaapi.repository.RoleplayImageRepository;
import net.toujoustudios.kazunyaapi.request.RoleplayImageRequest;
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
    public ResponseEntity<RoleplayImage> add(@RequestBody RoleplayImageRequest request) {
        RoleplayImage saved = save(request, new RoleplayImage());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleplayImage> update(@PathVariable int id, @RequestBody RoleplayImageRequest request) {
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

        if (request.url() == null || request.url().isBlank())
            throw new IllegalArgumentException("URL is required");
        if (request.type() == null || request.type().isBlank())
            throw new IllegalArgumentException("Type is required");

        o.setUrl(request.url());

        try {
            o.setType(InteractionType.valueOf(request.type()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid interaction type");
        }

        try {
            o.setGenders(request.genders().stream()
                    .map(InteractionGender::valueOf)
                    .toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid interaction gender");
        }

        return repository.save(o);
    }

}
