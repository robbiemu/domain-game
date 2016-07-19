package xyz.personalenrichment.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Tuple<L, R> {
	private L left;
	private R right;
}
