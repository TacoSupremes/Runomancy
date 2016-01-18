package com.tacosupremes.runomancy.common.runelogic;

import java.util.ArrayList;
import java.util.List;

public class RuneFormations {
	
	public static List<IRuneEffect> effects = new ArrayList<IRuneEffect>();
		
	//TODO:PUMP/OREDICTIONARYTHING/SPAWNER?
	
	public static void addEffect(IRuneEffect i){
		effects.add(i);
	}
	
	public static int getRange(IRuneEffect re){
		
		return Math.round((float)((float)Math.sqrt(re.getNeededBlocks().length)/2F))-1;
	}
}
