package net.toujoustudios.kazunyaapi.repository;

import net.toujoustudios.kazunyaapi.model.RoleplayInteraction;
import net.toujoustudios.kazunyaapi.type.InteractionGender;
import net.toujoustudios.kazunyaapi.type.InteractionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleplayInteractionRepository extends JpaRepository<RoleplayInteraction, Integer> {

    List<RoleplayInteraction> findByName(String name);

    @Query("SELECT DISTINCT ri FROM RoleplayInteraction ri " +
           "JOIN ri.images img " +
           "WHERE ri.name = :name AND img.type = :type")
    List<RoleplayInteraction> findByNameAndType(@Param("name") String name, @Param("type") InteractionType type);

    @Query("SELECT DISTINCT ri FROM RoleplayInteraction ri " +
           "JOIN ri.images img " +
           "WHERE ri.name = :name AND img.type = :type " +
           "AND EXISTS (SELECT 1 FROM RoleplayImage i WHERE i MEMBER OF ri.images " +
           "AND i.type = :type AND (:gender MEMBER OF i.genders OR 'ANY' MEMBER OF i.genders))")
    List<RoleplayInteraction> findByNameAndTypeAndGender(@Param("name") String name,
                                                          @Param("type") InteractionType type,
                                                          @Param("gender") InteractionGender gender);

}
