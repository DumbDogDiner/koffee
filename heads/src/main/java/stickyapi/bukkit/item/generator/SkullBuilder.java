/*
 * Copyright (c) 2020-2021 DumbDogDiner <dumbdogdiner.com>. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package stickyapi.bukkit.item.generator;

import com.dumbdogdiner.stickyapi.common.util.StringUtil;
import com.dumbdogdiner.stickyapi.util.textures.TextureHelper;
import com.dumbdogdiner.stickyapi.util.textures.TextureValidator;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

import java.net.URL;
import java.util.UUID;

/**
 * Utility for constructing ItemStacks of {@link Material#PLAYER_HEAD}.
 * <p>
 * You can specify a group and name from the embedded texture file, or simply set a name and a texture,
 * and generate player heads with ease!
 * </p>
 */

@SuppressWarnings("UnusedReturnValue")
public class SkullBuilder {
    @Getter
    @NotNull
    @VisibleForTesting
    private String category = "*";
    @Getter
    @VisibleForTesting
    protected int quantity = 1;
    @Getter
    private String head;


    @SuppressWarnings("DanglingJavadoc") // For lombok
    @Accessors(fluent = true, chain = true)
    @Setter
    @Getter
    /**
     * @param name The displayed name of the head
     * @return The displayed name of the head
     */
    protected String name;
    @Getter
    protected String texture;

    /**
     * Sets the category of the head type (Defaults to *, which is first match)
     *
     * @param category The category to set
     * @throws IllegalArgumentException if the category is not valid
     * @see TextureHelper#getCategories()
     */
    public @NotNull SkullBuilder category(@NotNull String category) {
        Preconditions.checkArgument(TextureHelper.getCategories().contains(category.toUpperCase()),
                "The specified group %s is not a valid category", category);
        this.category = category.toUpperCase();
        return this;
    }

    /**
     * Sets the specific head (from <code>textures.yml</code>)
     *
     * @param head The head to set
     * @throws IllegalArgumentException if the head does not exist
     * @see TextureHelper#getTexture(String, String)
     */
    public @NotNull SkullBuilder head(@NotNull String head) {
        head = head.toUpperCase();
        Preconditions.checkNotNull(TextureHelper.getTexture(category, head),
                "The specified head %s is not a valid head", head);
        this.head = head;
        this.texture = TextureHelper.getTexture(category, head);
        return this;
    }


    /**
     * Sets the quantity of items in the final item stack
     *
     * @param i the requested quantity
     * @throws IllegalArgumentException if the stack size is invalid
     */
    public @NotNull SkullBuilder quantity(int i) {
        Preconditions.checkArgument(i >= 0 && i <= 64,
                "Invalid stack size of %s specified  (must be between 0 and 64, inclusive)", i);
        this.quantity = i;
        return this;
    }


    /**
     * @param textureURL A {@link URL} where a valid PNG minecraft texture can be found
     * @return The current {@link SkullBuilder} instance
     * @throws IllegalArgumentException if the URL is invalid
     */
    public @NotNull SkullBuilder texture(@NotNull URL textureURL) {
        TextureValidator.validateTextureUrl(textureURL.toExternalForm());

        texture(TextureHelper.encodeTextureString(textureURL));
        return this;
    }

    /**
     * Set the texture with a pre-encoded string
     *
     * @param texture Base64 string of the json of texture location
     * @throws IllegalArgumentException if the texture string is invalid
     */
    public @NotNull SkullBuilder texture(@NotNull String texture) {
        TextureValidator.validateTextureString(texture);
        this.texture = texture;
        return this;
    }

    /**
     * Builds the final {@link ItemStack} of {@link Material#PLAYER_HEAD} as long as
     *
     * @return The constructed head
     */
    public @NotNull ItemStack build() {
        Preconditions.checkNotNull(texture);
        Preconditions.checkArgument(name != null || head != null);

        @NotNull SkullMeta meta = (SkullMeta) (new ItemStack(Material.PLAYER_HEAD, 1)).getItemMeta();
        @NotNull PlayerProfile profile = Bukkit.createProfile(new UUID(0, 0), head);

        profile.setName(TextureHelper.toQualifiedName(category, head == null ? name : head));
        if (name != null) {
            meta.setDisplayName(name);
        } else {
            meta.setDisplayName(StringUtil.capitaliseSentence(head));
        }

        profile.setProperty(new ProfileProperty("textures", texture));
        meta.setPlayerProfile(profile);
        @NotNull ItemStack head = new ItemStack(Material.PLAYER_HEAD, quantity);
        head.setItemMeta(meta);
        return head;
    }

    public SkullBuilder qualified(String qualifiedName) {
        String [] qn = qualifiedName.toUpperCase().split("\\.");
        category(qn[0]);
        return head(qn[1]);
    }
}
