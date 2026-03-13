package net.toujoustudios.kazunyaapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.filter.RoleplayInteractionFilter;
import net.toujoustudios.kazunyaapi.model.RoleplayInteraction;
import net.toujoustudios.kazunyaapi.model.RoleplayImage;
import net.toujoustudios.kazunyaapi.model.RoleplayMessage;
import net.toujoustudios.kazunyaapi.repository.RoleplayImageRepository;
import net.toujoustudios.kazunyaapi.repository.RoleplayInteractionRepository;
import net.toujoustudios.kazunyaapi.repository.RoleplayMessageRepository;
import net.toujoustudios.kazunyaapi.request.RoleplayInteractionRequest;
import net.toujoustudios.kazunyaapi.type.InteractionGender;
import net.toujoustudios.kazunyaapi.type.InteractionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roleplay-interactions")
@RequiredArgsConstructor
public class RoleplayInteractionController {

    private final RoleplayInteractionRepository repository;
    private final RoleplayImageRepository imageRepository;
    private final RoleplayMessageRepository messageRepository;

    @GetMapping
    public ResponseEntity<List<RoleplayInteraction>> get(@RequestParam(required = false) String name, @RequestParam(required = false) String type, @RequestParam(required = false) String gender) {

        if (name == null || name.isBlank())
            return ResponseEntity.ok(repository.findAll());

        if (type != null && !type.isBlank()) {

            InteractionType interactionType = InteractionType.valueOf(type.toUpperCase());
            if (gender != null && !gender.isBlank()) {

                String[] genderParts = gender.split("[+,]");
                List<RoleplayInteraction> results = new java.util.ArrayList<>();

                for (String genderStr : genderParts) {
                    genderStr = genderStr.trim();
                    if (!genderStr.isEmpty()) {
                        InteractionGender interactionGender = InteractionGender.valueOf(genderStr.toUpperCase());
                        List<RoleplayInteraction> filtered = repository.findByNameAndTypeAndGender(name, interactionType, interactionGender);
                        for (RoleplayInteraction interaction : filtered) {
                            if (!results.contains(interaction)) {
                                results.add(interaction);
                            }
                        }
                    }
                }

                return ResponseEntity.ok(RoleplayInteractionFilter.filterContent(results, interactionType, gender));
            }

            List<RoleplayInteraction> results = repository.findByNameAndType(name, interactionType);
            return ResponseEntity.ok(RoleplayInteractionFilter.filterContent(results, interactionType, null));

        }

        return ResponseEntity.ok(repository.findByName(name));

    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleplayInteraction> get(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleplayInteraction> add(@Valid @RequestBody RoleplayInteractionRequest request) {
        RoleplayInteraction saved = save(request, new RoleplayInteraction());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleplayInteraction> update(@PathVariable int id, @Valid @RequestBody RoleplayInteractionRequest request) {
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
        List<RoleplayImage> images = imageRepository.findAllById(request.getImages());
        List<RoleplayMessage> messages = messageRepository.findAllById(request.getMessages());
        o.setName(request.getName());
        o.setImages(images);
        o.setMessages(messages);
        return repository.save(o);
    }

}
