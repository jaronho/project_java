picasso是Square公司开源的一个Android图形缓存库,地址http://square.github.io/picasso/
/*************************************************
 * 可以实现图片下载和缓存功能。仅仅只需要一行代码就能完全实现图片的异步加载
 */
	Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
/*************************************************
 * Picasso不仅实现了图片异步加载的功能，还解决了android中加载图片时需要解决的一些常见问题：
 * 1.在adapter中需要取消已经不在视野范围的ImageView图片资源的加载，否则会导致图片错位，Picasso已经解决了这个问题。
 * 2.使用复杂的图片压缩转换来尽可能的减少内存消耗
 * 3.自带内存和硬盘二级缓存功能
 * ADAPTER 中的下载：Adapter的重用会被自动检测到，Picasso会取消上次的加载
 */
	@Override public void getView(int position, View convertView, ViewGroup parent) {
		SquaredImageView view = (SquaredImageView) convertView;
		if (view == null) {
			view = new SquaredImageView(context);
		}
		String url = getItem(position);
		Picasso.with(context).load(url).into(view);
	}
/*************************************************
 * 图片转换：转换图片以适应布局大小并减少内存占用
 */
	Picasso.with(context)
		.load(url)
		.resize(50, 50)
		.centerCrop()
		.into(imageView);

/*************************************************
 * 你还可以自定义转换：
 */
	public class CropSquareTransformation implements Transformation {
	@Override public Bitmap transform(Bitmap source) {
		int size = Math.min(source.getWidth(), source.getHeight());
		int x = (source.getWidth() - size) / 2;
		int y = (source.getHeight() - size) / 2;
		Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
		if (result != source) {
			source.recycle();
		}
		return result;
	}
	@Override public String key() { return "square()"; }
	}
将CropSquareTransformation 的对象传递给transform 方法即可
/*************************************************
 * Place holders-空白或者错误占位图片：picasso提供了两种占位图片，未加载完成或者加载发生错误的时需要一张图片作为提示。
 */
	Picasso.with(context)
		.load(url)
		.placeholder(R.drawable.user_placeholder)
		.error(R.drawable.user_placeholder_error)
		.into(imageView);
/*************************************************/
