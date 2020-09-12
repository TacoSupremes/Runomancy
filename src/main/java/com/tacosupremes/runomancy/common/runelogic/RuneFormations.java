package com.tacosupremes.runomancy.common.runelogic;

import java.util.ArrayList;
import java.util.List;

public class RuneFormations {
	
	public static List<IRuneEffect> effects = new ArrayList<IRuneEffect>();
	public static List<String> functionalEffects = new ArrayList<String>();	
	public static List<String> generatingEffects = new ArrayList<String>();
	
	//TODO:Runic Ward Variable SIZE 32 by 32     PUMP/OREDICTIONARYTHING/SPAWNER?
	
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
	

	
	
}
