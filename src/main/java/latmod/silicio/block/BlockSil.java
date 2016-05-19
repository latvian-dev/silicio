package latmod.silicio.block;

import com.feed_the_beast.ftbl.api.block.BlockLM;
import com.feed_the_beast.ftbl.util.LMMod;
import latmod.silicio.Silicio;
import net.minecraft.block.material.Material;

public abstract class BlockSil extends BlockLM
{
    public BlockSil(Material m)
    {
        super(m);
        setCreativeTab(Silicio.tab);
    }

    @Override
    public LMMod getMod()
    { return Silicio.mod; }
}