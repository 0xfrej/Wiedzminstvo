package dev.sharpwave.wiedzminstvo.entity.capabilities.horseowner;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ConstantConditions")
public class HorseOwnerProvider implements ICapabilitySerializable<CompoundNBT> {

    @CapabilityInject(IHorseOwner.class)
    public static final Capability<IHorseOwner> OWNER_CAPABILITY = null;

    private final IHorseOwner instance;

    {
        assert OWNER_CAPABILITY != null;
        instance = OWNER_CAPABILITY.getDefaultInstance();
    }

    @Override
    public CompoundNBT serializeNBT() {
        assert OWNER_CAPABILITY != null;
        return (CompoundNBT) OWNER_CAPABILITY.getStorage().writeNBT(OWNER_CAPABILITY, instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        assert OWNER_CAPABILITY != null;
        OWNER_CAPABILITY.getStorage().readNBT(OWNER_CAPABILITY, instance, null, nbt);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction Dist)
    {
        return cap == OWNER_CAPABILITY ? (LazyOptional<T>) LazyOptional.of(() -> instance) : LazyOptional.empty();
    }
}