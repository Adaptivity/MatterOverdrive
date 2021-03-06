/*
 * This file is part of Matter Overdrive
 * Copyright (c) 2015., Simeon Radivoev, All rights reserved.
 *
 * Matter Overdrive is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Matter Overdrive is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Matter Overdrive.  If not, see <http://www.gnu.org/licenses>.
 */

package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.init.MatterOverdriveItems;
import matteroverdrive.tile.TileEntityInscriber;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Simeon on 11/9/2015.
 */
public class TileEntityRendererInscriber extends TileEntitySpecialRenderer
{
    private IModelCustom model;
    private float nextHeadX,nextHeadY;
    private float lastHeadX,lastHeadY;
    private Random random;
    private EntityItem item;

    public TileEntityRendererInscriber()
    {
        model = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODEL_INSCRIBER));
        random = new Random();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float ticks)
    {
        if (item == null)
        {
            item = new EntityItem(tileEntity.getWorldObj());
            item.setEntityItemStack(new ItemStack(MatterOverdriveItems.isolinear_circuit,1,2));
        }

        if (tileEntity instanceof TileEntityInscriber)
        {
            double headX = 0.15*((TileEntityInscriber) tileEntity).geatHeadX()+0.02;
            double headY = 0.1*((TileEntityInscriber) tileEntity).geatHeadY();

            //MatterOverdrive.log.info("Time: " + time);
            glColor3f(1,1,1);
            glPushMatrix();
            bindTexture(new ResourceLocation(Reference.PATH_BLOCKS + "inscriber.png"));
            glTranslated(x + 0.5, y, z + 0.5);
            RenderUtils.rotateFromBlock(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
            glPushMatrix();
            glTranslated(0, 0.6, headX);
            model.renderPart("rail");
            glPopMatrix();

            glPushMatrix();
            glTranslated(headY, 0.84, headX - 0.06);
            model.renderPart("head");
            glPopMatrix();

            ItemStack newStack = ((TileEntityInscriber) tileEntity).getStackInSlot(TileEntityInscriber.MAIN_INPUT_SLOT_ID);
            if (newStack == null)
            {
                newStack = ((TileEntityInscriber) tileEntity).getStackInSlot(TileEntityInscriber.OUTPUT_SLOT_ID);
            }
            if (newStack != null) {
                item.setEntityItemStack(newStack);
                glPushMatrix();
                glTranslated(-0.23, 0.69, 0);
                glRotated(90, 0, 1, 0);
                glRotated(90, 1, 0, 0);
                item.hoverStart = 0f;
                RenderManager.instance.func_147939_a(item, 0, 0, 0, 0, 0, true);
                glPopMatrix();
            }
            glPopMatrix();
        }
    }
}
