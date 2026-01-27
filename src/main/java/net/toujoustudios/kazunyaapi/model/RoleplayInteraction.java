package net.toujoustudios.kazunyaapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Defines a roleplay interaction, like hug, kiss, etc.
 * <p>
 * This model is used primarily to fetch and filter images by
 * interaction type, gender appearance, and more, depending
 * on the user.
 *
 * @see net.toujoustudios.kazunyaapi.model.InteractionImage
 * @since 1.0.0
 * @author Toujou Studios
 */
@Data
@Entity
@Table(name = "roleplay_interaction")
public class RoleplayInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "roleplay_interaction_id")
    private List<InteractionImage> images;

}
