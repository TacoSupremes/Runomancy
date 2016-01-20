package com.tacosupremes.runomancy.gui;

public enum Categories {
	
Home(0), Runes(1), Runomancy(1), Generating(1), Functional(1), RunicItems(1), RunicBlocks(1),
Item(2), Recipe(3)


;

public int level;

private Categories(int i) {
	
	this.level = i;
	
}


public String getName(){
	
	return this.toString().toUpperCase();
}
}