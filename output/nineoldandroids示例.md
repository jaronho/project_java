http://nineoldandroids.com/

Usage

The API is exactly the same as the Honeycomb API, just change your imports to use com.nineoldandroids.animation.*.

For example, to animate a View vertically off of the screen you can use ObjectAnimator

	ObjectAnimator.ofFloat(myObject, "translationY", -myObject.getHeight()).start();

If you're familiar with the animation API already you should notice that this is precisely what would be done with the native api. The only difference, however, is that we've imported com.nineoldandroids.animation.ObjectAnimator at the top of the file instead of android.animation.ObjectAnimator.

Here's another example of a View animating its own background to go from red to blue and then back again forever. You can see this animation in the 'Bouncing Balls' demo in the samples application.

	ValueAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", /*Red*/0xFFFF8080, /*Blue*/0xFF8080FF);
	colorAnim.setDuration(3000);
	colorAnim.setEvaluator(new ArgbEvaluator());
	colorAnim.setRepeatCount(ValueAnimator.INFINITE);
	colorAnim.setRepeatMode(ValueAnimator.REVERSE);
	colorAnim.start();

This library includes compatibility implementations of almost all of the new properties which were introduced with Android 3.0 allowing you to perform very complex animations that work on every API level.

	AnimatorSet set = new AnimatorSet();
	set.playTogether(
		ObjectAnimator.ofFloat(myView, "rotationX", 0, 360),
		ObjectAnimator.ofFloat(myView, "rotationY", 0, 180),
		ObjectAnimator.ofFloat(myView, "rotation", 0, -90),
		ObjectAnimator.ofFloat(myView, "translationX", 0, 90),
		ObjectAnimator.ofFloat(myView, "translationY", 0, 90),
		ObjectAnimator.ofFloat(myView, "scaleX", 1, 1.5f),
		ObjectAnimator.ofFloat(myView, "scaleY", 1, 0.5f),
		ObjectAnimator.ofFloat(myView, "alpha", 1, 0.25f, 1)
	);
	set.setDuration(5 * 1000).start();

You can also use a compatibility version of ViewPropertyAnimator which allows animating views in a much more declarative manner

	Button myButton = (Button)findViewById(R.id.myButton);

	//Note: in order to use the ViewPropertyAnimator like this add the following import:
	//  import static com.nineoldandroids.view.ViewPropertyAnimator.animate;
	animate(myButton).setDuration(2000).rotationYBy(720).x(100).y(100);

There are some caveats to using this API on versions of Android prior to Honeycomb:

The native Property class and its animation was introduced in Android 3.2 and is thus not included in this library. There is, however, a com.nineoldandroids.util.Property class which allows you to create custom properties on your own views for easier animation.
LayoutTransition is not possible to implement prior to Android 3.0 and is not included.