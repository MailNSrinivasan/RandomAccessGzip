package com.java.util.zip.ra;

public class BestIndex {

	long bestLastIndex;
	long bestLastIndexCommentOffset;
	
	public BestIndex(long bestLastIndex, long bestLastIndexCommentOffset) {
		this.bestLastIndex = bestLastIndex;
		this.bestLastIndexCommentOffset = bestLastIndexCommentOffset;
	}
	
	
	public long getBestLastIndex() {
		return bestLastIndex;
	}

	public void setBestLastIndex(long bestLastIndex) {
		this.bestLastIndex = bestLastIndex;
	}

	public long getBestLastIndexCommentOffset() {
		return bestLastIndexCommentOffset;
	}

	public void setBestLastIndexCommentOffset(long bestLastIndexCommentOffset) {
		this.bestLastIndexCommentOffset = bestLastIndexCommentOffset;
	}


	
}