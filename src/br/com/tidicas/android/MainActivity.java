package br.com.tidicas.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * Classe principal da aplicação Android
 * @author Evaldo Junior
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
