package net.toujoustudios.kazunyaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import net.toujoustudios.kazunyaapi.type.InteractionGender;
import net.toujoustudios.kazunyaapi.type.InteractionType;

import java.util.List;

/**
 * Defines an image used for roleplay interactions.
 * The image data is never stored locally, but rather as an
 * external link, usually a GIF website or repository.
 *
 * @since 1.0.0
 * @author Toujou Studios
 */
@Data
@Entity
@Table(name = "roleplay_interaction_image")
public class RoleplayImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;

    @Enumerated(EnumType.STRING)
    private InteractionType type;

    @Enumerated(EnumType.STRING)
    private List<InteractionGender> genders;

    @ManyToOne
    @JoinColumn(name = "roleplay_interaction_id")
    @JsonIgnore
    private RoleplayInteraction interaction;

}
