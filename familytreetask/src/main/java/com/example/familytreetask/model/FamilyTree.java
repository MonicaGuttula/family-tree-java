package com.example.familytreetask.model;

import java.util.List;

public class FamilyTree {
    private String description;
    private Node root;

    // Getters and setters
    

    public static class Node {
        private String name;
        private List<Node> children;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Node> getChildren() {
			return children;
		}
		public void setChildren(List<Node> children) {
			this.children = children;
		}

        
        // Getters and setters
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
}
