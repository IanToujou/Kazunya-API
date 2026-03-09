package net.toujoustudios.kazunyaapi.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import net.toujoustudios.kazunyaapi.type.InteractionType;
import net.toujoustudios.kazunyaapi.validation.ValidEnum;

@Data
@Accessors(fluent = true)
public class RoleplayMessageRequest {

    @NotBlank(message = "Message cannot be empty.")
    String message;

    @NotBlank(message = "Type cannot be empty.")
    @ValidEnum(enumClass = InteractionType.class)
    String type;

}
