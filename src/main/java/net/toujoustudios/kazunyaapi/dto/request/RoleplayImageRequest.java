package net.toujoustudios.kazunyaapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import net.toujoustudios.kazunyaapi.type.InteractionGender;
import net.toujoustudios.kazunyaapi.type.InteractionType;
import net.toujoustudios.kazunyaapi.validation.ValidEnum;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Data
public class RoleplayImageRequest {

    @NotBlank(message = "URL cannot be empty.")
    @URL(message = "URL must be a valid URL.")
    String url;

    @NotBlank(message = "Type cannot be empty.")
    @ValidEnum(enumClass = InteractionType.class)
    String type;

    @NotEmpty(message = "At least one gender is required.")
    List<@ValidEnum(enumClass = InteractionGender.class) String> genders;

}