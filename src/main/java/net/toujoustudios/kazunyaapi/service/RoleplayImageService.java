package net.toujoustudios.kazunyaapi.service;

import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.dto.request.RoleplayImageRequest;
import net.toujoustudios.kazunyaapi.model.RoleplayImage;
import net.toujoustudios.kazunyaapi.repository.RoleplayImageRepository;
import net.toujoustudios.kazunyaapi.repository.RoleplayInteractionRepository;
import net.toujoustudios.kazunyaapi.type.InteractionGender;
import net.toujoustudios.kazunyaapi.type.InteractionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleplayImageService {

    private final RoleplayImageRepository repository;
    private final RoleplayInteractionRepository interactionRepository;

    @Transactional(readOnly = true)
    public List<RoleplayImage> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<RoleplayImage> findById(int id) {
        return repository.findById(id);
    }

    public RoleplayImage create(RoleplayImageRequest request) {
        return save(request, new RoleplayImage());
    }

    public RoleplayImage update(int id, RoleplayImageRequest request) {
        RoleplayImage existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Image not found with id: " + id));
        return save(request, existing);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public boolean exists(int id) {
        return repository.existsById(id);
    }

    private RoleplayImage save(RoleplayImageRequest request, RoleplayImage o) {
        o.setUrl(request.getUrl());
        o.setType(InteractionType.valueOf(request.getType()));
        o.setGenders(request.getGenders().stream()
                .map(InteractionGender::valueOf)
                .toList());
        if (request.getInteractionId() != null) {
            interactionRepository.findById(request.getInteractionId())
                    .ifPresent(o::setInteraction);
        }
        return repository.save(o);
    }

}
