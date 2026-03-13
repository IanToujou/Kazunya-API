package net.toujoustudios.kazunyaapi.service;

import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.dto.request.RoleplayInteractionRequest;
import net.toujoustudios.kazunyaapi.filter.RoleplayInteractionFilter;
import net.toujoustudios.kazunyaapi.model.RoleplayImage;
import net.toujoustudios.kazunyaapi.model.RoleplayInteraction;
import net.toujoustudios.kazunyaapi.model.RoleplayMessage;
import net.toujoustudios.kazunyaapi.repository.RoleplayImageRepository;
import net.toujoustudios.kazunyaapi.repository.RoleplayInteractionRepository;
import net.toujoustudios.kazunyaapi.repository.RoleplayMessageRepository;
import net.toujoustudios.kazunyaapi.type.InteractionGender;
import net.toujoustudios.kazunyaapi.type.InteractionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleplayInteractionService {

    private final RoleplayInteractionRepository repository;
    private final RoleplayImageRepository imageRepository;
    private final RoleplayMessageRepository messageRepository;

    @Transactional(readOnly = true)
    public List<RoleplayInteraction> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<RoleplayInteraction> findById(int id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<RoleplayInteraction> findByName(String name) {
        return repository.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<RoleplayInteraction> search(String name, String type, String gender) {
        if (name == null || name.isBlank())
            return repository.findAll();
        if (type == null || type.isBlank())
            return repository.findByName(name);
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
            return RoleplayInteractionFilter.filterContent(results, interactionType, gender);
        }
        List<RoleplayInteraction> results = repository.findByNameAndType(name, interactionType);
        return RoleplayInteractionFilter.filterContent(results, interactionType, null);
    }

    public RoleplayInteraction create(RoleplayInteractionRequest request) {
        return save(request, new RoleplayInteraction());
    }

    public RoleplayInteraction update(int id, RoleplayInteractionRequest request) {
        RoleplayInteraction existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Interaction not found with id: " + id));
        return save(request, existing);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public boolean exists(int id) {
        return repository.existsById(id);
    }

    private RoleplayInteraction save(RoleplayInteractionRequest request, RoleplayInteraction o) {
        List<RoleplayImage> images = imageRepository.findAllById(request.getImages());
        List<RoleplayMessage> messages = messageRepository.findAllById(request.getMessages());
        o.setName(request.getName());
        o.setImages(images);
        o.setMessages(messages);
        images.forEach(image -> image.setInteraction(o));
        messages.forEach(message -> message.setInteraction(o));
        return repository.save(o);
    }

}
