package com.bnotions.airspacecontrol.api;

public interface RequestCallback<T> {

	public abstract void onComplete(RequestResponse<T> response);
	public abstract void onFailure(RequestResponse<T> error);

}
