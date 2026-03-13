package net.toujoustudios.kazunyaapi.filter;

import net.toujoustudios.kazunyaapi.model.RoleplayImage;
import net.toujoustudios.kazunyaapi.model.RoleplayInteraction;
import net.toujoustudios.kazunyaapi.model.RoleplayMessage;
import net.toujoustudios.kazunyaapi.type.InteractionGender;
import net.toujoustudios.kazunyaapi.type.InteractionType;

import java.util.List;

public class RoleplayInteractionFilter {

    public static List<RoleplayInteraction> filterContent(List<RoleplayInteraction> interactions, InteractionType type, String genders) {
        List<InteractionGender> genderList = parseGenders(genders);
        for (RoleplayInteraction interaction : interactions) {
            List<RoleplayImage> filteredImages = interaction.getImages().stream()
                    .filter(img -> img.getType() == type)
                    .filter(img -> genderList.contains(InteractionGender.ANY) ||
                            img.getGenders().contains(InteractionGender.ANY) ||
                            img.getGenders().stream().anyMatch(genderList::contains))
                    .toList();
            interaction.setImages(filteredImages);
            List<RoleplayMessage> filteredMessages = interaction.getMessages().stream()
                    .filter(msg -> msg.getType() == type)
                    .toList();
            interaction.setMessages(filteredMessages);
        }
        return interactions;
    }

    private static List<InteractionGender> parseGenders(String gender) {

        if (gender == null || gender.isBlank() || gender.equalsIgnoreCase("any"))
            return List.of(InteractionGender.ANY);

        String[] parts = gender.split("[+,]");
        List<InteractionGender> genders = new java.util.ArrayList<>();
        for (String part : parts) {
            part = part.trim();
            if (!part.isEmpty())
                genders.add(InteractionGender.valueOf(part.toUpperCase()));
        }

        return genders.isEmpty() ? List.of(InteractionGender.ANY) : genders;

    }

}
