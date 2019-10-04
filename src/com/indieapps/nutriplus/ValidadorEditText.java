package com.indieapps.nutriplus;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class ValidadorEditText implements TextWatcher
{
	@Override
	final public void afterTextChanged(Editable text) 
	{
		String txt = text.toString();
		
		if(txt.length() > 0)
		{
			validar(txt);
		}
	}

	@Override
	final public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) 
	{}

	@Override
	final public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) 
	{}
	
	abstract public void validar(String texto);

}
