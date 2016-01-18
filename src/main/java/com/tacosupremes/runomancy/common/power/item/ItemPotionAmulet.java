package com.tacosupremes.runomancy.common.power.item;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.tacosupremes.runomancy.common.item.ItemMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemPotionAmulet extends ItemMod {

	public ItemPotionAmulet() {
		super("potionAmulet",0);
		
	}
	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int itemSlot, boolean isSelected) {
		
		if(itemSlot > 8)
			return;
		
		if(!is.hasTagCompound()){
			is.setTagCompound(new NBTTagCompound());
		}
		
		if(this.getEffects(is) != null){
			
			List<PotionEffect> l = this.getEffects(is);
			
			if(e instanceof EntityPlayer){
				
				EntityPlayer player = (EntityPlayer)e;
				
				
				for(PotionEffect p : l){
					
				player.addPotionEffect(p);
				
				}
				
			}
			
			
		}
		
	}
	@Override
	 public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	    {
	       
	            List<PotionEffect> list = Items.potionitem.getEffects(stack);
	            Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

	            if (list != null && !list.isEmpty())
	            {
	                for (PotionEffect potioneffect : list)
	                {
	                    String s1 = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();
	                    Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
	                    Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();

	                    if (map != null && map.size() > 0)
	                    {
	                        for (Entry<IAttribute, AttributeModifier> entry : map.entrySet())
	                        {
	                            AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
	                            AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
	                            multimap.put(((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1);
	                        }
	                    }

	                    if (potioneffect.getAmplifier() > 0)
	                    {
	                        s1 = s1 + " " + StatCollector.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
	                    }

	                    if (potioneffect.getDuration() > 20)
	                    {
	                        s1 = s1 + " (" + Potion.getDurationString(potioneffect) + ")";
	                    }

	                    if (potion.isBadEffect())
	                    {
	                        tooltip.add(EnumChatFormatting.RED + s1);
	                    }
	                    else
	                    {
	                        tooltip.add(EnumChatFormatting.GRAY + s1);
	                    }
	                }
	            }
	            

	            if (!multimap.isEmpty())
	            {
	                tooltip.add("");
	                tooltip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));

	                for (Entry<String, AttributeModifier> entry1 : multimap.entries())
	                {
	                    AttributeModifier attributemodifier2 = (AttributeModifier)entry1.getValue();
	                    double d0 = attributemodifier2.getAmount();
	                    double d1;

	                    if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2)
	                    {
	                        d1 = attributemodifier2.getAmount();
	                    }
	                    else
	                    {
	                        d1 = attributemodifier2.getAmount() * 100.0D;
	                    }

	                    if (d0 > 0.0D)
	                    {
	                        tooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier2.getOperation(), new Object[] {ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey())}));
	                    }
	                    else if (d0 < 0.0D)
	                    {
	                        d1 = d1 * -1.0D;
	                        tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier2.getOperation(), new Object[] {ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey())}));
	                    }
	                }
	            }
	        
	    }
	
	 public List<PotionEffect> getEffects(ItemStack stack)
	    {
	        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("CustomPotionEffects", 9))
	        {
	            List<PotionEffect> list1 = Lists.<PotionEffect>newArrayList();
	            NBTTagList nbttaglist = stack.getTagCompound().getTagList("CustomPotionEffects", 10);

	            for (int i = 0; i < nbttaglist.tagCount(); ++i)
	            {
	                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
	                PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);

	                if (potioneffect != null)
	                {
	                    list1.add(potioneffect);
	                }
	            }

	            return list1;
	        }
	        
	        return null;
	    }
	
	

}
