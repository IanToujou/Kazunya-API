package net.toujoustudios.kazunyaapi.model;

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

}
