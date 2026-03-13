package net.toujoustudios.kazunyaapi.service;

import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.dto.request.RoleplayMessageRequest;
import net.toujoustudios.kazunyaapi.model.RoleplayMessage;
import net.toujoustudios.kazunyaapi.repository.RoleplayMessageRepository;
import net.toujoustudios.kazunyaapi.repository.RoleplayInteractionRepository;
import net.toujoustudios.kazunyaapi.type.InteractionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleplayMessageService {

    private final RoleplayMessageRepository repository;
    private final RoleplayInteractionRepository interactionRepository;

    @Transactional(readOnly = true)
    public List<RoleplayMessage> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<RoleplayMessage> findById(int id) {
        return repository.findById(id);
    }

    public RoleplayMessage create(RoleplayMessageRequest request) {
        return save(request, new RoleplayMessage());
    }

    public RoleplayMessage update(int id, RoleplayMessageRequest request) {
        RoleplayMessage existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Message not found with id: " + id));
        return save(request, existing);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public boolean exists(int id) {
        return repository.existsById(id);
    }

    private RoleplayMessage save(RoleplayMessageRequest request, RoleplayMessage o) {
        o.setMessage(request.getMessage());
        o.setType(InteractionType.valueOf(request.getType()));

        // Set bi-directional reference if interaction ID is provided
        if (request.getInteractionId() != null) {
            interactionRepository.findById(request.getInteractionId())
                    .ifPresent(o::setInteraction);
        }

        return repository.save(o);
    }

}
