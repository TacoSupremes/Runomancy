package com.tacosupremes.runomancy.common.runelogic;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.gui.EffectPage;
import com.tacosupremes.runomancy.gui.FormationPage;
import com.tacosupremes.runomancy.gui.Pages;

public class RuneFormations {
	
	public static List<IRuneEffect> effects = new ArrayList<IRuneEffect>();
	public static List<String> functionalEffects = new ArrayList<String>();	
	public static List<String> generatingEffects = new ArrayList<String>();
	
	//TODO:PUMP/OREDICTIONARYTHING/SPAWNER?
	
	public static void addEffect(IRuneEffect i){
		effects.add(i);
		if(i instanceof IFunctionalRuneEffect)
			functionalEffects.add(i.getName());
		else
			generatingEffects.add(i.getName());
	}
	
	public static int getRange(IRuneEffect re){
		
		if(re == null)
			return 0;
		return Math.round((float)((float)Math.sqrt(re.getNeededBlocks().length) / 2F)) - 1;
	}
	
	
	public static void makePages(){
		
		for(IRuneEffect i : effects){
			Pages.addPage(i.getName(), new EffectPage(i));	
			Pages.addPage(i.getName()+".formation", new FormationPage(i));			
		}
		
	
		
		
		
	}
	
	
}
