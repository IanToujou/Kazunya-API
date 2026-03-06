package net.toujoustudios.kazunyaapi.repository;

import net.toujoustudios.kazunyaapi.model.RoleplayInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleplayInteractionRepository extends JpaRepository<RoleplayInteraction, Integer> {

    List<RoleplayInteraction> findByName(String name);

}
