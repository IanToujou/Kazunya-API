package net.toujoustudios.kazunyaapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Defines a roleplay interaction, like hug, kiss, etc.
 * <p>
 * This model is used primarily to fetch and filter images by
 * interaction type, gender appearance, and more, depending
 * on the user.
 *
 * @see RoleplayImage
 * @since 1.0.0
 * @author Toujou Studios
 */
@Data
@Entity
@Accessors(fluent = true)
@Table(name = "roleplay_interaction")
public class RoleplayInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "roleplay_interaction_id")
    private List<RoleplayImage> images;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "roleplay_message_id")
    private List<RoleplayMessage> messages;

}
