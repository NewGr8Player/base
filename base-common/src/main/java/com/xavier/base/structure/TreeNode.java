package com.xavier.common.structure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TreeNode<T> implements Serializable {

	private T current;

	private List<T> child = new ArrayList<>();

	public TreeNode(T current) {
		this.current = current;
	}
}
