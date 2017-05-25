Usage

Animate multiple view from one method

	ViewAnimator
		   .animate(image)
				.translationY(-1000, 0)
				.alpha(0,1)
		   .andAnimate(text)
				.dp().translationX(-20, 0)
				.decelerate()
				.duration(2000)
		   .thenAnimate(image)
				.scale(1f, 0.5f, 1f)
				.accelerate()
				.duration(1000)
		   .start();

More

Add same animation on multiples view

	ViewAnimator
		   .animate(image,text)
		   .scale(0,1)
		   .start();
		   
Add listeners

	ViewAnimator
		   .animate(image)
		   .scale(0,1)
		   .onStart(() -> {})
		   .onStop(() -> {})
		   .start();
		   
Use DP values

	ViewAnimator
		   .animate(image)
		   .dp().translationY(-200, 0)
		   .start();
		   
Animate Height / Width

	ViewAnimator
		   .animate(view)
		   .waitForHeight() //wait until a ViewTreeObserver notification
		   .dp().width(100,200)
		   .dp().height(50,100)
		   .start();
	   
Color animations

	ViewAnimator
		   .animate(view)
		   .textColor(Color.BLACK,Color.GREEN)
		   .backgroundColor(Color.WHITE,Color.BLACK)
		   .start();
		   
Rotation animations

	ViewAnimator
		   .animate(view)
		   .rotation(360)
		   .start();
		   
Custom animations

	ViewAnimator
		   .animate(text)
		   .custom(new AnimationListener.Update<TextView>() {
				@Override public void update(TextView view, float value) {
					  view.setText(String.format("%.02f",value));
				}
			}, 0, 1)
		   .start();
		   
Cancel animations

	ViewAnimator viewAnimator = ViewAnimator
		   .animate(view)
		   .rotation(360)
		   .start();

	viewAnimator.cancel();
	
Enhanced animations

	ViewAnimator
			.animate(button)
			.shake().interpolator(new LinearInterpolator());
			.bounceIn().interpolator(new BounceInterpolator());
			.flash().repeatCount(4);
			.flipHorizontal();
			.wave().duration(5000);
			.tada();
			.pulse();
			.standUp();
			.swing();
			.wobble();
			
Path animations ( Read http://blog.csdn.net/tianjian4592/article/details/47067161 )

    final int[] START_POINT = new int[]{ 300, 270 };
    final int[] BOTTOM_POINT = new int[]{ 300, 400 };
    final int[] LEFT_CONTROL_POINT = new int[]{ 450, 200 };
    final int[] RIGHT_CONTROL_POINT = new int[]{ 150, 200 };
    Path path = new Path();
    path.moveTo(START_POINT[0], START_POINT[1]);
    path.quadTo(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], BOTTOM_POINT[0], BOTTOM_POINT[1]);
    path.quadTo(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], START_POINT[0], START_POINT[1]);
    path.close();
    ViewAnimator.animate(view).path(path).repeatCount(2).start();
	
SVG path animations (Read http://www.w3school.com.cn/svg/svg_path.asp)

	<svg width="100%" height="100%">
		<path
			d="M 42.266949,70.444915 C 87.351695,30.995763 104.25847,28.177966 104.25847,28.177966 l 87.3517,36.631356 8.45339,14.088983 L 166.25,104.25847 50.720339,140.88983 c 0,0 -45.0847458,180.33898 -39.449153,194.42797 5.635594,14.08898 67.627119,183.15678 67.627119,183.15678 l 16.90678,81.7161 c 0,0 98.622885,19.72457 115.529665,22.54237 16.90678,2.8178 70.44491,-22.54237 78.8983,-33.81356 8.45339,-11.27118 76.08051,-107.07627 33.81356,-126.80085 -42.26695,-19.72457 -132.43644,-56.35593 -132.43644,-56.35593 0,0 -33.81356,-73.26271 -19.72458,-73.26271 14.08899,0 132.43644,73.26271 138.07204,33.81356 5.63559,-39.44915 19.72457,-169.0678 19.72457,-169.0678 0,0 28.17797,-25.36017 -28.17796,-19.72457 -56.35593,5.63559 -95.80509,11.27118 -95.80509,11.27118 l 42.26695,-87.35169 8.45339,-28.177968";
		/>
	</svg>
	
	final String SVG_PATH = "M 42.266949,70.444915 C 87.351695,30.995763 104.25847,28.177966 104.25847,28.177966 l 87.3517,36.631356 8.45339,14.088983 L 166.25,104.25847 50.720339,140.88983 c 0,0 -45.0847458,180.33898 -39.449153,194.42797 5.635594,14.08898 67.627119,183.15678 67.627119,183.15678 l 16.90678,81.7161 c 0,0 98.622885,19.72457 115.529665,22.54237 16.90678,2.8178 70.44491,-22.54237 78.8983,-33.81356 8.45339,-11.27118 76.08051,-107.07627 33.81356,-126.80085 -42.26695,-19.72457 -132.43644,-56.35593 -132.43644,-56.35593 0,0 -33.81356,-73.26271 -19.72458,-73.26271 14.08899,0 132.43644,73.26271 138.07204,33.81356 5.63559,-39.44915 19.72457,-169.0678 19.72457,-169.0678 0,0 28.17797,-25.36017 -28.17796,-19.72457 -56.35593,5.63559 -95.80509,11.27118 -95.80509,11.27118 l 42.26695,-87.35169 8.45339,-28.177968";
	ViewAnimator.animate(view).svgPath(SVG_PATH).repeatCount(3).start();