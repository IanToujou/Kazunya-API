package net.toujoustudios.kazunyaapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RoleplayInteractionRequest {

    @NotBlank(message = "Name cannot be empty.")
    @Size(min = 2, max = 50, message = "Name must contain between 2 and 50 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Name can only contain letters, numbers, spaces, and hyphens.")
    String name;

    @NotEmpty(message = "At least one image is required.")
    List<Integer> images;

    @NotEmpty(message = "At least one message is required.")
    List<Integer> messages;

}
