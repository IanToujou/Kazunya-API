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
 * @see RoleplayImage
 * @since 1.0.0
 * @author Toujou Studios
 */
@Data
@Entity
@Table(name = "roleplay_interaction")
public class RoleplayInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "roleplayInteraction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoleplayImage> images;

    @OneToMany(mappedBy = "roleplayInteraction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoleplayMessage> messages;

}
