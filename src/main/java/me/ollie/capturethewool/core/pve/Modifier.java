package me.ollie.capturethewool.core.pve;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

public record Modifier(Attribute attribute,
                        AttributeModifier.Operation operation, double amount) {

    public AttributeModifier toModifier() {
        return new AttributeModifier(attribute.getKey().asString(), amount, operation);
    }
}
