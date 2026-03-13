package net.toujoustudios.kazunyaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import net.toujoustudios.kazunyaapi.type.InteractionType;

@Data
@Entity
@Table(name = "roleplay_message")
public class RoleplayMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;

    @Enumerated(EnumType.STRING)
    private InteractionType type;

    @ManyToOne
    @JoinColumn(name = "roleplay_interaction_id")
    @JsonIgnore
    private RoleplayInteraction interaction;

}
