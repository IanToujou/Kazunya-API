package net.toujoustudios.kazunyaapi.model;

import jakarta.persistence.*;
import lombok.Data;
import net.toujoustudios.kazunyaapi.type.InteractionGender;
import net.toujoustudios.kazunyaapi.type.InteractionType;

import java.util.List;

/**
 * Defines an image that is used for roleplay interactions.
 * The image data is never stored locally, but rather as an
 * external link, usually a GIF website or repository.
 *
 * @since 1.0.0
 * @author Toujou Studios
 */
@Data
@Entity
@Table(name = "interaction_image")
public class InteractionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private InteractionType type;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<InteractionGender> genders;

}
