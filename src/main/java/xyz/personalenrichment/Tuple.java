package xyz.personalenrichment;

import lombok.Data;

@Data
public class Tuple<L, R> {
	L left;
	R right;
	
	public Tuple() {}
	public Tuple(L l, R r){
		left = l;
		right = r;
	}
}
