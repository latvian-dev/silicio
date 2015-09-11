package latmod.silicio.client.render.tile;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

/**
 * CBCable - LatvianModder
 * Created using Tabula 4.1.1
 */
@SideOnly(Side.CLIENT)
public class ModelCBCable extends ModelBase // RenderCBCable
{
	public ModelRenderer center;
	public ModelRenderer cable[];
	public ModelRenderer board[];
	
	public ModelCBCable()
	{
		textureWidth = 64;
		textureHeight = 32;
		center = new ModelRenderer(this, 0, 0);
		center.setRotationPoint(0F, 0F, 0F);
		center.addBox(-2F, -2F, -2F, 4, 4, 4, 0F);
		cable = new ModelRenderer[6];
		board = new ModelRenderer[6];
		
		//float PI = 3.141592653589793F;
		//float PI2 = 1.5707963267948966F;
		float PI = (float)Math.PI;
		float PI2 = (float)(Math.PI * 0.5D);
		
		cable[0] = new ModelRenderer(this, 16, 0);
		cable[0].setRotationPoint(0F, 0F, 0F);
		cable[0].addBox(-2F, 2F, -2F, 4, 6, 4, 0F);
		
		board[0] = new ModelRenderer(this, 0, 10);
		board[0].setRotationPoint(0F, 0F, 0F);
		board[0].addBox(-4F, 6.05F, -4F, 8, 2, 8, 0F);
		
		cable[1] = new ModelRenderer(this, 16, 0);
		cable[1].setRotationPoint(0F, 0F, 0F);
		cable[1].addBox(-2F, 2F, -2F, 4, 6, 4, 0F);
		setRotateAngle(cable[1], PI, 0F, 0F);
		
		board[1] = new ModelRenderer(this, 0, 10);
		board[1].setRotationPoint(0F, 0F, 0F);
		board[1].addBox(-4F, 6.05F, -4F, 8, 2, 8, 0F);
		setRotateAngle(board[1], PI, 0F, 0F);
		
		cable[2] = new ModelRenderer(this, 16, 0);
		cable[2].setRotationPoint(0F, 0F, 0F);
		cable[2].addBox(-2F, 2F, -2F, 4, 6, 4, 0F);
		setRotateAngle(cable[2], PI2, 0F, 0F);
		
		board[2] = new ModelRenderer(this, 0, 10);
		board[2].setRotationPoint(0F, 0F, 0F);
		board[2].addBox(-4F, 6.05F, -4F, 8, 2, 8, 0F);
		setRotateAngle(board[2], PI2, 0F, 0F);
		
		cable[3] = new ModelRenderer(this, 16, 0);
		cable[3].setRotationPoint(0F, 0F, 0F);
		cable[3].addBox(-2F, 2F, -2F, 4, 6, 4, 0F);
		setRotateAngle(cable[3], PI2, PI, 0F);
		
		board[3] = new ModelRenderer(this, 0, 10);
		board[3].setRotationPoint(0F, 0F, 0F);
		board[3].addBox(-4F, 6.05F, -4F, 8, 2, 8, 0F);
		setRotateAngle(board[3], PI2, PI, 0F);
		
		cable[4] = new ModelRenderer(this, 16, 0);
		cable[4].setRotationPoint(0F, 0F, 0F);
		cable[4].addBox(-2F, 2F, -2F, 4, 6, 4, 0F);
		setRotateAngle(cable[4], PI2, -PI2, 0F);
		
		board[4] = new ModelRenderer(this, 0, 10);
		board[4].setRotationPoint(0F, 0F, 0F);
		board[4].addBox(-4F, 6.05F, -4F, 8, 2, 8, 0F);
		setRotateAngle(board[4], PI2, -PI2, 0F);
		
		cable[5] = new ModelRenderer(this, 16, 0);
		cable[5].setRotationPoint(0F, 0F, 0F);
		cable[5].addBox(-2F, 2F, -2F, 4, 6, 4, 0F);
		setRotateAngle(cable[5], PI2, PI2, 0F);
		
		board[5] = new ModelRenderer(this, 0, 10);
		board[5].setRotationPoint(0F, 0F, 0F);
		board[5].addBox(-4F, 6.05F, -4F, 8, 2, 8, 0F);
		setRotateAngle(board[5], PI2, PI2, 0F);
	}
	
	public void render(Entity e, float f, float f1, float f2, float f3, float f4, float f5)
	{ 
	}
	
	public void setRotateAngle(ModelRenderer m, float x, float y, float z)
	{
		m.rotateAngleX = x;
		m.rotateAngleY = y;
		m.rotateAngleZ = z;
	}
}
