package ckathode.weaponmod;

import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.function.IntFunction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ReloadHelper {

    private static void initTagCompound(ItemStack itemstack) {
        if (!itemstack.has(ReloadState.TYPE)) {
            itemstack.set(ReloadState.TYPE, ReloadState.STATE_NONE);
        }
    }

    @NotNull
    public static ReloadState getReloadState(ItemStack itemstack) {
        try {
            if (itemstack.has(ReloadState.TYPE)) {
                return Objects.requireNonNull(itemstack.get(ReloadState.TYPE));
            }
        } catch (Throwable ignored) {
        }
        return ReloadState.STATE_NONE;
    }

    public static void setReloadState(ItemStack itemstack, ReloadState state) {
        initTagCompound(itemstack);
        itemstack.set(ReloadState.TYPE, state);
    }

    public enum ReloadState implements StringRepresentable {
        STATE_NONE(0, "none"),
        STATE_RELOADED(1, "reloaded"),
        STATE_READY(2, "ready");

        private static final IntFunction<ReloadState> BY_ID = ByIdMap.continuous(ReloadState::getId,
                ReloadState.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StringRepresentable.StringRepresentableCodec<ReloadState> CODEC =
                StringRepresentable.fromEnum(ReloadState::values);
        public static final StreamCodec<ByteBuf, ReloadState> STREAM_CODEC =
                ByteBufCodecs.idMapper(BY_ID, ReloadState::getId);

        public static final String TYPE_ID = "reload";
        public static final DataComponentType<ReloadState> TYPE = DataComponentType.<ReloadState>builder()
                .persistent(CODEC).networkSynchronized(STREAM_CODEC).build();

        private final int id;
        private final String name;

        ReloadState(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public boolean isReloaded() {
            return this == STATE_RELOADED || this == STATE_READY;
        }

        public static ReloadState byId(int colorId) {
            return BY_ID.apply(colorId);
        }

        public int getId() {
            return id;
        }

        @NotNull
        @Override
        public String getSerializedName() {
            return name;
        }
    }

}
