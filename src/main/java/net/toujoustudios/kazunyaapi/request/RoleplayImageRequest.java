package net.toujoustudios.kazunyaapi.request;

import java.util.List;

public record RoleplayImageRequest(String url, String type, List<String> genders) {
}
