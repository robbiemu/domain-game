package xyz.personalenrichment.tx;

import lombok.Data;
import xyz.personalenrichment.Tuple;

@Data
public class Move {
	String player;
	Tuple<Short, Short> primary;
	Tuple<Short, Short> secondary;
}
