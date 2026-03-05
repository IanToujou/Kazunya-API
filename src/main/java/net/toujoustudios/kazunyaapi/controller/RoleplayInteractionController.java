package net.toujoustudios.kazunyaapi.controller;

import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.model.RoleplayInteraction;
import net.toujoustudios.kazunyaapi.repository.RoleplayInteractionRepository;
import net.toujoustudios.kazunyaapi.request.RoleplayInteractionRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller("/roleplay-interactions")
@RequiredArgsConstructor
public class RoleplayInteractionController {

    private final RoleplayInteractionRepository repository;

    @GetMapping
    public List<RoleplayInteraction> get() {
        return repository.findAll();
    }

    @GetMapping
    public Optional<RoleplayInteraction> get(int id) {
        return repository.findById(id);
    }

    @PostMapping
    public void add(RoleplayInteractionRequest request) {
        save(request, new RoleplayInteraction());
    }

    private RoleplayInteraction save(RoleplayInteractionRequest request, RoleplayInteraction o) {



        o.setName(request.name());
        return repository.save(o);

    }

}
